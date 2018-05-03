package kingscollegelondon.segmajorproject;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }
        
    /**
     * This method signs in the user using an email and a password.
     * @param email
     * @param password
     */
    public void userLogin(String email,String password){

        //Check if user entered valid input
        if(validateUserInput(email, password)==true) {

            progressDialog.setMessage("Loginin...");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                        Intent intent = new Intent(LoginActivity.this,HomepageActivity.class);
                        //Redirect to homepage activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        progressDialog.hide();
                    }else{
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    /**
     * This method either directs the user to the UserRegistrationActivity, or attemps
     * to sign them in depending on wether they cliked the login button or the register button.
     * @param view
     */
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this,UserRegistrationActivity.class));
                break;
            case R.id.buttonLogin:
                if(editTextEmail!=null && editTextPassword !=null) {
                    String email = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    userLogin(email, password);
                    break;
                }
                break;
        }
    }

    /**
     * This method resets the email field.
     */
    public void setEditTextEmailToEmpty(){
        editTextEmail.setText("");
    }

    /**
     * This method resets the pasword field.
     */
    public void setEditTextPasswordToEmpty(){
        editTextPassword.setText("");
    }

    /**
     * This method returns a boolean to check if the user entered valid details.
     * @param email
     * @param password
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

}
