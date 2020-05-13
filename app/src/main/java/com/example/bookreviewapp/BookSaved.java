package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import java.util.ArrayList;


public class BookSaved extends AppCompatActivity {

    BookDB db;
    UserDB udb;
    String user_mail;

    ArrayList<String> book_name = new ArrayList<String>();
    ArrayList<String> author_name = new ArrayList<String>();
    ArrayList<String> rating = new ArrayList<String>();
    ArrayList<Integer> books = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        db = new BookDB(this);
        udb = new UserDB(this);
        user_mail = new signin().getUser();

        ListView bookList = (ListView) findViewById(R.id.BookListview);
        viewBookData();
        BookCustomAdapter customAdapter = new BookCustomAdapter(getApplicationContext(), books, book_name, author_name, rating);
        bookList.setAdapter(customAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view.findViewById(R.id.BookName)).getText().toString();
                Intent intent = new  Intent(getBaseContext(), BookDetails.class);
                intent.putExtra("book", selectedItem);
                startActivity(intent);
            }
        });
    }


    private void viewBookData(){
        Cursor cursor = db.getSavedBookData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Books yet!",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                book_name.add(cursor.getString(cursor.getColumnIndex("TITLE")));
                author_name.add(cursor.getString(cursor.getColumnIndex("AUTHOR")));
                float new_rating = cursor.getFloat(cursor.getColumnIndex("RATING"));
                rating.add("Rating: " + new_rating + "/5");
                books.add(R.drawable.book);
            }
        }
    }
}
