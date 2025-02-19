package com.truelayer.challenge.service;

import com.truelayer.challenge.client.PokeApiClient;
import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.dto.PokemonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PokedexServiceTest {

    @Mock
    private PokeApiClient pokeApiClient;

    @InjectMocks
    private PokedexServiceImpl pokedexService;

    private PokeApiResponse pokeApiResponse;

    @BeforeEach
    void setUp() {
        pokeApiResponse = new PokeApiResponse(618L, "Stunfisk", new PokeApiResponse.Habitat("forest"),
                Arrays.asList(
                        new PokeApiResponse.FlavorEntry("Its skin is very hard, so it is unhurt neven if stepped on by sumo wrestlers.", new PokeApiResponse.Language("en")),
                        new PokeApiResponse.FlavorEntry("Ni un pisotón de un luchador de sumo lo aplastaría gracias a su piel sólida.", new PokeApiResponse.Language("es"))
                ), true);
    }

    @Test
    void testGetPokemon_Success() {
        String pokemonName = "Stunfisk";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(Mono.just(pokeApiResponse));

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertEquals(pokeApiResponse.getId(), result.getId());
        assertEquals(pokeApiResponse.getName(), result.getName());
        assertEquals("Its skin is very hard, so it is unhurt neven if stepped on by sumo wrestlers.", result.getDescription());
        assertEquals(pokeApiResponse.getHabitat().getName(), result.getHabitat());
        assertTrue(result.isLegendary());
    }

    @Test
    void testGetPokemon_PokemonNotFound() {
        String pokemonName = "NonExistentPokemon";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(Mono.empty());

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNull(result);
    }

    @Test
    void testGetPokemon_NoFlavorTextFound() {
        PokeApiResponse responseWithoutFlavorText = new PokeApiResponse(1L, "Stunfisk",
                new PokeApiResponse.Habitat("forest"), Collections.emptyList(), true);
        String pokemonName = "Stunfisk";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(Mono.just(responseWithoutFlavorText));

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertNull(result.getDescription());
    }

    @Test
    void testGetPokemon_NoEnglishFlavorText() {
        PokeApiResponse responseWithoutEnglishFlavorText = new PokeApiResponse(1L, "Stunfisk", new PokeApiResponse.Habitat("forest"),
                List.of(new PokeApiResponse.FlavorEntry("Ni un pisotón de un luchador de sumo lo aplastaría gracias a su piel sólida.", new PokeApiResponse.Language("es"))), true);
        String pokemonName = "Stunfisk";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(Mono.just(responseWithoutEnglishFlavorText));

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertNull(result.getDescription());
    }

    @Test
    void testGetPokemon_EmptyResponse() {
        String pokemonName = "Stunfisk";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(Mono.empty());

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNull(result);
    }
}
