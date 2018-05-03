package kingscollegelondon.segmajorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebviewBonusActivity extends AppCompatActivity {

    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_bonus_activity);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        //create webView so that webpage could be viewed within the app
        WebView webb = (WebView) findViewById(R.id.webb);
        webb.setWebViewClient(new WebViewClient());
        webb.loadUrl(url);
        WebSettings webSettings = webb.getSettings();
        webSettings.setJavaScriptEnabled(true);





        txtHome = (TextView) findViewById(R.id.txt_home);
        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtProfile = (TextView) findViewById(R.id.txt_profile);


        //Menu bar

        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebviewBonusActivity.this,HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(WebviewBonusActivity.this,CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(WebviewBonusActivity.this,CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(WebviewBonusActivity.this,ProfileActivity.class);
                startActivity(intent3);
            }
        });


    }




}