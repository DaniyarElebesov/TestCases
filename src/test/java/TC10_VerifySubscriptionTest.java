import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC10_VerifySubscriptionTest extends BaseWebTest {

    @Test
    @DisplayName("Verifying subscription")
    @Owner("Daniyar")
    @Tag("TestCase10")

    public void verifySubscriptionTest() {

        open("", HomePage.class)
                .waitForPageLoaded()
                .visibilityOfSubscription()
                .inputEmailSubscription("daniyar@gmail.comm")
                .clickSubscriptionButton()
                .visibilityOfSuccessfulSubscription();

        WaitManager.pauseInSeconds(5);
    }
}
