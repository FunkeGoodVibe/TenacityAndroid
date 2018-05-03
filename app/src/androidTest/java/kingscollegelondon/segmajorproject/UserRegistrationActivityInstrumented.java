package kingscollegelondon.segmajorproject;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;

import java.util.Random;


public class UserRegistrationActivityInstrumented extends ActivityInstrumentationTestCase2<UserRegistrationActivity> {
    private UserRegistrationActivity userRegistrationActivity;
    private Button loginButton;

    public UserRegistrationActivityInstrumented() {
        super(UserRegistrationActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        userRegistrationActivity = getActivity();
    }

    @Test
    public void testInvalidEmailReturnsFalse() {

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
                userRegistrationActivity.registerUser(( ".com"), randomPassword());

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(0, am.getHits());

    }


    @Test
    public void testInvalidPasswordReturnsFalse() {

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
                userRegistrationActivity.registerUser((randomEmail() + "@" + randomEmail() + ".com"), "P");

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(0, am.getHits());

    }

    @Test
    public void testPasswordIsLessThan6CharactersFails() {

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
                userRegistrationActivity.registerUser((randomEmail() + "@" + randomEmail() + ".com"), "Less6");

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(0, am.getHits());

    }

    @Test
    public void testEmptyEmailAndPassowordFails() {

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
                userRegistrationActivity.registerUser("" , "");

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(0, am.getHits());

    }


//generate a random password
    public String randomPassword(){
        final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();

        String emailPassword="";
        Random r = new Random();

        for (int i = 0; i < 8; i++) {
            emailPassword+=alphabet.charAt(r.nextInt(N));
        }
        return emailPassword;
    }

    //generate a random email
    public String randomEmail(){
        final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();

        String emailPassword="";
        Random r = new Random();

        for (int i = 0; i < 9; i++) {
            emailPassword+=alphabet.charAt(r.nextInt(N));
        }
        return emailPassword;
    }


    @Test
    public void testSuccessfullyRegistersUsersAndRedirectsToProfileActivityReturnsTrue() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
               String email = randomEmail() + "@" + randomEmail() + ".com";
                userRegistrationActivity.registerUser(email, randomPassword());
            }
        });
        am.waitForActivityWithTimeout(10000);
       // assertEquals(0, am.getHits());

        assertEquals(1, am.getHits());
        //only works when this is the only test :(
    }

    @Test
    public void testButtonClickRegisterSuccesfullRedirection() {
    final TextView textViewLogin = (TextView) userRegistrationActivity.findViewById(R.id.textViewSignin);
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.LoginActivity", null, false);
        userRegistrationActivity.runOnUiThread(new Runnable() {
            public void run() {
                textViewLogin.performClick();

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(1, am.getHits());

    }

}
