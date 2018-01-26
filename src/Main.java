import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

public class Main {
    private static final String INPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameData.json";
    private static final String OUTPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameAnswer.json";

    private static List<Product> productList;

    public static void main(String[] args) {
        JSONReader jsonReader = new JSONReader();
        productList = jsonReader.read(INPUT_FILENAME);

        

        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.write(OUTPUT_FILENAME, productList);
    }
}
