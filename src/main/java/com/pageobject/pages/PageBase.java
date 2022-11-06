package com.pageobject.pages;

import com.pageobject.framework.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase extends TestBase {

    public HomePage homePage()
    {
        PageFactory.initElements(driver, HomePage.class);
        return new HomePage(driver);
    }
}
