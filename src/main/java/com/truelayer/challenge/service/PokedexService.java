package com.truelayer.challenge.service;

import com.truelayer.challenge.dto.PokemonDTO;

public interface PokedexService {

    PokemonDTO getPokemon(String pokemonName);

}
