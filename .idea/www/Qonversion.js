var exec = require('cordova/exec');

exports.purchase = function (arg0, success, error) {
    exec(success, error, 'Qonversion', 'purchase', [arg0]);
};

exports.restore = function (arg0, success, error) {
  exec(success, error, 'Qonversion', 'restore', [arg0]);
};
