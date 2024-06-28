package com.example.contacts_r7;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity {

    ListView lvContacts;
    ContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_contacts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvContacts = findViewById(R.id.lvContacts);

        ContactsDatabase database = new ContactsDatabase(this);
        database.open();
        ArrayList<Contact> contacts = database.readAllContacts();

        adapter = new ContactAdapter(this, contacts);
        lvContacts.setAdapter(adapter);

        database.close();

    }
}