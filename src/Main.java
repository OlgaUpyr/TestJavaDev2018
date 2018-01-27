import java.util.*;

public class Main {
    private static final String INPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameData.json";
    private static final String OUTPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameAnswer.json";

    private static List<Product> productList;
    private static Map<String, List<Edge>> graph;

    public static boolean isEdgeExist(String currentKeyName, String currentValueName){
        for (int l = 0; l < graph.get(currentKeyName).size(); l++) {
            Edge currEdge = graph.get(currentKeyName).get(l);
            if (currentValueName.equals(currEdge.getName())) {
                currEdge.setWeight(currEdge.getWeight() + 1);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JSONReader jsonReader = new JSONReader();
        productList = jsonReader.read(INPUT_FILENAME);

        String[] currentName;
        List<String> startEdges;
        graph = new LinkedHashMap<>();

        for (int i = 0; i < 1; i++) {
            startEdges = new ArrayList<>();
            graph.clear();
            for (int j = 0; j < productList.get(i).getNames().size(); j++) {
                currentName = productList.get(i).getNames().get(j).split("\\s");
                if(!(startEdges.contains(currentName[0])))
                    startEdges.add(currentName[0]);
                for (int k = 1; k < currentName.length; k++) {
                    if(!(currentName[k].equals("-") || currentName[k].equals("/"))) {
                        if (!graph.containsKey(currentName[k - 1]))
                            graph.put(currentName[k - 1], new LinkedList<>());
                        if(!isEdgeExist(currentName[k - 1], currentName[k]))
                            graph.get(currentName[k - 1]).add(new Edge(currentName[k], 1));
                    }
                }
            }
        }


//        for (Map.Entry<String, List<Edge>> entry : graph.entrySet()) {
//            System.out.println("Key = " + entry.getKey());
//            List<Edge> edgeList = entry.getValue();
//            for (int i = 0; i< edgeList.size(); i++) {
//                System.out.println("Value = " + edgeList.get(i).toString());
//            }
//        }

        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.write(OUTPUT_FILENAME, productList);
    }
}
