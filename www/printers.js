var exec = require('cordova/exec');

function Inject(){
}
Inject.prototype.coolMethod = function (arg0, success, error) {
    exec(success, error, 'printers', 'coolMethod', [arg0]);
};

Inject.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.Inject = new Inject();
  return window.plugins.Inject;
};

cordova.addConstructor(Inject.install);
