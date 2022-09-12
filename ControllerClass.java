/**Control the program with the method ControllerClass()*/

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**Control the program*/
public class ControllerClass {

    /**Creates an InputClass object*/
    public void controlProgram(){
        InputClass input;
        try{
            String userInput = JOptionPane.showInputDialog("Path:");
            input = new InputClass(userInput);
        } catch (IOException e){
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    /**Constructor for the ControllerClass*/
    public void ControllerClass(){
        controlProgram();
    }
}

