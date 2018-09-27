package excelupload.data;

// Getters are annotated with @JsonProperty  which allows Jackson to serialize and deserialize from JSON.

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuantumResult {
    private int id;
    private String name;


    public QuantumResult() {
        // Needed by Jackson deserialization
    }

    public QuantumResult(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

}
