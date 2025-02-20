package com.truelayer.challenge.client;

import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.exception.PokemonNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokeApiClientImpl implements PokeApiClient {

    private final WebClient webClient;

    public PokeApiClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://pokeapi.co/api/v2").build();
    }

    public PokeApiResponse getPokemon(String pokemonName) {
        return webClient.get()
                .uri("/pokemon-species/{name}", pokemonName)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, this::handleNotFound)
                .bodyToMono(PokeApiResponse.class)
                .block();
    }

    private Mono<? extends Throwable> handleNotFound(ClientResponse clientResponse) {
        return Mono.error(new PokemonNotFound("Pokemon not found"));
    }

}
