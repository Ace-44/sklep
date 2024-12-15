package com.example.zaliczenie_sklep;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SendSmsDirectly {
    private Context context;

    public SendSmsDirectly(Context context) {
        this.context = context;
    }

    public void sendSms(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(context, "SMS wysłany.", Toast.LENGTH_SHORT).show();
    }
}
