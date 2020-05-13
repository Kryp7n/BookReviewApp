package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddBook extends AppCompatActivity {

    BookDB book_db;
    Button book_add;
    EditText title, author, description;
    UserDB user_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_add);

        book_db = new BookDB(this);
        user_db = new UserDB(this);

        book_add = findViewById(R.id.add_book);
        title = findViewById(R.id.add_booktitle);
        author = findViewById(R.id.add_author);
        description = findViewById(R.id.add_bookDesc);

        book_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_title = title.getText().toString();
                String add_author = author.getText().toString();
                String add_description = description.getText().toString();

                Cursor cursor = book_db.getBookData(add_title);
                if(cursor.getCount() > 0){
                    Toast.makeText(AddBook.this, "Book Already Exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(add_title.length() != 0 && add_author.length() != 0 && add_description.length() != 0
                            && book_db.insertData(add_title,add_author,add_description,0,0) ){
                        Toast.makeText(AddBook.this, "Book Added", Toast.LENGTH_SHORT).show();
                        title.setText("");
                        author.setText("");
                        description.setText("");
                        Intent intent = new  Intent(getBaseContext(), BookList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddBook.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}