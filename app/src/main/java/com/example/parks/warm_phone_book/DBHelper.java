package com.example.parks.warm_phone_book;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parks on 2018-01-03.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "persons";
    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    private static final int DB_MODE = Context.MODE_PRIVATE;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olfVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, personInfo.getName());
        values.put(KEY_PHONE, personInfo.getPhoneNumber());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public PersonInfo getPerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID, KEY_NAME, KEY_PHONE}, KEY_ID + "=?", new String[] {String.valueOf(id)}, null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(Integer.parseInt(cursor.getString(0)));
        personInfo.setName(cursor.getString(1));
        personInfo.setPhoneNumber(cursor.getString(2));

        return personInfo;
    }

    public ArrayList<PersonInfo> getAllContacts(){
        ArrayList<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                PersonInfo personInfo = new PersonInfo();
                personInfo.setId(Integer.parseInt(cursor.getString(0)));
                personInfo.setName(cursor.getString(1));
                personInfo.setPhoneNumber(cursor.getString(2));
                personInfoList.add(personInfo);
            }while(cursor.moveToNext());
        }
        return personInfoList;
    }

    public int getPersonCount(){
        String countQery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePersonInfo(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, personInfo.getName());
        values.put(KEY_PHONE, personInfo.getPhoneNumber());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] {String.valueOf(personInfo.getId())});
    }

    public void deletePersonInfo(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] {String.valueOf(personInfo.getId())});
        db.close();
    }

}
