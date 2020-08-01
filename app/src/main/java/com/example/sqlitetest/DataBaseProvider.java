package com.example.sqlitetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseProvider extends ContentProvider {
    public static final int Book_DIR = 0;

    public static final int Book_ITEM = 1;

    public static final int CATEGORY_DIR = 2;

    public static final int CATEGORY_ITEM = 3;

    public static final String AUTHORITY = "com.example.sqlitetest.provider";

    private static UriMatcher uriMatcher;

    private MyDataBaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",Book_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",Book_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDataBaseHelper(getContext(),"BookStore.db",null,2);

        return true;
    }

    //查询数据
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case Book_DIR:
                cursor = db.query("book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Book_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("book",projection,"id = ?",new String[]{ bookId },null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("category",projection,"id = ?",new String[]{ categoryId },null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case Book_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sqlitetest.provider.book";
            case Book_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sqlitetest.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sqlitetest.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sqlitetest.provider.category";

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case Book_DIR:
            case Book_ITEM:
                long newBookId = db.insert("Book",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("category",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/"+newCategoryId);
                break;
            default:
                break;

        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)){
            case Book_DIR:
                deleteRows = db.delete("Book",selection,selectionArgs);
                break;
            case Book_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("book","id = ?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows = db.delete("category","id > ?",new String[]{categoryId});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)){
            case Book_DIR:
                updateRows = db.update("book",values,selection,selectionArgs);
                break;
            case Book_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updateRows = db.update("book",values,"id = ?",new String[]{ bookId } );
                break;
            case CATEGORY_DIR:
                updateRows = db.update("category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update("category",values,"id = ?",new String[]{ categoryId });
            default:
                break;
        }
        return updateRows;
    }
}
