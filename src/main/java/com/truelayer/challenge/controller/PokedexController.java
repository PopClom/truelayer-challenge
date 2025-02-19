package com.truelayer.challenge.controller;

import com.truelayer.challenge.dto.PokemonDTO;
import com.truelayer.challenge.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pokemon")
@Validated
public class PokedexController {

    private final PokedexService pokedexService;

    @Autowired
    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @GetMapping("/{pokemonId}")
    public PokemonDTO getPokemonById(@PathVariable Long pokemonId) {
        return PokemonDTO.builder()
                .name("Pikachu")
                .id(25L)
                .build();
    }

}
