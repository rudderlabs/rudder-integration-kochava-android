package com.rudderstack.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.databinding.ActivityMainBinding
import com.rudderstack.android.sdk.core.RudderProperty
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.identify.setOnClickListener { identify() }
        binding.reset.setOnClickListener { reset() }

        // Standard ECommerce events
        binding.orderCompletedWithMultipleProductsAsList.setOnClickListener { orderCompletedWithMultipleProductsAsList() }
        binding.orderCompletedWithMultipleProductsAsJSON.setOnClickListener { orderCompletedWithMultipleProductsAsJSON() }
        binding.orderCompletedWithSingleProductAsJSON.setOnClickListener { orderCompletedWithSingleProductAsJSON() }
        binding.checkoutStarted.setOnClickListener { checkoutStarted() }
        binding.productAdded.setOnClickListener { productAdded() }
        binding.productAddedToWishlist.setOnClickListener { productAddedToWishlist() }
        binding.productReviewed.setOnClickListener { productReviewed() }
        binding.productSearched.setOnClickListener { productSearched() }

        // Custom track events
        binding.customTrackWithProperties.setOnClickListener { customTrackWithProperties() }
        binding.customTrackWithoutProperties.setOnClickListener { customTrackWithoutProperties() }

        // Screen events
        binding.screenWithProperties.setOnClickListener { screenWithProperties() }
        binding.screenWithoutProperties.setOnClickListener { screenWithoutProperties() }
    }

    private fun identify() {
        MainApplication.rudderClient.identify("Kochava user ID")
    }

    private fun reset() {
        MainApplication.rudderClient.reset(false)
    }

    // Standard ECommerce events: https://support.kochava.com/reference-information/post-install-event-examples/
    private fun orderCompletedWithMultipleProductsAsList() {
        MainApplication.rudderClient.track(
            "Order Completed",
            RudderProperty()
                .putValue("products", getMultipleProductsAsList())
                .putValue("revenue", 8.99)
                .putValue("currency", "USD")
                .putValue("quantity", 2)
                .putValue("order_id", "order123")
                .putValue("checkout_id", "check123")
                .putValue("custom_1", "string")
                .putValue("custom_2", 1230)
                .putValue("custom_3", true)
                .putValue("custom_4", getJSONObject())
        )
    }

    private fun orderCompletedWithMultipleProductsAsJSON() {
        MainApplication.rudderClient.track(
            "order completed",
            RudderProperty()
                .putValue("products", getMultipleProductsAsJSON())
                .putValue("revenue", "15.00")
                .putValue("currency", "USD")
                .putValue("order_id", 1234)
                .putValue("affiliation", "Apple Store")
                .putValue("value", 20)
        )
    }

    private fun orderCompletedWithSingleProductAsJSON() {
        MainApplication.rudderClient.track(
            "order completed",
            RudderProperty()
                .putValue("products", getSingleProductsAsJSON())
                .putValue("revenue", 14)
                .putValue("currency", "USD")

                .putValue("sku", "G-32")
                .putValue("productId", 123)
                .putValue("quantity", 1)
        )
    }

    private fun checkoutStarted() {
        MainApplication.rudderClient.track(
            "Checkout Started",
            RudderProperty()
                .putValue("products", getMultipleProductsAsList())
                .putValue("currency", "USD")

                .putValue("order_id", "order123")
                .putValue("product_id", "pro123")
                .putValue("custom_1", "string")
                .putValue("custom_2", 1230)
                .putValue("custom_3", true)
                .putValue("custom_4", getJSONObject())
                .putValue("revenue", 8.99)
        )
    }

    private fun productAdded() {
        MainApplication.rudderClient.track(
            "Product Added",
            RudderProperty()
                .putValue("product_id", "product_001")
                .putValue("name", "Gold")
                .putValue("quantity", "678")
        )
    }

    private fun productAddedToWishlist() {
        MainApplication.rudderClient.track(
            "Product Added to Wishlist",
            RudderProperty()
                .putValue("name", "Gold")
                .putValue("productId", 678)
        )
    }

    private fun productReviewed() {
        MainApplication.rudderClient.track(
            "product reviewed",
            RudderProperty().putValue("rating", 5)
        )
    }

    private fun productSearched() {
        MainApplication.rudderClient.track(
            "products searched",
            RudderProperty().putValue("query", "www.facebook.com")
        )
    }

    // Custom track events

    private fun customTrackWithProperties() {
        val array1 = arrayOf(1, 2, 3, 4)
        MainApplication.rudderClient.track(
            "Custom track with properties",
            RudderProperty()
                .putValue("val", array1)
                .putValue("date", Date())
                .putValue("Colour", "Black")
                .putValue("Weight", "25lb")
        )
    }

    private fun customTrackWithoutProperties() {
        MainApplication.rudderClient.track("Custom track without properties")
    }

    // Custom screen events

    private fun screenWithProperties() {
        MainApplication.rudderClient.screen(
            "Screen with properties",
            RudderProperty()
                .putValue("val", Date())
                .putValue("prop_key", "prop_value")
                .putValue("Dynamic", "true")
                .putValue("Colour", "Grey")
        )
    }

    private fun screenWithoutProperties() {
        MainApplication.rudderClient.screen("Screen without properties")
    }

    // Standard event properties
    private fun getMultipleProductsAsList(): List<Map<String, Any>> {
        val product1 = mapOf("product_id" to "pro1", "name" to "monopoly")
        val product2 = mapOf("product_id" to "pro2", "name" to "games")
        return listOf(product1, product2)
    }

    private fun getMultipleProductsAsJSON(): JSONArray {
        val productsArray = JSONArray()

        val product1 = JSONObject().apply {
            put("product_id", 123.87)
            put("name", "Monopoly")
        }

        val product2 = JSONObject().apply {
            put("productId", "345")
            put("name", "UNO")
        }

        productsArray.put(product1)
        productsArray.put(product2)
        return productsArray
    }

    private fun getSingleProductsAsJSON(): JSONArray {
        val productsArray = JSONArray()

        val product1 = JSONObject().apply {
            put("product_id", 123.87)
            put("name", "Monopoly")
        }

        productsArray.put(product1)
        return productsArray
    }

    private fun getJSONObject(): JSONObject {
        return JSONObject("""{"name":"test name", "age":25}""")
    }
}
