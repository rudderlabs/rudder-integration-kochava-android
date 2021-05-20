package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderProperty
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        Standard Events
//        Track call with Standard event name and Standard  property
//        payload for Ecommerce Track event
//        val payload = RudderProperty()
//        val productsArray = JSONArray()
//        payload.put("order_id", 1234)
//        payload.put("affiliation", "Apple Store")
//        payload.put("value", 20)
//        payload.put("revenue", "15.00")
//        payload.put("currency", "USD")
//        payload.put("products", productsArray)
//        val product1 = JSONObject()
//        product1.put("product_id", 123.87)
//        product1.put("sku", "G-32")
//        product1.put("name", "Monopoly")
//        product1.put("price", 14)
//        product1.put("quantity", 1)
//        product1.put("category", "Games")
//        product1.put("url", "https://www.website.com/product/path")
//        product1.put("image_url", "https://www.website.com/product/path.jpg")
//        val product2 = JSONObject()
//        product2.put("productId", "345")
//        product2.put("sku", "F-32")
//        product2.put("name", "UNO")
//        product2.put("price", 3.45)
//        product2.put("quantity", 2)
//        product2.put("category", "Games")
//        product2.put("url", "https://www.website.com/product/path")
//        product2.put("image_url", "https://www.website.com/product/path.jpg")
//        productsArray.put(product1)
//        productsArray.put(product2)
//        MainApplication.rudderClient.track(
//                "order completed",
//                payload
//                )
//
//        val payload = RudderProperty()
//        val productsArray = JSONArray()
//        payload.put("products", productsArray)
//        val product1 = JSONObject()
//        product1.put("sku", "G-32")
//        product1.put("productId", 123)
//        //product1.put("name", "Monopoly")
//        product1.put("price", 14)
//        product1.put("quantity", 1)
//        productsArray.put(product1)
//        MainApplication.rudderClient.track(
//                "order completed",
//                payload
//        )
//
//        MainApplication.rudderClient.track(
//                "Product Added",
//                RudderProperty()
//                        .putValue("product_id", "product_001")
//        )
//
//        MainApplication.rudderClient.track("checkout started",
//                RudderProperty()
//                        .putValue("currency", "USD"))
//
//        MainApplication.rudderClient.track("Product Added to Wishlist",
//                RudderProperty()
//                        .putValue("name","Gold")
//                        .putValue("product_id",678))
//
//        MainApplication.rudderClient.track("product added",
//                RudderProperty()
//                        .putValue("name","Gold")
//                        .putValue("productId","678")
//                        .putValue("quantity","678.87"))
//
//        MainApplication.rudderClient.track("product reviewed",
//                RudderProperty()
//                        .putValue("rating", 5))
//
//        MainApplication.rudderClient.track("products searched",
//                RudderProperty()
//                        .putValue("query", "www.facebook.com"))
//
//        Only Standard Event
//        MainApplication.rudderClient.track("product added")
//
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

//        TC:15 Screen call with name, category and property
//        MainApplication.rudderClient.screen(
//            "Myntra_home",
//            RudderProperty()
//                    .putValue("Dynamic", "true").putValue("Colour", "Grey"),
//            null
//        );


    }
}
