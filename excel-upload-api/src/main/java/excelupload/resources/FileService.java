package excelupload.resources;

import com.codahale.metrics.annotation.Timed;
import excelupload.data.Location;
import excelupload.data.QuantumResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


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
    public List<Location> uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

//        String uploadedFileLocation = "./upload/" + fileDetail.getFileName();
//
//        // save file
//        writeToFile(uploadedInputStream, uploadedFileLocation);

        List<Location> results = new ArrayList<Location>();

        XSSFWorkbook wb = new XSSFWorkbook(uploadedInputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        //
        for (int i = 1; i <= rowCount; i++) {
            String streetAddress = this.getCellString(sheet.getRow(i).getCell(0));
            String city = this.getCellString(sheet.getRow(i).getCell(1));
            String county = this.getCellString(sheet.getRow(i).getCell(2));
            String state = this.getCellString(sheet.getRow(i).getCell(3));
            String country = this.getCellString(sheet.getRow(i).getCell(4));
            String postalCode = this.getCellString(sheet.getRow(i).getCell(5));

            double latitude = Double.parseDouble(this.getCellString(sheet.getRow(i).getCell(6)));
            double longitude = Double.parseDouble(this.getCellString(sheet.getRow(i).getCell(7)));


            results.add(new Location(streetAddress,
                                    city,
                                    county,
                                    state,
                                    country,
                                    postalCode,
                                    latitude,
                                    longitude));
        }
        return results;
    }

    private String getCellString(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
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
