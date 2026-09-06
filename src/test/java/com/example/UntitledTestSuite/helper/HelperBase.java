package com.example.UntitledTestSuite.helper;

import com.example.UntitledTestSuite.manager.ApplicationManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelperBase {

    protected ApplicationManager manager;
    protected WebDriver driver;
    protected WebDriverWait waitDriver;
    protected boolean acceptNextAlert = true;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
        this.driver = manager.getDriver();
        this.waitDriver = manager.getWaitDriver();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}
