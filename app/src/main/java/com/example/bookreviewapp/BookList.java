package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import java.util.ArrayList;

public class BookList extends AppCompatActivity {

    BookDB db;

    ArrayList<String> book_name = new ArrayList<String>();
    ArrayList<String> author_name = new ArrayList<String>();
    ArrayList<String> rating = new ArrayList<String>();
    ArrayList<Integer> books = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        db = new BookDB(this);

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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_addbook,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_book){
            Intent intent = new  Intent(getBaseContext(), AddBook.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.menu_savedbook){
            Intent intent = new  Intent(getBaseContext(), BookSaved.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.menu_signout){
            Intent intent = new  Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewBookData(){
        Cursor cursor = db.viewBookData();
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
