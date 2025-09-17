import com.gucci.data.MockDataService.UserFactory;
import com.gucci.entities.User;
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
public class TC1_RegistrationTest extends BaseWebTest {

    @Test
    @DisplayName("Signup new user")
    @Owner("Daniyar")
    @Tag("TestCase1")
    public void registerNewUserTest() {

        User user = UserFactory.createDefaultUser();

        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded()
                .fillName(user.getName())
                .fillEmail(user.getEmail())
                .clickSingUpButton(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(user)
                .waitForPageLoaded()
                .clickContinueButton()
                .verifyLoggedInAsUsername(user.getName())
                .clickDeleteAccountBtn()
                .waitForPageLoaded()
                .clickContinueButton();


        WaitManager.pauseInSeconds(5);
    }
}
