package com.truelayer.challenge.service;

import com.truelayer.challenge.client.PokeApiClient;
import com.truelayer.challenge.client.TranslationClient;
import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.dto.PokemonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokedexServiceImpl implements PokedexService {

    @Autowired
    PokeApiClient pokeApiClient;

    @Autowired
    TranslationClient translationClient;

    private static final String LANGUAGE_EN = "en";

    private static final String CAVE_HABITAT = "cave";

    public PokemonDTO getPokemon(String pokemonName) {
        PokeApiResponse response = pokeApiClient.getPokemon(pokemonName);

        return PokemonDTO.builder()
                .id(response.getId())
                .name(response.getName())
                .description(extractDescription(response))
                .habitat(response.getHabitat() != null ? response.getHabitat().getName() : null)
                .isLegendary(response.isLegendary())
                .build();
    }

    public PokemonDTO getTranslatedPokemon(String pokemonName) {
        PokeApiResponse response = pokeApiClient.getPokemon(pokemonName);
        String description = extractDescription(response);
        String habitat = response.getHabitat() != null ? response.getHabitat().getName() : null;

        if (CAVE_HABITAT.equals(habitat) || response.isLegendary()) {
            description = translationClient.yodaTranslation(description).getContents().getTranslated();
        } else {
            description = translationClient.shakespeareTranslation(description).getContents().getTranslated();
        }

        return PokemonDTO.builder()
                .id(response.getId())
                .name(response.getName())
                .description(description)
                .habitat(habitat)
                .isLegendary(response.isLegendary())
                .build();
    }

    private String extractDescription(PokeApiResponse response) {
        return response.getFlavorTextEntries().stream()
                .filter(flavorEntry -> LANGUAGE_EN.equals(flavorEntry.getLanguage().getName()))
                .map(flavorEntry -> flavorEntry.getFlavorText().replaceAll("[\\n\\f]", " ").trim())
                .findFirst().orElse(null);
    }

}
