package com.example.UntitledTestSuite.manager;

import com.example.UntitledTestSuite.helper.LoginHelper;
import com.example.UntitledTestSuite.helper.NavigationHelper;
import com.example.UntitledTestSuite.helper.TaskHelper;
import com.example.UntitledTestSuite.util.Settings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.fail;

public class ApplicationManager {

    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;
    private WebDriverWait waitDriver;
    private LoginHelper loginHelper;
    private TaskHelper taskHelper;
    private NavigationHelper navigationHelper;

    private static ThreadLocal<ApplicationManager> app = new ThreadLocal<ApplicationManager>();

    private ApplicationManager() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/79177/Downloads/chromedriver-win64_new/" +
                "chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = Settings.getBaseUrl();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
        waitDriver = new WebDriverWait(driver, Duration.ofSeconds(60));

        loginHelper = new LoginHelper(this);
        taskHelper = new TaskHelper(this);
        navigationHelper = new NavigationHelper(this, baseUrl);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
        }));
    }

    public static ApplicationManager getInstance() {
        if (app.get() == null) {
            ApplicationManager newInstance = new ApplicationManager();
            newInstance.getNavigationHelper().openAuthPage();
            app.set(newInstance);
        }
        return app.get();
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            fail(verificationErrorString);
        }
    }

    public WebDriverWait getWaitDriver() {
        return waitDriver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public TaskHelper getTaskHelper() {
        return taskHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

}
