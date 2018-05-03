package kingscollegelondon.segmajorproject;

import kingscollegelondon.segmajorproject.MainActivity;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import org.junit.Test;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;
    private Button loginButton;
    private Button signupButton;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    @Test
    public void testLoginButton() {
        loginButton = (Button) mActivity.findViewById(R.id.btn_logIn);
        //jump to log in activity
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.LoginActivity", null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginButton.performClick();
            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(1, am.getHits());
    }

    @Test
    public void testSignUpButton() {
        signupButton = (Button) mActivity.findViewById(R.id.btn_signUp);
        //jump to log in activity
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.UserRegistrationActivity", null, false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signupButton.performClick();
            }
        });
        am.waitForActivityWithTimeout(5000);
        assertEquals(1, am.getHits());
    }
}




