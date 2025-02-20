package com.truelayer.challenge.controller;

import com.truelayer.challenge.dto.PokemonDTO;
import com.truelayer.challenge.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pokemon")
public class PokedexController {

    private final PokedexService pokedexService;

    @Autowired
    public PokedexController(PokedexService pokedexService) {
        this.pokedexService = pokedexService;
    }

    @GetMapping("/{name}")
    public PokemonDTO getPokemonByName(@PathVariable String name) {
        return pokedexService.getPokemon(name);
    }

    @GetMapping("/translated/{name}")
    public PokemonDTO getTranslatedPokemonByName(@PathVariable String name) {
        return pokedexService.getTranslatedPokemon(name);
    }

}
