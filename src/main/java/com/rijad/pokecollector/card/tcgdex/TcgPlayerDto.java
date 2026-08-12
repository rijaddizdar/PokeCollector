package com.rijad.pokecollector.card.tcgdex;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class TcgPlayerDto{
    private String unit;
    private String updated;
    private final Map<String, VariantDto> variants = new LinkedHashMap<>();

    @JsonAnySetter
    public void addVariant(String name, VariantDto variant){
        this.variants.put(name, variant);
    }
    public Map<String, VariantDto> getVariants(){
        return this.variants;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
