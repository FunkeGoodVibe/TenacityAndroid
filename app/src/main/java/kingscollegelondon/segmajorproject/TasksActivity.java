package kingscollegelondon.segmajorproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TasksActivity extends AppCompatActivity {

    private static final String TAG = "ExercisesActivity";
    private LinearLayout layout;
    private DatabaseReference databaseReference;
    private int week;
    private ArrayList<EditText> answerBoxes;
    private String userID;
    private Button sendButton;
    private Button sendEmailButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabse;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_exercise);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();
        TextView sessionTitle = (TextView) findViewById(R.id.sessionTitle);
        layout = (LinearLayout) findViewById(R.id.layout);
        answerBoxes = new ArrayList<>();
        sendButton = getSendButton();
        sendEmailButton = getEmailSendButton();

        //Get week number from prior activity.
        Intent intent = getIntent();
        week = intent.getIntExtra("WEEK", 0);

        //Get ID of the user currently logged in.
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        sessionTitle.setText("Session " + week);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Make sure the layout currently contains nothing but the session title.
                if (layout.getChildCount() == 1) {
                    //Get the questions from the database and supply empty boxes for the answers.
                    for (DataSnapshot child : dataSnapshot.child("/Homework/Session " + week + "/Questions").getChildren()) {
                        layout.addView(getQuestionBox(child.getValue(String.class)));
                        EditText emptyAnswerBox = getAnEmptyAnswerBox();
                        answerBoxes.add(emptyAnswerBox);
                        layout.addView(emptyAnswerBox);
                    }
                    
                    fetchAnyExistingAnswersFromDatabase(dataSnapshot, week);
                    layout.addView(sendButton);
                    layout.addView(sendEmailButton);
                }
                Log.d(TAG, "Questions fetched from the database.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "An error occurred while fetching the questions from the database.");
            }
        });

    }

    /**
     * This method returns an arraylist with user answers for a specific week.
     * @return ArrayList<String>
     */
    public ArrayList<String> getUserData() {
        ArrayList<String> userAnswers = new ArrayList<String>();
        String answer1 = answerBoxes.get(0).getText().toString();
        userAnswers.add(answer1);


        try {
            String answer2 = answerBoxes.get(1).getText().toString();
            userAnswers.add(answer2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String answer3 = answerBoxes.get(2).getText().toString();
            userAnswers.add(answer3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userAnswers;
    }


    /**
     * This method retrieves parents first name
     * if it's not available because the user haven't completed
     * their profile page then it returns their email address which they
     * used to sign in to the app.
     * @return String
     */
    public String getParentName() {
        mAuth = FirebaseAuth.getInstance();
         String email = mAuth.getCurrentUser().getEmail();

        if (mAuth.getCurrentUser().getDisplayName() !=null) {
            return mAuth.getCurrentUser().getDisplayName();
        } else {
            return email;
        }
    }



    /**
     * This method opens users email account and automatically
     * fills in their homework responses and sends it to a specified
     * email.
     */
    public void sendEmailToFacilitator(){

        ArrayList userAnswers = getUserData();
        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);

        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getParentName()+ " answers");

        String userAnswersString = "";
        for(int i=0;i<userAnswers.size();i++){
            userAnswersString += "Question " + Integer.valueOf(((Integer) i)+1);
            userAnswersString += "\n";
            userAnswersString +=userAnswers.get(i).toString();
            userAnswersString += "\n";
        }

        emailIntent.putExtra(Intent.EXTRA_TEXT, userAnswersString);

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }

    
    /**
     * This method checks if any answers already exist in the database for the given week's questions. If there
     * are any answers these are fetched from the database.
     * @param dataSnapshot
     * @param week
     */
    private void fetchAnyExistingAnswersFromDatabase(DataSnapshot dataSnapshot, int week) {
        if (dataSnapshot.hasChild("/Users/" + userID + "/answers/week" + week)) {
            for (int i = 0; i < answerBoxes.size(); i++) {
                DataSnapshot answer = dataSnapshot.child("/Users/" + userID + "/answers/week" + week + "/answer" + (i + 1));
                if (answer.exists()) {
                    answerBoxes.get(i).setText(answer.getValue().toString());
                }
            }
        }
    }

    /**
     * This method creates and returns a button which sends the user's answers to the database
     * when clicked.
     * @return Button
     */
    private Button getSendButton() {
        Button button = new Button(this);
        button.setText("Send");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswersToDatabase();
            }
        });
        return button;
    }

    /**
     * This method creates and returns a button which allows the user to send an email to
     * the facilitators.
     * @return Button
     */
    private Button getEmailSendButton() {
        Button button = new Button(this);
        button.setText("Send to Facilitator");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailToFacilitator();
            }
        });
        return button;
    }

    /**
     * This method creates and returns an empty EditText.
     * @return EditText
     */
    private EditText getAnEmptyAnswerBox() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        EditText newEditText = new EditText(this);
        newEditText.setLayoutParams(params);
        return newEditText;
    }


    /**
     * This method creates and returns a TextView displaying a given String.
     * @param question
     * @return TextView
     */
    private TextView getQuestionBox(String question) {
        LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView newTextView = new TextView(this);
        newTextView.setLayoutParams(parameters);
        newTextView.setText(question);
        return newTextView;
    }



    /**
     * This method sends the user's answers to the database.
     */
    private void sendAnswersToDatabase() {
        DatabaseReference currentWeek = databaseReference.child("/Users/" + userID + "/answers/week" + week);
        for (int i = 0; i < answerBoxes.size(); i++) {
             DatabaseReference answer = currentWeek.child("answer" + (i+1));
             if (answerBoxes.get(i).getText().toString().isEmpty()) {
                 answer.removeValue();
             } else {
                 answer.setValue(answerBoxes.get(i).getText().toString());
             }
        }
    }

}
