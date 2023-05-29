package com.example.sfmc_plugin;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.salesforce.marketingcloud.messages.push.PushMessageManager;
import com.salesforce.marketingcloud.sfmcsdk.SFMCSdk;

import java.util.Map;

public class FirebaseInstance extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        Log.d("FirebaseInstance", "onNewToken: " + s);
        SFMCSdk.requestSdk(
                (sdk) -> sdk.mp(
                        (it) -> it.getPushMessageManager().setPushToken(s)
                )
        );

        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        if (PushMessageManager.isMarketingCloudPush(remoteMessage)) {
            SFMCSdk.requestSdk(
                    (sdk) -> sdk.mp(
                            (it) -> it.getPushMessageManager().handleMessage(remoteMessage)
                    )
            );
        } else {
            super.onMessageReceived(remoteMessage);
        }

    }
}