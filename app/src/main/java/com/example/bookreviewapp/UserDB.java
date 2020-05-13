package com.example.bookreviewapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "Users.db";
    private static final String DB_TABLE = "Users_Table";

    //coloumns
    private static final String NAME = "NAME";
    private static final String PHONE = "PHONE";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String SAVED_DATA = "SAVED_DATA";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            NAME + " TEXT, " +
            PHONE + " TEXT, " +
            PASSWORD + " TEXT, " +
            SAVED_DATA + " TEXT, " +
            EMAIL + " TEXT " + ")";

    public UserDB(Context context){
        super(context, DB_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase user_db) {
        user_db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase user_db, int oldVersion, int newVersion) {
        user_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(user_db);
    }

    //insert data
    public boolean insertData(String name, String phone, String email, String password, String saved){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD, password);
        contentValues.put(SAVED_DATA, saved);


        long result = db.insert(DB_TABLE, null , contentValues);

        return result != -1; //if result -1 data doesn't insert
    }

    //view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //get username
    public String getUsername(String title){
        String user="";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select "+NAME+" from " + DB_TABLE + " WHERE " +EMAIL+" Like '%"+title+"%'";
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){user = cursor.getString(cursor.getColumnIndex("NAME"));}
        return user;
    }

    //user exist
    public Cursor userExist(String mailid){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +EMAIL+" Like '%"+mailid+"%'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //get user saved_data
    public String getSavedData(String mailid){
        String save_data = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +EMAIL+" Like '%"+mailid+"%'";
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){save_data = cursor.getString(cursor.getColumnIndex("SAVED_DATA"));}
        return save_data;
    }

    //update saved_data
    public void updateSavedData(String book, String curr_saved, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        curr_saved = curr_saved + (book + ",");
        newValues.put(SAVED_DATA,curr_saved);
        String whereClause = "EMAIL=?";
        String whereArgs[] = {user.toString()};
        db.update(DB_TABLE, newValues, whereClause,whereArgs);
    }
    
}
