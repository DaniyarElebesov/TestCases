import com.gucci.data.MockDataService.UserFactory;
import com.gucci.entities.User;
import com.gucci.layers.web.page.LoginPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.SIGN_IN_LOGIN;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC3_LoginUserIncorrectTest extends BaseWebTest {


    @Test
    @DisplayName("Enter incorrect credentials to login")
    @Owner("Daniyar")
    @Tag("TestCase3")
    public void loginIncorrectTest() {

        User user = UserFactory.createDefaultUser();
        SoftAssertions softly = new SoftAssertions();

        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded()
                .fillEmailToLogin(user.getEmail())
                .fillPasswordToLogin(user.getPassword())
                .clickLoginBtnToLoginIncorrect()
                .verifyIncorrectLoginCredentials();


//        softly.assertThat("Hello")
//                        .as("Check greeting")
//                                .isEqualTo("Hi");
//
//        softly.assertAll();

        WaitManager.pauseInSeconds(5);
    }
}
