package com.lab;

public class CustomBugExceptionSpellingError implements IPassword{
    protected int passwordHash;

    public CustomBugExceptionSpellingError(String pw) throws Exception {
        String trimmedPW = pw.trim(); // Remove whitespace
        if (isToShort(trimmedPW)) {
            throw new Exception("To short password");
        }
        if (containsNumber(trimmedPW) == false) {
            throw new Exception("It contains a number, i promise >:)"); // The bug is missinformation
        }
        this.passwordHash = simpleHash(trimmedPW);
    }

    private int simpleHash(String input) {
        int hash = 7;
        for (int i = 0; i < input.length(); i++) {
            hash = hash * 31 + input.charAt(i);
        }
        return hash;
    }

    private boolean isToShort(String pw) {
        return pw.length() < 12;
    }

    private boolean containsNumber(String text) {
        return text.matches(".*\\d.*");
    }

    @Override
    public int getPasswordHash() {
        return this.passwordHash;
    }

    @Override
    public boolean isPasswordSame(IPassword other) {
        return this.passwordHash == other.getPasswordHash();
    }
}
