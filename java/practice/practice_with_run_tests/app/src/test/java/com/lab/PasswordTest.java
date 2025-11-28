package com.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Password implementations.
 * 
 * To test different buggy versions, simply uncomment the corresponding
 * getPassword() method and comment out the others.
 * 
 * Available implementations:
 * - Password: Correct implementation
 * - BugDoesNotTrim: Does not trim whitespace
 * - BugToShortPassword: Allows passwords shorter than 12 characters
 * - BugVeryShort: Allows way to short passwords
 * - BugWrongExceptionMessage: Wrong exception message for short passwords
 * - BugMissingPasswordLengthCheck: Does not throw exception for short passwords
 * - BugMissingNumberCheck: Does not throw exception if password lacks a number
 * - BugIsPasswordSameAlwaysTrue: isPasswordSame always returns true
 * - BugWrongHashingAlgorithm: Wrong hashing algorithm
 */

public class PasswordTest {
    private IPassword getPassword(String s) throws Exception {
         return (IPassword) new Password(s);
        // return (IPassword) new BugDoesNotTrim(s);
        // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
        // return (IPassword) new BugMissingNumberCheck(s);
        // return (IPassword) new BugMissingPasswordLengthCheck(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugVeryShort(s);
        // return (IPassword) new BugWrongExceptionMessage(s);
        // return (IPassword) new BugWrongHashingAlgorithm(s);
    }

    @Test
    public void shouldAlwaysPass() throws Exception {
        assertTrue(true);
    }

    @Test
    public void DoesPasswordNotTrim() throws Exception {
        assertThrows(Exception.class, () -> getPassword("            Example123"));
    }

    @Test
    public void IsPasswordAlwaysSame() throws Exception {
        var buggy = getPassword("Examplepassword123");
        var buggy2 = getPassword("Examplepass1234");
        assertFalse(buggy.isPasswordSame(buggy2));
    }

    @Test
    public void isItMissingNumbers() throws Exception {
        assertThrows(Exception.class, () -> getPassword("Passwordmrmrmrmr"));
    }

    @Test
    public void doesItCheckPassLength() throws Exception {
        assertThrows(Exception.class, () -> getPassword("Littlepass1"));
    }

    @Test
    public void smallerPasswordReq() throws Exception {
        assertThrows(Exception.class, () -> getPassword("Littl1"));
    }

    @Test
    public void isTheExceptionWrong() throws Exception {
        Exception theException = assertThrows(Exception.class, () -> getPassword("Example1"));
        
        assertEquals("To short password", theException.getMessage());
    }

    @Test
    public void isTheHashingWrong() throws Exception {
        IPassword cHash = new Password("Examplepassword123");
        var wrongHash = getPassword("Examplepassword123");
        assertEquals(cHash.getPasswordHash(), wrongHash.getPasswordHash());
    }
}
