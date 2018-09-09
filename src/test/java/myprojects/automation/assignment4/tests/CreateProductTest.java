package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CreateProductTest extends BaseTest {

    @DataProvider(name = "Authorization")

    public static Object[][] credentials() {

        return new Object[][] { { "webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw" }};

    }

    ProductData newProduct = new ProductData(ProductData.generate().getName(), ProductData.generate().getQty(),
            ProductData.generate().getPrice());

    @Test(dataProvider = "Authorization")
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        actions.login(login, password);
        actions.createProduct(newProduct);
    }

    // TODO implement logic to check product visibility on website

    @Test(dependsOnMethods={"createNewProduct"})
    public void checkingProductDisplay() {

        actions.checkingTheProduct(newProduct);
    }
}
