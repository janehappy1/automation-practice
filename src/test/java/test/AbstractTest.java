package test;

import browser.DriverFactory;
import browser.DriverType;
import browser.RunType;
import constants.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public abstract class AbstractTest {
    private WebDriver driver;

    public AbstractTest() {
    }

    @BeforeAll
    public static void setUp() {

    }

    @BeforeEach
    public void start() {
        initDriver();
        driver.get(Constants.BASE_URL);
    }

    @AfterEach
    public void end() {
        driver.quit();
    }

    private void initDriver() {
        String runTypeAsString = Optional.ofNullable(
                System.getProperty(Constants.RUN_TYPE)).orElse(RunType.LOCAL.getRunTypeAsString()
        );
        RunType runType = RunType.contains(runTypeAsString) ? RunType.getRunTypeByString(runTypeAsString) : RunType.LOCAL;
        String driverName = Optional.ofNullable(System.getProperty(Constants.BROWSER)).orElseGet(DriverType.CHROME::getBrowserAlias);
        String browserVersion = Optional.ofNullable(System.getProperty(Constants.BROWSER_VERSION)).orElse("");

        DriverFactory driverFactory = new DriverFactory(runType);
        try {
            this.driver = driverFactory.getDriver(driverName, browserVersion);
            this.driver.manage().window().setSize(new Dimension(1920, 1080));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
