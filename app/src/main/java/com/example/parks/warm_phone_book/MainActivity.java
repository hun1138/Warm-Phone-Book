package com.example.parks.warm_phone_book;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editNumber;
    private Button saveButton;

    private LinearLayout minMemberList; // min member layout

    ArrayList<PersonInfo> personInfos; // person data
    // Cursor cursor;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {Manifest.permission.READ_CONTACTS};


    private int rawContactInserIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minMemberList = (LinearLayout)findViewById(R.id.minMemberListId);

        //PersonInfo 데이터
        personInfos = new ArrayList<PersonInfo>();
        CheckPermission();

        editName = (EditText)findViewById(R.id.editName);
        editNumber = (EditText)findViewById(R.id.editNumber);
        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTemp = editName.getText().toString();
                String numberTemp = editNumber.getText().toString();
                AddContact(nameTemp,numberTemp);
            }
        });
    }

    private void init() { // 초기화
        Log.i("Phone_Number", "Into");
        GetContactIntoArrayList();
        makeMinMemeberLayout();
        Log.i("Phone_Number", "End");
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
        else{ // 데이터 화면에 뿌려주기(초기화)
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Phone_Number", "permission OK");
                    Toast.makeText(this, "permission OK", Toast.LENGTH_LONG).show();

                    // 데이터 화면에 뿌려주기(초기화)
                    init();
                } else {
                    Toast.makeText(this, "need permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void GetContactIntoArrayList(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            // PersonInfo class에 데이터를 저장후 리스트로 관리(추후 함수로 구현)
            //PersonInfo personInfoTemp = new PersonInfo(0, name, phoneNumber, "", "", "", "", "", "");
            PersonInfo personInfoTemp = new PersonInfo();
            personInfoTemp.setName(name);
            personInfoTemp.setPhoneNumber(phoneNumber);
            personInfos.add(personInfoTemp);
        }
        cursor.close();
    }

    private void makeMinMemeberLayout() { // add min_member_layout
        for(int i = 0; i < personInfos.size(); i++) {
            final PersonInfo personInfoTemp = personInfos.get(i);

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
            linearLayoutTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent memberLayoutIntent = new Intent(MainActivity.this, MemberLayoutActivity.class);
                        memberLayoutIntent.putExtra("PersonInfo", (Serializable) personInfoTemp);
                        startActivity(memberLayoutIntent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            minMemberList.addView(linearLayoutTemp); // index 0
        }
    }

    private void AddContact(String name, String phone){
        /*ArrayList<ContentProviderOperation> cList = new ArrayList<>();

        cList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        cList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "YES".toString())
                .build()); //이름

        cList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "101001".toString()) //전화번호
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()); // 번호 타입 핸드폰
            //save data

        cList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, "123@xyz.com")
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());

        //Log.i("TAG", "Selected account : " + name + " - "  + phone);

        try{
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, cList);//주소록 추가
            //cList.clear(); //list init
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (OperationApplicationException e) {
            e.printStackTrace();
        }*/

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone).putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).putExtra(ContactsContract.Intents.Insert.NAME,name);

        startActivity(intent);

    }
}



