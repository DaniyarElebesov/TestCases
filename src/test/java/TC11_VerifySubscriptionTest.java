import com.gucci.layers.web.page.headerMenu.CartPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.CART;
import static io.qameta.allure.Allure.step;
@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC11_VerifySubscriptionTest extends BaseWebTest {

    @Test
    @DisplayName("Verify subscription in cart page")
    @Owner("Daniyar")
    @Tag("TestCase11")

    public void verifySubscriptionTest() {

        SoftAssertions softAssertions = new SoftAssertions();

        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(CART, CartPage.class)
                .scrollToFooter();

        step("verification of 'SUBSCRIPTION' text visibility", () -> {
                    softAssertions.assertThat(homePage.subscriptionIsVisible.getText())
                            .isEqualTo("SUBSCRIPTION");
                }
        );

        homePage.inputEmailInSubscriptionField()
                .clickSubscriptionArrow();

        step("verification of 'You have been successfully subscribed!' text visibility", () -> {
                    softAssertions.assertThat(homePage.subscriptionSuccessText.getText())
                            .isEqualTo("You have been successfully subscribed!");
                }
        );


        softAssertions.assertAll();


        WaitManager.pauseInSeconds(5);
    }
}
