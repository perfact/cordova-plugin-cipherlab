package de.perfact.cordova.cipherlab;

import com.cipherlab.barcode.*;
import com.cipherlab.barcodebase.*;
import com.cipherlab.barcode.decoder.*;
import com.cipherlab.barcode.decoderparams.*;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class CipherlabScanner extends CordovaPlugin {

    private static final String TAG = "CipherlabScanner";
    private ReaderManager mReaderManager;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        // Init ReaderManager when plugin starts
        mReaderManager = ReaderManager.InitInstance(cordova.getActivity());
        Log.d(TAG, "plugin initialized: " + mReaderManager.toString());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext cb) throws JSONException {
        Log.d(TAG, "execute called: " + action);
        if ("setScannerEnabled".equals(action)) {
            boolean enable = args.getBoolean(0);
            if (mReaderManager == null) {
                Log.d(TAG, "ReaderManager not initialized");
                cb.error("ReaderManager not initialized");
                return true;
            }
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
        Log.d(TAG, "plugin destroyed");
        super.onDestroy();
    }
}
