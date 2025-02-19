package com.truelayer.challenge.dto;

import lombok.Value;

import java.util.List;

@Value
public class PokeApiResponse {

    Long id;

    String name;

    Habitat habitat;

    List<FlavorEntry> flavorTextEntries;

    boolean isLegendary;

    @Value
    public static class Habitat {
        String name;
    }

    @Value
    public static class FlavorEntry {
        String flavorText;
        Language language;
    }

    @Value
    public static class Language {
        String name;
    }
}
