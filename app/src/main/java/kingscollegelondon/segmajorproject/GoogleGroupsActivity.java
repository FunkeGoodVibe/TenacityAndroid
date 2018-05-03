package kingscollegelondon.segmajorproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoogleGroupsActivity extends AppCompatActivity {


    Button camdenGroupChat;
    Button greenwichGroupChat;
    Button lambethGroupChat;
    Button lewishamGroupChat;
    Button southwarkGroupChat;
    Button westminsterGroupChat;

    Button camdenGroupChatFacilitator;
    Button greenwichGroupChatFacilitator;
    Button lambethGroupChatFacilitator;
    Button lewishamGroupChatFacilitator;
    Button southwarkGroupChatFacilitator;
    Button westminsterGroupChatFacilitator;

    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;

    /**
     * This method launches the WebviewBonusActivity and passes on a given url to 
     * said acitivy.
     * @param url
     */
    public void openingWebviewUrl(String url) {
        Intent webviewIntent = new Intent(GoogleGroupsActivity.this, WebviewBonusActivity.class);
        webviewIntent.putExtra("url", url);
        startActivity(webviewIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_groups);


        txtHome = (TextView) findViewById(R.id.txt_home);
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoogleGroupsActivity.this,HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(GoogleGroupsActivity.this,CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(GoogleGroupsActivity.this,CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile = (TextView) findViewById(R.id.txt_profile);
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(GoogleGroupsActivity.this,ProfileActivity.class);
                startActivity(intent3);
            }
        });


        camdenGroupChatFacilitator = (Button) findViewById(R.id.camdenGroupChatFacilitator);
        greenwichGroupChatFacilitator = (Button) findViewById(R.id.greenwichGroupChatFacilitator);
        lambethGroupChatFacilitator = (Button) findViewById(R.id.lambethGroupChatFacilitator);
        lewishamGroupChatFacilitator = (Button) findViewById(R.id.lewishamGroupChatFacilitator);
        southwarkGroupChatFacilitator = (Button) findViewById(R.id.southwarkGroupChatFacilitator);
        westminsterGroupChatFacilitator = (Button) findViewById(R.id.westminsterGroupChatFacilitator);


        //open web url


        camdenGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/camden-epec/";
                openingWebviewUrl(url);

            }
        });


        greenwichGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/greenwich-epec/";
                openingWebviewUrl(url);

            }
        });


        lambethGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/lambeth-epec/";
                openingWebviewUrl(url);

            }
        });


        lewishamGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/lewisham-epec/";
                openingWebviewUrl(url);

            }
        });


        southwarkGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/southwark-epec/";
                openingWebviewUrl(url);

            }
        });


        westminsterGroupChatFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!contactowner/westmister-epec/";
                openingWebviewUrl(url);

            }
        });

        camdenGroupChat = (Button) findViewById(R.id.camdenGroupChat);
        greenwichGroupChat = (Button) findViewById(R.id.greenwichGroupChat);
        lambethGroupChat = (Button) findViewById(R.id.lambethGroupChat);
        lewishamGroupChat = (Button) findViewById(R.id.lewishamGroupChat);
        southwarkGroupChat = (Button) findViewById(R.id.southwarkGroupChat);
        westminsterGroupChat = (Button) findViewById(R.id.westminsterGroupChat);



        camdenGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/d/forum/camden-epec";
                openingWebviewUrl(url);

            }
        });


        greenwichGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/d/forum/greenwich-epec";
                openingWebviewUrl(url);

            }
        });


        lambethGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!forum/lambeth-epec";
                openingWebviewUrl(url);

            }
        });


        lewishamGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!forum/lewisham-epec";
                openingWebviewUrl(url);

            }
        });


        southwarkGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!forum/southwark-epec";
                openingWebviewUrl(url);

            }
        });

        westminsterGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://groups.google.com/forum/#!forum/westmister-epec";
                openingWebviewUrl(url);

            }
        });


    }
}
