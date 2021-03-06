package com.example.lym.lilypad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LYM on 2016-11-10.
 */
public class AddObject extends Activity {
    Button btnSave, btnCancel, btnInit, btnAsk;
    EditText etName;
    SoundPool sound;
    TextView result;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    int soundID[] = new int[7];
    final String[] Bell = {"벨소리를 선택하세요", "벨소리1", "벨소리2", "벨소리3", "벨소리4", "벨소리5", "벨소리6"};
    final Integer[] BellId = {null, R.raw.bell1, R.raw.bell2, R.raw.bell3, R.raw.bell4, R.raw.bell5, R.raw.bell6};
    static int selectedBell;


   //db 생성
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "BtnTBL", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE BtnTBL(etName CHAR(20) PRIMARY KEY);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS BtnTBL");
            onCreate(db);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_object);
        setTitle("타이틀을 입력하세요.");
        result = (TextView)findViewById(R.id.result);
        etName = (EditText) findViewById(R.id.etName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);



        // 음악재생을 위해 사운드풀
        sound = new SoundPool(1, AudioManager.STREAM_RING, 0); // 동시재생수, 재생타입, 음악 품질(보통 0)

        for (int i = 1; i < soundID.length; i++) {
            soundID[i] = sound.load(this, BellId[i], 1);
        }

        // DB 입력 결과를 확인하기 위한 텍스트뷰



        spinner.setPrompt("벨소리 선택");
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Bell);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sound.play(soundID[position], 1, 1, 0, 0, 1); // 음원ID, 좌측볼륨, 우측볼륨, 우선순위, 반복, 배속
                selectedBell = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 스피너에서 아무것도 선택하지 않았을 경우
            }
        });

        myHelper = new myDBHelper(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
           public void onClick(View v) {
               //String btnName = etName.getText().toString();
               //int bell = selectedBell;

                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO BtnTBL VALUES ('"+etName.getText().toString()+"');");
                Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();
                sqlDB.close();
                Intent intent = new Intent(AddObject.this, MainActivity.class);
                startActivity(intent);
           }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
        });








    }


}