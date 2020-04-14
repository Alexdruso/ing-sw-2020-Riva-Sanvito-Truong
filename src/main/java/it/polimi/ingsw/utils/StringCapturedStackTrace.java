package it.polimi.ingsw.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringCapturedStackTrace {
    StringWriter errors;

    public StringCapturedStackTrace (Exception ex) {
        errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
    }

    @Override
    public String toString() {
        return errors.toString();
    }
}
