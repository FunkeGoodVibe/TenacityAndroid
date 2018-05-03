package kingscollegelondon.segmajorproject;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginActivityTests {

    @Test
    public void testPasswordIsLessThan6ReturnsFalse() throws Exception {
        String email = "Email@gmail.com";
        String password = "12345";
        assertEquals(false, new LoginActivity().validateUserInput(email,password));
    }

    @Test
    public void testPasswordFieldIsEmptyReturnsFalse() throws Exception {
        String email = "Email@gmail.com";
        String password = "";
        assertEquals(false, new LoginActivity().validateUserInput(email,password));
    }
    @Test
    public void testValidPasswordAndEmailReturnsTrue() throws Exception {
        String email = "Email@gmail.com";
        String password = "123456789";
        assertEquals(true, new LoginActivity().validateUserInput(email,password));
    }

    @Test
    public void testEmptyEmailReturnsFalse() throws Exception {
        String email = "";
        String password = "123456789";
        assertEquals(false, new LoginActivity().validateUserInput(email, password));
    }
}

