package kingscollegelondon.segmajorproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;


public class UserProfileActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText numberOfChildren;
    private EditText telephoneNumber;
    private EditText nameOfChild;
    private Button saveButton;
    private ImageView profileImage;
    private Uri uriProfileImage;
    private EditText surname;
    private static final int  IMAGE_CHOOSER = 101;
    private ProgressDialog progressDialog;
    private String profileImageUrl;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabse;
    private  FirebaseUser currentUser;
    private Button logOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uID = currentUser.getUid();
        mUserDatabse = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        //get user from database


        profileImage = (ImageView) findViewById(R.id.imageView);

        firstName = (EditText) findViewById(R.id.editTextFirstNameUserProfile);

        surname = (EditText) findViewById(R.id.editTextSurnameUserProfile);

        firstName = (EditText) findViewById(R.id.editTextFirstNameUserProfile);

        nameOfChild = (EditText) findViewById(R.id.editTextNameOfChildUserProfile);

        telephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumberUserProfile);

        numberOfChildren = (EditText) findViewById(R.id.editTextNumberOfKidsUserProfile);

        logOut = (Button) findViewById(R.id.buttonLogOut);


        saveButton = (Button) findViewById(R.id.buttonSaveUserProfile);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromUser();
            }
        });


        //Check for changes in the database
        mUserDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("name").getValue() !=null &&  dataSnapshot.child("surname").getValue() !=null &&
                dataSnapshot.child("number of children").getValue() !=null &&  dataSnapshot.child("name of child").getValue() !=null
                ) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String surname1 = dataSnapshot.child("surname").getValue().toString();
                    String telephoneNumber1 = dataSnapshot.child("telephone number").getValue().toString();
                    String numberOfChildren1 = dataSnapshot.child("number of children").getValue().toString();
                    String nameOfChild1 = dataSnapshot.child("name of child").getValue().toString();
                    //retrieve user info

                    firstName.setText(name);
                    surname.setText(surname1);
                    telephoneNumber.setText(telephoneNumber1);
                    numberOfChildren.setText(numberOfChildren1);
                    nameOfChild.setText(nameOfChild1);

                    if(dataSnapshot.child("image").getValue() !=null) {

                        String image1 = dataSnapshot.child("image").getValue().toString();
                        Picasso.with(UserProfileActivity.this).load(image1).into(profileImage);
                        //load their profile photo into the profile image container

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
                uploadToFirebase();

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UserProfileActivity.this,LoginActivity.class));
            }
        });

    }

    //Check if user is logged in
    @Override
    protected void onStart(){
        super.onStart();
        if( mAuth.getCurrentUser() ==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    /**
     * This method uploads user info to the database.
     */
    private void uploadToFirebase(){

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", firstName.getText().toString());
        userMap.put("surname", surname.getText().toString());
        userMap.put("telephone number", telephoneNumber.getText().toString());
        userMap.put("number of children", numberOfChildren.getText().toString());
        userMap.put("name of child", nameOfChild.getText().toString());
        userMap.put("image", profileImageUrl);
        mUserDatabse.setValue(userMap);

    }

    /**
     * This method saves username and their profile photo to the server.
     */
    private void saveUserInfo() {


        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(firstName.getText().toString())
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    /**
     * This method converts the selected profile image into a bitmap.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CHOOSER && resultCode == RESULT_OK && data !=null && data.getData() !=null){
           uriProfileImage =  data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                profileImage.setImageBitmap(bitmap);

                uploadImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * This method uploads the image to firebase.
     */
    public void uploadImageToFirebase() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profileImages/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.hide();
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.hide();
                            Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    /**
     * This method asks the user to provide a profile image from their image gallery
     */
    public void getImageFromUser(){
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose profile image"), IMAGE_CHOOSER);
;    }
}
