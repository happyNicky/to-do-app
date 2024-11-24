package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpPage extends AppCompatActivity {
    private EditText email,password,userName;
    private String txtEmail,txtPassword,txtUserName;
    private Button signUpButton;
    private Intent intent;
    private ProgressBar pgBar;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);


        email=findViewById(R.id.emailEditTxtSignUpPage);
        password=findViewById(R.id.passwordEditTxtSignUpPage);
        userName=findViewById(R.id.userNameEditTxtSignUpPage);
        signUpButton=findViewById(R.id.sign_up_button);
        pgBar=findViewById(R.id.Progress_bar);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgBar.setVisibility(View.VISIBLE);
                txtEmail=email.getText().toString();
                txtPassword=password.getText().toString();
                txtUserName=userName.getText().toString();
                boolean checkValidEmail= utilClass.isValidEmail(txtEmail);
                if (txtEmail.isEmpty())
                    email.setError("invalid email!");
                else if(txtPassword.isEmpty())
                    password.setError("invalid password!");
                else if (txtUserName.isEmpty())
                    userName.setError("invalid user name!");
                else if(!checkValidEmail)
                {
                    email.setError("invalid email!");
                    email.setText("");
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(signUpPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {     pgBar.setVisibility(View.GONE);
                                intent=new Intent(signUpPage.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {      pgBar.setVisibility(View.GONE);
                                Toast.makeText(signUpPage.this, "sign up faild ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}