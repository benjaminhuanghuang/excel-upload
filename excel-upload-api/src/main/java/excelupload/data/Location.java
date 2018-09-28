package excelupload.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class Location {
    private Address address;
    private Characteristics characteristics;

    public Location() {
        // Needed by Jackson deserialization
    }

    public Location(Address address, Characteristics characteristics) {
        this.address = address;
    }

    @JsonProperty
    public Address getAddress() {
        return this.address;
    }

    @JsonProperty
    public Characteristics getCharacteristics() {
        return this.characteristics;
    }
}
