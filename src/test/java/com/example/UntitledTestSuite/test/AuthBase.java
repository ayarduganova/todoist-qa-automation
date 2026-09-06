package com.example.UntitledTestSuite.test;

import org.junit.Before;

public class AuthBase extends TestBase {

    @Before
    public void setUpLogin() throws Exception {
        super.setUp();
        manager.getNavigationHelper().openMainPage();
        manager.getLoginHelper().login(validUser);
        manager.getNavigationHelper().waitMainPageLoad();
    }

}
