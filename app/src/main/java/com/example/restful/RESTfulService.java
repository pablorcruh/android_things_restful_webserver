package com.example.restful;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.nio.HttpServerHelper;
import org.restlet.routing.Router;

public class RESTfulService extends IntentService {

    private static final String ACTION_START="com.example.restful.START";
    private static final String ACTION_STOP="com.example.restful.STOP";
    private Component mComponent;

    public RESTfulService(){
        super("RESTfulService");
        Engine.getInstance().getRegisteredServers().clear();
        Engine.getInstance().getRegisteredServers().add(new HttpServerHelper(null));
        mComponent=new Component();
        Router router = new Router(mComponent.getContext().createChildContext());
        mComponent.getServers().add(Protocol.HTTP, 8080);
        mComponent.getDefaultHost().attach("/rest", router);
        router.attach("/led",LEDResource.class);
    }

    public static void startServer(Context context){
        Intent intent = new Intent(context, RESTfulService.class);
        intent.setAction(ACTION_START);
        context.startService(intent);
    }

    public static void stopServer(Context context) {
        Intent intent = new Intent(context, RESTfulService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent !=null){
            final String action = intent.getAction();
            if(ACTION_START.equals(action)){
                handleStart();
            }else if(ACTION_STOP.equals(action)){
                handleStop();
            }
        }
    }

    private void handleStart(){
        try{
            mComponent.start();
        }catch(Exception e){
            Log.e(getClass().getSimpleName(),e.toString());
        }
    }

    private void handleStop(){
        try{
            mComponent.stop();
        }catch(Exception e){
            Log.e(getClass().getSimpleName(),e.toString());
        }
    }
}
