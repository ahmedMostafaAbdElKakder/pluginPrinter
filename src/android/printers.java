package printerCustom;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class printers extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            Log.v(TAG, "coolMethod called with message =" + message);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
        }

    private void coolMethod(String message, CallbackContext callbackContext) {
          Log.v(TAG, "Inject's coolMethod called ,message="+message);
          messageReceived = message;
        if (message != null && message.length() > 0) {
                cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {

            final android.widget.Toast toast = android.widget.Toast.makeText(
              cordova.getActivity().getWindow().getContext(),
              messageReceived,
              android.widget.Toast.LENGTH_LONG 
                );
                toast.setGravity(GRAVITY_CENTER, 0, 0);
                toast.show();
            }
            });
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
