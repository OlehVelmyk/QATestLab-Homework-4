package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        //throw new UnsupportedOperationException();

        String url = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
        driver.get(url);

        WebElement fieldEmail = driver.findElement(By.id("email"));
        fieldEmail.sendKeys(login);

        WebElement fieldPassword = driver.findElement(By.id("passwd"));
        fieldPassword.sendKeys(password);

        WebElement buttonSignIn = driver.findElement(By.className("ladda-label"));
        buttonSignIn.click();
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        //throw new UnsupportedOperationException();

        selectItemCatalogProducts();

        WebElement newGoodsButton = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-configuration-add")));
        newGoodsButton.click();

        WebElement goodsName = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("form_step1_name_1")));
        goodsName.sendKeys(newProduct.getName());

        WebElement goodsAmount = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("form_step1_qty_0_shortcut")));
        goodsAmount.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        goodsAmount.sendKeys(newProduct.getQty().toString());

        WebElement goodsPrice = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("form_step1_price_shortcut")));
        goodsPrice.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        goodsPrice.sendKeys(newProduct.getPrice().toString());

        WebElement productActivation = driver.findElement(By.className("switch-input"));
        productActivation.click();

        popupNotification();

        WebElement buttonSaveGoods = driver.findElement(By.id("submit"));
        buttonSaveGoods.click();

        popupNotification();
    }

    public void selectItemCatalogProducts() {

        try {
            WebElement itemCatalog = driver.findElement(By.id("subtab-AdminCatalog"));
            Actions actions = new Actions(driver);
            actions.moveToElement(itemCatalog).build().perform();

            WebElement itemGoods = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("товары")));
            itemGoods.click();
        } catch (org.openqa.selenium.TimeoutException ex) {
            WebElement itemOrders = driver.findElement(By.id("subtab-AdminParentOrders"));
            Actions actions = new Actions(driver);
            actions.moveToElement(itemOrders).build().perform();

            WebElement itemCatalog = driver.findElement(By.id("subtab-AdminCatalog"));
            Actions actions1 = new Actions(driver);
            actions1.moveToElement(itemCatalog).build().perform();

            WebElement itemGoods = (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("товары")));
            itemGoods.click();
        }
    }

    public  void popupNotification() {

        try {
            WebElement saveSettingsPopupNotification = (new WebDriverWait(driver, 30))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("growls")));
            saveSettingsPopupNotification.isDisplayed();
            WebElement closePopupNotification = (new WebDriverWait(driver, 30))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
            closePopupNotification.click();
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            WebElement saveSettingsPopupNotification = (new WebDriverWait(driver, 30))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("growls")));
            saveSettingsPopupNotification.isDisplayed();
            WebElement closePopupNotification = (new WebDriverWait(driver, 30))
                    .until(ExpectedConditions.elementToBeClickable(By.className("growl-close")));
            closePopupNotification.click();
        }
    }

    public void checkingTheProduct(ProductData newProduct) {

        String url = "http://prestashop-automation.qatestlab.com.ua/";
        driver.get(url);

        WebElement allGoodsLink = driver.findElement(By.className("all-product-link"));
        allGoodsLink.click();

        WebElement searchField = driver.findElement(By.className("ui-autocomplete-input"));
        searchField.sendKeys(newProduct.getName());

        WebElement iconSearch = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
        iconSearch.click();

        //Check that created product appears on the page
        WebElement productDisplayed = driver.findElement(By.linkText(newProduct.getName()));
        Assert.assertTrue(productDisplayed.isDisplayed());
        productDisplayed.click();

        //Check the product name
        String productName = driver.findElement(By.cssSelector(".h1")).getText();
        Assert.assertTrue(newProduct.getName().equalsIgnoreCase(productName));

        //Check the product price
        String productPrice = driver.findElement(By.cssSelector(".current-price>span")).getAttribute("content");
        Assert.assertEquals(productPrice, String.valueOf(newProduct.getPrice()) );

        //Check the product amount
        String productAmount = driver.findElement(By.cssSelector(".product-quantities > span")).getText();
        String newProductAmount = regularRule(productAmount);
        Assert.assertEquals(newProductAmount, String.valueOf(newProduct.getQty()));
    }

    public String regularRule(String productAmount) {

        String digit = null;
        String pattern = "\\d{1,}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(productAmount);
        if (m.find()) {
            digit = m.group();
        }
        return digit;
    }

                            /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

        // wait.until(...);
        // ...
    }
}
