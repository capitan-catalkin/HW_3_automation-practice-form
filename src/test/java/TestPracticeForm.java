import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;



public class TestPracticeForm {
    @BeforeAll
    static void beforeAll(){
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void fillFormTest(){
        Configuration.pageLoadStrategy = "eager";  //тест не дожидается полной загрузки страницы а начинает проходится как появляются нужные элементы
        //executeJavaScript("$('#fixedban').remove()");
        //executeJavaScript("$('footer').remove()");   //убирает банеры со страницы которые мешают нажатию кнопок


        open("/automation-practice-form");

        $("#firstName").setValue("Sergey");
        $("#lastName").setValue("Esenin");
        $("#userEmail").setValue("esenin@mail.ru");
        $(".custom-control-label").click();
        $("#userNumber").setValue("1234567890");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("September");
        $(".react-datepicker__year-select").selectOption("1900");
        $(".react-datepicker__day--021").click();
        //вопрос к проверяющим$(".subjects-auto-complete__value-container--is-multi").click();
        // почему можно кликнуть на это поле по этому локатору, но нельзя что то ввести?
        $("#subjectsInput").setValue("b").pressEnter();
        $("label[for='hobbies-checkbox-2']").click();  //выбор чекбокса по названию (сначала выбрал эдемент label и потом название for
        $(".form-control-file").uploadFile(new File("src/test/resources/cat1.jpg"));
        $("#currentAddress").setValue("Konstantinovo");
        $("#state").click();
        $("#react-select-3-option-1").click();
        $("#city").click();
        $("#react-select-4-option-1").click();
        $("#submit").click();

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
    }
}
