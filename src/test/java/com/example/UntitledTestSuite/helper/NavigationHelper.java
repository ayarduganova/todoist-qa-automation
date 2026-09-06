package com.example.UntitledTestSuite.helper;

import com.example.UntitledTestSuite.manager.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationHelper extends HelperBase {

    private String baseUrl;

    public NavigationHelper(ApplicationManager manager, String baseUrl) {
        super(manager);
        this.baseUrl = baseUrl;
    }

    public void openAuthPage() {
        driver.get("https://app.todoist.com/auth/login");
    }

    public void openMainPage(){
        driver.get("https://app.todoist.com/app/today");
    }

    public void openAddNewTaskModal() {
        waitDriver.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//div[@id='todoist_app']/div/div/div[2]/nav/div[2]/button"))).click();
    }

    public void openUpcoming() throws InterruptedException {
        driver.get("https://app.todoist.com/app/upcoming");
        Thread.sleep(3000);
    }

    public void waitMainPageLoad() {
        waitDriver.until(ExpectedConditions.urlContains("app.todoist.com/app"));
    }

}
