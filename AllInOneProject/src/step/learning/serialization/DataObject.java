package step.learning.serialization;

import java.io.Serializable;

public class DataObject implements Serializable {
    private final int privateField;
    protected final double protectedField;
    public final String publicField;
    private final transient int transientField;

    public DataObject(Object... args) {
        privateField = args.length > 0 ? (int) args[0] : -1;
        protectedField = args.length > 1 ? (double) args[1] : -1;
        publicField = args.length > 2 ? (String) args[2] : "-";
        transientField = args.length > 3 ? (int) args[3] : -1;
    }

    @Override
    public String toString() {
        return String.format("{ privateField: %d, protectedField: %f," +
                        "publicField: '%s', transientField: %d }",
                privateField, protectedField, publicField, transientField);
    }
}