package dz.kyrios.adminservice.config.filter.enums;

public enum Operation {
    Less("<"),
    LessOrEquals("<="),
    NotEquals("<>"),
    Equals("="),
    Greater(">"),
    GreaterOrEquals(">="),
    Between("between"),
    Contains("contains"),
    Endswith("endswith"),
    Notcontains("notcontains"),
    Startswith("startswith"),
    In("in"),
    NotIn("notin");
    public final String label;

    private Operation(String label) {
        this.label = label;
    }

    public static Operation valueOfLabel(String label) {
        for (Operation e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public static boolean isOperation(String label){
        return valueOfLabel(label) !=null;
    }
}
