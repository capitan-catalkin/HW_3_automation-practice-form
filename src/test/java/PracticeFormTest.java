import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


@Tag("add_user")
public class PracticeFormTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version","100.0");
        Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
        Configuration.remote = System.getProperty("host", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        Configuration.pageLoadStrategy = "eager";

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();
    }

    @Test
    void fillFormTest() {
        step("Open page", () -> {
            open("/automation-practice-form");
            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
        });

        step("Filling  form", () -> {
            $("#firstName").setValue("Sergey");
            $("#lastName").setValue("Esenin");
            $("#userEmail").setValue("esenin@mail.ru");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("1234567890");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("September");
            $(".react-datepicker__year-select").selectOption("1900");
            $(".react-datepicker__day--021").click();
            $("#subjectsInput").setValue("b").pressEnter();
            $("#hobbiesWrapper").$(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("cat1.jpg");
            ;
            $("#currentAddress").setValue("Konstantinovo");
            $("#state").click(); //выбрал поле где надо искать совпадение
            $("#stateCity-wrapper").$(byText("Uttar Pradesh")).click(); //выбрал элемент по id и начал искать совпадения
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Lucknow")).click();
            ;
            $("#submit").click();
        });

        step("Form verification", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(
                    text("Sergey Esenin"),
                    text("esenin@mail.ru"),
                    text("Male"),
                    text("1234567890"),
                    text("21 September,1900"),
                    text("Biology"),
                    text("Reading"),
                    text("cat1.jpg"),
                    text("Konstantinovo"),
                    text("Uttar Pradesh Lucknow")
            );
        });
    }
}
