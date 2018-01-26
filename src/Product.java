import java.util.List;

public class Product {
    private Long id;
    private List<String> names;
    private String bestName;
    private String feature;

    public Product(){}

    public Product(Long id, List<String> names) {
        this.id = id;
        this.names = names;
    }

    public Long getId() {
        return id;
    }

    public List<String> getNames() {
        return names;
    }

    public String getBestName() {
        return bestName;
    }

    public void setBestName(String optimalName) {
        this.bestName = optimalName;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
