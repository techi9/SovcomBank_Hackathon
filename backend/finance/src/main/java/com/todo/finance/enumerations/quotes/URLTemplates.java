package com.todo.finance.enumerations.quotes;

import lombok.Getter;

@Getter
public enum URLTemplates {
    QUOTES("/XML_daily.asp"),
    DYNAMIC("/XML_dynamic.asp");
    private final String url;
    URLTemplates(String urlTemplate) {
        this.url = urlTemplate;
    }

}
