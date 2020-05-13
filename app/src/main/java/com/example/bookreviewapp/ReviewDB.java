package com.example.bookreviewapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewDB extends SQLiteOpenHelper{
    private static final String DB_NAME = "Reviews.db";
    private static final String DB_TABLE = "Reviews_Table";

    //coloumns
    private static final String USER = "USER";
    private static final String BOOK = "BOOK";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String RATING = "RATING";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            USER + " TEXT, " +
            BOOK + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            RATING + " REAL " + ")";

    public ReviewDB(Context context){
        super(context, DB_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase review_db) {
        review_db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase review_db, int oldVersion, int newVersion) {
        review_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(review_db);
    }

    //insert data
    public boolean insertData(String user, String book, String description, float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER, user);
        contentValues.put(BOOK, book);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(RATING, rating);

        long result = db.insert(DB_TABLE, null , contentValues);

        return result != -1; //if result -1 data doesn't insert
    }

    //get review details
    public Cursor getReviewData(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +BOOK+" Like '%"+title+"%'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //user review status
    public int review(String user, String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +USER+" Like '%"+user+"%'" + " AND "+BOOK+" Like '%"+title+"%'";
        Cursor cursor = db.rawQuery(query,null);
        return  cursor.getCount();
    }

}
