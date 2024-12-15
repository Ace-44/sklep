package com.example.zaliczenie_sklep;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    private ListView listViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        listViewOrders = findViewById(R.id.listViewOrders);

        loadOrdersFromDatabase();
    }

    private void loadOrdersFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PHONE,
                DatabaseHelper.COLUMN_DATE,
                DatabaseHelper.COLUMN_PRICE,
                DatabaseHelper.COLUMN_SUM,
                DatabaseHelper.COLUMN_METHOD
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ORDERS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> orders = new ArrayList<>();
        while (cursor.moveToNext()) {
            String order = "Nazwa: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)) +
                    "\nEmail: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)) +
                    "\nTelefon: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)) +
                    "\nData: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)) +
                    "\nCena: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)) +
                    "\nSuma: " + cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUM)) +
                    "\nMetoda: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_METHOD));
            orders.add(order);
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        listViewOrders.setAdapter(adapter);
    }
}
