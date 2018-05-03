package kingscollegelondon.segmajorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.File;


public class CourseWorkActivity extends AppCompatActivity {


    SliderLayout sliderShow;


    Button handoutBT1;
    Button handoutBT2;
    Button handoutBT3;
    Button handoutBT4;
    Button handoutBT5;
    Button handoutBT6;
    Button handoutBT7;
    Button handoutBT8;

    Button bonus_activityBT1;
    Button bonus_activityBT2;
    Button bonus_activityBT3;
    Button bonus_activityBT4;
    Button bonus_activityBT5;
    Button bonus_activityBT6;
    Button bonus_activityBT7;
    Button bonus_activityBT8;

    Button tasksWeek1;
    Button tasksWeek2;
    Button tasksWeek3;
    Button tasksWeek4;
    Button tasksWeek5;
    Button tasksWeek6;
    Button tasksWeek7;
    Button tasksWeek8;

    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    /**
     * This method adds an OnClickListener, which starts the TasksActivity when this listener is activated.
     * This method also passes on the the week number of the chosen week to the next activity.
     * @param button
     * @param number
     */
    public void addListenerForTasks(Button Button, int number) {
        final int week = number;
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseWorkActivity.this, TasksActivity.class);
                intent.putExtra("WEEK", week);
                startActivity(intent);
            }
        });
    }

    /**
     * This method downloads a file from a given url. If the download is successful, the file is opened.
     * @param url
     * @param file
     */
    public void callbacks(String url, File file){

        AQuery aq = new AQuery(getApplicationContext());
        aq.download(url, file, new AjaxCallback<File>() {
            @Override
            public void callback(String url, File object, AjaxStatus status) {
                //If download successful.
                if (object != null) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), "kingscollegelondon.segmajorproject.provider", object), "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getApplicationContext().startActivity(intent);
                } else
                    //If download not successful.
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    /**
     * This method passes the url to a new Intent
     * @param url
     */
    public void openingUrl(String url){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

    /**
     * This method starts the WebviewBonusActivity. It also takes a url in the form of a String and sends 
     * this to the next activity.
     * @param url
     */
    public void openingWebviewUrl(String url) {
        Intent webviewIntent = new Intent(CourseWorkActivity.this, WebviewBonusActivity.class);
        webviewIntent.putExtra("url", url);
        startActivity(webviewIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_work);


        ActivityCompat.requestPermissions(CourseWorkActivity.this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                0);


        handoutBT1 = (Button) findViewById(R.id.handoutButton1);
        handoutBT2 = (Button) findViewById(R.id.handoutButton2);
        handoutBT3 = (Button) findViewById(R.id.handoutButton3);
        handoutBT4 = (Button) findViewById(R.id.handoutButton4);
        handoutBT5 = (Button) findViewById(R.id.handoutButton5);
        handoutBT6 = (Button) findViewById(R.id.handoutButton6);
        handoutBT7 = (Button) findViewById(R.id.handoutButton7);
        handoutBT8 = (Button) findViewById(R.id.handoutButton8);





        handoutBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week1.pdf?alt=media&token=4b83574d-61bb-4723-9e26-9c5c684adfcc";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week1.pdf");

                callbacks(url, file);


            }
        });




        handoutBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week2.pdf?alt=media&token=d639cb31-6169-409f-9935-8425fa20fa38";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week2.pdf");

                callbacks(url, file);
            }
        });



        handoutBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week3.pdf?alt=media&token=07105675-2ebb-4522-bf59-9abc3d87252d";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week3.pdf");

                callbacks(url, file);


            }
        });


        handoutBT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week4.pdf?alt=media&token=c0f2674b-bdb0-45bc-9eac-746c0f8cf2f9";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week4.pdf");

                callbacks(url, file);


            }
        });


        handoutBT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week5.pdf?alt=media&token=e05a6aee-ad00-4882-b9ba-ca1df62adbc4";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week5.pdf");

                callbacks(url, file);


            }
        });


        handoutBT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week6.pdf?alt=media&token=bbd244d2-ccd6-4e13-b1de-c083d2071644f";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week6.pdf");

                callbacks(url, file);


            }
        });


        handoutBT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week7.pdf?alt=media&token=a8b1ce13-ba68-4bbc-af4e-524a8e8f928e";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week7.pdf");

                callbacks(url, file);


            }
        });


        handoutBT8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/week8.pdf?alt=media&token=7548ad4d-1ed5-4c4f-9c4c-bdbc74f3ba39";
                final File file = new File(Environment.getExternalStorageDirectory() + "/Download/week8.pdf");

                callbacks(url, file);


            }
        });



        bonus_activityBT1 = (Button) findViewById(R.id.bonus_activityButton1);
        bonus_activityBT2 = (Button) findViewById(R.id.bonus_activityButton2);
        bonus_activityBT3 = (Button) findViewById(R.id.bonus_activityButton3);
        bonus_activityBT4 = (Button) findViewById(R.id.bonus_activityButton4);
        bonus_activityBT5 = (Button) findViewById(R.id.bonus_activityButton5);
        bonus_activityBT6 = (Button) findViewById(R.id.bonus_activityButton6);
        bonus_activityBT7 = (Button) findViewById(R.id.bonus_activityButton7);
        bonus_activityBT8 = (Button) findViewById(R.id.bonus_activityButton8);



        bonus_activityBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://docs.google.com/forms/d/1ntotnG1klkPF0bP0l3XYiDc6eM4PjtD3-YnzA4z8ZDM/viewform";

                openingWebviewUrl(url);

            }
        });


        bonus_activityBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url ="https://docs.google.com/forms/d/e/1FAIpQLSeIlLij88E_yNSHWbWqTO7aID8x_5fuBw9VNgY5re392rdy6A/viewform";

                openingWebviewUrl(url);

            }
        });


        bonus_activityBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url = "https://docs.google.com/forms/d/1gXSlJ8b5F7GgOavKbAT1sg_R22WVEI8BZe8TzyYSbeY/viewform";
                openingWebviewUrl(url);

            }
        });


        bonus_activityBT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url ="https://docs.google.com/forms/d/e/1FAIpQLSfXhnrgVnb3v9cssSWaU40L_mOTXpUL7CHxe-W1f9Ma2GPthQ/viewform";
                openingWebviewUrl(url);

            }
        });


        bonus_activityBT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url ="https://docs.google.com/forms/d/1oVJNrThMfV3qAGYHtm0FmcSdLcLNyHShMpz2rvZMyAs/viewform";
                openingWebviewUrl(url);


            }
        });


        bonus_activityBT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url ="https://drive.google.com/open?id=1ZgzkKqSqHABjMq9Y6Q_0G1oKJw8_RbuN";
                openingWebviewUrl(url);

            }
        });


        bonus_activityBT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://twitter.com/CPCS_EPEC";
                openingWebviewUrl(url);

            }
        });


        bonus_activityBT8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String url ="https://docs.google.com/forms/d/1NJW3xpsWEGAd0DWZJ1JIURr7Mw1txjzcifRgrJ_vuks";
                openingWebviewUrl(url);


            }
        });


        txtHome = (TextView) findViewById(R.id.txt_home);
        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtCourse = (TextView) findViewById(R.id.txt_course);
        txtProfile = (TextView) findViewById(R.id.txt_profile);


        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseWorkActivity.this,HomepageActivity.class);
                startActivity(intent);
            }
        });

        txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(CourseWorkActivity.this,CreateChatRoomActivity.class);
                startActivity(intent1);
            }
        });

        txtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(CourseWorkActivity.this,CourseWorkActivity.class);
                startActivity(intent2);
            }
        });

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(CourseWorkActivity.this,ProfileActivity.class);
                startActivity(intent3);
            }
        });

        tasksWeek1 = (Button) findViewById(R.id.taskButton1);
        tasksWeek2 = (Button) findViewById(R.id.taskButton2);
        tasksWeek3 = (Button) findViewById(R.id.taskButton3);
        tasksWeek4 = (Button) findViewById(R.id.taskButton4);
        tasksWeek5 = (Button) findViewById(R.id.taskButton5);
        tasksWeek6 = (Button) findViewById(R.id.taskButton6);
        tasksWeek7 = (Button) findViewById(R.id.taskButton7);
        tasksWeek8 = (Button) findViewById(R.id.taskButton8);


        addListenerForTasks(tasksWeek1, 1);
        addListenerForTasks(tasksWeek2, 2);
        addListenerForTasks(tasksWeek3, 3);
        addListenerForTasks(tasksWeek4, 4);
        addListenerForTasks(tasksWeek5, 5);
        addListenerForTasks(tasksWeek6, 6);
        addListenerForTasks(tasksWeek7, 7);
        addListenerForTasks(tasksWeek8, 8);




        sliderShow = (SliderLayout) findViewById(R.id.slider);

        // create slider view, TextSliderView has text description, DefaultSliderView does not.
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("David")
                .image("https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/ParentImageFiles%2FDavid01%20(1).jpg?alt=media&token=4ff0921c-8a1b-488f-91e1-3c745fc326e4");

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("Isabel")
                .image("https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/ParentImageFiles%2FIsabel%20(1).jpg?alt=media&token=269837ab-82df-4398-b4cb-8745baed7b0c");
//        DefaultSliderView defaultSliderView = new DefaultSliderView(this);
//        defaultSliderView
//                .description("Android")
//                .image(R.drawable.wooeng.jpeg);
        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("Rachel-Miriam")
                .image("https://firebasestorage.googleapis.com/v0/b/beingaparent-563cd.appspot.com/o/ParentImageFiles%2FRachel-Miriam%20(1)%20(1).jpg?alt=media&token=420605fc-d5a8-42a2-9c73-18d16f16113d");


        sliderShow.addSlider(textSliderView);
        sliderShow.addSlider(textSliderView2);
        sliderShow.addSlider(textSliderView3);
//        sliderShow.addSlider(defaultSliderView);



    }

    @Override
    protected void onDestroy() {
        sliderShow.stopAutoCycle();
        super.onDestroy();
    }

}
