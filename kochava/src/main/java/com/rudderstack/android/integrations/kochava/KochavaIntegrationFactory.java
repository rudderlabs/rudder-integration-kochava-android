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

import java.util.HashMap;
import java.util.Map;


public class KochavaIntegrationFactory extends RudderIntegration<RudderClient> {
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
                .setLogLevel(configureLogLevel(rudderConfig.getLogLevel()))
        );
        RudderLogger.logInfo("Initialized Kochava SDK");
    }

    private void processRudderEvent(RudderMessage element) {
        String type = element.getType();
        if (type != null) {
            switch (type) {

                case MessageType.TRACK:
                    String evName = (element.getEventName()).toLowerCase();
                    JSONObject data = null;
                    if(element.getProperties() != null)
                        data = new JSONObject(element.getProperties());

                    //Standard event
                    if(eventsMapping.containsKey((element.getEventName()).toLowerCase())){
                        Tracker.sendEvent(new Tracker.Event( (Integer) eventsMapping.get(evName) )
                                .addCustom(data)
                        );
                        return;
                    }

                    //Custom Event
                    Tracker.sendEvent(evName, new Gson().toJson(element.getProperties()));

                    break;
                case MessageType.SCREEN:
                    String screenName = "screen view "+(element.getEventName()).toLowerCase();
                    Tracker.sendEvent(screenName, new Gson().toJson(element.getProperties()));
                    break;
                default:
                    RudderLogger.logWarn("MessageType is not specified or supported");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        // nothing to do
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

    public static void registeredForPushNotificationsWithFCMToken(String token){
        Tracker.addPushToken(token);
    }

    private int configureLogLevel(int rsLogLevel){
        if(rsLogLevel == RudderLogger.RudderLogLevel.VERBOSE)
        {
            return RudderLogger.RudderLogLevel.VERBOSE;
        }
        if(rsLogLevel == RudderLogger.RudderLogLevel.DEBUG)
        {
            return RudderLogger.RudderLogLevel.DEBUG;
        }
        if(rsLogLevel == RudderLogger.RudderLogLevel.INFO)
        {
            return RudderLogger.RudderLogLevel.INFO;
        }
        if(rsLogLevel == RudderLogger.RudderLogLevel.WARN)
        {
            return RudderLogger.RudderLogLevel.WARN;
        }
        if(rsLogLevel == RudderLogger.RudderLogLevel.ERROR)
        {
            return RudderLogger.RudderLogLevel.ERROR;
        }
        return RudderLogger.RudderLogLevel.NONE;
    }
}