package com.truelayer.challenge.strategy;

import com.truelayer.challenge.client.TranslationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YodaTranslationStrategy implements TranslationStrategy {

    @Autowired
    TranslationClient translationClient;

    @Override
    public String translate(String text) {
        return translationClient.yodaTranslation(text)
                .getContents()
                .getTranslated();
    }
}
