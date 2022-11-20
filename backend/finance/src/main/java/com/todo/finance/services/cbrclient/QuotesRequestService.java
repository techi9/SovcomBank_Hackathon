package com.todo.finance.services.cbrclient;

public interface QuotesRequestService {
    String getCurrentQuotes();
    String getCurrentQuotesWithRetry();
    String getQuotesByDate(String date);
    String getDynamicById(String date1, String date2, String quoteId);
}
