package kingscollegelondon.segmajorproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private ImageView topLogo;
    private ImageView centerLogo;
    private Button mBtnSignUp;
    private Button mBtnLogIn;
    private TextView txtWelcome;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topLogo = (ImageView) findViewById(R.id.Iv_logo);
        centerLogo = (ImageView) findViewById(R.id.Iv_centre);
        
        //Register and login button
        mBtnSignUp = (Button) findViewById(R.id.btn_signUp);
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogIn = (Button) findViewById(R.id.btn_logIn);
        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);

            }
        });

        txtWelcome = (TextView) findViewById(R.id.txt_Welcome);

    }}
