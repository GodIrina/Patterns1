package ru.netology.delivery.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.Data.DataGenerator;
import ru.netology.delivery.Data.User;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.Data.DataGenerator.generateDate;

public class DeliveryFormTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }


    @Test
    void shouldSetInitialDateAndPlanNewDate() {
        User user = DataGenerator.Registration.generateUser("ru");
        String initialDate = generateDate(3);
        String newDate = generateDate(7);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").val(initialDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча "
                + "успешно запланирована на " + initialDate), Duration.ofSeconds(10)).shouldBe(visible);
        refresh();
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").val(newDate);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=replan-notification] .notification__content button").click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча "
                + "успешно запланирована на " + newDate), Duration.ofSeconds(10)).shouldBe(visible);
    }

    @Test
    void shouldSendForm() {
        User user = DataGenerator.Registration.generateUser("ru");
        String date = generateDate(3);
        $x("//*[@data-test-id='city']//input").val(user.getCity());
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").val(date);
        $x("//*[@data-test-id='name']//input").val(user.getName());
        $x("//*[@data-test-id='phone']//input").val(user.getPhone());
        $x("//*[@data-test-id='agreement']").click();
        $x("//*[text()='Запланировать']").click();
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на "
                + date), Duration.ofSeconds(10)).shouldBe(visible);
    }
}


