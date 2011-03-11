package org.syphr.mythtv.proto;

public class CommandException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public CommandException()
    {
        super();
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(Throwable cause)
    {
        super(cause);
    }
}
