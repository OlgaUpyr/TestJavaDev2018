import java.util.*;

public class Main {
    private static final String INPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameData.json";
    private static final String OUTPUT_FILENAME = "C:\\Users\\Oly\\IdeaProjects\\" +
            "TestJavaDev2018\\src\\IOFiles\\bestProductNameAnswer.json";

    private static List<Edge> graph;
    private static List<List<Edge>> availableNames;
    private static String bestName = "";
    private static int edgeIndex = 0;
    private static int indexDFS = 0;
    private static boolean indicator = false;
    private static boolean checkNewName = false;


    private static int getNeighbourEdgeIndex(String currentValueName, int index){
        for (int l = 0; l < graph.size(); l++) {
            Edge currEdge = graph.get(l);
            for (int i = 0; i < currEdge.getNeighbours().size(); i++) {
                if (currentValueName.toLowerCase().equals(currEdge.getNeighbours().get(i).getName().toLowerCase()) && l == index) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static int getNeighbourEdgeWeight(String currentValueName){
        for (int l = 0; l < graph.size(); l++) {
            Edge currEdge = graph.get(l);
            for (int i = 0; i < currEdge.getNeighbours().size(); i++) {
                if (currentValueName.toLowerCase().equals(currEdge.getNeighbours().get(i).getName().toLowerCase())) {
                    return currEdge.getNeighbours().get(i).getWeight() + 1;
                }
            }
        }
        return 1;
    }

    private static int getKeyEdgeIndex(String currentKeyName){
        for (int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getName().toLowerCase().equals(currentKeyName.toLowerCase()))
                return i;
        }
        return -1;
    }

    private static boolean isEdgeNameExist(String name, int index){
        for (int i = 0; i < availableNames.get(index).size(); i++) {
            if(name.toLowerCase().equals(availableNames.get(index).get(i).getName().toLowerCase()))
                return true;
        }
        return false;
    }

    private static void DFS(Edge edge){
        if(!isEdgeNameExist(edge.getName(), indexDFS))
            availableNames.get(indexDFS).add(edge);
        else {
            availableNames.add(new ArrayList<>());
            indexDFS++;
            checkNewName = true;
            return;
        }
        indicator = false;
        for (int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getName().toLowerCase().equals(edge.getName().toLowerCase())) {
                edgeIndex = i;
                checkNewName = false;
                indicator = true;
                break;
            }
        }
        if(!indicator) {
            availableNames.add(new ArrayList<>());
            indexDFS++;
            checkNewName = true;
            return;
        }
        List<Edge> neighbours = graph.get(edgeIndex).getNeighbours();
        graph.get(edgeIndex).setVisited(true);
        for (int i = 0; i < neighbours.size(); i++) {
            Edge e = neighbours.get(i);
            if(e != null && !e.isVisited()) {
                if(checkNewName)
                    availableNames.get(indexDFS).addAll(availableNames.get(indexDFS - 1).subList(0, getEdgeIndex(edge.getName()) + 1));
                DFS(e);
            }
        }
    }

    private static int getEdgeIndex(String name){
        for (int i = 0; i < availableNames.get(indexDFS - 1).size(); i++) {
            if(name.toLowerCase().equals(availableNames.get(indexDFS - 1).get(i).getName().toLowerCase()))
                return i;
        }
        return 0;
    }

    private static Edge getStartEdge(String name, Integer weight){
        for (int i = 0; i < graph.size(); i++) {
            if(graph.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                graph.get(i).setWeight(weight);
                return graph.get(i);
            }
        }
        return new Edge();
    }

    public static void main(String[] args) {
        List<Product> productList;

        JSONReader jsonReader = new JSONReader();
        productList = jsonReader.read(INPUT_FILENAME);

        String[] currentName;
        Map<String, Integer> startEdges;
        graph = new ArrayList<>();
        availableNames = new ArrayList<>();
        availableNames.add(new ArrayList<>());

        for (int i = 0; i < 7; i++) {
            indicator = false;
            startEdges = new HashMap<>();
            graph.clear();
            for (int j = 0; j < productList.get(i).getNames().size(); j++) {
                String pattern = " - ";
                String currentNameStr = productList.get(i).getNames().get(j);
                currentNameStr = currentNameStr.replaceAll(pattern, " ");
                currentName = currentNameStr.split("\\s");
                for (Map.Entry<String, Integer> edgeEntry: startEdges.entrySet()) {
                    if(edgeEntry.getKey().toLowerCase().equals(currentName[0].toLowerCase())){
                        currentName[0] = edgeEntry.getKey();
                        Integer weight = startEdges.get(currentName[0]);
                        startEdges.replace(currentName[0], weight + 1);
                        indicator = true;
                    }
                }
                if(!indicator) {
                    startEdges.put(currentName[0], 1);
                }
                for (int k = 1; k < currentName.length; k++) {
                    int currIndex = getKeyEdgeIndex(currentName[k - 1]);
                    if (currIndex == -1) {
                        graph.add(new Edge(currentName[k - 1], 1));
                        currIndex = graph.size() - 1;
                    }
                    int currNeighbourIndex = getNeighbourEdgeIndex(currentName[k], currIndex);
                    if (currNeighbourIndex == -1) {
                        graph.get(currIndex).addNeighbours(new Edge(currentName[k], getNeighbourEdgeWeight(currentName[k])));
                    } else {
                        Edge edge = graph.get(currIndex).getNeighbours().get(currNeighbourIndex);
                        edge.setWeight(edge.getWeight() + 1);
                    }
                }
            }

            for (Map.Entry<String, Integer> edgeEntry: startEdges.entrySet()) {
                availableNames = new ArrayList<>();
                availableNames.add(new ArrayList<>());
                indexDFS = 0;
                DFS(getStartEdge(edgeEntry.getKey(), edgeEntry.getValue()));

                int wordCount = 0, totalCount = 0, currentWordCount;
                bestName = "";
                for (int k = 0; k < availableNames.get(0).size(); k++) {
                    wordCount += availableNames.get(0).get(k).getWeight();
                }
                if(totalCount < wordCount && availableNames.size() == 2
                        ){
                    bestName = "";
                    for (int k = 0; k < availableNames.get(0).size(); k++) {
                        bestName += availableNames.get(0).get(k).getName() + " ";
                    }
                }
                for (int j = 1; j < availableNames.size() - 1; j++) {
                    currentWordCount = 0;
                    for (int k = 0; k < availableNames.get(j).size(); k++) {
                        currentWordCount += availableNames.get(j).get(k).getWeight();
                    }
                    if((wordCount < currentWordCount && totalCount < wordCount) || (wordCount < currentWordCount)) {
                        totalCount = wordCount;
                        bestName = "";
                        for (int k = 0; k < availableNames.get(j).size(); k++) {
                            bestName += availableNames.get(j).get(k).getName() + " ";
                        }
                    }
                }

                for (int j = 1; j < availableNames.size() - 1; j++) {
                    for (int k = 0; k < availableNames.get(j).size(); k++) {
                        System.out.print(availableNames.get(j).get(k) + " ");
                    }
                    System.out.println();
                }
            }
            System.out.println(bestName);
            productList.get(i).setBestName(bestName);
        }

        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.write(OUTPUT_FILENAME, productList);
    }
}
