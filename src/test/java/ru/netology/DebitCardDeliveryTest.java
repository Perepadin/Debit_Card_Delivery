package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DebitCardDeliveryTest {


    private String DeliveryDate(int plusDays) {
        LocalDate localDate = LocalDate.now();
        LocalDate deliveryDate = localDate.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDate.format(formatter);
        return dateText;
    }


    @BeforeEach
    public void openChrome() {
        open("http://localhost:9999");

    }

    @Test
    void shouldTestPassedV1() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Встреча успешно забронирована на " + DeliveryDate(6)));
    }

    @Test
    void shouldTestPassedV2() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(10));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь-Русский");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Встреча успешно забронирована на " + DeliveryDate(10)));

    }

    @Test
    void shouldTestFieldNameFailedWithEnglish() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Ivan");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestFieldNameFailed() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван 12");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestFieldNameEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestFieldNCityEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }


    @Test
    void shouldTestFieldPhoneFailed() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+7927000000");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestFieldPhoneEmpty() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestNoClickAgreement() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
