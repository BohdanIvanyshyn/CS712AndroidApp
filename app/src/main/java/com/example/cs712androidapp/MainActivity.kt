package com.example.cs712androidapp

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: MyBroadcastReceiver

    private val PERMISSION_REQUEST_CODE = 1
    private val CUSTOM_PERMISSION = "com.example.cs712androidapp.MSE712"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(CUSTOM_PERMISSION),
                PERMISSION_REQUEST_CODE
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnExplicit = findViewById<Button>(R.id.btnExplicit)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)
        val btnStartService = findViewById<Button>(R.id.btnStartService)
        val btnSendBroadcast = findViewById<Button>(R.id.btnSendBroadcast)
        val btnThird = findViewById<Button>(R.id.btnThird)

        // Explicit intent
        btnExplicit.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, SecondActivity::class.java))
            }
        }

        btnThird.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

        // Implicit intent
        btnImplicit.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent("com.example.cs712androidapp.OPEN_SECOND"))
            }
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