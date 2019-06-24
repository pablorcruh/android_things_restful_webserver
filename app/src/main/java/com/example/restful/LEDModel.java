package com.example.restful;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class LEDModel {
    private static final String TAG = "LEDModel";
    private static LEDModel instance=null;
    PeripheralManager service;
    private Gpio mLedGpio;
    private final String PIN_LED="BCM18";
    public static LEDModel getInstance(){
        if(instance==null){
            instance = new LEDModel();
        }
        return instance;
    }

    private LEDModel(){
        service= PeripheralManager.getInstance();
        try{
            mLedGpio= service.openGpio(PIN_LED);
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        }catch(Exception e){
            Log.e(TAG,"Error en el API PeripheralIO",e);
        }
    }

    public static Boolean setState(boolean state){
        try{
            getInstance().mLedGpio.setValue(state);
            return true;
        }catch(IOException e){
            Log.e(TAG,"Error en el API PeripheralIO",e);
            return false;
        }
    }

    public static boolean getState(){
        boolean value=false;
        try{
            value = getInstance().mLedGpio.getValue();
        }catch(IOException e){
            Log.e(TAG,"Error en el API PeripheralIO", e);
        }
        return value;
    }
}
