package com.example.UntitledTestSuite.test;

import org.junit.Test;

public class LoginTest extends TestBase {

    @Test
    public void loginWithValidData() throws InterruptedException {
        manager.getLoginHelper().login(validUser);
        manager.getNavigationHelper().waitMainPageLoad();
        manager.getLoginHelper().verifySuccessLogin(validUser);
    }

    @Test
    public void loginWithInvalidData() throws InterruptedException {
        manager.getLoginHelper().login(invalidUser);
        manager.getLoginHelper().verifyInvalidLogin();
    }

}
