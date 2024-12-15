package com.example.zaliczenie_sklep;

import android.content.Context;
import android.content.Intent;

public class ShareCart {
    private Context context;

    public ShareCart(Context context) {
        this.context = context;
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Zawartość koszyka");
        context.startActivity(Intent.createChooser(intent, "Udostępnij koszyk..."));
    }
}
