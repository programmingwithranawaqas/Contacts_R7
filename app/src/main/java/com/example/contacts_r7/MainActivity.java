package com.example.contacts_r7;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnSave, btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        btnSave.setOnClickListener(v->{
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString();

            if (TextUtils.isEmpty(name))
            {
                etName.setError("Enter correct name");
                return;
            }

            if (TextUtils.isEmpty(phone))
            {
                etPhone.setError("Enter correct phone number");
                return;
            }

            ContactsDatabase database = new ContactsDatabase(this);
            database.open();
            database.addContact(name, phone);
            database.close();
            etName.setText("");
            etPhone.setText("");
        });
        btnView.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, ViewContacts.class));
        });
    }

    private void init()
    {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
    }
}