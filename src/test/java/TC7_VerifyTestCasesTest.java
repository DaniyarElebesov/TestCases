import com.gucci.layers.web.page.LoginPage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.gucci.data.Sections.SIGN_IN_LOGIN;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC7_VerifyTestCasesTest extends BaseWebTest {

    @Test
    @DisplayName("Verify Test Cases page")
    @Owner("Daniyar")
    @Tag("TestCase7")

    public void verifyTestCasesPageTest() {

        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBtwSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded();
    }
}
