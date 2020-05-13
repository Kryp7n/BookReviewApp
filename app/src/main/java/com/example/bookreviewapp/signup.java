package com.example.bookreviewapp;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    UserDB user_db;
    Button sign_up;
    EditText name, phone, email, password, confpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        user_db = new UserDB(this);

        sign_up = findViewById(R.id.SubmitSignup);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.SignupPass);
        confpass = findViewById(R.id.SignupConf);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signup_name = name.getText().toString();
                String signup_phone = phone.getText().toString();
                String signup_email = email.getText().toString();
                String signup_password = password.getText().toString();
                String signup_confpass = confpass.getText().toString();
                String saved = "";

                Cursor cursor = user_db.userExist(signup_email);
                if(cursor.getCount() < 1){
                    if(signup_name.length() != 0 && signup_phone.length() != 0 && signup_email.length() != 0 && signup_password.length() != 0
                            && user_db.insertData(signup_name,signup_phone,signup_email,signup_password,saved)){

                        if(signup_password.equals(signup_confpass)) {
                            Toast.makeText(signup.this, "Signed Up", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            phone.setText("");
                            email.setText("");
                            password.setText("");
                            confpass.setText("");
                        }
                        else{
                            Toast.makeText(signup.this, "Passwords Mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(signup.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(signup.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    password.setText("");
                    confpass.setText("");
                }
            }
        });
    }
}
