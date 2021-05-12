package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val traits = RudderTraitsBuilder();
//        traits.setUserName("123456789");
//        traits.build().put("login","username");
        //MainApplication.rudderClient.identify(traits);


        //Standard Events

//        TC:4 Track call with event name and property after Identify
//        MainApplication.rudderClient.track("product added",
//            RudderProperty()
//                .putValue("Colour","Black")
//                .putValue("Weight","25lb"))

//        MainApplication.rudderClient.track("product reviewed",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"))

        //Add to Cart
        MainApplication.rudderClient.track("product added")

        //Search
        MainApplication.rudderClient.track("products searched",
                RudderProperty()
                        .putValue("Colour","Black")
                        .putValue("Weight","25lb"))

        //Custom Track Event
//        MainApplication.rudderClient.track("This is custom event",
//                RudderProperty()
//                        .putValue("Colour","Black")
//                        .putValue("Weight","25lb"));

//        TC:5 Identify with anonymousId, email, and name
        /*val traits1 = RudderTraits();
        traits1.putName("Arpan Saha");
        traits1.putEmail("hahaha@gmail.com");
        MainApplication.rudderClient.identify(traits1);
        */

//        MainApplication.rudderClient.track(
//                "Product Added",
//                RudderProperty()
//                        .putValue("product_id", "product_001")
//        )


//        TC:6 Screen with identified anonymousId, name and properties
//        MainApplication.rudderClient.screen("Flipkart Page",
//            RudderProperty()
//                .putValue("url","www.flipkart.com")
//                .putValue("referer","facebook")
//                .putValue("height",5))











//        TC:13 Track call with name and properties with array of strings/integers
//        val myArray3 = arrayOf<String>("Red","Green","Black");
//        MainApplication.rudderClient.track("Sample event with name and array of strings.",
//                RudderProperty().putValue("colour",myArray3));

//        val intarray = arrayOf<Int>(10,20,30,40);
//        MainApplication.rudderClient.track("Sample event with name and array of integer.",
//            RudderProperty().putValue("size",intarray));
//
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
