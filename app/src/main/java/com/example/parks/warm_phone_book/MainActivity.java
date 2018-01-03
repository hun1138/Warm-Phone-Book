package com.example.parks.warm_phone_book;

<<<<<<< HEAD
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    LinearLayout ll;
    Button loadBtn;

    ArrayList<String> storeContacts;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name, PhoneNumber;



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {Manifest.permission.READ_CONTACTS};
=======
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout minMemberList;
>>>>>>> 64aa19224906d6dbb3580ecfea388a9c9f1002f6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        list=(ListView)findViewById(R.id.listView1);
        //ll = (LinearLayout)findViewById(R.id.LinearLayout1);
        loadBtn = (Button)findViewById(R.id.ContactButton);
        storeContacts = new ArrayList<String>();
        CheckPermission();

        loadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GetContactIntoArrayList();
                arrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.test,
                        R.id.textView, storeContacts
                );

                list.setAdapter(arrayAdapter);
            }
        });
    }


    private void CheckPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
                Log.i("Phone_Number","shoudShowRequestPermission");
                Toast.makeText(this, "need permission", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);

            }
            else{
                Log.i("Phone_Number", "requestPermission");
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        }
        else{
            Log.i("Phone_Number", "Into");
            //LoadContactsAyscn lca = new LoadContactsAyscn();
            //lca.execute();
            Log.i("Phone_Number", "End");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult){
        switch(requestCode){
            case REQUEST_EXTERNAL_STORAGE:{
                if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("Phone_Number", "permission OK");
                    Toast.makeText(this,"permission OK", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "need permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void GetContactIntoArrayList(){
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            PhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            storeContacts.add(name + " " + ":" + " " + PhoneNumber);
        }
        cursor.close();
    }
/*
    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>>{
        ProgressDialog pd;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pd = ProgressDialog.show(MainActivity.this, "Loading Contacts", "PleaseWait");
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params){
            ArrayList<String> contacts = new ArrayList<String>();

            Cursor c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

            while(c.moveToNext()){
                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(contactName + ":" +phNumber);
            }
            c.close();
            return contacts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> contacts){
            super.onPostExecute(contacts);
            pd.cancel();
            ll.removeView(loadBtn);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.test, contacts);
            list.setAdapter(adapter);
        }
    }
    */
=======
        minMemberList = (LinearLayout)findViewById(R.id.minMemberListId);
        addMinMemeberLayout();
    }

    private void addMinMemeberLayout() { // add min_member_layout
        for(int i = 0; i < 20; i++) {
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayoutTemp = (LinearLayout)inflater.inflate(R.layout.min_member_layout, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20,20);
            linearLayoutTemp.setLayoutParams(layoutParams);
            minMemberList.addView(linearLayoutTemp, 0);
        }
    }
>>>>>>> 64aa19224906d6dbb3580ecfea388a9c9f1002f6
}



