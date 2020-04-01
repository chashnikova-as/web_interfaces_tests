import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * Тестирование страницы для подачи заявки на дебетовую карту
 */

public class CardClaimTests {

    @BeforeEach
    void openUrl () {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimSuccess.csv", delimiter = '|', numLinesToSkip = 1)
    void checkSendClaimSuccess(String clientName, String phone, String message) {
        SelenideElement form = $("div");
        form.$("[data-test-id=name] input").setValue(clientName);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $(".Success_successBlock__2L3Cw").shouldHave(exactText(message));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimInvalidName.csv", delimiter = '|', numLinesToSkip = 1)
    void checkSendClaimInvalidClientName(String clientName, String phone, String message) {
        SelenideElement form = $("div");
        form.$("[data-test-id=name] input").setValue(clientName);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText(message));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimInvalidPhone.csv", delimiter = '|', numLinesToSkip = 1)
    void checkSendClaimInvalidPhone(String clientName, String phone, String message) {
        SelenideElement form = $("div");
        form.$("[data-test-id=name] input").setValue(clientName);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText(message));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimWithoutAgree.csv", delimiter = '|', numLinesToSkip = 1)
    void checkSendClaimWithoutAgree(String clientName, String phone, String color) {
        SelenideElement form = $("div");
        form.$("[data-test-id=name] input").setValue(clientName);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[type=button]").click();
        String checkboxTextColor = form.$(".checkbox__text").getCssValue("color");
        assertNotEquals(color, checkboxTextColor, "Цвет текста флага должен был измениться!");
    }
}
