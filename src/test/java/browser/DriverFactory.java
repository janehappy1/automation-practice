package browser;

import constants.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class DriverFactory {
    private RunType runType;
    private String browserVersion;
    private String driverName;

    public DriverFactory(RunType runType) {
        this.runType = runType;
    }

    public WebDriver getDriver(String driverName, String browserVersion) throws MalformedURLException {
        this.driverName = driverName;
        this.browserVersion = browserVersion;
        WebDriver driver = null;
        if (DriverType.contains(driverName)) {
            DriverType driverType = DriverType.getDriverTypeByBrowserAlias(driverName);
            if (RunType.LOCAL.equals(runType))
                driver = generateWebDriver(driverType);
            else if (RunType.SELENOID.equals(runType))
                driver = generateRemoteWebDriver(driverType);
        } else {
            throw new IllegalArgumentException("Driver not correctly");
        }
        return driver;
    }

    private WebDriver generateWebDriver(DriverType driverType) {
        WebDriver driver;
        switch (driverType) {
            case CHROME -> driver = getChromeDriver();
            case FIREFOX -> driver = getFirefoxDriver();
            case OPERA -> driver = getOperaDriver();
            default -> throw new IllegalArgumentException(driverType + " driver not found");
        }
        return driver;
    }

    private WebDriver generateRemoteWebDriver(DriverType driverType) throws MalformedURLException {
        WebDriver driver;
        switch (driverType) {
            case CHROME -> driver = getRemoteChromeDriver();
            case FIREFOX -> driver = getRemoteFirefoxDriver();
            case OPERA -> driver = getRemoteOperaDriver();
            default -> throw new IllegalArgumentException(driverType + " driver not found");
        }
        return driver;
    }

    private WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", Constants.PATH_TO_CHROME_DRIVER);
        return new ChromeDriver(chromeOptions);
    }

    private WebDriver getFirefoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", Constants.PATH_TO_FIREFOX_DRIVER);
        return new FirefoxDriver(firefoxOptions);
    }


    private WebDriver getOperaDriver() {
        OperaOptions operaOptions = new OperaOptions();
        return new OperaDriver(operaOptions);
    }

    private WebDriver getRemoteChromeDriver() throws MalformedURLException {
       /* DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setVersion(
                browserVersion.equals("") ? Constants.DEFAULT_CHROME_VERSION : browserVersion
        );
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        return new RemoteWebDriver(URI.create(Constants.SELENOID_URL).toURL(), capabilities);*/
       ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("79.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        options.merge(capabilities);

        return new RemoteWebDriver(
                URI.create("http://localhost:4444/wd/hub").toURL(),
                options
        );
    }

    private WebDriver getRemoteFirefoxDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setVersion(
                browserVersion.equals("") ? Constants.DEFAULT_FIREFOX_VERSION : browserVersion
        );
        capabilities.setCapability("enableVNC", true);
        return new RemoteWebDriver(new URL(Constants.SELENOID_URL), capabilities);
    }

    private WebDriver getRemoteOperaDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
        capabilities.setVersion(
                browserVersion.equals("") ? Constants.DEFAULT_OPERA_VERSION : browserVersion
        );
        return new RemoteWebDriver(new URL(Constants.SELENOID_URL), capabilities);
    }

    private String getPath(DriverType driverType){
        String path = "";
        return switch (driverType) {
            case CHROME -> Constants.PATH_TO_CHROME_DRIVER;
            case OPERA -> Constants.PATH_TO_OPERA_DRIVER;
            case FIREFOX -> Constants.PATH_TO_FIREFOX_DRIVER;
            default -> path;
        };
    }

}
