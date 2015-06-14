package com.kimsmik.kimsmikweddingapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class KimSmikService extends Service{
    public KimSmikService() {
    }


    private ServiceBinder mBinder = new ServiceBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private class ServiceBinder extends Binder{
        public KimSmikService GetService(){
            return KimSmikService.this;
        }
    }
}
