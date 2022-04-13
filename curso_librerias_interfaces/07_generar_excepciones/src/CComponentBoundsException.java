package ccomponents;

class CComponentBoundsException extends RuntimeException
{
    int errorCode;

    public CComponentBoundsException (String message)
    {
        super (message);
    }

    public CComponentBoundsException (int errorCode, String message)
    {
        super (message);
        this.errorCode = errorCode;
    }

}