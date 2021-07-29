package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;


public class DebitCardDeliveryTestTask2 {


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
    void shouldTestPassedTask2WithCity() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Ке");
        $$("[class='menu-item__control']").find(exactText("Кемерово")).click();
        form.$("[data-test-id=date]").$("[class='input__control']").click();
        form.$("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(DeliveryDate(6));
        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Встреча успешно забронирована на " + DeliveryDate(6)));
    }

    @Test
    void shouldTestPassedTask2WithData() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Ке");
        $$("[class='menu-item__control']").find(exactText("Кемерово")).click();
        form.$("[data-test-id=date] input").click();


        String currentDay = $("[class=calendar__layout] .calendar__day_state_current.calendar__day").text();
        int data = Integer.parseInt(currentDay) + 4;

        $(withText(String.valueOf(data))).shouldBe(cssClass("calendar__day")).click();

        form.$("[data-test-id=name] input").setValue("Иван Васильевич Царь");
        form.$("[data-test-id=phone] input").setValue("+79270000001");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Встреча успешно забронирована на " + DeliveryDate(7)));
        // DeliveryDate = data +3
    }
}







