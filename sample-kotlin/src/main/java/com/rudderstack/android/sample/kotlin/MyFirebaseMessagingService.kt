package com.rudderstack.android.sample.kotlin

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.rudderstack.android.integrations.kochava.KochavaIntegrationFactory.registeredForPushNotificationsWithFCMToken


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registeredForPushNotificationsWithFCMToken("c7uG_gBNLh8:APA91bGPKM-wTawyNsditdmNvFbAybGoExkQxorkCj6DFUYyfMa4YcNUNqbAVkRTvfnZuNjv1")
    }
}