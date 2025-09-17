import com.gucci.data.MockDataService.UserFactory;
import com.gucci.entities.User;
import com.gucci.layers.web.page.headerMenu.ContactUsPage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.switchTo;


@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class TC6_ContactUsTest extends BaseWebTest {

    @Test
    @DisplayName("Filling contact us form")
    @Owner("Daniyar")
    @Tag("TestCase6")

    public void contactUsForm() {

        User user = UserFactory.createDefaultUser();
        ContactUsPage contactUsPage = new ContactUsPage();


        open("", HomePage.class)
                .waitForPageLoaded()
                .clickContactUsButton()
                .waitForPageLoaded()
                .enterName(user.getName())
                .enterEmail(user.getEmail())
                .enterSubject(user.getSubject())
                .enterMessage(user.getText())
                .clickSubmitButton();
        switchTo().alert().accept();

        contactUsPage.successMessageIsVisible()
                .clickHomeButton()
                .waitForPageLoaded();



        WaitManager.pauseInSeconds(5);
    }
}
