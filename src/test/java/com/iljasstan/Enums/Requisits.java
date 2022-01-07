package com.iljasstan.Enums;

public enum Requisits {
    LEGALADDRESS("Юридический адрес"),
    POSTALADDRESS("Почтовый адрес"),
    INN("ИНН"),
    KPP("КПП");

    String desc;

    Requisits(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
