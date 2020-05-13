package com.example.bookreviewapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "Books.db";
    private static final String DB_TABLE = "Books_Table";

    //coloumns
    private static final String TITLE = "TITLE";
    private static final String AUTHOR = "AUTHOR";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String SAVED = "SAVED";
    private static final String RATING = "RATING";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            TITLE + " TEXT, " +
            AUTHOR + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            SAVED + " INT, " +
            RATING + " REAL " + ")";

    public BookDB(Context context){
        super(context, DB_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase book_db) {
        book_db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase book_db, int oldVersion, int newVersion) {
        book_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(book_db);
    }

    //insert data
    public boolean insertData(String title, String author, String description, int saved, float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(AUTHOR, author);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(SAVED, saved);
        contentValues.put(RATING, rating);

        long result = db.insert(DB_TABLE, null , contentValues);

        return result != -1; //if result -1 data doesn't insert
    }

    //view data
    public Cursor viewBookData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //get book details
    public Cursor getBookData(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +TITLE+" Like '%"+title+"%'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //get saved books
    public Cursor getSavedBookData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE + " WHERE " +SAVED+" Like '%"+1+"%'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


    //update save data
    public void updateSaved(String book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(SAVED, 1);
        String whereClause = "TITLE=?";
        String whereArgs[] = {book.toString()};
        db.update(DB_TABLE, newValues, whereClause,whereArgs);
    }

    //reset save data
    public void resetSaved(String book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(SAVED, 0);
        String whereClause = "TITLE=?";
        String whereArgs[] = {book.toString()};
        db.update(DB_TABLE, newValues, whereClause,whereArgs);
    }

    //get rating
    public Float getRating(String title){
        float rating = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+ DB_TABLE + " WHERE " +TITLE+" LIKE '%" + title + "%'";
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){rating = cursor.getFloat(cursor.getColumnIndex("RATING"));}
        return rating;
    }

    //update rating
    public void updateRating(float new_rating, String book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(RATING, new_rating);
        String whereClause = "TITLE=?";
        String whereArgs[] = {book.toString()};
        db.update(DB_TABLE, newValues, whereClause,whereArgs);
    }

}
