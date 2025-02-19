package com.truelayer.challenge.client;

import com.truelayer.challenge.dto.PokeApiResponse;
import reactor.core.publisher.Mono;

public interface PokeApiClient {

    Mono<PokeApiResponse> getPokemon(String pokemonName);

}
