package com.pageobject.homepage.tests;

import com.pageobject.pages.PageBase;
import org.testng.Assert;
import org.testng.annotations.Test;


public class HomePageTest extends PageBase {

    @Test
    public void addHighestPriceProductToCart() {
          String homePageHeaderText = homePage().getHomePageHeader();
          homePage().selectHighestPrice();
        //Assert.assertTrue(homePageHeaderText.contains("Homepage"));
    }
}
