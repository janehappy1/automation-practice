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
import java.util.Optional;

public class DriverFactory {
    private final RunType runType;
    private String browserVersion;

    public DriverFactory(RunType runType) {
        this.runType = runType;
    }

    public WebDriver getDriver(String driverName, String browserVersion) throws MalformedURLException {
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
        switch (driverType) {
            case CHROME: return getChromeDriver();
            case FIREFOX: return getFirefoxDriver();
            case OPERA: return getOperaDriver();
            default: throw new IllegalArgumentException(driverType + " driver not found");
        }
    }

    private WebDriver generateRemoteWebDriver(DriverType driverType) throws MalformedURLException {
        switch (driverType) {
            case CHROME: return getRemoteChromeDriver();
            case FIREFOX: return getRemoteFirefoxDriver();
            case OPERA: return getRemoteOperaDriver();
            default: throw new IllegalArgumentException(driverType + " driver not found");
        }
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

        String selenoidUrl = Optional.of(System.getProperty("selenoid-url")).orElse(Constants.SELENOID_URL);

        return new RemoteWebDriver(
                URI.create(selenoidUrl).toURL(),
                options
        );
    }

    private WebDriver getRemoteFirefoxDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setVersion(
                browserVersion.equals("") ? Constants.DEFAULT_FIREFOX_VERSION : browserVersion
        );
        capabilities.setCapability("enableVNC", true);
        String selenoidUrl = Optional.of(System.getProperty("selenoid-url")).orElse(Constants.SELENOID_URL);
        return new RemoteWebDriver(new URL(selenoidUrl), capabilities);
    }

    private WebDriver getRemoteOperaDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
        capabilities.setVersion(
                browserVersion.equals("") ? Constants.DEFAULT_OPERA_VERSION : browserVersion
        );
        String selenoidUrl = Optional.of(System.getProperty("selenoid-url")).orElse(Constants.SELENOID_URL);
        return new RemoteWebDriver(new URL(selenoidUrl), capabilities);
    }

    private String getPath(DriverType driverType){
        String path = "";
        switch (driverType) {
            case CHROME: return Constants.PATH_TO_CHROME_DRIVER;
            case OPERA: return Constants.PATH_TO_OPERA_DRIVER;
            case FIREFOX: return Constants.PATH_TO_FIREFOX_DRIVER;
            default: return path;
        }
    }

}
