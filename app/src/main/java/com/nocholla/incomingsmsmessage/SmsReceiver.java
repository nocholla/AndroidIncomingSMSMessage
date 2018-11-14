package com.nocholla.incomingsmsmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String senderPhoneNumber = null;

            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                senderPhoneNumber = smsMessage.getOriginatingAddress();
                Log.d("DEBUG PHONE NUMBER", senderPhoneNumber);

                String senderMessage = smsMessage.getDisplayMessageBody();
                Log.d("DEBUG MESSAGE", senderMessage);

                Toast.makeText(context, "Sender Phone Number: "+ senderPhoneNumber + ", Sender Message : " + senderMessage, Toast.LENGTH_SHORT).show();

            }

            // Reply SMS
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(senderPhoneNumber, null, "Busy at the moment. Will call you later!", null, null);

        }

    }
}
