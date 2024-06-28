package com.example.contacts_r7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactsDatabase {

    final String DATABASE_NAME = "ContactsDB";
    final String TABLE_NAME = "ContactsTable";
    final String KEY_ID = "_id";
    final String KEY_NAME = "_name";
    final String KEY_PHONE = "_phone";
    final int VERSION = 1;
    final Context context;
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    public ContactsDatabase(Context context)
    {
        this.context = context;

    }

    public void addContact(String name, String phone)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PHONE, phone);

        long count = sqLiteDatabase.insert(TABLE_NAME, null, cv);
        if(count>0)
        {
            Toast.makeText(context, "contact added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "contact not added", Toast.LENGTH_SHORT).show();
        }
    }


    public ArrayList<Contact> readAllContacts()
    {
        String []columns = new String[]{KEY_ID, KEY_NAME, KEY_PHONE};
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);

        int index_id = cursor.getColumnIndex(KEY_ID);
        int index_name = cursor.getColumnIndex(KEY_NAME);
        int index_phone = cursor.getColumnIndex(KEY_PHONE);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Contact temp = new Contact();
            temp.setId(cursor.getInt(index_id));
            temp.setName(cursor.getString(index_name));
            temp.setPhone(cursor.getString(index_phone));

            contacts.add(temp);
        }

        cursor.close();
        return contacts;
    }

    public void open()
    {
        myHelper = new MyHelper(context, DATABASE_NAME, null, VERSION);
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    public void close()
    {
        sqLiteDatabase.close();
        myHelper.close();
    }

    public void removeContact(int id) {
        int count = sqLiteDatabase.delete(TABLE_NAME, KEY_ID+"=?", new String[]{id+""});
        if(count>0)
        {
            Toast.makeText(context, "contact deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "contact not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateContact(int id, String name, String phone) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PHONE, phone);

        long count = sqLiteDatabase.update(TABLE_NAME, cv, KEY_ID+"=?", new String[]{id+""});
        if(count>0)
        {
            Toast.makeText(context, "contact updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "contact not updated", Toast.LENGTH_SHORT).show();
        }
    }


    private class MyHelper extends SQLiteOpenHelper
    {

        public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            CREATE TABLE ContactsTable(
                  _id INTEGER PRIMARY KEY AUTOINCREMENT,
                  _name TEXT,
                  _phone TEXT
            );
             */
            String query = "CREATE TABLE "+TABLE_NAME+"("+
                    KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NAME+" TEXT,"
                    +KEY_PHONE+" TEXT );";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // data backup
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
    }
}
