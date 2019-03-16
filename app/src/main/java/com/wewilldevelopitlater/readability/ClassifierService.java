package com.wewilldevelopitlater.readability;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ClassifierService extends Service { //TODO: Http communication
    public ClassifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
