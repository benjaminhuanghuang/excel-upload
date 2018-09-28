package excelupload.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Strings;
import excelupload.data.Location;
import excelupload.data.Address;
import excelupload.data.Characteristics;

import excelupload.data.QuantumResult;

//
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
//
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// For RESTful API
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


@Path("/file")
public class FileService {
    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public String Test() {
        return "Hello";
    }


    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

//        String uploadedFileLocation = "./upload/" + fileDetail.getFileName();
//
//        // save file
//        writeToFile(uploadedInputStream, uploadedFileLocation);

        List<Location> locations = new ArrayList<Location>();

        Workbook wb = WorkbookFactory.create(uploadedInputStream);
        Sheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        HashMap<String, Object> hazardLayers = new HashMap<String, Object>();

        //
        for (int i = 1; i <= rowCount; i++) {
            String state = this.getCellString(sheet.getRow(i).getCell(0));
            String county = this.getCellString(sheet.getRow(i).getCell(1));
            String streetAddress = this.getCellString(sheet.getRow(i).getCell(2));
            String city = this.getCellString(sheet.getRow(i).getCell(3));
            String country = this.getCellString(sheet.getRow(i).getCell(4));
            String postalCode = this.getCellString(sheet.getRow(i).getCell(5));

            String countryCode = this.getCellString(sheet.getRow(i).getCell(6));
            String countryScheme = this.getCellString(sheet.getRow(i).getCell(7));

            double latitude = sheet.getRow(i).getCell(8).getNumericCellValue();
            double longitude = sheet.getRow(i).getCell(9).getNumericCellValue();

            String tiv = this.getCellString(sheet.getRow(i).getCell(10));
            String tivCurrency = this.getCellString(sheet.getRow(i).getCell(11));

            String constructionScheme = this.getCellString(sheet.getRow(i).getCell(12));
            String constructionClass = this.getCellString(sheet.getRow(i).getCell(13));

            String occupancyScheme = this.getCellString(sheet.getRow(i).getCell(14));
            String occupancyType = this.getCellString(sheet.getRow(i).getCell(15));


            Address address = new Address(streetAddress,
                    city,
                    county,
                    state,
                    country,
                    postalCode,
                    latitude,
                    longitude, countryScheme, countryCode);


            Characteristics characteristics = new Characteristics(occupancyScheme, occupancyType, constructionScheme, constructionClass, 10, 1998);

            locations.add(new Location(address, characteristics));
            hazardLayers = this.buildHazardLayers(countryCode);

        }
        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put("locations", locations);
        payload.put("hazardLayers", hazardLayers);

        String res = this.callLIAPI(payload);
        return res;
    }

    public HashMap<String, Object> buildHazardLayers(String countryCode) {
        HashMap<String, Object> layers = new HashMap<String, Object>();

        if (countryCode == "US") {
//            layers.put("US_Earthquake_Risk_Score", null);
            layers.put("US_Hurricane_Risk_Score", new HashMap<String, Object>().put("annualLossRate", 200));
//            layers.put("US_Earthquake_Loss_Cost", null);
//            layers.put("US_Hurricane_Loss_Cost", null);
//            layers.put("US_MMI", null);
//            layers.put("Us_FEMA_Flood", null);
//            layers.put("US_Flood_Hazard_Metadata", null);
        } else if (countryCode == "UK") {
            layers.put("European_Flood", null);
        } else if (countryCode == "NZ") {
            layers.put("NZ_MMI", null);
        }
        return layers;
    }

    public String callLIAPI(HashMap<String, Object> payload) {
        String url = "https://one.dev.azure.rmsonecloud.net/api/li/locationintelligence";
        String token = "Bearer "+"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEuNiIsInR5cGUiOiJpYW0iLCJsb2dpbiI6IjE1MzgxMDM0NDI0MTYiLCJpYXQiOjE1MzgxMDM0NDIsImV4cCI6MTUzODEwNDM0MiwiYXVkIjpbImlhbSJdfQ.ieFhrvA4ZM9US8T53dLjiDQZj2zkereFdfPcTqwb0SJdKMjGY2zplhaNuHGVmmNh57wuXXO2ncF-R0BPjx38LtdfUzCPIonVVbVC5z3YtJvUCKhi7V37q7gko0Uq_QZIEK7EG9kHhOrJuIRCZHdEkeb5eDZ0eHWKVPQwicPMLln1_7VB_KKnZaDvKfgxc6djAx9If3AbCwlIMcQy7X-mNdXian0PNKg9X-ocwWvXkZ8sMDmRtQvghccWo5kxXUQJEJueiTBkueylP0QMZxWP4I8Hbp14TDd23QUSk-lGQHFf-m9uRVXu3Zb4sOEUOZIaXJpEGLQMvbjvTtnxGGzp_d9f2KB-VP1xNRggW26ZSWi4PWhgPcq0jlGXgU6uKoAFHql0OpocO5Wa_zX1wZJy6iPkf2BSAFtGls9yQwJ9RqGFG-9yKdPet9S4k7V3wElgtz4_6XqqIDZyVYj9KVAq83M6Gx0_Ny0Rn7DsrYGP0oAcjVOnDuK90RTUQl7MDU3D3mdXlusfNgumHLws0nU8uAAKdNi_3zBJW8xIObd8LDJBxCB6KMk449gP6ObVLbISa6Dq3QhjxsRf5y0nFaAlC0529144PpsVTac4FjjpNzao57T-rikiLthEjK1aPO5aFVac4tNugBoNmfTEi7nz7ZjpbaS69zSRKXqenKNY2M4";

        //create jersey rest client
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);


        //send POST request to LI service
        Response apiResponse = target.request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(Entity.json(payload), Response.class);

        //validate response
        String output = apiResponse.readEntity(String.class);
        return output;
    }

    private String getCellString(Cell cell) {
        if (cell == null)
            return "";

        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                return  NumberToTextConverter.toText(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BLANK:
                return "";
            default:
                return "";
        }

    }

    private List<QuantumResult> createMockResult() {
        List<QuantumResult> results = new ArrayList<QuantumResult>();

        results.add(new QuantumResult(1, "NewQuantum"));
        return results;
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
