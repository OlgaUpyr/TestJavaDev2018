import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONReader {

    public List<Product> read(String INPUT_FILENAME){
        JSONParser parser = new JSONParser();
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = (JSONArray) parser.parse(
                    new FileReader(INPUT_FILENAME));

            for (int i = 0; i < array.size(); i++) {
                JSONObject product = (JSONObject) array.get(i);
                Long id = (Long) product.get("id");
                System.out.println("ID: " + id);
                JSONArray names = (JSONArray) product.get("names");
                System.out.println("Names:");
                Iterator<String> iterator = names.iterator();
                while(iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
                productList.add(new Product(id, names));
                System.out.println("\n\n");
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return productList;
    }
}
