package de.philippst.abfahrtsmonitor.exception;

public class KvbAppException extends Exception{
    public KvbAppException() { super(); }
    public KvbAppException(String message) { super(message); }
    public KvbAppException(String message, Throwable cause) { super(message, cause); }
    public KvbAppException(Throwable cause) { super(cause); }
}
