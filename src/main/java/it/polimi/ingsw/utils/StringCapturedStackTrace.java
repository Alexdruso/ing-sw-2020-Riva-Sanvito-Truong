package it.polimi.ingsw.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A class to transform an exception to a string stack trace.
 */
public class StringCapturedStackTrace {
    StringWriter errors;

    /**
     * Initializes a new StringCapturedStackTrace
     *
     * @param ex the exception to extract the stack trace from
     */
    public StringCapturedStackTrace (Exception ex) {
        errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
    }

    @Override
    public String toString() {
        return errors.toString();
    }
}
