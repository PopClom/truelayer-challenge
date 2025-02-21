package com.truelayer.challenge.strategy;

import org.springframework.stereotype.Component;

@Component
public class FallbackTranslationStrategy implements TranslationStrategy {

    @Override
    public String translate(String text) {
        return text;
    }
}
