package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty
    private final String location;

    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

}
