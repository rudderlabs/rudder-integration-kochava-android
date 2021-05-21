package com.rudderstack.android.integrations.kochava;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.kochava.base.Tracker;
import com.kochava.base.Tracker.Configuration;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.TimeZone;


public class KochavaIntegrationFactory extends RudderIntegration<Void> {
    private static final String KOCHAVA_KEY = "Kochava";
    private static final Map<String, Object> eventsMapping = new HashMap<String, Object>(){
        {
            put("product added", Tracker.EVENT_TYPE_ADD_TO_CART);
            put("product added to wishlist", Tracker.EVENT_TYPE_ADD_TO_WISH_LIST);
            put("checkout started", Tracker.EVENT_TYPE_CHECKOUT_START);
            put("order completed", Tracker.EVENT_TYPE_PURCHASE);
            put("product reviewed", Tracker.EVENT_TYPE_RATING);
            put("products searched", Tracker.EVENT_TYPE_SEARCH);
        }
    };

    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new KochavaIntegrationFactory(settings, rudderConfig);
        }

        @Override
        public String key() {
            return KOCHAVA_KEY;
        }
    };

    private KochavaIntegrationFactory(@NonNull Object config, RudderConfig rudderConfig) {
        if (RudderClient.getApplication() == null) {
            RudderLogger.logError("Application is null. Aborting Kochava initialization.");
            return;
        }

        Gson gson = new Gson();
        KochavaDestinationConfig destinationConfig = gson.fromJson(
                gson.toJson(config),
                KochavaDestinationConfig.class
        );


        if (TextUtils.isEmpty(destinationConfig.apiKey)) {
            RudderLogger.logError("Invalid Kochava Account Credentials, Aborting");
            return;
        }

        // Start the Kochava Tracker
        Tracker.configure(new Configuration(RudderClient.getApplication())
                .setAppGuid(destinationConfig.apiKey)
                .setLogLevel(rudderConfig.getLogLevel())
        );
        RudderLogger.logInfo("Initialized Kochava SDK");
    }

    private void processRudderEvent(RudderMessage element) throws JSONException {
        String type = element.getType();
        if (type != null) {
            switch (type) {

                case MessageType.TRACK:

                    String eventName = element.getEventName();
                    if(eventName == null)
                        return;

                    Map<String, Object> eventProperties = element.getProperties();
                    Tracker.Event event = null;

                    //Standard Events
                    if(eventsMapping.containsKey(eventName.toLowerCase())) {

                        eventName = eventName.toLowerCase();
                        event = new Tracker.Event((Integer) eventsMapping.get(eventName));

                        if (eventProperties != null && eventProperties.size() != 0) {

                            if (eventName.equals("order completed")) {
                                if(eventProperties.containsKey("products")){
                                    setProductsProperties(getJSONArray(eventProperties.get("products")), event);
                                }
                                if (eventProperties.containsKey("revenue")) {
                                    event.setPrice(getDouble(eventProperties.get("revenue")));
                                    eventProperties.remove("revenue");
                                }
                                if (eventProperties.containsKey("currency")) {
                                    event.setCurrency((String) eventProperties.get("currency"));
                                    eventProperties.remove("currency");
                                }
                            }

                            if (eventName.equals("checkout started")) {
                                if(eventProperties.containsKey("products")){
                                    setProductsProperties(getJSONArray(eventProperties.get("products")), event);
                                }
                                if (eventProperties.containsKey("currency")) {
                                    event.setCurrency((String) eventProperties.get("currency"));
                                    eventProperties.remove("currency");
                                }
                            }

                            if (eventName.equals("product added to wishlist")) {
                                eventProperties = setProductProperties(eventProperties, event);
                            }

                            if (eventName.equals("product added")) {
                                eventProperties = setProductProperties(eventProperties, event);
                                if (eventProperties.containsKey("quantity")) {
                                    event.setQuantity(getDouble(eventProperties.get("quantity")));
                                    eventProperties.remove("quantity");
                                }
                            }

                            if (eventName.equals("product reviewed")) {
                                if (eventProperties.containsKey("rating")) {
                                    event.setRatingValue(getDouble(eventProperties.get("rating")));
                                    eventProperties.remove("rating");
                                }
                            }

                            if (eventName.equals("products searched")) {
                                if (eventProperties.containsKey("query")) {
                                    event.setUri((String) eventProperties.get("query"));
                                    eventProperties.remove("query");
                                }
                            }
                        }
                    }

                    // Custom Events
                    else {
                        event = new Tracker.Event(eventName);
                    }

                    // Getting the Custom Properties
                    if (eventProperties != null && eventProperties.size() != 0) {
                        eventProperties = dateToISOString(eventProperties);
                        event.addCustom(new JSONObject(eventProperties));
                    }
                    Tracker.sendEvent(event);
                    break;

                case MessageType.SCREEN:
                    String screenName = element.getEventName();
                    if(screenName == null)
                        return;
                    if(element.getProperties() != null && element.getProperties().size() != 0) {
                        Tracker.sendEvent("screen view " + screenName, new Gson().toJson(element.getProperties()));
                        return;
                    }
                    Tracker.sendEvent(new Tracker.Event("screen view " + screenName));
                    break;

                default:
                    RudderLogger.logWarn("MessageType is not specified or supported");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        RudderLogger.logWarn("Kochava does not support the reset");
    }

    @Override
    public void dump(@Nullable RudderMessage element) {
        try {
            if (element != null) {
                processRudderEvent(element);
            }
        } catch (Exception e) {
            RudderLogger.logError(e);
        }
    }

    // To get double type variable
    private double getDouble(Object val) {
        if (val instanceof String) {
            return Double.parseDouble((String) val);
        }
        else if (val instanceof Integer) {
           return Double.valueOf((Integer) val);
        }
        return (Double) val;
    }

    // To get String type variable
    private String getString(Object val) {
        if(val instanceof Integer) {
            return Integer.toString((Integer) val);
        }
        else if(val instanceof Double) {
            return Double.toString((Double) val);
        }
        return (String) val;
    }

    @NonNull
    private JSONArray getJSONArray(@Nullable Object object) {
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        if(object instanceof List){
            ArrayList<Object> arrayList = new ArrayList<>();
            for(Object element: (List) object ) {
                arrayList.add(element);
            }
            return new JSONArray(arrayList);
        }
        return new JSONArray((ArrayList) object);
    }

    // If eventProperties contains key of Products
    private void setProductsProperties(JSONArray products, Tracker.Event event) throws JSONException {
        ArrayList<String> productName = new ArrayList<>();
        ArrayList<String> product_id = new ArrayList<>();
        for(int i = 0; i < products.length(); i++) {
            JSONObject product = (JSONObject) products.get(i);
            if (product.has("name")) {
                productName.add((String) product.get(("name")));
            }
            if (product.has("product_id")) {
                product_id.add(getString(product.get("product_id")));
                continue;
            }
            if (product.has("productId")) {
                product_id.add(getString(product.get("productId")));
            }
        }
        if (productName.size() != 0) {
            event.setName(productName.toString());
        }
        if (product_id.size() != 0) {
            event.setContentId(product_id.toString());
        }
    }

    // If eventProperties contains key of 'name', 'product_id' or 'productId'
    private Map<String, Object> setProductProperties (Map<String, Object> eventProperties, Tracker.Event event){
        if (eventProperties.containsKey("name")) {
            event.setName((String) eventProperties.get("name"));
            eventProperties.remove("name");
        }

        if (eventProperties.containsKey("product_id")) {
            event.setContentId(getString(eventProperties.get("product_id")));
            eventProperties.remove("product_id");
        }
        else if (eventProperties.containsKey("productId")) {
            event.setContentId(getString(eventProperties.get("productId")));
            eventProperties.remove("productId");
        }
        return eventProperties;
    }

    // Converting the Date into ISO format
    private Map<String, Object> dateToISOString(Map<String, Object> eventProperties) {
        for (Map.Entry entry : eventProperties.entrySet()) {
            if (entry.getValue() instanceof Date) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                eventProperties.put((String) entry.getKey(), formatter.format(entry.getValue()));
            }
        }
        return eventProperties;
    }

    public static void registeredForPushNotificationsWithFCMToken(String token){
        Tracker.addPushToken(token);
        System.out.println("Token is pushed: " + token);
    }
}