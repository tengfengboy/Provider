package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {
    MyDataBaseHelper dbHelper ;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDataBaseHelper(this,"BOOKStore.db",null,3);
        Button bt_Create = findViewById(R.id.bt_CreateDataBase);
        Button bt_add = findViewById(R.id.add);
        Button bt_delete = findViewById(R.id.delete);
        Button bt_update = findViewById(R.id.update);
        Button bt_select = findViewById(R.id.select);
        Button bt_create_database = findViewById(R.id.create_database);

        bt_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });

        //LitePal添加数据
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setName("Woe");
                book.setAuthor("Dan Brown");
                book.setPages(46);
                book.setPrice(54.6);
                book.setPress("Tinghua ");
                book.save();
                book.setPages(956);
                book.save();
            }
        });


//SQLite添加数据
//        bt_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//
//                values.put("name","tengfeng");
//                values.put("author","Dan Brown");
//                values.put("pages",454);
//                values.put("price",16.96);
//                db.insert("book",null,values);
//                values.clear();
//
//                //开始装第二条数据
//                values.put("name","tom");
//                values.put("author","Dan ben");
//                values.put("pages",444);
//                values.put("price",14.94);
//                db.insert("book",null,values);
//
//            }
//        });
        //删除数据
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class,"price <= ?","555");


               // SQLiteDatabase db = dbHelper.getWritableDatabase();
              //  db.delete("book","pages > ?",new String[]{"444"});
            }
        });
        //更新数据
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Book book = new Book();
                book.setPrice(555);
                book.setPress("Anchor");
                book.updateAll("name = ? and author = ?","Woe","Dan Brown");
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("price",10.99);
//                db.update("book",values,"id = ?",new String[]{"2"});

            }
        });
        //查询数据
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询书库库中所以的数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));


                        Log.d(TAG, "book name: "+name);
                        Log.d(TAG, "author name: "+author);
                        Log.d(TAG, "pages: "+pages);
                        Log.d(TAG, "price "+price);

                    }while (cursor.moveToNext());

                }
                cursor.close();
            }
        });

        //使用Litepal创建数据库
        bt_create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("创建datebase");
                Connector.getDatabase();
            }
        });
    }
}