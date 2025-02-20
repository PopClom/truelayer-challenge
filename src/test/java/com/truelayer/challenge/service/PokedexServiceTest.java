package com.truelayer.challenge.service;

import com.truelayer.challenge.client.PokeApiClient;
import com.truelayer.challenge.dto.PokeApiResponse;
import com.truelayer.challenge.dto.PokemonDTO;
import com.truelayer.challenge.exception.PokemonNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    private static final String ENGLISH_DESCRIPTION = "Its skin is very hard, so it is unhurt neven if stepped on by sumo wrestlers.";

    private static final String SPANISH_DESCRIPTION = "Ni un pisotón de un luchador de sumo lo aplastaría gracias a su piel sólida.";

    @BeforeEach
    void setUp() {
        pokeApiResponse = new PokeApiResponse(618L, "Stunfisk", new PokeApiResponse.Habitat("forest"),
                Arrays.asList(
                        new PokeApiResponse.FlavorEntry(ENGLISH_DESCRIPTION, new PokeApiResponse.Language("en")),
                        new PokeApiResponse.FlavorEntry(SPANISH_DESCRIPTION, new PokeApiResponse.Language("es"))
                ), true);
    }

    @Test
    void testGetPokemon_Success() {
        String pokemonName = "Stunfisk";
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(pokeApiResponse);

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertEquals(pokeApiResponse.getId(), result.getId());
        assertEquals(pokeApiResponse.getName(), result.getName());
        assertEquals(ENGLISH_DESCRIPTION, result.getDescription());
        assertEquals(pokeApiResponse.getHabitat().getName(), result.getHabitat());
        assertTrue(result.isLegendary());
    }

    @Test
    void testGetPokemon_PokemonNotFound() {
        String pokemonName = "NonExistentPokemon";
        when(pokeApiClient.getPokemon(pokemonName)).thenThrow(new PokemonNotFound("Pokemon not found"));

        assertThrows(PokemonNotFound.class, () -> pokedexService.getPokemon(pokemonName));
    }

    @Test
    void testGetPokemon_NoFlavorTextFound() {
        String pokemonName = "Stunfisk";
        PokeApiResponse.Habitat habitat = new PokeApiResponse.Habitat("forest");
        PokeApiResponse responseWithoutFlavorText = new PokeApiResponse(618L, pokemonName, habitat, Collections.emptyList(), true);
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(responseWithoutFlavorText);

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertNull(result.getDescription());
    }

    @Test
    void testGetPokemon_NoEnglishFlavorText() {
        String pokemonName = "Stunfisk";
        PokeApiResponse.Habitat habitat = new PokeApiResponse.Habitat("forest");
        List<PokeApiResponse.FlavorEntry> entries = List.of(new PokeApiResponse.FlavorEntry(SPANISH_DESCRIPTION, new PokeApiResponse.Language("es")));
        PokeApiResponse responseWithoutEnglishFlavorText = new PokeApiResponse(618L, pokemonName, habitat, entries, true);
        when(pokeApiClient.getPokemon(pokemonName)).thenReturn(responseWithoutEnglishFlavorText);

        PokemonDTO result = pokedexService.getPokemon(pokemonName);

        assertNotNull(result);
        assertNull(result.getDescription());
    }

}
