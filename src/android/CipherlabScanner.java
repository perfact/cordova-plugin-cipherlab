package de.perfact.cordova.cipherlabscanner;

package com.example.cipherlabscanner;

import com.cipherlab.barcode.ReaderManager;
import com.cipherlab.barcodebase.ClResult;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class CipherlabScanner extends CordovaPlugin {

    private ReaderManager mReaderManager;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        // Init ReaderManager when plugin starts
        mReaderManager = ReaderManager.InitInstance(cordova.getActivity());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext cb) throws JSONException {
        if ("setScannerEnabled".equals(action)) {
            boolean enable = args.getBoolean(0);
            if (mReaderManager == null) {
                cb.error("ReaderManager not initialized");
                return true;
            }
            ClResult res = mReaderManager.SetActive(enable);
            if (res == ClResult.S_OK) {
                cb.success();
            } else {
                cb.error("SetActive failed: " + res.toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        if (mReaderManager != null) {
            mReaderManager.Release();
            mReaderManager = null;
        }
        super.onDestroy();
    }
}
