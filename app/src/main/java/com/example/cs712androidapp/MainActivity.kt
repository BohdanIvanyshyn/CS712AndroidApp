package com.example.cs712androidapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnExplicit = findViewById<Button>(R.id.btnExplicit)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)
        val btnStartService = findViewById<Button>(R.id.btnStartService)
        val btnSendBroadcast = findViewById<Button>(R.id.btnSendBroadcast)

        // Explicit intent
        btnExplicit.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        // Implicit intent
        btnImplicit.setOnClickListener {
            startActivity(Intent("com.example.cs712androidapp.OPEN_SECOND"))
        }

        // Start foreground service safely
        btnStartService.setOnClickListener {
            val intent = Intent(this, MyForegroundService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }

        // Send broadcast
        btnSendBroadcast.setOnClickListener {
            val intent = Intent("com.example.MY_ACTION").apply {
                setPackage(packageName)
            }
            sendBroadcast(intent)
        }

        // Register broadcast receiver dynamically
        receiver = MyBroadcastReceiver()
        val filter = IntentFilter("com.example.MY_ACTION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            registerReceiver(receiver, filter, android.content.Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
