package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Standard Events
//        TC:4 Track call with event name and property
//        MainApplication.rudderClient.track(
//                "Product Added",
//                RudderProperty()
//                        .putValue("product_id", "product_001")
//        )
//
//        MainApplication.rudderClient.track("add to wishlist",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"))
//
//        MainApplication.rudderClient.track("checkout started",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"))
//
//        MainApplication.rudderClient.track("order completed",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"))
//
//        MainApplication.rudderClient.track("product reviewed",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"))
//
//        MainApplication.rudderClient.track("products searched")



//        Custom Track Events:
//        MainApplication.rudderClient.track("Youtube Opened")
//
//        MainApplication.rudderClient.track("Testing Token",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"));


//        Screen Call:
//        MainApplication.rudderClient.screen("Sample Screen Name",
//            RudderProperty()
//                .putValue("prop_key","prop_value"));


//        TC:14 Screen call with name
//        MainApplication.rudderClient.screen("Flipkart Page");
//
//        TC:15 Screen call with name, category and property
//        MainApplication.rudderClient.screen(
//            "Myntra_home",
//            "Mail-T-Shirts",
//            RudderProperty().putValue("Dynamic", "true").putValue("Colour", "Grey"),
//            null
//        );


    }
}
