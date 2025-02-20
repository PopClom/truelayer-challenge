package com.truelayer.challenge.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TranslationResponse {

    Contents contents;

    @Value
    @Builder
    public static class Contents {
        String translated;
    }

}
