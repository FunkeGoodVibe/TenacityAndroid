package kingscollegelondon.segmajorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileActivity extends AppCompatActivity  {

    private ImageView logoBAP;
    private Button buttonChat;
    private Button buttonQuestionaire;
    private Button buttonProfile;
    private Button googleGroups;
    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        logoBAP = (ImageView) findViewById(R.id.Iv_bapLogo);
        buttonChat = (Button) findViewById(R.id.buttonChat);
        buttonQuestionaire = (Button) findViewById(R.id.buttonQuestionaire);
        buttonProfile = (Button) findViewById(R.id.buttonUserProfile);
        googleGroups = (Button) findViewById(R.id.buttonGoogleGroups);

        //Redirect users to appropriate pages based on what they click on.
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this, CreateChatRoomActivity.class));

            }
        });

        buttonQuestionaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this, QuestionnaireActivity.class));

            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this, UserProfileActivity.class));

            }
        });

        googleGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,GoogleGroupsActivity.class));
            }
        });

        
        //Menu bar
        txtHome = (TextView) findViewById(R.id.txt_home);
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(ProfileActivity.this,CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(ProfileActivity.this,CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile = (TextView) findViewById(R.id.txt_profile);
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(ProfileActivity.this,ProfileActivity.class);
                startActivity(intent3);
            }
        });



    }
}
