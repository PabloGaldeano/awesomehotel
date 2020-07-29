package com.tss.awesomehotel.exception.codes;

public enum ErrorCodes
{
    AUTHENTICATION(100, "You have to be logged-in to access here"),
    INTERNAL(1, "There was  an internal problem: %s"),
    MASQUERADE(300, "Please try again later"),
    REGISTRATION(400,"The username already exists"),
    INVALID_CREDENTIALS(401, "The specified credentials are not valid");

    private int errorCode;
    private String errorMessage;

    ErrorCodes(int code, String message)
    {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
