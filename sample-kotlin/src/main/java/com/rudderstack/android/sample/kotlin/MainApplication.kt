package com.rudderstack.android.sample.kotlin

import android.app.Application
import android.content.Context
import com.kochava.tracker.TrackerApi
import com.kochava.tracker.log.LogLevel
import com.rudderlabs.android.sample.kotlin.BuildConfig
import com.rudderstack.android.integrations.kochava.KochavaIntegrationFactory
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderConfig
import com.rudderstack.android.sdk.core.RudderLogger

class MainApplication : Application() {
    companion object {
        lateinit var rudderClient: RudderClient
    }

    override fun onCreate() {
        super.onCreate()
        // Start the Kochava Tracker
        initKochavaSDK(this)

        rudderClient = RudderClient.getInstance(
            this,
            BuildConfig.WRITE_KEY,
            RudderConfig.Builder()
                    .withDataPlaneUrl(BuildConfig.DATA_PLANE_URL)
                    .withFactory(KochavaIntegrationFactory.FACTORY)
                    .withLogLevel(RudderLogger.RudderLogLevel.VERBOSE)
                    .withTrackLifecycleEvents(false)
                    .build()
        )
    }

    private fun initKochavaSDK(context: Context) {
        // https://support.kochava.com/sdk-integration/android-sdk-integration/
        val kochavaInstance: TrackerApi = com.kochava.tracker.Tracker.getInstance()
        kochavaInstance.setLogLevel(LogLevel.TRACE)
        kochavaInstance.startWithAppGuid(context, "YOUR_ANDROID_APP_GUID")
    }
}
