package com.pageobject.pages;

import com.pageobject.helpers.Helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.time.Duration;
import java.util.*;

public class HomePage extends PageBase {

    private static final By HOME_PAGE_HEADER_TEXT = By.cssSelector(".logo.img-responsive");
    protected WebDriver driver;

    public HomePage(WebDriver dr)
    {
        this.driver = dr;
    }

    public String getHomePageHeader()
    {
        return driver.findElement(HOME_PAGE_HEADER_TEXT).getText();
    }

    public void selectHighestPrice()
    {
        By dressTab = By.xpath("//*[@id='block_top_menu']/ul/li[2]/a");
        By dressPrice = By.xpath(" //*[@id='center_column']//*[@class='product_list grid row']/child::li[1]//div[@class='right-block']//span[@class='price product-price']");
       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50,5));
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(dressTab)).click();
        // wb.findElement(By.xpath("")).click();
        //Fetch All the Products Text
        wait.until(ExpectedConditions.presenceOfElementLocated(dressPrice));
        List<WebElement> list_of_dress_price =  driver.findElements(By.xpath(" //*[@id='center_column']//*[@class='product_list grid row']/child::li//div[@class='right-block']//span[@class='price product-price']"));
        List<WebElement> list_of_dresses = driver.findElements(By.xpath("//*[@id='center_column']//*[@class='product_list grid row']/child::li//div[@class='right-block']//h5/a"));

        //Use of HashMaop to store Products and Their prices(after conversion to Integer)
        String dress_name;
        String dress_price;
        int int_product_price;
        HashMap<Integer, String> map_final_products = new HashMap<Integer,String>();
        for(int i=0;i<list_of_dresses.size();i++) {
            dress_name = list_of_dresses.get(i).getText();//Iterate and fetch product name
            dress_price = list_of_dress_price.get(i).getText();//Iterate and fetch product price
            dress_price = dress_price.replaceAll("[^0-9]", "");//Replace anything wil space other than numbers
            int_product_price = Integer.parseInt(dress_price);//Convert to Integer
            map_final_products.put(int_product_price,dress_name);//Add product and price in HashMap
        }
        //Get all the keys from Hash Map
        Set<Integer> allkeys = map_final_products.keySet();
        ArrayList<Integer> array_list_values_product_prices = new ArrayList<Integer>(allkeys);

        //Sort the Prices in Array List using Collections class
        //this will sort in ascending order lowest at the top and highest at the bottom
        Collections.sort(array_list_values_product_prices);

        //Highest Product is
        int high_price = array_list_values_product_prices.get(array_list_values_product_prices.size()-1);
        String highpricedressname = Integer.toString(high_price);
        int i = 1;
while ((list_of_dress_price.size()>=i-1))
{
    if(highpricedressname.equals(list_of_dress_price.get(i-1).getText().replaceAll("[^0-9]", "")))
    {
        list_of_dresses.get(i-1).click();
        By addToCartButton = By.cssSelector("#add_to_cart button");
        wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButton));
        driver.findElement(addToCartButton).click();
        break;
    }
    i++;
}

        closeWindow();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".shopping_cart >a > .ajax_cart_quantity")));
        String cartItems = driver.findElement(By.cssSelector(".shopping_cart")).getText();
        Assert.assertTrue(!cartItems.contains("empty"));
        //Low price is
        int low_price = array_list_values_product_prices.get(0);

        //Below will display both High and Low Price of item in chart
//        System.out.println("High dress Price is: " + high_price + " Dress name is: " + map_final_products.get(high_price));
//        System.out.println("Low dress Price is: " + low_price + " Dress name is: " + map_final_products.get(low_price));


    }

    public void closeWindow(){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
               switchToThePopUpWindow();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cross")));
        driver.findElement(By.cssSelector(".cross")).click();
        driver.getWindowHandles().stream().forEachOrdered((i)-> {driver.switchTo().window(i);});
           }

    public void switchToThePopUpWindow(){

        driver.getWindowHandles().stream().forEachOrdered((i)-> {driver.switchTo().window(i);});
        try { Thread.sleep(5000);}
        catch(Exception e){}



    }

}
