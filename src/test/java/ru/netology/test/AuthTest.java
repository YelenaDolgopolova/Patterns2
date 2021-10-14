package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;


public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPassRegisteredUserTest() {
        RegistrationInfo registeredUser = DataGenerator.Registration.shouldPassLogin();
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(appear);
    }

    @Test
    void shouldFallRegisteredUserTest() {
        RegistrationInfo noRegisteredUser = DataGenerator.Registration.shouldFallLogin();
        $("[data-test-id=login] input").setValue(noRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(noRegisteredUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(appear);
    }

    @Test
    void shouldFallLogin(){
        RegistrationInfo wrongLogin = DataGenerator.Registration.shouldGetFailLogin();
        $("[data-test-id=login] input").setValue(wrongLogin.getLogin());
        $("[data-test-id=password] input").setValue(wrongLogin.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(appear).shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void shouldFallPassword(){
        RegistrationInfo wrongPassword = DataGenerator.Registration.shouldGetFailPassword();
        $("[data-test-id=login] input").setValue(wrongPassword.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(appear).shouldHave(text("Неверно указан логин или пароль"));
    }
}
