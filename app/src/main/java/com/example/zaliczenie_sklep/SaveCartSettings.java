package com.example.zaliczenie_sklep;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SaveCartSettings {
    private Context context;

    public SaveCartSettings(Context context) {
        this.context = context;
    }

    public void saveSettings() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CartSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Setting", "value");
        editor.apply();
        Toast.makeText(context, "Ustawienia koszyka zapisane.", Toast.LENGTH_SHORT).show();
    }
}
