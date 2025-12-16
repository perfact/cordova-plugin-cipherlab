// www/cipherlabscanner.js
var exec = require('cordova/exec');

var CipherlabScanner = {
  setScannerEnabled: function (enable, success, error) {
    exec(success, error, 'CipherlabScanner', 'setScannerEnabled', [!!enable]);
  }
}

module.exports = CipherlabScanner;
