/**Read Files, format strings and add each string to an ordered TreeMap*/

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**Responsible for the input object*/
public class InputClass {
    private String path;
    private ArrayList<String> strings;
    private Scanner readString;
    private OutputClass output;

    /**Constructor for the InputClass*/
    public InputClass(String path) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        this.path = path;
        this.strings = new ArrayList<>(0);
        this.output = new OutputClass();
        processFile();
    }

    /**gets the path of a file
     * @return path
     * */
    public String getPath() {
        return path;
    }

    /**This method is responsible for format the string(remove the pontuaction, commas and put to lower case)
     * @return a string without punctuation and in lower case
     * */
    private String formatString(String str){
        String punctuation = "[[_{}()!@#$%¨&*+=/?´`~^;:.>,<|]]";
        String strFormat = str.replaceAll(punctuation, "").toLowerCase();
        return strFormat;
    }

    /**This method is responsible for the file reading, calling the function wich will process the file, and printing all the file processing
     * @return a TreeMap containing objects with a String as Key and an ArrayList as value
     * */
    private AbstractMap<String, ArrayList<String>> processFile() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        File file = new File(getPath());
        readString = new Scanner(file, "UTF-8");

        if (!file.exists()) {
            throw new IOException("INVALID PATH!");
        }

        while(readString.hasNext()){
            strings.add(formatString(readString.next()));
        }

        readString.close();
        System.out.println(strings);
        System.out.println(generateOrderedMap(strings));
        System.out.println(generateOrderedArray(strings, generateOrderedMap(strings)));
        System.out.println(generateLinkedMap(strings));
        output.textWriter(generateLinkedMap(strings));
        return generateLinkedMap(strings);
    }

    /**This function generates and ordered HashMap
     * @return a sorted HashMap */
    private Map<String, Integer> generateOrderedMap(ArrayList<String> list)
    {
        Map<String, Integer> hashMap = new HashMap<String, Integer>();

        for (String i : list) {
            Integer j = hashMap.get(i);
            hashMap.put(i, (j == null) ? 1 : j + 1);
        }

        LinkedHashMap<String, Integer> orderedMap = hashMap.entrySet() .stream() .sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, content) -> content, LinkedHashMap::new));
        return orderedMap;
    }

    /**This method gets the index of an element in an arraylist
     * @return the index of an element*/
    private int getIndex(ArrayList<String> list, String elementOfList){
        int index = 0;
        for (int count = 0; count < list.size(); count++){
            if(elementOfList.equals(list.get(count))) {
                index = count;
                break;
            }
        }
        return index;
    }

    /**This method generates an ordered array with the key and values from a map
     * @return an ordered array*/
    private ArrayList<String> generateOrderedArray(ArrayList<String> list, Map<String, Integer> hashMap){
        ArrayList<String> orderedList = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            orderedList.add(entry.getKey());
            orderedList.add(String.valueOf((entry.getValue())));

            if (list.size() >= getIndex(list, entry.getKey()) + 2) {
                orderedList.add(list.get(getIndex(list, entry.getKey()) + 1));
            }
        }
        return orderedList;
    }

    /**This function adds an element and a list to a TreeMap*/
    private void addToMap(String elementOfText, ArrayList<String> list, TreeMap<String, ArrayList<String>> map){
        map.put(elementOfText, list);
    }

    /**This method generates a TreeMap containing a word(key) from the txt and an ArrayList(value) wich contains every word that the key points
     * @return an ordered TreeMap*/
    private TreeMap<String, ArrayList<String>> generateLinkedMap(ArrayList<String> list){
        TreeMap<String, ArrayList<String>> linkedMap = new TreeMap<String, ArrayList<String>>();

        for(int count = 0; count < list.size() - 1; count++){

            if(linkedMap.containsKey(list.get(count))) {

                if (!linkedMap.get(list.get(count)).contains(list.get(count + 1))) {
                    linkedMap.get(list.get(count)).add(list.get(count + 1));
                    Collections.sort(linkedMap.get(list.get(count)));
                }
            }

            else{
                ArrayList<String> pointers = new ArrayList<String>();
                pointers.add(list.get(count + 1));
                addToMap(list.get(count), pointers, linkedMap);
            }
        }
        return linkedMap;
    }
}

