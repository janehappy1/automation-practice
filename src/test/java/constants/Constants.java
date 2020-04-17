package constants;

public class Constants {

    //wait times
    public static final int PAGE_LOAD = 5000;

    public static final int ALERT_LOAD = 3000;

    //command property
    public static final String RUN_TYPE = "run-type";

    public static final String BROWSER = "browser";

    public static final String BROWSER_VERSION = "browser-version";

    //default browser version
    public static final String DEFAULT_CHROME_VERSION = "79.0";
    public static final String DEFAULT_FIREFOX_VERSION = "73.0";
    public static final String DEFAULT_OPERA_VERSION = "";
    public static final String DEFAULT_IE_VERSION = "";

    //urls
    public static final String BASE_URL = "http://automationpractice.com/index.php";

    //selenoide
    public static final String SELENOID_URL = "http://localhost:4444/wd/hub";

    //paths
    public static final String PATH_TO_CHROME_DRIVER = "src/test/resources/drivers/chromedriver.exe";
    public static final String PATH_TO_FIREFOX_DRIVER = "src/test/resources/drivers/geckodriver.exe";
    public static final String PATH_TO_OPERA_DRIVER = "src/test/resources/drivers/operadriver.exe";

}
