/**Generate the output File (.csv)*/

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**Responsible for the output object, wich is a .csv file*/
public class OutputClass {

    /**This method generates a .csv file and writes the elements from a TreeMap in each line*/
    public void textWriter(TreeMap<String, ArrayList<String>> graphMap) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        try {
            ArrayList<String> verifyArray = new ArrayList<String>();
            CSVWriter writer = new CSVWriter(new FileWriter("generatedFile.csv"), ',', ICSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_LINE_END) ;
            for (Map.Entry<String, ArrayList<String>> entry : graphMap.entrySet()) {
                for(String keys:graphMap.keySet()){
                    String[] pointersArray= new String[2];
                    pointersArray[0] = keys;
                    pointersArray[1] = graphMap.get(keys).toString().replaceAll("\\p{Punct}", "").replaceAll("\"", "");
                    String str = pointersArray[0] + " " + pointersArray[1];
                    if (!verifyArray.contains(pointersArray[0] + pointersArray[1])){
                        String[] pointersArrayMerged = new String[1];
                        pointersArrayMerged[0] = str;
                        String splitPointersArrayMerded[] = pointersArrayMerged[0].split(" ");
                        writer.writeNext(splitPointersArrayMerded);
                    }
                    verifyArray.add(pointersArray[0]+pointersArray[1]);
                }
            }

            writer.flush();
            writer.close();
            JOptionPane.showMessageDialog(null, "CSV file was successfully generated!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
