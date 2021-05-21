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
        val answer1 = JSONObject("""{"name":"test name", "age":25}""")
        val map= mapOf("product_id" to "pro1", "name" to "monopoly", "price" to 1000)
        val map1= mapOf("product_id" to "pro2", "name" to "games", "price" to 2000)
        val list= listOf(map, map1);
        MainApplication.rudderClient.track("Order Completed",
                RudderProperty()
                        .putValue("order_id", "order123")
                        .putValue("product_id","pro123")
                        .putValue("checkout_id", "check123")
                        .putValue("name", "test")
                        .putValue("custom_1", "string")
                        .putValue("custom_2", 1230)
                        .putValue("custom_3", true)
                        .putValue("custom_4", answer1)
                        .putValue("revenue", 8.99)
                        .putValue("quantity", 2)
                        .putValue("currency", "USD")
                        .putValue("products", list)
        )
//
//        MainApplication.rudderClient.track("Checkout Started",
//                RudderProperty()
//                        .putValue("order_id", "order123")
//                        .putValue("product_id","pro123")
//                        .putValue("name", "test")
//                        .putValue("custom_1", "string")
//                        .putValue("custom_2", 1230)
//                        .putValue("custom_3", true)
//                        .putValue("custom_4", answer1)
//                        .putValue("revenue", 8.99)
//                        .putValue("currency", "USD")
//                        .putValue("products", list)
//        )
//
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


//        val array1 = arrayOf(1,2,3,4)
//        MainApplication.rudderClient.track("Track 6",
//                RudderProperty().putValue("val",array1))
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
