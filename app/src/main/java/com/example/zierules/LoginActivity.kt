package com.example.zierules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.zierules.databinding.ActivityLoginBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.google.gson.JsonParser
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityLoginBinding
    private lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        sharedPref = PreferenceHelper(this)

        binding.buttonLogin.setOnClickListener {
            if (binding.editTextNIS.text.isNullOrEmpty() || binding.editTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(this, "Input Harus Di isi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val nis: String = binding.editTextNIS.text.toString()
            val pass: String = binding.editTextPassword.text.toString()
            login(nis, pass)
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            pindahIntent()
        }
    }

    private fun pindahIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun login(nis: String, pass: String) {

        val queue = Volley.newRequestQueue(this)
        val url = "${MyApplication.BASE_URL}/student/login?"
        val body = JSONObject()
        body.put("nis", nis)
        body.put("password", pass)

        val jsonRequest = object : JsonObjectRequest(Request.Method.POST, url, body,
            { response ->
                val jsonObject = JsonParser.parseString(response.toString()).asJsonObject
                val status = jsonObject.get("status").asInt
                val message = jsonObject.get("message").asString
                try {
                    if(status == 200) {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        val token = jsonObject.get("token").asString
                        sharedPref.put(Constant.PREF_NIS, nis)
                        sharedPref.put(Constant.PREF_PASSWORD, pass)
                        sharedPref.put(Constant.PREF_TOKEN, token)
                        sharedPref.put(Constant.PREF_IS_LOGIN, true)
                        pindahIntent()
                    } else if(status == 404){
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                if (error.networkResponse != null && error.networkResponse.statusCode == 422) {
                    val responseString = String(error.networkResponse.data, Charset.forName("UTF-8"))
                    try {
                        val errorBody = JSONObject(responseString)
                        val errorMessage = errorBody.optString("message")
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        Toast.makeText(this, "Error: Invalid JSON response", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

        queue.add(jsonRequest)
    }
}