package com.example.lym.lilypad;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AddObject {
    Button btnAdd, btnChange;
    ListView listItem;
    EditText newItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("타이틀을 입력하세요");
        btnChange= (Button) findViewById(R.id.btnChange);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        newItem = (EditText)findViewById(R.id.newItem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddObject.class);
                startActivity(i);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT etName FROM BtnTBL;", null);
                String strName = "이름" + "\r\n" + "-------" + "\r\n";
                while(cursor.moveToNext()){

                    strName += cursor.getString(0) + "\r\n";

                }
                newItem.setText(strName);
                cursor.close();
                sqlDB.close();
            }
        });



    }



}