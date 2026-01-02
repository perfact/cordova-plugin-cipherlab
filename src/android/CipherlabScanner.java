package de.perfact.cordova.cipherlab;

import com.cipherlab.barcode.*;
import com.cipherlab.barcodebase.*;
import com.cipherlab.barcode.decoder.*;
import com.cipherlab.barcode.decoderparams.*;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CipherlabScanner extends CordovaPlugin {

    private static final String TAG = "CipherlabScanner";
    private ReaderManager mReaderManager;
    private boolean isReaderServiceConnected = false;
    private BroadcastReceiver readerConnReceiver;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        Activity activity = cordova.getActivity();
        mReaderManager = ReaderManager.InitInstance(activity);
        // NEW: listen for ReaderService connection
        IntentFilter filter = new IntentFilter();
        filter.addAction(GeneralString.Intent_READERSERVICE_CONNECTED);

        readerConnReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: " + intent.getAction());
                if (GeneralString.Intent_READERSERVICE_CONNECTED.equals(intent.getAction())) {
                    Log.d(TAG, "ReaderService connected (broadcast received)");
                    isReaderServiceConnected = true;
                }
            }
        };
        activity.registerReceiver(readerConnReceiver, filter);

        Log.d(TAG, "plugin initialized: " + mReaderManager.toString());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext cb) throws JSONException {
        Log.d(TAG, "execute called: " + action);
        if (!isReaderServiceConnected) {
            Log.d(TAG, "Service not connected");
            cb.error("Service not connected");
            return false;
        }
        switch (action) {
            case "setScannerEnabled":
                setScannerEnabled(args.getBoolean(0));
                break;
            default:
                return false;
        }
        return true;
    }

    private void setScannerEnabled(Boolean enable) {
        if (mReaderManager == null) {
            Log.d(TAG, "ReaderManager not initialized");
            cb.error("ReaderManager not initialized");
            return true;
        }
        Log.d(TAG, "isReaderServiceConnected = " + isReaderServiceConnected);

        boolean curr_active = mReaderManager.GetActive();
        Log.d(TAG, "current GetActive state: " + curr_active);
        ClResult res = mReaderManager.SetActive(enable);
        if (res == ClResult.S_OK) {
            Log.d(TAG, "setScannerEnabled successful!");
            cb.success();
        } else {
            Log.d(TAG, "SetActive failed: " + res.toString());
            cb.error("SetActive failed: " + res.toString());
        }
    }

    @Override
    public void onDestroy() {
        Activity activity = cordova.getActivity();
        if (readerConnReceiver != null) {
            try {
                activity.unregisterReceiver(readerConnReceiver);
            } catch (IllegalArgumentException ignore) {}
            readerConnReceiver = null;
        }
        if (mReaderManager != null) {
            mReaderManager.Release();
            mReaderManager = null;
        }
        Log.d(TAG, "plugin destroyed");
        super.onDestroy();
    }
}
