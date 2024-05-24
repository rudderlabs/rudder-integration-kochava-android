package com.rudderstack.android.sample.kotlin

import android.app.Application
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
}
