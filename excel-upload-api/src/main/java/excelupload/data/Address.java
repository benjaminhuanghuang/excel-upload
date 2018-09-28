package excelupload.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    private String streetAddress;
    private String cityName;
    private String admin2Name; //county
    private String admin1Name; //state;
    private String countryName;
    private String postalCode;

    private double latitude;
    private double longitude;

    private String countryScheme;
    private String countryCode;


    public Address() {
        // Needed by Jackson deserialization
    }

    public Address(String streetAddress,
                   String cityName,
                   String admin2Name,
                   String admin1Name,
                   String countryName,
                   String postalCode,
                   double latitude,
                   double longitude,
                   String countryScheme,
                   String countryCode) {
        this.streetAddress = streetAddress;
        this.cityName = cityName;
        this.admin2Name = admin2Name;
        this.admin1Name = admin1Name;
        this.countryName = countryName;
        this.postalCode = postalCode;

        this.latitude = latitude;
        this.longitude = longitude;

        this.countryScheme = countryScheme;
        this.countryCode = countryCode;
    }

    @JsonProperty
    public String getStreetAddress() {
        return streetAddress;
    }

    @JsonProperty
    public String getCityName() {
        return cityName;
    }

    @JsonProperty
    public String getAdmin2Name() {
        return admin2Name;
    }

    @JsonProperty
    public String getAdmin1Name() {
        return admin1Name;
    }

    @JsonProperty
    public String getCountryName() {
        return countryName;
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

    @JsonProperty
    public String getCountryScheme() {
        return countryScheme;
    }

    @JsonProperty
    public String getCountryCode() {
        return countryCode;
    }
}
