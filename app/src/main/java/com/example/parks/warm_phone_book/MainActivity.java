package com.example.parks.warm_phone_book;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    LinearLayout ll;
    Button loadBtn;

    ArrayList<String> storeContacts;
    ArrayList<PersonInfo> personInfos;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name, PhoneNumber;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {Manifest.permission.READ_CONTACTS};
    private LinearLayout minMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minMemberList = (LinearLayout)findViewById(R.id.minMemberListId);

        //list=(ListView)findViewById(R.id.listView1);
        //ll = (LinearLayout)findViewById(R.id.LinearLayout1);
        loadBtn = (Button)findViewById(R.id.ContactButton);
        storeContacts = new ArrayList<String>();

        //PersonInfo 데이터
        personInfos = new ArrayList<PersonInfo>();
        CheckPermission();


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetContactIntoArrayList();

                // 화면에 띄운다
                addMinMemeberLayout();
                /*
                arrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.test,
                        R.id.textView, storeContacts
                );

                list.setAdapter(arrayAdapter);
                */
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Phone_Number", "permission OK");
                    Toast.makeText(this, "permission OK", Toast.LENGTH_LONG).show();
                } else {
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

            // PersonInfo class에 데이터를 저장후 리스트로 관리(추후 함수로 구현)
            PersonInfo personInfoTemp = new PersonInfo(0, name=name, PhoneNumber=PhoneNumber, "", "", "", "", "", "");
            personInfos.add(personInfoTemp);
            //*****
        }
        cursor.close();
    }

    private void addMinMemeberLayout() { // add min_member_layout
        for(int i = 0; i < personInfos.size(); i++) {
            PersonInfo personInfoTemp = personInfos.get(i);

            LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayoutTemp = (LinearLayout)inflater.inflate(R.layout.min_member_layout, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20,20);
            linearLayoutTemp.setLayoutParams(layoutParams);

            TextView minNameText = (TextView)linearLayoutTemp.findViewById(R.id.minNameId);
            minNameText.setText(personInfoTemp.getName());

            TextView minPhoneNumberText = (TextView)linearLayoutTemp.findViewById(R.id.minPhoneNumberId);
            minPhoneNumberText.setText(personInfoTemp.getPhoneNumber());

            /*
            TextView minDayCountText = (TextView)linearLayoutTemp.findViewById(R.id.minDayCountId);
            minDayCountText.setText("");
            */
            minMemberList.addView(linearLayoutTemp, 0);
        }
    }
}



