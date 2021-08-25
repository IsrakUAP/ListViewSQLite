package com.example.listviewsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayData extends AppCompatActivity {
 DBmain dBmain;
 SQLiteDatabase sqLiteDatabase;
 ListView listView;
 String []name,age;
 int []id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        dBmain = new DBmain(DisplayData.this);
//        create method
        findid();
        dis();


    }
//for display data
    private void dis() {
        sqLiteDatabase=dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select *from thing",null);
        if(cursor.getCount()>0){
            id= new int[cursor.getCount()];
            name= new String[cursor.getCount()];
            age= new String[cursor.getCount()];

            int i=0;
            while (cursor.moveToNext()){
                id[i]=cursor.getInt(0);
                name[i]=cursor.getString(1);
                age[i]=cursor.getString(2);
                i++;
            }
            Custom adapter=new Custom();
            listView.setAdapter(adapter);
        }
    }

    private void findid() {
        listView = findViewById(R.id.listViewid);

    }

    private class Custom extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView,textView1;
            ImageView edit,delete;
            convertView = LayoutInflater.from(DisplayData.this).inflate(R.layout.singledata,parent,false);
            textView = convertView.findViewById(R.id.txt_name);
            textView1 = convertView.findViewById(R.id.txt_age);
            edit = convertView.findViewById(R.id.edit_data);
            delete = convertView.findViewById(R.id.delete_data);
            textView.setText(name[position]);
            textView1.setText(age[position]);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",id[position]);
                    bundle.putString("name",name[position]);
                    bundle.putString("age",age[position]);
                    Intent intent = new Intent(DisplayData.this,MainActivity.class);
                    intent.putExtra("userdata",bundle);
                    startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sqLiteDatabase= dBmain.getWritableDatabase();
                    long recd = sqLiteDatabase.delete("thing","id="+id[position],null);
                    if (recd!=1){
                        Toast.makeText(DisplayData.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                        dis();
                    }
                }
            });

            return convertView;
        }
    }
}