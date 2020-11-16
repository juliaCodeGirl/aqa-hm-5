package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ru.netology.web.DataGenerator.*;


public class DeliveryWithFakerData {

    private String city = getCity();
    private String date = getDate(3);
    private String dateOver = getDate(10);
    private String dateExpired = getDate(0);
    private String datePast = getDate(-2);
    private String name = getName();
    private String phone = getPhone();


    @Test
    void shouldRegisterNewDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(text("Встреча успешно запланирована на " + date));

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateOver);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] button").click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 12000);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(text("Встреча успешно запланирована на " + dateOver));
    }

    @Test
    void shouldRegisterExpiredDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateExpired);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 12000);
    }

    @Test
    void shouldRegisterPastDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification__title").waitUntil(visible, 12000);

        open("http://localhost:9999/");
        $("[data-test-id='city'] [type='text']").setValue(city);
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateExpired);
        $("[data-test-id='name'] [type='text']").setValue(name);
        $("[data-test-id='phone'] [type='tel']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 12000);
    }
}