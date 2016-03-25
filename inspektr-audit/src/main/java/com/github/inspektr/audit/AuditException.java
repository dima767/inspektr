package com.github.inspektr.audit;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AuditException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 6016858958104058800L;

    private Throwable[] originalCauses;

    public AuditException() {
	super();
    }

    public AuditException(String message, Throwable cause) {
	super(message, cause);
    }

    public AuditException(String message) {
	super(message);
    }

    public AuditException(Throwable cause) {
	super(cause);
    }

    public AuditException(String message, Throwable cause, Throwable... originalExceptions) {
	super(message, cause);
	this.originalCauses = originalExceptions;
    }

    @Override
    public void printStackTrace(PrintStream ps) {
	super.printStackTrace(ps);
	ps.println("--- Additionaly, the following problems occurred: ---");
	if (originalCauses != null) {
	    int i = 0;
	    for (Throwable originalCause : originalCauses) {
		if (originalCause != null) {
		    i++;
		    ps.println("- Original Cause " + i + ": -");
		    originalCause.printStackTrace(ps);
		    ps.println("- End of Original Cause " + i + " -");
		}
	    }
	}
	ps.println("--- This is the end of the underlying problems ---");
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
	super.printStackTrace(pw);
	pw.println("--- Additionaly, the following problems occurred: ---");
	if (originalCauses != null) {
	    int i = 0;
	    for (Throwable originalCause : originalCauses) {
		if (originalCause != null) {
		    i++;
		    pw.println("- Original Cause " + i + ": -");
		    originalCause.printStackTrace(pw);
		    pw.println("- End of Original Cause " + i + " -");
		}
	    }
	}
	pw.println("--- This is the end of the underlying problems ---");
    }

}
