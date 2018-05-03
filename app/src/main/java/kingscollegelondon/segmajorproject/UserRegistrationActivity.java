package kingscollegelondon.segmajorproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword=  (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.textViewSignin).setOnClickListener(this);

    }



    /**
     * This method either redirects the user to the login page or attempts to 
     * register them.
     * @param view
     */
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonRegister:
                if(editTextEmail!=null && editTextPassword !=null) {
                    String email = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    registerUser(email, password);
                    break;
                }
            case R.id.textViewSignin:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    /**
     * This method returns a boolean to check if the user entered valid details.
     * @param email
     * @param password
     * @return boolean
     */
    public boolean validateUserInput(String email,String password){
        boolean result=false;

        if(email.isEmpty()){
            if(getApplicationContext()!=null) {
                editTextEmail.setError("Please enter your email");
                editTextEmail.requestFocus();
            }
            return false;
        }
        if(getApplicationContext()!=null) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();
                return false;
            }

        }

        if(password.isEmpty()){
            if(getApplicationContext()!=null) {
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
            }
                return false;
        }

        if(password.length() <6){
            if(getApplicationContext()!=null) {
                editTextPassword.setError("Password must contain at least 6 characters");
                editTextPassword.requestFocus();
            }
                return false;
        }
        result = true;

        return result;
    }

    /**
     * This method resets the email field.
     */
    public void setEditTextEmailToEmpty(){
        editTextEmail.setText("");
    }

    /**
     * This method resets the password field.
     */
    public void setEditTextPasswordToEmpty(){
        editTextPassword.setText("");
    }

    /**
     * This method creates a user and updates the database.
     * @param email
     * @param password
     */
    public void registerUser(String email,String password){
       if(validateUserInput(email, password)==true) {

           progressDialog.setMessage("Registering User...");
           progressDialog.show();
           
           mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                   if (task.isSuccessful()) {
                       progressDialog.hide();
                       setEditTextPasswordToEmpty();
                       setEditTextEmailToEmpty();
                       finish();
                      startActivity(new Intent(UserRegistrationActivity.this, HomepageActivity.class));
                   } else {
                       Toast.makeText(getApplicationContext(), "Could not register, Please try again", Toast.LENGTH_SHORT).show();

                       if(task.getException() instanceof FirebaseAuthUserCollisionException){
                           Toast.makeText(getApplicationContext(), "This email has been taken. Please use a different email!", Toast.LENGTH_LONG).show();
                       }
                       progressDialog.hide();
                   }
               }
           });

       }
    }
}
