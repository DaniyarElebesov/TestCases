
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC9_SearchProductTest extends BaseWebTest {

    @Test
    @DisplayName("Product search")
    @Owner("Daniyar")
    @Tag("TestCase9")

    public void searchProductTest(){

        open("", HomePage.class)
                .waitForPageLoaded()
                .clickProductsButton()
                .waitForPageLoaded()
                .inputSearch("T-Shirt")
                .searchedProductsIsVisible();

        WaitManager.pauseInSeconds(5);
    }
}
