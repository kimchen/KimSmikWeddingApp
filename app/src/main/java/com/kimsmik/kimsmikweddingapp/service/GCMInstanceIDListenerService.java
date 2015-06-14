package com.kimsmik.kimsmikweddingapp.service;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class GCMInstanceIDListenerService extends InstanceIDListenerService {
    public GCMInstanceIDListenerService() {
    }

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, GCMIntentService.class);
        startService(intent);
    }
}
