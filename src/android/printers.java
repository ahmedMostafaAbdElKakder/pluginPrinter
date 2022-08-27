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
            private Context mContext; /*
            receipt Data Buffer
            */
            private byte[] receiptData = null; private int receiptDataLength;
            /*
            Serial Port
            */
            private String mComPort, mBaudRate; private int posComPort, posBaudRate; private SerialPortDevice serialPortDevice;
            /* Printer
            */
            public HashMap<String, PrinterDevice> mPrinterDeviceMap; public PrinterManager mPrinterManager;
            private Printer mPrinter;
            @Override
            protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
            mContext = this;
            getPreferences();
            /*
                Create PrinterManager
            */
            mPrinterManager = new PrinterManager(mContext, mHandler,null);
            loadSerialPrinter();
            /*
                Sample Receipt Data Load
            */
            InputStream inStream = getResources().openRawResource(R.raw.receipt_sample_01);
            try {
            int nBytes = inStream.available();
            receiptData = new byte[nBytes]; receiptDataLength = inStream.read(receiptData);
            } catch (IOException e) { receiptData = null;
            } }
            public void onClick(View view) { switch (view.getId()) {
            case R.id.btn_connect : // Connection
                    connection();
            break;
            case R.id.buttonPrint :
            } }
            // Print Sample Receipt
            doPrint();
            break;
            private void getPreferences() { //
            SharedPreferences sf = getSharedPreferences("SerialInfoFile", MODE_PRIVATE); posComPort = sf.getInt("posComport", 4);
            posBaudRate = sf.getInt("posBaudRate", 0);
            mComPort = sf.getString("comport", "COM5");
            mBaudRate = sf.getString("baudRate", "9600"); }
            /**
            *loadSerialPrinter
            */
            private void loadSerialPrinter() {
            if (null != mPrinter) { mPrinter.disconnect(); mPrinter = null;
            }
            SerialPortService serialPortService = new SerialPortService(mContext, mComPort, mBaudRate);
            serialPortService.loadSerialPortDeviceMaps(); serialPortDevice = serialPortService.getSerialPortDevice();
            /* Clear all data for reloading */
            if (null != mPrinterDeviceMap && mPrinterDeviceMap.size() > 0) { mPrinterDeviceMap.clear();
            }
            ArrayList<String> mUseSerialPorts = new ArrayList<>(); mUseSerialPorts.add(mComPort);
            String[] ports = mUseSerialPorts.toArray(new String[0]); mPrinterManager.setSerialPorts(ports);
            mPrinterManager.startDiscovery(PrinterConstants.PRINTER_TYPE_SERIAL); }
            /**

            * Connect to Printer
            */
            public void connection() {
            if (null == mPrinter) { PrinterDevice device =
            mPrinterDeviceMap.get(serialPortDevice.getDeviceName()); mPrinter = mPrinterManager.connectDevice(device); if (null != mPrinter) {
            btn_connect.setText(getResources().getString(R.string.disconnect)); sp_ComPort.setEnabled(false);
            sp_BaudRate.setEnabled(false);
            btn_Reload.setEnabled(false);
            }
            } else {
            mPrinter.disconnect();
            mPrinter = null; btn_connect.setText(getResources().getString(R.string.connect)); sp_ComPort.setEnabled(true);
            sp_BaudRate.setEnabled(true);
            btn_Reload.setEnabled(true);
            sbScriptMsg.setLength(0); }
            }
            /**
            *
            */
            public void doPrint() {
            /* Print receipt sample */
            if (mPrinter != null && receiptDataLength > 0) { mPrinter.executeDirectIO(receiptData);
            }
            /* Line feed and cut paper */
            mPrinter.lineFeed(5);
            //mSelectedPrinterItem.mPrinter.cutPaper();
            }
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
