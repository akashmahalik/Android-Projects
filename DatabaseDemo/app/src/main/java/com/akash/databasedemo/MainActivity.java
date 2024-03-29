package com.akash.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR , age INT(3) )");
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR , age INT(3),id INTEGER PRIMARY KEY )");

            myDatabase.execSQL("INSERT INTO newUsers (name , age) VALUES('Akash',20)");

            myDatabase.execSQL("INSERT INTO newUsers (name , age) VALUES('Partho',12)");
         //   myDatabase.execSQL("DELETE FROM users where name = 'Akash' LIMIT 1");
             //   myDatabase.execSQL("UPDATE users SET age  = 21 WHERE name = 'Akash' ");
               // myDatabase.execSQL("DELETE FROM users WHERE name = 'Akash' ");
//            Cursor c = myDatabase.rawQuery("SELECT * FROM users",null);
            //Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE age < 18",null);
            //Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name = 'Akash'",null);
//            Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name = 'Akash' and age>14",null);
           // Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE name LIKE '%A%' ",null);
            Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers",null);
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();
            while (c != null){
                Log.i("name",c.getString(nameIndex));
                Log.i("age",Integer.toString(c.getInt(ageIndex)));
                Log.i("id",Integer.toString(c.getInt(idIndex)));

                c.moveToNext();
            }
        }
        catch (Exception e){

        }
    }
}
