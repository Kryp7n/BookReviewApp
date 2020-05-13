package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddReview extends AppCompatActivity{

    ReviewDB review_db;
    UserDB user_db;
    BookDB book_db;
    signin user;

    Button review_add;
    EditText description;
    RatingBar rate;
    String book;
    float new_rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);

        Bundle bundle = getIntent().getExtras();
        book = bundle.getString("book");

        review_db = new ReviewDB(this);
        user_db = new UserDB(this);
        book_db = new BookDB(this);
        user = new signin();

        review_add = findViewById(R.id.add_review);
        description = findViewById(R.id.book_review);
        rate = findViewById(R.id.ReviewRating);

        review_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review_description = description.getText().toString();
                Cursor cursor = review_db.getReviewData(book);
                new_rating = (rate.getRating() + book_db.getRating(book))/(cursor.getCount()+1);
                new_rating = (int)new_rating;
                book_db.updateRating(new_rating,book);
                if(review_description.length() != 0
                        && review_db.insertData(user_db.getUsername(user.getUser()),book,review_description,new_rating)){
                    description.setText("");
                    rate.setRating(0);
                    Intent intent = new  Intent(getBaseContext(), BookList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddReview.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
