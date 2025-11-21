// www/cipherlab-scanner.js
var exec = require('cordova/exec');

exports.setScannerEnabled = function (enable, success, error) {
  exec(success, error, 'CipherlabScanner', 'setScannerEnabled', [!!enable]);
};
