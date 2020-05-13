package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signin extends AppCompatActivity {

    UserDB db;
    BookDB bdb;

    Button login;
    EditText email, password;
    private static String login_email, login_password;

    public static String getUser(){
        return login_email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = new UserDB(this);
        bdb = new BookDB(this);

        login = findViewById(R.id.SubmitLogin);
        email = findViewById(R.id.LoginMail);
        password = findViewById(R.id.LoginPass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_email = email.getText().toString();
                login_password = password.getText().toString();
                if(login_email.length() != 0 && login_password.length() != 0) {

                    //reset saved data
                    Cursor cursor = bdb.viewBookData();
                    while(cursor.moveToNext()) {
                        String reset_book = cursor.getString(cursor.getColumnIndex("TITLE"));
                        bdb.resetSaved(reset_book);
                    }

                    //updates saved data
                    String book ="";
                    String saved_data = db.getSavedData(login_email);
                    for(int i = 0; i < saved_data.length(); i++){
                         if(saved_data.charAt(i) != ','){
                             book += saved_data.charAt(i);
                         }
                         else{
                             bdb.updateSaved(book);
                             book="";
                         }
                    }
                    email.setText("");
                    password.setText("");
                    viewData();
                }
                else{
                    Toast.makeText(signin.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData(){
        Cursor cursor = db.viewData();
        String email="", Password="";

        int registered = 0;
        while(cursor.moveToNext()){
            email = cursor.getString(cursor.getColumnIndex("EMAIL"));
            Password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            if(login_email.equals(email)) {
                registered = 1;
                if (login_password.equals(Password)){
                    Intent intent = new  Intent(getBaseContext(), BookList.class);
                    startActivity(intent);}
                else{
                    Toast.makeText(this, "Incorrect Password/Email" , Toast.LENGTH_SHORT).show();}
            }
        }
        if(registered == 0){
            Toast.makeText(this, "Not Registered" , Toast.LENGTH_SHORT).show();
        }

    }

}
