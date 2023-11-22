package com.example.mobilelab2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactActivity extends AppCompatActivity {
    Button all_contacts_btn, selected_contacts_btn, back_btn;
    ListView lv_contactsList;
    ArrayAdapter contactsArrayAdapter;

    Pattern pattern = Pattern.compile(".*@ukr.net$");
    Matcher matcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        all_contacts_btn = findViewById(R.id.all_contacts_btn);
        selected_contacts_btn = findViewById(R.id.selected_contacts_btn);
        back_btn = findViewById(R.id.back_btn);
        lv_contactsList = findViewById(R.id.lv_contactsList);

        all_contacts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsArrayAdapter = new ArrayAdapter<>(ContactActivity.this, android.R.layout.simple_list_item_1, getPhoneContactsList());
                lv_contactsList.setAdapter(contactsArrayAdapter);
            }
        });

        selected_contacts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactsArrayAdapter = new ArrayAdapter<>(ContactActivity.this, android.R.layout.simple_list_item_1, getSortedContactsList());
                lv_contactsList.setAdapter(contactsArrayAdapter);
            }
        });

        back_btn.setOnClickListener(v -> {
            finish();
        });
    }

    private List<Contact> getPhoneContactsList(){

        List<Contact> returnList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(ContactActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactActivity.this, new String[] {android.Manifest.permission.READ_CONTACTS}, 0);
        }
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri_p = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        Cursor cursor_p = contentResolver.query(uri_p, null, null, null, null);
        String contactName = null;
        String contactNumber = null;
        String contactMail = null;


        while (cursor.moveToNext() && cursor_p.moveToNext()){
            contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactMail = cursor_p.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS));
            Contact contact = new Contact(contactName, contactNumber, contactMail);
            returnList.add(contact);

       }

        cursor.close();
        cursor_p.close();
        return returnList;
    }

    private List<Contact> getSortedContactsList(){

        List<Contact> returnList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(ContactActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, 0);
        }
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri_p = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        Cursor cursor_p = contentResolver.query(uri_p, null, null, null, null);

        Log.i( "CONTACT_PROVIDER", "Number fo contacts: " + Integer.toString(cursor.getCount()));
        String contactName = null;
        String contactNumber = null;
        String contactMail = null;


        while (cursor.moveToNext() && cursor_p.moveToNext()){
            contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactMail = cursor_p.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS));
            matcher = pattern.matcher(contactMail);
            if(!matcher.find()) {
                Contact contact = new Contact(contactName, contactNumber, contactMail);
                returnList.add(contact);
            }
           Log.i("CONTACT_PROVIDER", "Contact Name: " + contactName + " ContactPhone: " + contactNumber + " ContactMail: " + contactMail);
        }

        cursor.close();
        cursor_p.close();
        return returnList;
    }
}
