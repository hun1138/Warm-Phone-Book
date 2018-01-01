package com.example.parks.warm_phone_book;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Parks on 2018-01-01.
 */

public class DBManager extends SQLiteOpenHelper{
    public DBManager(Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //생성자에서 넘겨받은 이름과 버전의 데이터베이스가 존재하지 않을때 한번 호출됩니다.
        //새로운 데이터베이스를 생성할때 사용하기에 알맞다.

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //데이터베이스가 존재하지만 버전이 다르면 호출된다.
    }


}
