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


public class HomepageActivity extends AppCompatActivity {

    private ImageView iTopLogo;
    private ImageView iCentreLogo;
    private TextView txtAhsc;
    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);

        //The following is the code for the menu bar.
        iTopLogo = (ImageView) findViewById(R.id.Iv_topLogo);
        iCentreLogo = (ImageView) findViewById(R.id.Iv_CPCS_logo);
        txtAhsc = (TextView) findViewById(R.id.txt_AHSC);

        txtHome = (TextView) findViewById(R.id.txt_home);
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this,HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(HomepageActivity.this,CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(HomepageActivity.this,CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile = (TextView) findViewById(R.id.txt_profile);
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(HomepageActivity.this,ProfileActivity.class);
                startActivity(intent3);
            }
        });




    }}
