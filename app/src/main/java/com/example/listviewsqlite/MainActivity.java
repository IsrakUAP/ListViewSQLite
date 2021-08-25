package com.example.listviewsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static DBmain dBmain;
    private static EditText editName,ageName;
    private static Button submit, edit, display;
    private static SQLiteDatabase sqLiteDatabase;
    private static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBmain = new DBmain(MainActivity.this);
//        method creating
        findid();
        getData();
        clear();
        editData();

    }

    private void editData() {
        if(getIntent().getBundleExtra("userdata")!=null){

            Bundle bundle = getIntent().getBundleExtra("userdata");
        id = bundle.getInt("id");
        editName.setText(bundle.getString("name"));
        ageName.setText(bundle.getString("age"));
        edit.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
        }
    }

    private void clear() {
        editName.setText("");
        ageName.setText("");
    }


    private void getData() {
      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ContentValues contentValues = new ContentValues();
              contentValues.put("name",editName.getText().toString());
              contentValues.put("age",ageName.getText().toString());
              sqLiteDatabase = dBmain.getReadableDatabase();
              Long recid = sqLiteDatabase.insert("thing",null,contentValues);
              if(recid!=null){

                  Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
              clear();
              }
              else {

                  Toast.makeText(MainActivity.this, "Something is Wrong pls try again ", Toast.LENGTH_SHORT).show();
              }
          }
      });
      display.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
          startActivity(new Intent(MainActivity.this, DisplayData.class));
          }
      });
//      now edit the data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues= new ContentValues();
                contentValues.put("name",editName.getText().toString());
                contentValues.put("age",ageName.getText().toString());
                sqLiteDatabase = dBmain.getWritableDatabase();
                long recid= sqLiteDatabase.update("thing",contentValues,"id="+id,null);
                if(recid!=-1){
                    Toast.makeText(MainActivity.this, "Data update successfully", Toast.LENGTH_SHORT).show();
//                    when successfully edit data submit button visible and edit button despair
                    submit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                }
            else {

                    Toast.makeText(MainActivity.this, "Something wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findid() {

  editName = findViewById(R.id.editNameId);
  ageName = findViewById(R.id.ageNameId);
  submit = findViewById(R.id.idbtn_sumbit);
  edit= findViewById(R.id.idbtn_edit);
  display = findViewById(R.id.idbtn_display);
    }
}