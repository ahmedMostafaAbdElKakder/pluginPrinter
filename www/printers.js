var exec = require('cordova/exec');

exec.coolMethod = function (arg0, success, error) {
    exec(success, error, 'printers', 'coolMethod', [arg0]);
};

var app = {
  initialize: function() {
      document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
  },
  // deviceready Event Handler
  //
  // Bind any cordova events here. Common events are:
  // 'pause', 'resume', etc.
  onDeviceReady: function() {
      this.receivedEvent('deviceready');
  },
  receivedEvent: function(id) {
      function success(result){
          debugger;
          alert("Jerry plugin result: " + result);
      };
      setTimeout( function(){
          debugger;
          Cordova.exec(success, null, "Adder", "performAdd", [10,20]);
      }, 10000); 
  }
};
app.initialize();

