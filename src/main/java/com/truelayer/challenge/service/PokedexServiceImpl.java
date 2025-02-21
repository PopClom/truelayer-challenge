package com.truelayer.challenge.service;

import com.truelayer.challenge.client.PokeApiClient;
import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.dto.PokemonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokedexServiceImpl implements PokedexService {

    @Autowired
    PokeApiClient pokeApiClient;

    @Autowired
    TranslationService translationService;

    private static final String LANGUAGE_EN = "en";

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
        PokemonDTO pokemon = getPokemon(pokemonName);
        return translationService.translate(pokemon);
    }

    private String extractDescription(PokeApiResponse response) {
        return response.getFlavorTextEntries().stream()
                .filter(flavorEntry -> LANGUAGE_EN.equals(flavorEntry.getLanguage().getName()))
                .map(flavorEntry -> flavorEntry.getFlavorText().replaceAll("[\\n\\f]", " ").trim())
                .findFirst().orElse(null);
    }

}
