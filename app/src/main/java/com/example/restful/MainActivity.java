package com.example.restful;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RESTfulService.startServer(this); // Arrancar el servidor
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        RESTfulService.stopServer(this); // Detener el servidor
    }
}
