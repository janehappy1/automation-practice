package browser;

public enum DriverType {
    CHROME("chrome"),
    IE("ie"),
    FIREFOX("firefox"),
    OPERA("opera");

    private final String browserAlias;

    DriverType(String browserAlias) {
        this.browserAlias = browserAlias;
    }

    public String getBrowserAlias(){
        return this.browserAlias;
    }

    public static boolean contains(String browserAlias){
        for (DriverType browser : DriverType.values()) {
            if(browser.getBrowserAlias().equals(browserAlias))
                return true;
        }
        return false;
    }

    public static DriverType getDriverTypeByBrowserAlias(String browserAlias){
        for (DriverType driverType : DriverType.values()) {
            if(driverType.getBrowserAlias().equals(browserAlias))
                return driverType;
        }
        throw new IllegalArgumentException(browserAlias + " not found");
    }
}
