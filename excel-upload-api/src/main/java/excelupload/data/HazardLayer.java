package excelupload.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HazardLayer {
    private String lookup;
    private int returnPeroid;
    private Boolean defended;
    private int proximityDistanceInMeters;


    public HazardLayer() {
        // Needed by Jackson deserialization
    }

    public HazardLayer(String lookup,
                       int returnPeroid,
                       Boolean defended,
                       int proximityDistanceInMeters) {
        this.lookup = lookup;
        this.returnPeroid = returnPeroid;
        this.defended = defended;
        this.proximityDistanceInMeters = proximityDistanceInMeters;
    }

    @JsonProperty
    public String getLookup() {
        return this.lookup;
    }

    @JsonProperty
    public int getReturnPeroid() {
        return this.returnPeroid;
    }

    @JsonProperty
    public Boolean getDefended() {
        return this.defended;
    }

    @JsonProperty
    public int getProximityDistanceInMeters() {
        return this.proximityDistanceInMeters;
    }

}
