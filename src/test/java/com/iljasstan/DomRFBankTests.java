package com.iljasstan;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.iljasstan.Enums.Deposits;
import com.iljasstan.Enums.Requisits;
import config.Browser;
import config.BrowserConfig;
import config.CredentialsConfig;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class DomRFBankTests {
    private final String indexURL = "https://domrfbank.ru",
            appstoreTitle = "App Store: Банк Дом.РФ",
            googlePlayTitle = "Приложения в Google Play – Банк Дом.РФ";


    CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class);
    String login = credentials.login();
    String password = credentials.password();
    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
        Configuration.browser = capabilities.getBrowserName();
        Configuration.remote = String.format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", login, password);
    }

    @Test
    @AllureId("6575")
    @DisplayName("Ссылка с главной страницы банка должна вести на страницу приложения банка в App Store")
    @Tag("critical")
    @Feature("Открыть страницу приложения банка в App Store")
    public void openAppStore() {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Нажать на иконку App Store внизу страницы", () -> {
            $("[alt = 'ios']").click();
            switchTo().window(1);
        });
        step("Проверить, что открылась страница " + appstoreTitle, () -> {
            //$("link [href='appstoreUrl'").exists();
            $("title").shouldHave(Condition.exactOwnText(appstoreTitle));
        });
    }
    @Test
    @AllureId("6576")
    @DisplayName("Ссылка с главной страницы банка должна вести на страницу приложения банка в Google Play")
    @Tag("critical")
    @Feature("Открыть страницу приложения банка в Google Play")
    public void openGooglePlay() {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Нажать на иконку Google Play внизу страницы", () -> {
            $("[alt = 'android']").click();
            switchTo().window(1);
        });
        step("Проверить, что открылась страница " + googlePlayTitle, () -> {
            $("#main-title").shouldHave(Condition.exactOwnText(googlePlayTitle));
        });
    }
    @EnumSource(value = Requisits.class)
    @ParameterizedTest(name = "Реквизиты банка должны отображаться на соответствующей странице")
    @AllureId("6574")
    @DisplayName("Реквизиты банка должны отображаться на соответствующей странице")
    @Tag("major")
    @Feature("Просмотр реквизитов банка")
    public void checkRequisites(Requisits requisit) {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Нажать кнопку О банке в шапке страницы", () -> {
            $("a[href=\"/about/\"]").click();
        });
        step("Нажать на кнопку Реквизиты", () -> {
            $("a[href=\"/about/requisites/\"]").click();
        });
        step("Проверить, что на открывшейся странице есть поля Юридический адрес, Почтовый адрес, ИНН, КПП", () -> {
            $(".requisites-info__container").shouldHave(Condition.text(requisit.getDesc()));
        });
    }
    @EnumSource(value = Deposits.class)
    @ParameterizedTest(name = "На странице вкладов и счетов должен появится полный список вкладов и счетов")
    @AllureId("6573")
    @DisplayName("На странице вкладов и счетов должен появится полный список вкладов и счетов")
    @Tag("major")
    @Feature("Просмотр списка вкладов и счетов")
    public void checkTheListOfDeposits(Deposits deposit) {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Кликнуть на кнопку Вклады в шапке страницы", () -> {
            $("a[href=\"/deposits/\"]").click();
        });
        step("Кликнуть на кнопку Вклады и счета", () -> {
            $("a[href=\"/deposits/others/\"]").click();
        });
        step("Проверить, что на странице показаны вклады с названиями Надёжный, Стратегический, Дома Лучше, " +
                "Доступный, Накопительный счёт, Доходный+, До востребования", () -> {
            $(".faq-cards").shouldHave(Condition.text(deposit.getDesc()));
        });
    }
    @Test
    @AllureId("6578")
    @DisplayName("На странице входа в интернет-банк при вводе SQL-запросов в поля \"логин\" и \"пароль\" " +
            "должно появляться сообщение об ошибке")
    @Tag("critical")
    @Feature("Сообщение об ошибке при входе в интернет-банк с неверными логином и паролем")
    public void checkTheErrorMessageAfterFillingSQLCommandsToLoginForm() {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Нажать на кнопку Войти", () -> {
            $(".header-actions-dropdown__toggle").click();
            step("В открывшемся списке выбрать Интернет-банк", () -> {
                $("a[href=\"https://my.domrfbank.ru/#/\"]").click();
                switchTo().window(1);
            });
        });
        step("Ввести в поле логин select *, в поле пароль select *", () -> {
            $("[name=\"username\"]").setValue("Select *");
            $("[name=\"password\"]").setValue("Select *").click();
        });
        step("нажать Enter", () -> {
            $("[name=\"password\"]").pressEnter();
        });
        step("Проверить, что отобразилось сообщение об ошибке", () -> {
            $(".bss-popup__inner").shouldBe(Condition.visible);
        });
    }
    @Test
    @AllureId("6577")
    @DisplayName("На странице входа в интернет-банк при вводе неверных логина и пароля должно появляться сообщение об ошибке")
    @Tag("critical")
    @Feature("Сообщение об ошибке при входе в интернет-банк с неверными логином и паролем")
    public void checkTheErrorMessageAfterFillingInvalidDataToLoginForm() {
        step("Открыть главную страницу " + indexURL, () -> {
            open(indexURL);
        });
        step("Нажать на кнопку Войти", () -> {
            $(".header-actions-dropdown__toggle").click();
            step("В открывшемся списке выбрать Интернет-банк", () -> {
                $("a[href=\"https://my.domrfbank.ru/#/\"]").click();
                switchTo().window(1);
            });
        });
        step("Ввести в поле логин ivalid login, в поле пароль invalid password", () -> {
            $("[name=\"username\"]").setValue("ivalid login");
            $("[name=\"password\"]").setValue("invalid password");
        });
        step("Нажать кнопку Войти в банк", () -> {
            $(".bss-button").click();
        });
        step("Проверить, что отобразилось сообщение об ошибке", () -> {
            $(".bss-popup__inner").shouldBe(Condition.visible);
        });
    }
    @AfterEach
    void afterEach() {
        closeWebDriver();
    }
}
