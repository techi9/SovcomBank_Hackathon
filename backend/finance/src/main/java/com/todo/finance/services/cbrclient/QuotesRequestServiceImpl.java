package com.todo.finance.services.cbrclient;

import com.todo.finance.enumerations.quotes.URLTemplates;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class QuotesRequestServiceImpl implements QuotesRequestService {
    public static final int DELAY_MILLIS = 100;
    public static final int MAX_RETRY_ATTEMPTS = 3;
    private final WebClient webClient;

    @Override
    public String getCurrentQuotes() {
        return webClient
                .get()
                .uri(URLTemplates.QUOTES.getUrl())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("An error has occurred {}", error.getMessage())) // TODO handle exception
                .onErrorResume(error -> Mono.just(""))
                .block();
    }

    @Override
    public String getCurrentQuotesWithRetry() {
        return webClient
                .get()
                .uri(URLTemplates.QUOTES.getUrl())
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(MAX_RETRY_ATTEMPTS, Duration.ofMillis(DELAY_MILLIS)))
                .doOnError(error -> log.error("An error has occurred {}", error.getMessage())) // TODO handle exception
                .onErrorResume(error -> Mono.just(""))
                .block();
    }

    @Override
    public String getQuotesByDate(String date) {
        return webClient
                .get()
                .uri(URLTemplates.QUOTES.getUrl(), uri -> uri.queryParam("date_req", date).build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("An error has occurred {}", error.getMessage())) // TODO handle exception
                .onErrorResume(error -> Mono.just(""))
                .block();
    }

    @Override
    public String getDynamicById(String date1, String date2, String quoteId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("date_req1", Collections.singletonList(date1));
        params.put("date_req2", Collections.singletonList(date2));
        params.put("VAL_NM_RQ", Collections.singletonList(quoteId));
        return webClient
                .get()
                .uri(URLTemplates.DYNAMIC.getUrl(), uri -> uri.queryParams(params).build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("An error has occurred {}", error.getMessage())) // TODO handle exception
                .onErrorResume(error -> Mono.just(""))
                .block();
    }
}
