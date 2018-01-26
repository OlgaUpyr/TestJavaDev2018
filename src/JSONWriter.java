import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONWriter {

    public void write(String OUTPUT_FILENAME, List<Product> productList){
        JSONArray array = new JSONArray();
        for (int i = 0; i < productList.size(); i++) {
            JSONObject product = new JSONObject();
            product.put("feature", productList.get(i).getFeature());
            product.put("name", productList.get(i).getBestName());
            product.put("id", productList.get(i).getId());
            array.add(product);
        }

        try (FileWriter writer = new FileWriter(OUTPUT_FILENAME)){
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
