package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import java.util.ArrayList;


public class ReviewList extends AppCompatActivity{

    ReviewDB db;

    ArrayList<String> user = new ArrayList<String>();
    ArrayList<String> user_review = new ArrayList<String>();
    String book;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_review_list);
        db = new ReviewDB(this);

        title = findViewById(R.id.BookReviewTitle);

        Bundle bundle = getIntent().getExtras();
        book = bundle.getString("book");

        ListView reviewList = (ListView) findViewById(R.id.BookReviewListview);
        title.setText(book);
        getReviewData();

        ReviewCustomAdapter customAdapter = new ReviewCustomAdapter(getApplicationContext(), user, user_review);
        reviewList.setAdapter(customAdapter);
    }

    private void getReviewData(){
        Cursor cursor = db.getReviewData(book);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Reviews yet!",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                user.add(cursor.getString(cursor.getColumnIndex("USER")));
                user_review.add(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            }
        }
    }

}
