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

Where to get the documentation
------------------------------

As of this writing, the documentation and library are available for download at

https://www.cipherlab.com/en/download-c2380/Document.html

If this link expires, the general Path on the Cipherlab site is:

    Home → Download → Mobile Computers → Android & Windows → SDK → Reader SDK

Within this chapter, there's a "Documentation" and a "Library" section.

The current hyperlink for the Android Programming Guide is:

    https://www.cipherlab.com/en/download/file/7364

The current hyperlink for the Barcode Library is:

    https://www.cipherlab.com/en/download/file/7951

Similarly, the documentation for the ReaderConfig App is available in

    Home → Download → Mobile Computers → Android & Windows → Utilities Manual →
    ReaderConfig

The current hyperlink for the ReaderConfig manual is:

    https://www.cipherlab.com/en/download/file/7291


About the BarcodeAPI file
-------------------------

The file `BarcodeAPI_V1_1_60.jar` was downloaded from www.cipherlab.com. None
of the files downloaded contain a license or a copyright note, which is why the
file is included in this repository.

This file is attributed to CipherLab Headquarters:

    CipherLab Co.,Ltd
    12F., No. 333, Sec. 2, Dunhua S. Rd.,
    Da'an Dist., Taipei City 106033, Taiwan
