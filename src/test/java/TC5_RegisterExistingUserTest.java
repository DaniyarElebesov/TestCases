import com.gucci.layers.web.page.LoginPage;
import com.gucci.layers.web.page.SignUpPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.SIGN_IN_LOGIN;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC5_RegisterExistingUserTest extends BaseWebTest {

    @Test
    @DisplayName("Register user with existing email address")
    @Owner("Daniyar")
    @Tag("TestCase5")

    public void registerUserWithExistingEmailTest() {

        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded()
                .fillName("Данияр")
                .fillEmail("daniyar@gmail.comm")
                .clickSingUpButtonForExistingEmail()
                .verifyEmailAddressIsTaken();


        WaitManager.pauseInSeconds(5);
    }
}
