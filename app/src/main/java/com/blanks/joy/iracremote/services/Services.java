package com.blanks.joy.iracremote.services;

import android.hardware.ConsumerIrManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.blanks.joy.iracremote.R;
import com.blanks.joy.iracremote.constants.Constants;
import com.blanks.joy.iracremote.devices.TransmissionCodes;
import com.blanks.joy.iracremote.instance.Singleton;

/**
 * Created by Joy on 23/08/15.
 */
public class Services {
    private static final String TAG = "JoyIR";
    public static void power(Singleton inst, ConsumerIrManager cirm, RemoteViews rv){
        boolean isPoweredOn = inst.power;
        try {
            TransmissionCodes data;
            //TextView tv = ((TextView) findViewById(R.id.temp));

            if (isPoweredOn) {
                data = inst.getIrCodesAll().get(inst.sequence, Constants.power + 1);
                inst.power = false;
                //tv.setText("--");
                rv.setInt(R.id.edge,"setBackgroundResource",R.drawable.remote_off);
            } else {
                // poweron
                data = inst.getIrCodesAll().get(inst.sequence, Constants.power);
                //tv.setText("" + inst.temp);
                inst.power = true;
                rv.setImageViewResource(R.id.swing, (inst.swing ? R.drawable.swingon : R.drawable.swingoff));
                rv.setInt(R.id.edge,"setBackgroundResource",R.drawable.remote_on);
            }
            int freq = data.getFrequency();
            int[] c = (data.getTransmission());
            cirm.transmit(freq, c);
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

}