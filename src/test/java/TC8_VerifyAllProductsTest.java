import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC8_VerifyAllProductsTest extends BaseWebTest{

    @Test
    @DisplayName("Verify All Products and product detail page")
    @Owner("Daniyar")
    @Tag("TestCase8")

    public void verifyAllProductsTest() {

        open("", HomePage.class)
                .waitForPageLoaded()
                .clickProductsButton()
                .waitForPageLoaded()
                .printAllProducts()
                .clickViewProductButton()
                .waitForPageLoaded()
                .visibilityOfDetails();

        WaitManager.pauseInSeconds(5);

    }
}
