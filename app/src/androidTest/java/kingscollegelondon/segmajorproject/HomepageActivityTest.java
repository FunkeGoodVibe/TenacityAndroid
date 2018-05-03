package kingscollegelondon.segmajorproject;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tgbus on 2018/2/28.
 */
public class HomepageActivityTest extends ActivityInstrumentationTestCase2<HomepageActivity> {
    private HomepageActivity homepageActivity;
    private TextView txtHome;
    private TextView txtCourse;
    private TextView txtChat;
    private TextView txtProfile;


    public HomepageActivityTest(){
        super(HomepageActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        homepageActivity = getActivity();
    }
    @Test
    public void testHomeClick() {
        txtHome = (TextView) homepageActivity.findViewById(R.id.txt_home);

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.HomepageActivity", null, false);

        homepageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtHome.performClick();
            }
        });
        //Maximum waiting time is 5s
        am.waitForActivityWithTimeout(5000);
        //When it succeeds，am.getHits() is 1，otherwise  is 0
        assertEquals(1, am.getHits());
    }
@Test
    public void testCourseClick() {
        txtCourse = (TextView) homepageActivity.findViewById(R.id.txt_course);

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.CourseWorkActivity", null, false);

        homepageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtCourse.performClick();
            }
        });
        //Maximum waiting time is 5s
        am.waitForActivityWithTimeout(5000);
        //When it succeeds，am.getHits() is 1，otherwise  is 0
        assertEquals(1, am.getHits());
    }

    @Test
    public void testChatClick() {
        txtChat = (TextView) homepageActivity.findViewById(R.id.txt_chat);

        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.CreateChatRoomActivity", null, false);

        homepageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtChat.performClick();
            }
        });
        //Maximum waiting time is 5s
        am.waitForActivityWithTimeout(5000);
        //When it succeeds，am.getHits() is 1，otherwise  is 0
        assertEquals(1, am.getHits());
    }

    @Test
    public void testProfileClick() {
        txtProfile = (TextView) homepageActivity.findViewById(R.id.txt_profile);
        //jump to log in activity
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(
                "kingscollegelondon.segmajorproject.ProfileActivity", null, false);


        homepageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtProfile.performClick();
            }
        });
        //Maximum waiting time is 5s
        am.waitForActivityWithTimeout(5000);
        //When it succeeds，am.getHits() is 1，otherwise  is 0
        assertEquals(1, am.getHits());

}}