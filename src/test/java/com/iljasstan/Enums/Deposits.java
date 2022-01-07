package com.iljasstan.Enums;

public enum Deposits {
    DEPOSIT0("Надёжный"),
    DEPOSIT1("Стратегический"),
    DEPOSIT2("ДОМа лучше"),
    DEPOSIT3("Доступный"),
    DEPOSIT4("Накопительный счет"),
    DEPOSIT5("Доходный+"),
    DEPOSIT6("До востребования");

    String desc;

    Deposits(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
