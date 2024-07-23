package com.gerimedica.assignment.util;

import com.gerimedica.assignment.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Prashanth Nander
 */
public class CsvUtility {

    public static String TYPE = "text/csv";

    public static boolean hasCsvFormat (MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Product> csvToProductList (InputStream is) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(bReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Product> productList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                String fromDateString = csvRecord.get("fromDate");
                String toDateString = csvRecord.get("toDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fromDate = LocalDate.parse(fromDateString, formatter);
                LocalDate toDate = null;
                if (null != toDateString && !toDateString.isEmpty())
                    toDate = LocalDate.parse(toDateString, formatter);

                Product product = new Product();
                product.setSource(csvRecord.get("source"));
                product.setCodeListCode(csvRecord.get("codeListCode"));
                product.setCode(csvRecord.get("code"));
                product.setDisplayValue(csvRecord.get("displayValue"));
                product.setLongDescription(csvRecord.get("longDescription"));
                product.setFromDate(fromDate);
                product.setToDate(toDate);
                if (!csvRecord.get("sortingPriority").isEmpty())
                    product.setSortingPriority(Integer.valueOf(csvRecord.get("sortingPriority")));
                productList.add(product);
            }
            return productList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV data :: " + e.getMessage());
        }
    }
}

