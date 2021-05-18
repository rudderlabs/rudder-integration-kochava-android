package com.rudderstack.android.sample.kotlin

import com.google.firebase.messaging.FirebaseMessagingService
import com.rudderstack.android.integrations.kochava.KochavaIntegrationFactory.registeredForPushNotificationsWithFCMToken

// Extending FirebaseMessagingService class
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // The onNewToken callback fires whenever a new token is generated.
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Method to pass the push token to the Kochava native sdk
        registeredForPushNotificationsWithFCMToken(token)
    }
}