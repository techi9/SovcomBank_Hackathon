package com.todo.finance.enumerations.quotes;

import lombok.Getter;

@Getter
public enum CurrencyCodes {
    AUD("R01010"),
    GBP("R01035"),
    BYR("R01090"),
    DKK("R01215"),
    USD("R01235"),
    EUR("R01239"),
    ISK("R01310"),
    KZT("R01335"),
    CAD("R01350"),
    NOK("R01535"),
    XDR("R01589"),
    SGD("R01625"),
    TRL("R01700"),
    UAH("R01720"),
    SEK("R01770"),
    CHF("R01775"),
    JPY("R01820");
    private final String id;
    CurrencyCodes(String id) {
        this.id = id;
    }
}
