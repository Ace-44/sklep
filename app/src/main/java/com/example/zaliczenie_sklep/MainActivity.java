package com.example.zaliczenie_sklep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2, spinner3, spinner4;
    private EditText editTextName, editTextEmail, editTextPhone;
    private RadioGroup radioGroup;
    private Button buttonSend, buttonPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroup = findViewById(R.id.radioGroup);
        buttonSend = findViewById(R.id.buttonSend);
        buttonPreview = findViewById(R.id.buttonPreview);

        List<CustomItem> items1 = new ArrayList<>();
        items1.add(new CustomItem("BRAK", R.drawable.baseline_block_24, 0));
        items1.add(new CustomItem("Komputer Ryzen 7 3700x RTX 3060 ti 16gb ram cena: 6000", R.drawable.komputer1, 6000));
        items1.add(new CustomItem("Komputer ryzen 9 5900x RTX 4060 ti 32gb ram cena: 8000", R.drawable.komputer2, 8000));
        items1.add(new CustomItem("Komputer Ryzen 9 9900x RTX 4090 64gb ram cena: 10000", R.drawable.komputer3, 10000));

        List<CustomItem> items2 = new ArrayList<>();
        items2.add(new CustomItem("BRAK" , R.drawable.baseline_block_24, 0));
        items2.add(new CustomItem("Ducky one 2 mini RGB cena: 500zł" , R.drawable.klawiatura1, 500));
        items2.add(new CustomItem("wooting 60he cena: 1100zł", R.drawable.klawiatura2, 1100));
        items2.add(new CustomItem("atk edge 60he cena: 700zł", R.drawable.klawiatura3, 700));

        List<CustomItem> items3 = new ArrayList<>();
        items3.add(new CustomItem("BRAK", R.drawable.baseline_block_24, 0));
        items3.add(new CustomItem("Glorious Model O cena: 400zł", R.drawable.mysz1, 400));
        items3.add(new CustomItem("ATK F1 Ultimate cena: 300zł", R.drawable.mysz2, 300));
        items3.add(new CustomItem("Razer Viper Mini Signature Edition cena 1200zł", R.drawable.mysz3, 1200));

        List<CustomItem> items4 = new ArrayList<>();
        items4.add(new CustomItem("BRAK", R.drawable.baseline_block_24, 0));
        items4.add(new CustomItem("Monitor 144hz 24 cale cena:1200złBrakMonitor 144hz 24 cale cena:1200zł", R.drawable.monitor1, 1200));
        items4.add(new CustomItem("Monitor 2k 240hz 24 cale cena 1500zł", R.drawable.monitor2, 1500));
        items4.add(new CustomItem("Monitor 4k 240hz 27 cali cena 2500zł", R.drawable.monitor3, 2500));

        CustomAdapter adapter1 = new CustomAdapter(this, items1);
        CustomAdapter adapter2 = new CustomAdapter(this, items2);
        CustomAdapter adapter3 = new CustomAdapter(this, items3);
        CustomAdapter adapter4 = new CustomAdapter(this, items4);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {

            private void sendEmail(String email, String message) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Zamówienie");
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Wyślij email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "Brak aplikacji do wysyłania emaili.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClick(View v) {
                    String name = editTextName.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String phone = editTextPhone.getText().toString();
                    String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

                    CustomItem selected1 = (CustomItem) spinner1.getSelectedItem();
                    CustomItem selected2 = (CustomItem) spinner2.getSelectedItem();
                    CustomItem selected3 = (CustomItem) spinner3.getSelectedItem();
                    CustomItem selected4 = (CustomItem) spinner4.getSelectedItem();

                    int sum = selected1.getHiddenValue() + selected2.getHiddenValue() + selected3.getHiddenValue() + selected4.getHiddenValue();
                    String price = String.valueOf(sum);

                    String message = "Data zamówienia: " + date + "\nCena: " + price + "\nNazwa firmy / Imię i nazwisko: " + name + "\nSuma wartości: " + sum;

                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    String method = "";

                if (!name.isEmpty()) {
                    if (selectedId == R.id.radioEmail) {
                        if (!email.isEmpty()) {
                            method = "Email";
                            Log.d("MainActivity", "Wywołanie metody sendEmail");
                            sendEmail(email, message);
                            Log.d("MainActivity", "Metoda sendEmail zakończona");
                        } else {
                            Toast.makeText(getApplicationContext(), "Podaj adres e-mail do wysłania maila.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (selectedId == R.id.radioSms) {
                        if (isPhoneNumberValid(phone)) {
                            method = "SMS";
                            sendSms(phone, message);
                        } else {
                            Toast.makeText(getApplicationContext(), "Podaj numer telefonu do wysłania SMS.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    saveOrderToDatabase(name, email, phone, date, price, sum, method);
                } else {
                    Toast.makeText(getApplicationContext(), "Podaj imię, nazwisko/nazwę firmy", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

                CustomItem selected1 = (CustomItem) spinner1.getSelectedItem();
                CustomItem selected2 = (CustomItem) spinner2.getSelectedItem();
                CustomItem selected3 = (CustomItem) spinner3.getSelectedItem();
                CustomItem selected4 = (CustomItem) spinner4.getSelectedItem();

                int sum = selected1.getHiddenValue() + selected2.getHiddenValue() + selected3.getHiddenValue() + selected4.getHiddenValue();
                String price = String.valueOf(sum);

                String message = "Data zamówienia: " + date + "\nCena: " + price + " zł" + "\nNazwa firmy / Imię i nazwisko: " + name ;

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radioEmail) {
                    message += "\nMetoda: E-mail\nAdres e-mail: " + email;
                } else if (selectedId == R.id.radioSms) {
                    message += "\nMetoda: SMS\nNumer telefonu: " + phone;
                }

                showPreviewDialog(message);
            }
        });
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null && !phoneNumber.isEmpty();
    }



    private void saveOrderToDatabase(String name, String email, String phone, String date, String price, int sum, String method) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PHONE, phone);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_PRICE, price);
        values.put(DatabaseHelper.COLUMN_SUM, sum);
        values.put(DatabaseHelper.COLUMN_METHOD, method);

        long newRowId = db.insert(DatabaseHelper.TABLE_ORDERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Zamówienie zapisane w bazie danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Błąd zapisywania zamówienia.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void showAuthorInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Informacje o autorze programu")
                .setMessage("Autor programu: Antoni Kucharski\n" +
                        "Informacje o autorze: Lubie biegać")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MainActivity", "onCreateOptionsMenu wywołane");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MainActivity", "onOptionsItemSelected wywołane z id: " + item.getItemId());
        int id = item.getItemId();
        String phoneNumber = editTextPhone.getText().toString();
        String message = "Przykładowa wiadomość SMS";

        if (id == R.id.menu_orders ){
            Log.d("MainActivity", "menu_orders wybrane");
            startActivity(new Intent(this, OrderListActivity.class));
            return true;
        } else if (id == R.id.menu_save_cart) {
            Log.d("MainActivity", "menu_save_cart wybrane");
            SaveCartSettings saveCartSettings = new SaveCartSettings(this);
            saveCartSettings.saveSettings();
            return true;
        } else if (id == R.id.menu_about) {
            showAuthorInfoDialog();
            return true;
        } else if (id==R.id.menu_share_cart) {
            Log.d("MainActivity", "menu_share_cart wybrane");
            ShareCart shareCart = new ShareCart(this);
            shareCart.share();
            return true;
        } else if (id == R.id.menu_send_sms) {
            Log.d("MainActivity", "menu_send_sms wybrane");
            SendSmsDirectly sendSms = new SendSmsDirectly(this);
            return true;
        } else {
            Log.d("MainActivity", "Żaden przypadek nie pasuje");
            return super.onOptionsItemSelected(item);
        }
    }


    private void sendSms(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(MainActivity.this, "SMS wysłany.", Toast.LENGTH_SHORT).show();
    }

    private void showPreviewDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Podgląd wiadomości")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public class CustomItem {
        private String text;
        private int imageResId;
        private int hiddenValue;

        public CustomItem(String text, int imageResId, int hiddenValue) {
            this.text = text;
            this.imageResId = imageResId;
            this.hiddenValue = hiddenValue;
        }

        public String getText() {
            return text;
        }

        public int getImageResId() {
            return imageResId;
        }

        public int getHiddenValue() {
            return hiddenValue;
        }
    }

    public class CustomAdapter extends ArrayAdapter<CustomItem> {

        public CustomAdapter(Context context, List<CustomItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            }

            CustomItem item = getItem(position);

            TextView textView = convertView.findViewById(R.id.spinner_text);
            ImageView imageView = convertView.findViewById(R.id.spinner_image);

            textView.setText(item.getText());
            imageView.setImageResource(item.getImageResId());

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
}

