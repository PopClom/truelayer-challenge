package com.truelayer.challenge.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PokemonDTO {

    Long id;

    String name;

}
