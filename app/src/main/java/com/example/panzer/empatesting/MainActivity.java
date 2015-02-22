package com.example.panzer.empatesting;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;


public class MainActivity extends ActionBarActivity implements EmpaDataDelegate, EmpaStatusDelegate{


    private EmpaDeviceManager deviceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceManager = new EmpaDeviceManager(getApplicationContext(), this, this);
        deviceManager.authenticateWithAPIKey("1bbfc16948b5482ebe9729ab1750ec8c");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void didReceiveGSR(float v, double v2) {
        Log.i("EMPATICADATA_GSR", String.valueOf(v) + ","+String.valueOf(v2));

    }

    @Override
    public void didReceiveBVP(float v, double v2) {

    }

    @Override
    public void didReceiveIBI(float v, double v2) {

    }

    @Override
    public void didReceiveTemperature(float v, double v2) {

    }

    @Override
    public void didReceiveAcceleration(int i, int i2, int i3, double v) {

    }

    @Override
    public void didReceiveBatteryLevel(float v, double v2) {

    }

    @Override
    public void didUpdateStatus(EmpaStatus empaStatus) {

        Toast.makeText(getApplicationContext(),"DM STATUS: " + empaStatus.toString(),Toast.LENGTH_SHORT).show();

        if(empaStatus == EmpaStatus.READY){
             deviceManager.startScanning();

            Toast.makeText(getApplicationContext(),"SCAN STARTED", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus empaSensorStatus, EmpaSensorType empaSensorType) {



    }

    @Override
    public void didDiscoverDevice(BluetoothDevice device, int i, boolean allowed) {
        // Stop scanning. The first allowed device will do.
        if (allowed) {
            deviceManager.stopScanning();
            // Connect to the device
            try {
                deviceManager.connectDevice(device);
                Toast.makeText(getApplicationContext(),"CONNECTED" , Toast.LENGTH_LONG).show();

                // Depending on your configuration profile, you might be unable to connect to a device.
                // This should happen only if you try to connect when allowed == false.
            } catch (ConnectionNotAllowedException e) {
                Toast.makeText(MainActivity.this, "Sorry, can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void didRequestEnableBluetooth() {

    }
}
