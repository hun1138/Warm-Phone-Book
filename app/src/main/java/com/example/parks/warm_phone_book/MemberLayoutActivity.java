package com.example.parks.warm_phone_book;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemberLayoutActivity extends AppCompatActivity {

    private PersonInfo personInfoTemp;

    private TextView name;
    private TextView phoneNumber;
    private TextView callDay;
    private TextView recentCallDay;

    private Button callButton;
    private Button textButton;
    private Button addEventTextButton;

    private LinearLayout eventLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_layout);

        personInfoTemp = (PersonInfo)getIntent().getSerializableExtra("PersonInfo");

        name = (TextView)findViewById(R.id.nameId);
        phoneNumber = (TextView)findViewById(R.id.phoneNumberId);
        callDay = (TextView)findViewById(R.id.callDayId);
        recentCallDay = (TextView)findViewById(R.id.recentCallDayId);

        eventLayout = (LinearLayout)findViewById(R.id.addEventLayoutId);

        callButton = (Button)findViewById(R.id.callButtonId);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickCall(personInfoTemp.getPhoneNumber());
            }
        });

        textButton = (Button)findViewById(R.id.textButtonId);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickSendMeesage(personInfoTemp.getPhoneNumber());
            }
        });

        addEventTextButton = (Button)findViewById(R.id.addEventButtonId);
        addEventTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventText();
            }
        });


        name.setText(personInfoTemp.getName());
        phoneNumber.setText(personInfoTemp.getPhoneNumber());
        callDay.setText(personInfoTemp.getCallDday());
        recentCallDay.setText();
    }

    private void addEventText() {

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayoutTemp = (LinearLayout)inflater.inflate(R.layout.member_event_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10,10);
        //linearLayoutTemp.setPadding(20, 20, 20, 20);
        linearLayoutTemp.setLayoutParams(layoutParams);

        TextView eventText = (TextView)linearLayoutTemp.findViewById(R.id.eventTextViewId);
        //db

        eventText.setText("test");
        eventLayout.addView(linearLayoutTemp, 0); // index 0
    }

    private void ClickCall(String phoneNumber){
        //전화번호 가지고 있는 String을 보내는 함수 ?
        Intent callintent =new Intent(Intent.ACTION_DIAL);
        callintent.setData(Uri.parse("tel:"+phoneNumber));
        try{
            startActivity(callintent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //메시지 창으로 이동-->
    private void ClickSendMeesage(String phoneNumber){
        Intent sendMessageintent = new Intent(Intent.ACTION_VIEW);
        sendMessageintent.setData(Uri.parse("smsto:" + phoneNumber));
        try{
            startActivity(sendMessageintent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
