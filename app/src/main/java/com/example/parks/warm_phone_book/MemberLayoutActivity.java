package com.example.parks.warm_phone_book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MemberLayoutActivity extends AppCompatActivity {

    private TextView name;
    private TextView phoneNumber;
    private TextView dayCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_layout);

        PersonInfo personInfoTemp = (PersonInfo)getIntent().getSerializableExtra("PersonInfo");

        name = (TextView)findViewById(R.id.nameId);
        phoneNumber = (TextView)findViewById(R.id.phoneNumberId);
        dayCount = (TextView)findViewById(R.id.dayCountId);

        name.setText(personInfoTemp.getName());
        phoneNumber.setText(personInfoTemp.getPhoneNumber());
    }
}
