package com.iljasstan;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.iljasstan.Enums.Deposits;
import com.iljasstan.Enums.Requisits;
import com.iljasstan.pages.IndexBankDomRF;
import config.Browser;
import config.BrowserConfig;
import config.CredentialsConfig;
import helpers.Attach;
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
    IndexBankDomRF page = new IndexBankDomRF();
    private final String indexURL = "https://domrfbank.ru",
            appstoreTitle = "App Store: Банк Дом.РФ",
            googlePlayTitle = "Приложения в Google Play – Банк Дом.РФ",
            alternateGooglePlayTitle = "Банк Дом.РФ - Apps on Google Play";


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
//        Configuration.browser = capabilities.getBrowserName();
//        Configuration.remote = String.format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", login, password);
    }

    @Test
    @AllureId("6575")
    @DisplayName("Ссылка с главной страницы банка должна вести на страницу приложения банка в App Store")
    @Tag("critical")
    @Feature("Открыть страницу приложения банка в App Store")
    void openAppStore() {
        step("Открыть главную страницу " + indexURL, () -> {
            page.openIndexURL();
        });
        step("Нажать на иконку App Store внизу страницы", () -> {
            page.pressAppsStoreButton();
        });
        step("Проверить, что открылась страница " + appstoreTitle, () -> {
            page.checkAppStoreTitle();
        });
    }
    @Test
    @AllureId("6576")
    @DisplayName("Ссылка с главной страницы банка должна вести на страницу приложения банка в Google Play")
    @Tag("critical")
    @Feature("Открыть страницу приложения банка в Google Play")
    public void openGooglePlay() {
        step("Открыть главную страницу " + indexURL, () -> {
            page.openIndexURL();
        });
        step("Нажать на иконку Google Play внизу страницы", () -> {
            page.pressGooglePlayButton();
        });
        step("Проверить, что открылась страница " + googlePlayTitle, () -> {
            page.checkGogglePlayTitle();
        });
    }
    @Test
    @AllureId("6574")
    @DisplayName("Реквизиты банка должны отображаться на соответствующей странице")
    @Tag("major")
    @Feature("Просмотр реквизитов банка")
    public void checkRequisites() {
        step("Открыть главную страницу " + indexURL, () -> {
            page.openIndexURL();
        });
        step("Нажать кнопку О банке в шапке страницы", () -> {
            page.pressAboutBankButton();
        });
        step("Нажать на кнопку Реквизиты", () -> {
            page.pressRequisitesButton();
        });
        step("Проверить, что на открывшейся странице есть поля Юридический адрес, Почтовый адрес, ИНН, КПП", () -> {
            page.checkFieldsInRequisitesTable();
        });
    }
    @Test
    @AllureId("6573")
    @DisplayName("На странице вкладов и счетов должен появится полный список вкладов и счетов")
    @Tag("major")
    @Feature("Просмотр списка вкладов и счетов")
    public void checkTheListOfDeposits() {
        step("Открыть главную страницу " + indexURL, () -> {
            page.openIndexURL();
        });
        step("Кликнуть на кнопку Вклады в шапке страницы", () -> {
            page.pressDepositsButton();
        });
        step("Кликнуть на кнопку Вклады и счета", () -> {
            page.pressDepositsAndOthersButton();
        });
        step("Проверить, что на странице показаны вклады с названиями Надёжный, Стратегический, Дома Лучше, " +
                "Доступный, Накопительный счёт, Доходный+, До востребования", () -> {
            page.checkTheListOfDeposits();
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
            page.openIndexURL();
        });
        step("Нажать на кнопку Войти", () -> {
            page.pressEnterButton();
            step("В открывшемся списке выбрать Интернет-банк", () -> {
                page.pressInternetBankButton();
            });
        });
        step("Ввести в поле логин select *, в поле пароль select *", () -> {
            page.fillLoginFieldsSQL();
        });
        step("нажать Enter", () -> {
            page.pressEnterAtLoginPage();
        });
        step("Проверить, что отобразилось сообщение об ошибке", () -> {
            page.checkTheErrorMessageIsVisible();
        });
    }
    @Test
    @AllureId("6577")
    @DisplayName("На странице входа в интернет-банк при вводе неверных логина и пароля должно появляться сообщение об ошибке")
    @Tag("critical")
    @Feature("Сообщение об ошибке при входе в интернет-банк с неверными логином и паролем")
    public void checkTheErrorMessageAfterFillingInvalidDataToLoginForm() {
        step("Открыть главную страницу " + indexURL, () -> {
            page.openIndexURL();
        });
        step("Нажать на кнопку Войти", () -> {
            page.pressEnterButton();
            step("В открывшемся списке выбрать Интернет-банк", () -> {
                page.pressInternetBankButton();
            });
        });
        step("Ввести в поле логин ivalid login, в поле пароль invalid password", () -> {
            page.fillLoginFieldsInvalidData();
        });
        step("Нажать кнопку Войти в банк", () -> {
            page.pressEnterToInternetBank();
        });
        step("Проверить, что отобразилось сообщение об ошибке", () -> {
            page.checkTheErrorMessageIsVisible();
        });
    }
    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}