package com.truelayer.challenge.client;

import com.truelayer.challenge.dto.TranslationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TranslationClientImpl implements TranslationClient {

    private final WebClient webClient;

    public TranslationClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.funtranslations.com/translate").build();
    }

    public TranslationResponse yodaTranslation(String text) {
        return webClient.post()
                .uri("/yoda.json")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("text", text))
                .retrieve()
                .bodyToMono(TranslationResponse.class)
                .block();
    }

    public TranslationResponse shakespeareTranslation(String text) {
        return webClient.post()
                .uri("/shakespeare.json")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("text", text))
                .retrieve()
                .bodyToMono(TranslationResponse.class)
                .block();
    }

}
