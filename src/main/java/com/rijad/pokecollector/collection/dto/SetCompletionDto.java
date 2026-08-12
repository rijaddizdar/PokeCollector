package com.rijad.pokecollector.collection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SetCompletionDto(String setName, String externalId, long owned, int total) {
    @JsonProperty("percentage")
    public double percentage(){
        return total==0?0:owned*100.0/total;
    }
}

