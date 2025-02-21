package com.truelayer.challenge.service;

import com.truelayer.challenge.dto.PokemonDTO;
import com.truelayer.challenge.strategy.FallbackTranslationStrategy;
import com.truelayer.challenge.strategy.ShakespeareTranslationStrategy;
import com.truelayer.challenge.strategy.TranslationStrategy;
import com.truelayer.challenge.strategy.YodaTranslationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    YodaTranslationStrategy yodaTranslationStrategy;

    @Autowired
    ShakespeareTranslationStrategy shakespeareTranslationStrategy;

    @Autowired
    FallbackTranslationStrategy fallbackStrategy;

    private static final String CAVE_HABITAT = "cave";

    public PokemonDTO translate(PokemonDTO pokemon) {
        String description = pokemon.getDescription();
        TranslationStrategy strategy = selectStrategy(pokemon);

        try {
            description = strategy.translate(description);
        } catch (RuntimeException e) {
            log.error("Translation failed: ", e);
            description = fallbackStrategy.translate(description);
        }

        return pokemon.toBuilder().description(description).build();
    }

    private TranslationStrategy selectStrategy(PokemonDTO pokemon) {
        if (CAVE_HABITAT.equals(pokemon.getHabitat()) || pokemon.isLegendary()) {
            return yodaTranslationStrategy;
        }
        return shakespeareTranslationStrategy;
    }
}
