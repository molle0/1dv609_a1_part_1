package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SwedishSocialSecurityNumberTest {
    
    private SSNHelper helper;
    
    @BeforeEach
    public void setUp() {
        helper = new SSNHelper();

    }
    
    @Test
    public void shouldAcceptValidSSN() throws Exception {
        SSNHelper ssnMock = mock(SSNHelper.class);

        when(ssnMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(ssnMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(ssnMock.isValidDay("01")).thenReturn(true);
        when(ssnMock.isValidMonth("01")).thenReturn(true);
        when(ssnMock.luhnIsCorrect("900101-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber ssn = new SwedishSocialSecurityNumber("900101-0017", ssnMock);

        assertEquals("90", ssn.getYear());
        assertEquals("01", ssn.getMonth());
        assertEquals("01", ssn.getDay());
        assertEquals("0017", ssn.getSerialNumber());

        verify(ssnMock).isCorrectLength("900101-0017");
        verify(ssnMock).isCorrectFormat("900101-0017");
        verify(ssnMock).isValidMonth("01");
        verify(ssnMock).isValidDay("01");
        verify(ssnMock).luhnIsCorrect("900101-0017");
    }   

    @Test
    public void shouldThrowCorrectLengthException() throws Exception {
        SSNHelper ssnMock = mock(SSNHelper.class);

        when(ssnMock.isCorrectLength("900101-0017")).thenReturn(false);

        Exception message = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("900101-0017", ssnMock));
        assertEquals("To short, must be 11 characters", message.getMessage());

        verify(ssnMock).isCorrectLength("900101-0017");
    }

    @Test
    public void shouldThrowLuhnException() throws Exception {
        
        SSNHelper ssnMock = mock(SSNHelper.class);


        when(ssnMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(ssnMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(ssnMock.isValidMonth("01")).thenReturn(true);
        when(ssnMock.isValidDay("01")).thenReturn(true);
        when(ssnMock.luhnIsCorrect("900101-0017")).thenReturn(false);

        Exception message = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("900101-0017", ssnMock));
        assertEquals("Invalid SSN according to Luhn's algorithm", message.getMessage());

        verify(ssnMock).isCorrectFormat("900101-0017");
    }

    @Test
    public void shouldNotThrowTrimException() throws Exception {
        SSNHelper ssnMock = mock(SSNHelper.class);

        when(ssnMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(ssnMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(ssnMock.isValidDay("01")).thenReturn(true);
        when(ssnMock.isValidMonth("01")).thenReturn(true);
        when(ssnMock.luhnIsCorrect("900101-0017")).thenReturn(true);

        assertDoesNotThrow(() -> new SwedishSocialSecurityNumber("     900101-0017", ssnMock));

        verify(ssnMock).isCorrectLength("900101-0017");
        verify(ssnMock).isCorrectFormat("900101-0017");
        verify(ssnMock).isValidMonth("01");
        verify(ssnMock).isValidDay("01");
        verify(ssnMock).luhnIsCorrect("900101-0017");
    }

    @Test
    public void shouldBeCorrectYear() throws Exception {
        SSNHelper ssnMock = mock(SSNHelper.class);

        when(ssnMock.isCorrectLength("900101-0017")).thenReturn(true);
        when(ssnMock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(ssnMock.isValidMonth("01")).thenReturn(true);
        when(ssnMock.isValidDay("01")).thenReturn(true);
        when(ssnMock.luhnIsCorrect("900101-0017")).thenReturn(true);
        
        SwedishSocialSecurityNumber yearCheck = new SwedishSocialSecurityNumber("900101-0017", ssnMock);

        assertEquals("90", yearCheck.getYear());

        verify(ssnMock).isCorrectLength("900101-0017");
        verify(ssnMock).isCorrectFormat("900101-0017");
        verify(ssnMock).isValidMonth("01");
        verify(ssnMock).isValidDay("01");
        verify(ssnMock).luhnIsCorrect("900101-0017");
    }

    @Test
    public void ssnIsIncorrectLength() {
        assertFalse(helper.isCorrectLength("900101-001"));
        assertFalse(helper.isCorrectLength("900101-0011111"));
        assertFalse(helper.isCorrectLength("91-00"));
    }

    @Test
    public void ssnIsIncorrectFormat() {
        assertFalse(helper.isCorrectFormat("900100017"));
        assertFalse(helper.isCorrectFormat("abcdef-abcd"));
        assertFalse(helper.isCorrectFormat("90010/0017"));
        assertTrue(helper.isCorrectFormat("900101-0017"));
    }

    @Test
    public void ssnIsInvalidDay() {
        assertFalse(helper.isValidDay("32"));
        assertFalse(helper.isValidDay("-1"));
        assertFalse(helper.isValidDay("0"));   
    }

    @Test
    public void ssnIsValidDay() {
        assertTrue(helper.isValidDay("1"));
        assertTrue(helper.isValidDay("15"));
        assertTrue(helper.isValidDay("31"));
    }

    @Test
    public void ssnIsInvalidMonth() {
        assertFalse(helper.isValidMonth("13"));
        assertFalse(helper.isValidMonth("0"));
        assertFalse(helper.isValidMonth("-1"));
    }

    @Test
    public void ssnIsValidMonth() {
        assertTrue(helper.isValidMonth("1"));
        assertTrue(helper.isValidMonth("6"));
        assertTrue(helper.isValidMonth("12"));
    }

    @Test
    public void ssnIsIncorrectLuhn() {
        assertTrue(helper.luhnIsCorrect("500000-0009"));
    }
}