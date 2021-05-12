package com.rudderstack.android.integrations.kochava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

import com.kochava.base.Tracker;
import com.kochava.base.Tracker.Configuration;

import org.json.JSONObject;



public class KochavaIntegrationFactory extends RudderIntegration<RudderClient> {
    private static final String KOCHAVA_KEY = "Kochava";
    
    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new KochavaIntegrationFactory(settings);
        }

        @Override
        public String key() {
            return KOCHAVA_KEY;
        }
    };

    private KochavaIntegrationFactory(@NonNull Object config) {
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

        );

    }

    private void processRudderEvent(RudderMessage element) {
        String type = element.getType();
        if (type != null) {
            switch (type) {

                case MessageType.TRACK:
                    String evName = (element.getEventName()).toLowerCase();

                    if (evName != null) {

                        JSONObject data = null;
                        if(element.getProperties() != null)
                        data = new JSONObject(element.getProperties());

                        //Standard event
                        if (evName.equals("product added")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_ADD_TO_CART)
                                    .addCustom(data)
                            );
                            return;
                        }
                        if (evName.equals("add to wishlist")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_ADD_TO_WISH_LIST)
                                    .addCustom(data)
                            );
                            return;
                        }
                        if (evName.equals("checkout started")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_CHECKOUT_START)
                                    .addCustom(data)
                            );
                            return;
                        }
                        if (evName.equals("order completed")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_PURCHASE)
                                    .addCustom(data)
                            );
                            return;
                        }
                        if (evName.equals("product reviewed")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_RATING)
                                    .addCustom(data)
                            );
                            return;
                        }
                        if (evName.equals("products searched")) {
                            Tracker.sendEvent(new Tracker.Event(Tracker.EVENT_TYPE_SEARCH)
                                    .addCustom(data)
                            );
                            return;
                        }

                        //Custom Event
                        Tracker.sendEvent(evName, new Gson().toJson(element.getProperties()));
                    }
                    break;
                case MessageType.SCREEN:
                    String screenName = (element.getEventName()).toLowerCase();
                    if (screenName != null) {
                        Tracker.sendEvent(screenName, new Gson().toJson(element.getProperties()));
                    }
                    break;
                default:
                    RudderLogger.logWarn("MessageType is not specified or supported");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        
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

    @Override
    public RudderClient getUnderlyingInstance() {
        return null;
    }
}