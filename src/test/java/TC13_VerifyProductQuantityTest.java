import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC13_VerifyProductQuantityTest extends BaseWebTest {

    @Test
    @DisplayName("Verify Product quantity in Cart")
    @Owner("Daniyar")
    @Tag("TestCase13")

    public void verifyProductQuantityTest(){

        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .clickViewProduct14Button();
    }
}

