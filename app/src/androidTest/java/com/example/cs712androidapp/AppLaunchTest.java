package com.example.cs712androidapp;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppLaunchTest {

    private static final String PCG = "com.example.cs712androidapp";
    private static final String TAG = "AppLaunchTest";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(
                InstrumentationRegistry.getInstrumentation()
        );
    }

    private void launchApp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PCG);

        if (intent == null) {
            throw new RuntimeException("Cannot find launch intent for package");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(PCG).depth(0)), 5000);
        UiObject2 button = device.wait(
                Until.findObject(By.text("Start Activity Explicitly")),
                5000
        );

        assertTrue("Button not found", button != null);
        button.click();
    }

    @Test
    public void testSecurityAndPrivacyVisible() throws Exception {
        launchApp();
        UiObject2 text = device.wait(
                Until.findObject(By.textContains("Security and privacy")),
                8000
        );
        assertTrue("Security and privacy text not found", text != null);
        Log.i(TAG, "pass: 'Security and privacy' was found on screen");
    }
    @Test
    public void testBatteryOptimizationVisible() throws Exception {
        launchApp();
        UiObject2 text = device.wait(
                Until.findObject(By.textContains("Battery optimization")),
                8000
        );

        assertTrue("Battery optimization text not found", text != null);
        Log.i(TAG, "pass: 'Battery optimization' was found on screen");
    }
}