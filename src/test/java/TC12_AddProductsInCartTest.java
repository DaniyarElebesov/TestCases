import com.google.gson.internal.bind.util.ISO8601Utils;
import com.gucci.layers.web.page.headerMenu.ProductsPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.PRODUCTS;
import static io.qameta.allure.Allure.step;
@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC12_AddProductsInCartTest extends BaseWebTest {

    @Test
    @DisplayName("Add Products in Cart")
    @Owner("Daniyar")
    @Tag("TestCase12")

    public void addProductsInCartTest() {
        SoftAssertions  softAssertions = new SoftAssertions();

        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(PRODUCTS, ProductsPage.class)
                .clickFirstProductAddToCart()
                .clickContinueShoppingButton()
                .clickSecondProductAddToCart()
                .clickViewCartButton()
                .listOfProductsInTheCart()
                .verifyPriceQtyTotalPrice();


        softAssertions.assertAll();
        WaitManager.pauseInSeconds(5);
    }
}
