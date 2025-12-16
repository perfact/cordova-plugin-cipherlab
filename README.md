Cordova plugin for Cipherlab scanners
=====================================

This plugin adds a new object, `window.CipherlabScanner`, which exposes one
function, `setScannerEnabled(enabled, success_cb, error_cb)`.

The function disables or enables the hardware scanner buttons on Cipherlab
scanners, depending on the boolean variable `enabled`.

Please note that the plugin will be initialized on the first call, and because
the Cipherlab API dictates a delay between initializing and first use, the
first attempt to call the function will fail.

This shortcoming will be addressed in a future release.
