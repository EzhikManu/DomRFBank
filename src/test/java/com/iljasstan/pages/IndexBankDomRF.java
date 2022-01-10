package com.iljasstan.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.iljasstan.Enums.Deposits;
import com.iljasstan.Enums.Requisits;

import static com.codeborne.selenide.Selenide.*;

public class IndexBankDomRF {
    private final String indexURL = "https://domrfbank.ru",
            appstoreTitle = "App Store: Банк Дом.РФ",
            googlePlayTitle = "Приложения в Google Play – Банк Дом.РФ",
            alternateGooglePlayTitle = "Банк Дом.РФ - Apps on Google Play";

    private final SelenideElement
            appStoreIcon = $("[alt = 'ios']"),
            appStoreHead = $("title"),
            googlePlayIcon = $("[alt = 'android']"),
            googlePlayHead = $("#main-title"),
            aboutBankButton = $("a[href=\"/about/\"]"),
            requisitesButton = $("a[href=\"/about/requisites/\"]"),
            requisitesTable = $(".requisites-info__container"),
            depositsButtonAtIndexUrl = $("a[href=\"/deposits/\"]"),
            depositsAndOthersButton = $("a[href=\"/deposits/others/\"]"),
            depositsContainer = $(".faq-cards"),
            enterButton = $(".header-actions-dropdown__toggle"),
            internetBankButton = $("a[href=\"https://my.domrfbank.ru/#/\"]"),
            userNameField = $("[name=\"username\"]"),
            passwordField = $("[name=\"password\"]"),
            errorAlert = $(".bss-popup__inner"),
            enterIntoBankButton = $(".bss-button");

    public void openIndexURL() {
        open(indexURL);
    }

    public void pressAppsStoreButton() {
        appStoreIcon.click();
        switchTo().window(1);
    }

    public void checkAppStoreTitle() {
        appStoreHead.shouldHave(Condition.exactOwnText(appstoreTitle));
    }

    public void pressGooglePlayButton() {
        googlePlayIcon.click();
        switchTo().window(1);
    }

    public void checkGogglePlayTitle() {
        if (googlePlayHead.has(Condition.exactOwnText(googlePlayTitle))) {
            googlePlayHead.shouldHave(Condition.exactOwnText(googlePlayTitle)); }
        else googlePlayHead.shouldHave(Condition.exactOwnText(alternateGooglePlayTitle));
    }

    public void pressAboutBankButton() {
        aboutBankButton.click();
    }

    public void pressRequisitesButton() {
        requisitesButton.click();
    }

    public void checkFieldsInRequisitesTable() {
        requisitesTable.shouldHave(Condition.text(Requisits.LEGALADDRESS.getDesc()));
        requisitesTable.shouldHave(Condition.text(Requisits.POSTALADDRESS.getDesc()));
        requisitesTable.shouldHave(Condition.text(Requisits.INN.getDesc()));
        requisitesTable.shouldHave(Condition.text(Requisits.KPP.getDesc()));
    }

    public void pressDepositsButton () {
        depositsButtonAtIndexUrl.click();
    }

    public void pressDepositsAndOthersButton() {
        depositsAndOthersButton.click();
    }

    public void checkTheListOfDeposits() {
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT0.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT1.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT2.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT3.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT4.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT5.getDesc()));
        depositsContainer.shouldHave(Condition.text(Deposits.DEPOSIT6.getDesc()));
    }

    public void pressEnterButton() {
        enterButton.click();
    }

    public void pressInternetBankButton() {
        internetBankButton.click();
        switchTo().window(1);
    }

    public void fillLoginFieldsSQL() {
        userNameField.setValue("Select *");
        passwordField.setValue("Select *").click();
    }

    public void pressEnterAtLoginPage() {
        passwordField.pressEnter();
    }

    public void checkTheErrorMessageIsVisible() {
        errorAlert.shouldBe(Condition.visible);
    }
    public void fillLoginFieldsInvalidData() {
        userNameField.setValue("invalid login");
        passwordField.setValue("invalid password");
    }

    public void pressEnterToInternetBank() {
        enterIntoBankButton.click();
    }
}
