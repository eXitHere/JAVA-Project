package dfts;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List; 
import javafx.util.Pair;
  
public class MapCode { 
  
    private int v;  
    private ArrayList<Integer>[] adjList;  
    private ArrayList<Integer>[] time;
    private List<String> nameStation = new ArrayList<>();
    private List<String> nameRailway = new ArrayList<>();
    private List<List<String>> subRailway = new ArrayList<>();
    private List<List<String>> timeRailway = new ArrayList<>();
    private final List<List<Integer>> displayed = new ArrayList<>();
    private final List<List<Integer>> timeDisplay = new ArrayList<>();
    private final MyMap<Integer,String> mapName = new MyMap<>();
    private final List<Integer> price = new ArrayList<>();
    
    public MapCode(List<String> ns,List<String> nr, List<List<String>> sr,List<List<String>> tr){
        this.nameStation = ns;
        this.nameRailway = nr;
        this.subRailway = sr;
        this.timeRailway = tr;
        this.v = this.nameStation.size(); 
        initAdjList();
        initList();
    }
    
    @SuppressWarnings("unchecked") 
    private void initAdjList(){ 
        adjList = new ArrayList[v];
        time = new ArrayList[v];
        for(int i = 0; i < v; i++){ 
            adjList[i] = new ArrayList<>(); 
            time[i] = new ArrayList<>();
        } 
    } 
      
    public void addEdge(int u, int v,int t){
        boolean added = false;
        for(var x : adjList[u]){
            if(x.equals(u)){
                added = true;
            }
        }
        if(!added){
            adjList[u].add(v);
            time[u].add(t);
        }
        added = false;
        for(var x : adjList[v]){
            if(x.equals(u)){
                added = true;
            }
        }
        if(!added){
            adjList[v].add(u);
            time[v].add(t);
        }
    } 
       
    public void printAllPaths(int s, int d){ 
        /*int index=0;
        for(var x : adjList){
            System.out.println("In : " + getName(index));
            for(var y : x){
                System.out.print(getName(y) + " and ");
            }
            System.out.println("\n\n");
            index++;
        }*/
        boolean[] isVisited = new boolean[v]; 
        ArrayList<Integer> pathList = new ArrayList<>(); 
        ArrayList<Integer> timeList = new ArrayList<>();
        pathList.add(s); 
        printAllPathsUtil(s, d, isVisited, pathList,timeList,0); 
    } 

    private void printAllPathsUtil(Integer u, Integer d,boolean[] isVisited,List<Integer> localPathList,List<Integer> localTimeList,int price) {
        isVisited[u] = true; 
        
        if (u.equals(d)){ 
            //System.out.println(localPathList);
            boolean isDisplay = false;
            for(var x : displayed){
                if(x.equals(localPathList)){
                    isDisplay = true;
                }
            }
            /*for(var o : localPathList){
                System.out.print(this.getName(o) + " -> ");
            }
            System.out.println("\nPrice : " + price + " Bath");
            System.out.println(localTimeList + "\n");*/
            if(!isDisplay){
                this.displayed.add(new ArrayList<>(localPathList));
                this.price.add(new Integer(price));
                this.timeDisplay.add(new ArrayList<>(localTimeList));
            }    
            /*System.out.println("After add");
            System.out.println(this.displayed);
            System.out.println(this.price);
            System.out.println(this.timeDisplay);
            System.out.println("\n\n");*/
            isVisited[u]= false;
            return ; 
        } 
          
        for (Integer i : adjList[u]){ 
            if (!isVisited[i]){ 
                localPathList.add(i);
                localTimeList.add(time[u].get(adjList[u].indexOf(i))); 
                price += time[u].get(adjList[u].indexOf(i))*2;
                printAllPathsUtil(i, d, isVisited, localPathList, localTimeList ,price);
                localPathList.remove(i); 
                localTimeList.remove(time[u].get(adjList[u].indexOf(i)));
            } 
        } 
        isVisited[u] = false; 
    } 
    
    public Pair<Pair<List<List<Integer>>,List<List<Integer>>>,List<Integer>> getPath(String start,String stop){
        //System.out.println("Call thiddaa");
        //System.out.println("Start " +start + " : " + stop);
        /*
            System.out.println(this.displayed);
            System.out.println(this.price);
            System.out.println(this.timeDisplay);
        */
        printAllPaths(getIndex(start), getIndex(stop));
        /*System.out.println("ingetPath " );
        System.out.println(this.displayed);
        System.out.println(this.price);
        System.out.println(this.timeDisplay);
        System.out.println("\n\n");*/
        return new Pair<>(new Pair<>(this.displayed,this.timeDisplay),this.price);
    }
    
    /*
    // Driver program 
    public static void main(String[] args) throws IOException  
    { 
        // Create a sample graph 
        MapCode g = new MapCode(); 
        g.readData();
        g.initList();
        
        // arbitrary source 
        int s = 2; 
      
        // arbitrary destination 
        int d = 3; 
        System.out.println(g.getName(s) + " -> " + g.getName(d));
        System.out.println("Following are all different paths from "+s+" to "+d); 
        g.printAllPaths(s, d); 
  
    }
    */
    public void initList(){
        this.nameStation.forEach((n) -> {
            this.mapName.put(this.nameStation.indexOf(n), n);
        });
        //System.out.println("map : " + this.mapName);
        for(var sub : this.subRailway){
            int indexSub = this.subRailway.indexOf(sub);
            for(int i=1;i<sub.size();i++){
                this.addEdge(this.mapName.getKey(sub.get(i)), this.mapName.getKey(sub.get(i-1)),toInt(timeRailway.get(indexSub).get(i-1)));
                for(int anatherIndex = this.subRailway.indexOf(sub)+1; anatherIndex< this.subRailway.size(); anatherIndex++){
                    List<String> anather = this.subRailway.get(anatherIndex);
                     if(!sub.equals(anather)){
                        int indexAnather = this.subRailway.indexOf(anather);
                        for(int j=0;j<anather.size();j++){
                            if(anather.get(j).equals(sub.get(i))){
                                //System.out.println("Found " + indexSub + " -- " + indexAnather + " value : " + anather.get(j));
                                if(j+1<anather.size()){ //can +1
                                    this.addEdge(this.mapName.getKey(sub.get(i)), this.mapName.getKey(anather.get(j+1)),toInt(timeRailway.get(indexAnather).get(j)));
                                    //System.out.println("D1");
                                }else if(j>0){ //can -1
                                    //System.out.println("D2");
                                    this.addEdge(this.mapName.getKey(sub.get(i)), this.mapName.getKey(anather.get(j-1)),toInt(timeRailway.get(indexAnather).get(j-1)));
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public String getName(int index){
        return this.mapName.get(index);
    }
    
    public int getIndex(String name){
        return this.mapName.getKey(name);
    }
    
    private int getSize(){
        return this.nameStation.size();
    }
    
    private Integer toInt(String value){
        return Integer.parseInt(value);
    }
    
    private void readData() throws FileNotFoundException, IOException{
        String line;
        List<String> bufReturn;
        bufReturn = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/resources/data/", "data.txt") , Charset.forName("UTF-8"))) {
            while ((line = reader.readLine()) != null ) {
              //separate all csv fields into string array
                //String[] lineVariables = line.split(","); 
                //System.out.println(Arrays.toString(lineVariables));
                bufReturn.add(line);
            }
        }catch (IOException e) {
            System.err.println(e);
        }
        nameStation = Arrays.asList(bufReturn.get(0).split(","));
        nameRailway = Arrays.asList(bufReturn.get(1).split(","));
        int lastIndex = 2;
        for (String nameRailway1 : nameRailway) {
            subRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
        for (String nameRailway1 : nameRailway) {
            timeRailway.add(Arrays.asList(bufReturn.get(lastIndex).split(",")));
            lastIndex++;
        }
    }
} 