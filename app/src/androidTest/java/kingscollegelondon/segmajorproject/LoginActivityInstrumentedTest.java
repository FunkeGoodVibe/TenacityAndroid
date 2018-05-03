package kingscollegelondon.segmajorproject;


import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.Test;

public class LoginActivityInstrumentedTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity loginActivity;
    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    public LoginActivityInstrumentedTest(){
        super(LoginActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();
    }

    @Test
    public void testSuccessfullyLoginUserAndRedirectsToHomepageActivityReturnsTrue() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.HomepageActivity", null, false);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                String email = "test@gmail.com";
                loginActivity.userLogin(email, "test123");
            }
        });
        am.waitForActivityWithTimeout(10000);
        // assertEquals(0, am.getHits());

        assertEquals(1, am.getHits());
        //only works when this is the only test :(
    }

    @Test
    public void testSuccessfullLoginIncorrectPasswordReturnsFalse() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.HomepageActivity", null, false);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                String email = "test@gmail.com";
                loginActivity.userLogin(email, "test1232");
            }
        });
        am.waitForActivityWithTimeout(10000);
        assertEquals(0, am.getHits());
    }

 @Test
    public void testSuccessfullLoginIncorrectEmailReturnsFalse() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.HomepageActivity", null, false);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                String email = "fakeEmail@gmail.com";
                loginActivity.userLogin(email, "test123");
            }
        });
        am.waitForActivityWithTimeout(10000);
        assertEquals(0, am.getHits());
    }

    @Test
    public void testSuccessfullLoginNoPasswordAndEmailReturnsFalse() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.HomepageActivity", null, false);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                String email = "";
                loginActivity.userLogin(email, "");
            }
        });
        am.waitForActivityWithTimeout(10000);
        assertEquals(0, am.getHits());
    }


    @Test
    public void testButtonClickSignInSuccesfullRedirection() {
        final TextView textViewSignUp = (TextView) loginActivity.findViewById(R.id.textViewSignup);
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.UserRegistrationActivity", null, false);
        loginActivity.runOnUiThread(new Runnable() {
            public void run() {
                textViewSignUp.performClick();

            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(1, am.getHits());

    }


}
