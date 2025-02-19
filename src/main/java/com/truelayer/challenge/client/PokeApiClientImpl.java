package com.truelayer.challenge.client;

import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.exception.PokemonNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public Mono<PokeApiResponse> getPokemon(String pokemonName) {
        return webClient.get()
                .uri("/pokemon-species/{name}", pokemonName)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleNotFound)
                .bodyToMono(PokeApiResponse.class);
    }

    private Mono<? extends Throwable> handleNotFound(ClientResponse clientResponse) {
        if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
            return Mono.error(new PokemonNotFound("Pokemon not found"));
        }
        return Mono.empty();
    }

}
