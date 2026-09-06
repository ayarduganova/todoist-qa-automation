package com.example.UntitledTestSuite.helper;

import com.example.UntitledTestSuite.manager.ApplicationManager;
import com.example.UntitledTestSuite.data.AccountData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;


public class LoginHelper extends HelperBase {

    public LoginHelper(ApplicationManager manager) {
        super(manager);
    }

    public boolean isLoggedIn() {
        try {
            WebElement element = driver.findElement(By.cssSelector("div.ZmV8mtF.lxbqvGJ.fb8d74bb"));
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLoggedIn(String username) {
        try {
            WebElement userNameSpan = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("span.VJ2Igaw.fb8d74bb")));
            return username.equals(userNameSpan.getText());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void login(AccountData user) {
        if(isLoggedIn()) {
            if(isLoggedIn(user.getUsername())){
                return;
            }
            logout();
        }
        driver.findElement(By.id("element-0")).click();
        driver.findElement(By.id("element-0")).clear();
        driver.findElement(By.id("element-0")).sendKeys(user.getEmail());
        driver.findElement(By.id("element-2")).clear();
        driver.findElement(By.id("element-2")).sendKeys(user.getPassword());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    public void logout() {
        try {
           waitDriver.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button[aria-label='Настройки']"))).click();
           Thread.sleep(3000);
           waitDriver.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@role='menuitem' and contains(., 'Выйти')]"))).click();

           Thread.sleep(3000);
        } catch (TimeoutException e) {
            throw new RuntimeException("Не удалось выполнить выход: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifySuccessLogin(AccountData user) throws InterruptedException {
        WebElement userNameSpan = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span.VJ2Igaw.fb8d74bb")));
        String userName = userNameSpan.getText();

        Thread.sleep(3000);
        Assert.assertEquals(user.getUsername(), userName);
    }

    public void verifyInvalidLogin() throws InterruptedException {
        try {
            WebElement errorElement = waitDriver
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("div.a83bd4e0._266d6623._8f5b5f2b.fb8d74bb")));

            Assert.assertEquals("Текст ошибки не совпадает", errorElement.getText().trim(),
                    "Неверный Email или пароль.");

        } catch (TimeoutException e) {
            System.out.println("Ошибка не появилась");
            return;
        }
    }

}
