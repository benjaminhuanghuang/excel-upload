package excelupload.resources;

import excelupload.data.Address;
import excelupload.data.Characteristics;
import excelupload.data.Location;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.client.Entity;


import static org.junit.Assert.*;

public class FileServiceTest {

    @Test
    public void callLIAPITest() {
        FileService fs = new FileService();

        List<Location> locations = new ArrayList<Location>();

        String state = "CA";
        String county = "";
        String streetAddress = "7575 gateway blvd";
        String city = "Newark";
        String country = "USA";
        String postalCode = "94560";

        String countryCode = "US";
        String countryScheme = "ISO2A";

        double latitude = 0;
        double longitude = 0;

        String tiv = "";
        String tivCurrency = "USD";

        String constructionScheme = "ATC";
        String constructionClass = "72A";

        String occupancyScheme = "AFM";
        String occupancyType = "31";


        Address address = new Address(streetAddress,
                city,
                county,
                state,
                country,
                postalCode,
                latitude,
                longitude, countryScheme, countryCode);


        Characteristics characteristics = new Characteristics(occupancyScheme, occupancyType, constructionScheme, constructionClass, 0, 1998);

        locations.add(new Location(address, characteristics));

        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put("locations", locations);
        payload.put("hazardLayers", fs.buildHazardLayers("US"));

//        System.out.println("--Payload-----");
//        System.out.println(Entity.json(payload));

        String res = fs.callLIAPI(payload);
        System.out.println("--Result-----");
        System.out.println(res);
    }
}