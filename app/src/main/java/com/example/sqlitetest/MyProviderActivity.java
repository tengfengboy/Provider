package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyProviderActivity extends AppCompatActivity {
    private String newId;
    String TAG = "MyProviderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Button addData = (Button) findViewById(R.id.add_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);

        //添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.sqlitetest.provider/book");

                ContentValues values = new ContentValues();
                values.put("name","A Clash of kings");
                values.put("Author","George Martin");
                values.put("pages",1040);
                values.put("price",22.85);

                //将value添加到Contentvalue中然后注入到Uri中
                Uri newUri = getContentResolver().insert(uri,values);

                //插入成功后会返回一个id，将这个取出来
                newId = newUri.getPathSegments().get(1);


            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.sqlitetest.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d(TAG, "book name is: " + name);
                        Log.d(TAG, "book author is: " + author);
                        Log.d(TAG, "book pages is: " + pages);
                        Log.d(TAG, "book price is: " + price);


                    }
                    cursor.close();
                }
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.sqlitetest.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages", 156);
                values.put("price", 24.05);
                getContentResolver().update(uri, values, null, null);
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.sqlitetest.provider/book/" + newId);
                getContentResolver().delete(uri,null,null);
            }
        });

    }
}