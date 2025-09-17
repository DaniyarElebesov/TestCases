import com.gucci.layers.web.page.LoginPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.SIGN_IN_LOGIN;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC4_LogoutUserTest extends BaseWebTest {

    @Test
    @DisplayName("Logout user and verify")
    @Owner("Daniyar")
    @Tag("TestCase4")


    public void logoutUserTest() {

        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded()
                .fillEmailToLogin("daniyar@gmail.comm")
                .fillPasswordToLogin("123")
                .clickLoginBtnToLogin()
                .verifyLoggedInAsUsername("Данияр")
                .clickLogoutButton()
                .waitForPageLoaded();

        WaitManager.pauseInSeconds(4);
    }
}
