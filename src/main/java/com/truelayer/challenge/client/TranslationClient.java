package com.truelayer.challenge.client;

import com.truelayer.challenge.dto.TranslationResponse;
import reactor.core.publisher.Mono;

public interface TranslationClient {

    TranslationResponse yodaTranslation(String text);

    TranslationResponse shakespeareTranslation(String text);

}
