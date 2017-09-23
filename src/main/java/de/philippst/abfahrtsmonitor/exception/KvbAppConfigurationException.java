package de.philippst.abfahrtsmonitor.exception;

public class KvbAppConfigurationException extends KvbAppException{
    public KvbAppConfigurationException() { super(); }
    public KvbAppConfigurationException(String message) { super(message); }
    public KvbAppConfigurationException(String message, Throwable cause) { super(message, cause); }
    public KvbAppConfigurationException(Throwable cause) { super(cause); }
}
