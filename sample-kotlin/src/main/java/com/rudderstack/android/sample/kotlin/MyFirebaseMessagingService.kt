package com.rudderstack.android.sample.kotlin

import com.google.firebase.messaging.FirebaseMessagingService
import com.rudderstack.android.integrations.kochava.KochavaIntegrationFactory.registeredForPushNotificationsWithFCMToken

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registeredForPushNotificationsWithFCMToken(token)
    }
}
