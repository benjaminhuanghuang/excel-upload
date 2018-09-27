package excelupload.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    private String streetAddress;
    private String city;
    private String county;
    private String state;
    private String country;
    private String postalCode;

    private double latitude;
    private double longitude;


    public Location() {
        // Needed by Jackson deserialization
    }

    public Location(String streetAddress,
                    String city,
                    String county,
                    String state,
                    String country,
                    String postalCode,
                    double latitude,
                    double longitude) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.county = county;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonProperty
    public String getStreetAddress() {
        return streetAddress;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public String getCounty() {
        return county;
    }

    @JsonProperty
    public String getState() {
        return state;
    }

    @JsonProperty
    public String getCountry() {
        return country;
    }

    @JsonProperty
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty
    public double getLongitude() {
        return longitude;
    }
}
