package com.truelayer.challenge.service;

import com.truelayer.challenge.dto.PokemonDTO;
import com.truelayer.challenge.strategy.FallbackTranslationStrategy;
import com.truelayer.challenge.strategy.ShakespeareTranslationStrategy;
import com.truelayer.challenge.strategy.YodaTranslationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationServiceImplTest {

    @InjectMocks
    private TranslationServiceImpl translationService;

    @Mock
    private YodaTranslationStrategy yodaTranslationStrategy;

    @Mock
    private ShakespeareTranslationStrategy shakespeareTranslationStrategy;

    @Mock
    private FallbackTranslationStrategy fallbackStrategy;

    private static final String ORIGINAL_DESCRIPTION = "A very powerful Pokémon.";
    private static final String YODA_TRANSLATION = "Powerful, this Pokémon is.";
    private static final String SHAKESPEARE_TRANSLATION = "A most wondrous and mighty Pokémon.";
    private static final String FALLBACK_TRANSLATION = "A strong Pokémon indeed.";

    @Test
    void testTranslate_LegendaryPokemon_UsesYodaTranslation() {
        PokemonDTO legendaryPokemon = PokemonDTO.builder()
                .id(1L)
                .name("Mewtwo")
                .description(ORIGINAL_DESCRIPTION)
                .habitat("forest")
                .isLegendary(true)
                .build();

        when(yodaTranslationStrategy.translate(ORIGINAL_DESCRIPTION)).thenReturn(YODA_TRANSLATION);

        PokemonDTO result = translationService.translate(legendaryPokemon);

        assertNotNull(result);
        assertEquals(YODA_TRANSLATION, result.getDescription());
    }

    @Test
    void testTranslate_CaveHabitatPokemon_UsesYodaTranslation() {
        PokemonDTO cavePokemon = PokemonDTO.builder()
                .id(2L)
                .name("Zubat")
                .description(ORIGINAL_DESCRIPTION)
                .habitat("cave")
                .isLegendary(false)
                .build();

        when(yodaTranslationStrategy.translate(ORIGINAL_DESCRIPTION)).thenReturn(YODA_TRANSLATION);

        PokemonDTO result = translationService.translate(cavePokemon);

        assertNotNull(result);
        assertEquals(YODA_TRANSLATION, result.getDescription());
    }

    @Test
    void testTranslate_NormalPokemon_UsesShakespeareTranslation() {
        PokemonDTO normalPokemon = PokemonDTO.builder()
                .id(3L)
                .name("Stunfisk")
                .description(ORIGINAL_DESCRIPTION)
                .habitat("forest")
                .isLegendary(false)
                .build();

        when(shakespeareTranslationStrategy.translate(ORIGINAL_DESCRIPTION)).thenReturn(SHAKESPEARE_TRANSLATION);

        PokemonDTO result = translationService.translate(normalPokemon);

        assertNotNull(result);
        assertEquals(SHAKESPEARE_TRANSLATION, result.getDescription());
    }

    @Test
    void testTranslate_TranslationFails_UsesFallbackTranslation() {
        PokemonDTO normalPokemon = PokemonDTO.builder()
                .id(4L)
                .name("Pikachu")
                .description(ORIGINAL_DESCRIPTION)
                .habitat("forest")
                .isLegendary(false)
                .build();

        when(shakespeareTranslationStrategy.translate(ORIGINAL_DESCRIPTION)).thenThrow(new RuntimeException("Translation API down"));
        when(fallbackStrategy.translate(ORIGINAL_DESCRIPTION)).thenReturn(FALLBACK_TRANSLATION);

        PokemonDTO result = translationService.translate(normalPokemon);

        assertNotNull(result);
        assertEquals(FALLBACK_TRANSLATION, result.getDescription());
    }
}
