package browser;

import java.util.function.Supplier;

public enum RunType implements Supplier<RunType> {
    SELENOID("selenoid"),
    LOCAL("local");

    private String runTypeAsString;

    RunType(String runTypeAsString) {
        this.runTypeAsString = runTypeAsString;
    }

    public String getRunTypeAsString(){
        return this.runTypeAsString;
    }

    @Override
    public RunType get() {
        return this;
    }

    public static boolean contains(String runTypeAsString){
        for (RunType run : RunType.values()) {
            if(runTypeAsString.equals(run.getRunTypeAsString()))
                return true;
        }
        return false;
    }

    public static RunType getRunTypeByString(String runTypeAsString){
        for (RunType runType : RunType.values()) {
            if(runType.getRunTypeAsString().equals(runTypeAsString))
                return runType;
        }
        throw new IllegalArgumentException(runTypeAsString + " not found in " + RunType.class.getName());
    }
}
