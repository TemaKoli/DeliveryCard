package ru.netology.selenidetest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
public class DeliveryCard {
    private String generateDate (int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void positiveTest() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Воронеж");
        String planningDate = generateDate (4,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);//очистка поля даты
        $("[data-test-id='date'] input").setValue(planningDate);//ввод даты(со смещением на +4 дня) с применением метода
        $("[data-test-id='name'] input").setValue("Салтыков-Щадрин Александр");
        $("[data-test-id='phone'] input").setValue("+79359635175");
        $("[data-test-id='agreement'].checkbox").click();
        $(".button").click();

        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
