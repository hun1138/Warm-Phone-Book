package com.example.parks.warm_phone_book;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout minMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minMemberList = (LinearLayout)findViewById(R.id.minMemberListId);
    }

    private void addMinMemeberLayout() { // add min_member_layout
        for(int i = 0; i < 10; i++) {
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayoutTemp = (LinearLayout)inflater.inflate(R.layout.min_member_layout, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20,20);
            linearLayoutTemp.setLayoutParams(layoutParams);
            minMemberList.addView(linearLayoutTemp, 0);
        }
    }
}
