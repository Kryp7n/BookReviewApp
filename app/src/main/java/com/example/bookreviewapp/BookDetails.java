package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetails extends AppCompatActivity {

    TextView title, description, author;
    BookDB db = new BookDB(this);
    ReviewDB rdb = new ReviewDB(this);
    UserDB udb = new UserDB(this);
    Button view_review, add_review, save;
    String book, user_mail;
    boolean review = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        Bundle bundle = getIntent().getExtras();
        book = bundle.getString("book");
        user_mail = new signin().getUser();

        title = findViewById(R.id.BookTitle);
        description = findViewById(R.id.BookDescription);
        author = findViewById(R.id.BookAuthor);
        view_review = findViewById(R.id.Reviews);
        add_review = findViewById(R.id.AddReview);
        save = findViewById(R.id.Save);

        Cursor cursor = db.getBookData(book);
        while(cursor.moveToNext()){
            title.setText(cursor.getString(cursor.getColumnIndex("TITLE")));
            description.setText(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            author.setText("Author: " + cursor.getString(cursor.getColumnIndex("AUTHOR")));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateSaved(book);
                udb.updateSavedData(book,udb.getSavedData(user_mail),user_mail);
                Toast.makeText(getApplicationContext(),"Saved" ,Toast.LENGTH_SHORT).show();
            }
        });

        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdb.review(udb.getUsername(new signin().getUser()),book) < 1){
                    Intent intent = new  Intent(getBaseContext(), AddReview.class);
                    intent.putExtra("book", book);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Review already added" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        view_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), ReviewList.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

    }

}