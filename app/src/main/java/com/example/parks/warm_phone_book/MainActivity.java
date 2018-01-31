package com.example.parks.warm_phone_book;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /*
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
*/
    private AdView mAdView;

    private LinearLayout minMemberList; // min member layout
    ArrayList<PersonInfo> personInfos; // person data

    private static final int REQUEST_EXTERNAL_STORAGE = 1; //0은 수락 , 1은 거절
    private static String PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    private static String PERMISSION_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    private static String[] PERMISSION_STORAGE = {PERMISSION_READ_CONTACTS, PERMISSION_CALL_LOG};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adViewStart();

        minMemberList = (LinearLayout) findViewById(R.id.minMemberListId);

        CheckPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContact();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
    private void adViewStart() {
        //MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void init() { // 초기화
        GetContactIntoArrayList();
        makeMinMemeberLayout();
        /*
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new PersonAdapter(personInfos, this);
        recyclerView.setAdapter(adapter);
        */
    }

    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                Log.i("Phone_Number", "shoudShowRequestPermission");
                Toast.makeText(this, "need permission", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                Log.i("Phone_Number", "requestPermission");
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } else { // 데이터 화면에 뿌려주기(초기화)
            init();
        }
    }

    private void RequestPermission_CALL_LOG() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
                Toast.makeText(this, "need permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
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

    public void GetContactIntoArrayList() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int UserId = cursor.getInt((cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //int UserKey = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                //Log.i("DisPlayName PhoneNumber" , name + " : " + phoneNumber);
                //String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)); //룩업 키
                //Uri contactUri = ContactsContract.Contacts.getLookupUri(UserId, lookupKey);
                // PersonInfo class에 데이터를 저장후 리스트로 관리(추후 함수로 구현)
                //PersonInfo personInfoTemp = new PersonInfo(0, name, phoneNumber, "", "", "", "", "", "");

            PersonInfo personInfoTemp = new PersonInfo();
            personInfoTemp.setId(UserId);
            personInfoTemp.setName(name);
            personInfoTemp.setPhoneNumber(phoneNumber);

            try{
                Cursor callCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.NUMBER + "=?",
                        new String[]{String.valueOf(phoneNumber)}, "date DESC LIMIT 1");
                while(callCursor != null && callCursor.moveToNext()){
                    Long callTime = Long.valueOf(callCursor.getString(callCursor.getColumnIndex(CallLog.Calls.DATE)));

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Date dDate = new Date(callTime);
                    String today = format.format(dDate);
                    personInfoTemp.setRecentCallDay(today);
                    String diffDay = diffOfDate(callTime);
                    personInfoTemp.setCallDday(diffDay);
                }
                callCursor.close();
            }
            catch (SecurityException e){
                RequestPermission_CALL_LOG();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
            layoutParams.setMargins(10, 10, 10,10);
            //linearLayoutTemp.setPadding(20, 20, 20, 20);
            linearLayoutTemp.setLayoutParams(layoutParams);

            TextView minNameText = (TextView)linearLayoutTemp.findViewById(R.id.minNameId);
            minNameText.setText(personInfoTemp.getName());
            //minNameText.setTypeface(Typeface.createFromAsset(getAssets(), "SDMiSaeng.ttf"));

            TextView minPhoneNumberText = (TextView)linearLayoutTemp.findViewById(R.id.minPhoneNumberId);
            minPhoneNumberText.setText(personInfoTemp.getPhoneNumber());


            TextView minDayCountText = (TextView)linearLayoutTemp.findViewById(R.id.minDayCountId);
            minDayCountText.setText(personInfoTemp.getCallDday());

            linearLayoutTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent memberLayoutIntent = new Intent(MainActivity.this, MemberLayoutActivity.class);
                        memberLayoutIntent.putExtra("PersonInfo", (Serializable) personInfoTemp);
                        startActivity(memberLayoutIntent);

                        //클릭시 다이얼로 전화번호 전송하는 코드 추후에 버튼생성시 넣을것.
                        //ClickCall(personInfoTemp.getPhoneNumber());
                        //ClickSendMeesage(personInfoTemp.getPhoneNumber());
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            minMemberList.addView(linearLayoutTemp); // index 0
        }
    }

    private void AddContact(){
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        startActivity(intent);
    }

    private void AddContactsTest(PersonInfo info)
    {
        ArrayList<ContentProviderOperation> cList = new ArrayList<>();

        cList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        cList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, info.getName())
                .build()); //이름

        cList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, info.getPhoneNumber()) //전화번호
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()); // 번호 타입 핸드폰
            //save data

        cList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, info.getEmail())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());

        try{
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, cList);

        }catch (RemoteException e){
            e.printStackTrace();
        }catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //showDatePickerDialog();
                return true;
            case R.id.action_settings:
                //receiptInformHandler.showAllLayout();
                //TextView tv1= (TextView)findViewById(R.id.action_text);
                //tv1.setText("전체");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //>>: 수정 요망
    public static String diffOfDate(Long callTime)throws Exception{
        Long currentTime = System.currentTimeMillis();
        Long diffTime = (currentTime - callTime) / (24*60*60*1000);
        String msg = null;
        if(diffTime < 1){
            msg = "오늘";
        }else if(diffTime > 365){
            msg = "1년 넘음";
        }else{
            msg = diffTime.toString() + "일전";
        }
        return msg;
    }
}



