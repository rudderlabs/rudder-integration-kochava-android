package com.rudderstack.android.integrations.kochava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

import com.kochava.base.Tracker;
import com.kochava.base.Tracker.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KochavaIntegrationFactory extends RudderIntegration<Void> {
    private static final String KOCHAVA_KEY = "Kochava";
    private static Map<String, Object> eventsMapping = new HashMap<String, Object>(){
        {
            put("product added", Tracker.EVENT_TYPE_ADD_TO_CART);
            put("add to wishlist", Tracker.EVENT_TYPE_ADD_TO_WISH_LIST);
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
                                if (eventProperties.containsKey("products")) {
                                    String name = getProductProperties((JSONArray) eventProperties.get("products"),"name");
                                    if (name != null && name.length() != 0) {
                                        event.setName(name);
                                    }

                                    String product_id = getProductProperties((JSONArray) eventProperties.get("products"),"product_id");
                                    if (product_id != null && product_id.length() != 0) {
                                        event.setContentId(product_id);
                                    }
                                    else {
                                        String productId = getProductProperties((JSONArray) eventProperties.get("products"),"productId");
                                        if (productId != null && productId.length() != 0) {
                                            event.setContentId(productId);
                                        }
                                    }
                                }
                                if (eventProperties.containsKey("revenue")) {
                                    event.setPrice(getRevenue(eventProperties.get("revenue")));
                                    eventProperties.remove("revenue");
                                }
                                if (eventProperties.containsKey("currency")) {
                                    event.setCurrency((String) eventProperties.get("currency"));
                                    eventProperties.remove("currency");
                                }
                            }

                            if (eventName.equals("checkout started")) {
                                if (eventProperties.containsKey("products")) {
                                    String name = getProductProperties((JSONArray) eventProperties.get("products"),"name");
                                    if (name != null && name.length() != 0) {
                                        event.setName(name);
                                    }

                                    String product_id = getProductProperties((JSONArray) eventProperties.get("products"),"product_id");
                                    if (product_id != null && product_id.length() != 0) {
                                        event.setContentId(product_id);
                                    }
                                    else {
                                        String productId = getProductProperties((JSONArray) eventProperties.get("products"),"productId");
                                        if (productId != null && productId.length() != 0) {
                                            event.setContentId(productId);
                                        }
                                    }
                                }
                                if (eventProperties.containsKey("currency")) {
                                    event.setCurrency((String) eventProperties.get("currency"));
                                    eventProperties.remove("currency");
                                }
                            }

                            if (eventName.equals("add to wishlist")) {
                                if (eventProperties.containsKey("name")) {
                                    event.setName((String) eventProperties.get("name"));
                                    eventProperties.remove("name");
                                }
                                if (eventProperties.containsKey("product_id")) {
                                    if( eventProperties.get("product_id") instanceof Integer) {
                                        event.setContentId(Integer.toString((Integer) eventProperties.get("product_id")));
                                    }
                                    else {
                                        event.setContentId((String) eventProperties.get("product_id"));
                                    }
                                    eventProperties.remove("product_id");
                                }
                            }

                            if (eventName.equals("product added")) {
                                if (eventProperties.containsKey("name")) {
                                    event.setName((String) eventProperties.get("name"));
                                    eventProperties.remove("name");
                                }
                                if (eventProperties.containsKey("product_id")) {
                                    if( eventProperties.get("product_id") instanceof Integer) {
                                        event.setContentId(Integer.toString((Integer) eventProperties.get("product_id")));
                                    }
                                    else {
                                        event.setContentId((String) eventProperties.get("product_id"));
                                    }
                                    eventProperties.remove("product_id");
                                }
                                if (eventProperties.containsKey("quantity")) {
                                    if( eventProperties.get("quantity") instanceof String) {
                                        event.setQuantity(Double.parseDouble((String) eventProperties.get("quantity")));
                                    }
                                    else {
                                        event.setQuantity(getQuantity(eventProperties.get("quantity")));
                                    }
                                    eventProperties.remove("quantity");
                                }
                            }

                            if (eventName.equals("product reviewed")) {
                                if (eventProperties.containsKey("rating")) {
                                    event.setRatingValue(Double.parseDouble ((String)eventProperties.get("rating")));
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

                    // Getting the Custom events
                    if (eventProperties != null && eventProperties.size() != 0) {
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

    private double getRevenue(Object val) {
        if (val != null) {
            String str = String.valueOf(val);
            return Double.parseDouble(str);
        }
        return 0;
    }

    private double getQuantity(Object val) {
        if (val != null) {
            String str = String.valueOf(val);
            return Double.parseDouble(str);
        }
        return 0;
    }

    // If event.properties contains key of Products then below method is called.
    private String getProductProperties(JSONArray products, String property) throws JSONException {
        ArrayList<String> productProperties = new ArrayList<>();
        for(int i = 0; i < products.length(); i++) {
            JSONObject product = (JSONObject) products.get(i);
            if(product.has(property)){
                if(product.get(property) instanceof Integer) {
                    productProperties.add( Integer.toString((Integer) product.get(property)));
                }
                else {
                    productProperties.add((String) product.get(property));
                }
            }
        }

        if( productProperties == null || productProperties.size() == 0)
            return null;

        return productProperties.toString();
    }

    public static void registeredForPushNotificationsWithFCMToken(String token){
        Tracker.addPushToken(token);
    }


}