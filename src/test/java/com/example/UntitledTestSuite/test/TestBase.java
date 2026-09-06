package com.example.UntitledTestSuite.test;

import com.example.UntitledTestSuite.data.AccountData;
import com.example.UntitledTestSuite.manager.ApplicationManager;
import com.example.UntitledTestSuite.util.Settings;
import org.junit.Before;

public class TestBase {

    protected ApplicationManager manager;
    protected AccountData validUser;
    protected AccountData invalidUser;

    @Before
    public void setUp() throws Exception {
        manager = ApplicationManager.getInstance();
        manager.getNavigationHelper().openMainPage();

        validUser = new AccountData(Settings.getLogin(), Settings.getPassword(), Settings.getUsername());
        invalidUser = new AccountData("arduganova@gmail.com", "8S67Hv9CH", "arduganova");
    }

}
