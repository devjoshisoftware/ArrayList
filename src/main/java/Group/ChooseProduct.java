package Group;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ChooseProduct extends Utils
{
    LoadProps loadProps = new LoadProps();
    SoftAssert softAssert1 = new SoftAssert();

    @BeforeMethod
    public void openBrowser()
    {System.setProperty("webdriver.chrome.driver", "src\\main\\Resources\\BrowserDriver\\chromedriver.exe");
        //open the Browser
        driver = new ChromeDriver();
        //maximise the browser window screen
        //driver.manage().window().fullscreen();
        driver.manage().window().maximize();
        //set implicit wait fro driver object
        implicitWaitTime(10);
        //open the website // "url" is called from testdataConfig.properties
        getUrl(loadProps.getProperty("url"));
    }

    @AfterMethod
    public void closeBrowser()
    {
        driver.quit();
    }
    @Test
    public void compareProductsFromHomePageAndClearThemFromTheList()
    {

        SoftAssert softAssert1 = new SoftAssert();
        //click on first product to compare
        clickOnElement(By.xpath("//div[1]/div/div[2]/div[3]/div[2]/input[2]"));
        //check the green line on top of the page to make sure product has been added
        Assert.assertEquals((driver.findElement(By.xpath("//p[@class='content']")).getText()),"The product has been added to your product comparison");
        //explicit wait for invisible the green line
        waitForAlertInvisible((By.id("bar-notification")), 5);
        //click on second product to compare
        clickOnElement(By.xpath("//div[2]/div/div[2]/div[3]/div[2]/input[2]"));
        //check the green line on top of the page to make sure product has been added
        Assert.assertEquals((driver.findElement(By.xpath("//p[@class='content']")).getText()),"The product has been added to your product comparison");
        //click on compare product in green line
        clickOnElement(By.xpath("//a[contains(text(),'product comparison')]"));
        //assert the both added items is displayed in page
        WebElement firstItem = driver.findElement(By.xpath("//a[contains(text(),'Apple MacBook Pro 13-inch')]"));
        Assert.assertTrue(firstItem.isDisplayed());
        WebElement secondItem = driver.findElement(By.xpath("//a[contains(text(),'Build your own computer')]"));
        Assert.assertTrue(secondItem.isDisplayed());
        //click on clear button
        clickOnElement(By.xpath("//a[@class='clear-list']"));
        //make sure both products are cleared from the page
        assertMethod(By.xpath("//div[@class='no-data']"),"You have no items to compare.");
        //softAssert will keep checking all the asserts even though one of them is failed
        softAssert1.assertAll();
    }
    @Test
    public void userCanAddCommentForOnlineShoppingOnHomePage()
    {
        SoftAssert softAssert2 = new SoftAssert();
        //click on 'Details'to leave the review
        clickOnElement(By.xpath("//div[@class='buttons']//a[@href='/new-online-store-is-open']"));
        //assert to check we are on right page
        assertMethod(By.xpath("//h1[contains(text(),'New online store is open!')]"),"New online store is open!");
        //write title of the comment
        enterText(By.id("AddNewComment_CommentTitle"),"nop commerce");  //Learn unique software testing
        //write comments
        enterText(By.id("AddNewComment_CommentText"),"wonderful website for shopping"); //Practice makes perfect
        //click on New Comment
        clickOnElement(By.xpath("//input[@value='New comment']"));
        //assert to check comment has been successfully submitted
        assertMethod(By.xpath("//div[@class='result']"),"News comment is successfully added.");
        //assert to check written comment is posted
        assertMethod(By.xpath("//p[contains(text(),'website for shopping')]"),"wonderful website for shopping");

        WebElement location = driver.findElement(By.xpath("//p[contains(text(),'website for shopping')]"));
        //location.getLocation();
        //Assert.assertEquals(location.getLocation(),"(635, 58031)");

        Assert.assertEquals(location.getLocation(),"(635, 58031)");
    }
    @Test
    public void userShouldAbleToSearchDesiredProductFromSearchStoreBar()
    {
        SoftAssert softAssert3 = new SoftAssert();
        //enter the product name in search store
       enterText(By.xpath("//input[@name='q']"),loadProps.getProperty("SearchStore"));
       //click on search bar
        clickOnElement(By.xpath("//input[@type='submit']"));

        List<WebElement> products = driver.findElements(By.xpath("//div[@class='item-grid']//a[contains(text(),'Nike')]"));
        System.out.println(products.size());

        int count=0;
        for (WebElement searchProduct : products)
        {
            if (searchProduct.getAttribute("outerHTML").contains(loadProps.getProperty("SearchStore")))
            {
                count++;
                System.out.println(searchProduct.getText());
            }
            else
                {
                    System.out.println("Not a described Product "+ searchProduct.getText());
                }
        }
        System.out.println(count);
        Assert.assertEquals(products.size(),count);
        softAssert3.assertAll();

    }

    }


