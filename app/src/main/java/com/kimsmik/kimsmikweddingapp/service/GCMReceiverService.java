package com.kimsmik.kimsmikweddingapp.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.kimsmik.kimsmikweddingapp.controller.ScrollingTextActivity;

public class GCMReceiverService extends GcmListenerService {
    public GCMReceiverService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(this.getClass().getName(), "From: " + from);
        Log.d(this.getClass().getName(), "Message: " + message);
        Intent intent = new Intent();
        intent.putExtra("message",message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(this,ScrollingTextActivity.class);
        startActivity(intent);
    }
}
