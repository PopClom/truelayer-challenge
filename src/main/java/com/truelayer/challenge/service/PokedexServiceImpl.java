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

    private static final String LANGUAGE_EN = "en";

    public PokemonDTO getPokemon(String pokemonName) {
        PokeApiResponse response = pokeApiClient.getPokemon(pokemonName).block();
        if (response == null) {
            return null;
        }

        return PokemonDTO.builder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getFlavorTextEntries().stream()
                        .filter(flavorEntry -> LANGUAGE_EN.equals(flavorEntry.getLanguage().getName()))
                        .map(PokeApiResponse.FlavorEntry::getFlavorText)
                        .findFirst().orElse(null))
                .habitat(response.getHabitat() != null ? response.getHabitat().getName() : null)
                .isLegendary(response.isLegendary())
                .build();
    }

}
