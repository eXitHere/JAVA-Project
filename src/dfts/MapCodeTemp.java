package dfts;

import static java.lang.Math.ceil;
import java.util.ArrayList; 
import java.util.Collection;
import java.util.LinkedList;
import java.util.List; 
import javafx.util.Pair;

public class MapCodeTemp { 
    
    private final int v;  
    private ArrayList<Integer>[] adjList;  
    private final List<List<Integer>> wayAll = new LinkedList<>();
    
    private final double[] timeStart = {7.50,6.45,7.10,8.00};
    private final double[] timeStop = {22.50,23.45,22.10,23.00};
    private final MyMap<String, Integer> nameStation = new MyMap<>();
    private List<String> nameRailway = new ArrayList<>();
    private List<List<String>> subRailway = new ArrayList<>();
    private List<List<String>> timeRailway = new ArrayList<>();
    private List<List<String>> timeTableReverse = new ArrayList<>();
    private List<List<Integer>> priceAll = new ArrayList<>();
    private List<List<Integer>> price = new ArrayList<>();
    private List<List<Integer>> priceReverse = new ArrayList<>();
    private List<List<Integer>> priceAllReverse = new ArrayList<>();
    private int pricePerMin = 2;
    
    //private List<List<Integer>> timeTable = new ArrayList<>();
    
    public MapCodeTemp(List<String> ns,List<String> nr, List<List<String>> sr, List<List<String>> tr){ 
        int index= 0;
        for (String temp1 : ns) {
            nameStation.put(temp1,index++);
        }
        this.nameRailway = nr;
        this.subRailway = sr;
        this.timeRailway = tr;
        this.v = this.nameStation.size(); 
        initAdjList();  
        initMember();
        //System.out.println(adjList.length);
    } 
    
    private void initMember(){
        for (int i=0;i<nameRailway.size();i++) { //init
            if(subRailway.get(i).size()==1){
                this.addEdge(nameStation.get(subRailway.get(i).get(0)), nameStation.get(subRailway.get(i).get(0)));
               // System.out.println(nameStation.get(subRailway.get(i).get(0)) +" "+nameStation.get(subRailway.get(i).get(0)));
            }
            for(int j=1;j<subRailway.get(i).size();j++){
               // System.out.println(subRailway.get(i).get(j));
               // System.out.println(nameStation.get(subRailway.get(i).get(j-1)) +" "+nameStation.get(subRailway.get(i).get(j)));
                this.addEdge(nameStation.get(subRailway.get(i).get(j-1)), nameStation.get(subRailway.get(i).get(j)));
                this.addEdge(nameStation.get(subRailway.get(i).get(j)), nameStation.get(subRailway.get(i).get(j-1)));
            }
        }
        for(int i=0;i<nameRailway.size();i++){
            for(int j=0;j<subRailway.get(i).size();j++){
                for(int k=i+1;k<nameRailway.size();k++){
                    for(int l=0;l<subRailway.get(k).size();l++){
                        if(nameStation.get(subRailway.get(i).get(j)) == nameStation.get(subRailway.get(k).get(l))){
                            //System.out.println(i + " To " + k);
                            //System.out.println(subRailway.get(k).get(l-1));
                            //System.out.println("Debug : " + subRailway.get(k).get(l-1));
                            if(nameStation.get(subRailway.get(k).get(l-1)) != null){
                                this.addEdge(nameStation.get(subRailway.get(i).get(j)), nameStation.get(subRailway.get(k).get(l-1)));
                                //System.out.println(nameStation.get(subRailway.get(i).get(j)) + " to " +nameStation.get(subRailway.get(k).get(l-1)) );
                                //System.out.println(nameStation.getKey(nameStation.get(subRailway.get(i).get(j))) + " to " + subRailway.get(k).get(l-1));
                                this.addEdge(nameStation.get(subRailway.get(k).get(l-1)), nameStation.get(subRailway.get(i).get(j)));
                                
                            }
                            if(nameStation.get(subRailway.get(k).get(l+1)) != null){
                                this.addEdge(nameStation.get(subRailway.get(i).get(j)), nameStation.get(subRailway.get(k).get(l+1)));
                                //System.out.println(nameStation.getKey(nameStation.get(subRailway.get(i).get(j))) + " to " + subRailway.get(k).get(l+1));
                                this.addEdge(nameStation.get(subRailway.get(k).get(l+1)), nameStation.get(subRailway.get(i).get(j)));
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked") 
    private void initAdjList() { 
        adjList = new ArrayList[v];   
        for(int i = 0; i < v; i++) { 
            adjList[i] = new ArrayList<>(); 
        } 
    } 

    private void addEdge(int u, int v) { 
        adjList[u].add(v);  
    } 
      
    private List<List<Integer>> printAllPaths(int s, int d)  { 
        boolean[] isVisited = new boolean[v]; 
        ArrayList<Integer> pathList = new ArrayList<>(); 
        pathList.add(s); 
        printAllPathsUtil(s, d, isVisited, pathList); 
        /*for(int i=0;i<wayAll.size();i++){
            System.out.println(wayAll.get(i));
        }*/
        return wayAll;
    } 
  
    private void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited, List<Integer> localPathList) { 
        isVisited[u] = true;    
        if (u.equals(d))  { 
            //System.out.println(localPathList);
            boolean dupicate = false;
            for(List<Integer> t : wayAll){
                //System.out.println(t + " Equals to " + localPathList);
                if(t.equals(localPathList)){
                    //System.out.println("Removed : " + t);
                    dupicate = true;
                    break;
                    //return;
                }
            }
            if(!dupicate) wayAll.add(new LinkedList<>(localPathList));
            isVisited[u]= false; 
            return ; 
        } 
          
        for (Integer i : adjList[u])  { 
            if (!isVisited[i]) { 
                localPathList.add(i); 
                printAllPathsUtil(i, d, isVisited, localPathList); 
                localPathList.remove(i); 
            } 
        } 
        
        isVisited[u] = false; 
    } 
    
    private int getMinute(double time){
        String n = Double.toString(time);
        //System.out.println("Debug Minute " + n + " : " + n.split("\\.").length);
        String[] numberString = String.format("%.2f", time).split("\\.");
        //return 0;
        return Integer.parseInt(numberString[0])*60+Integer.parseInt(numberString[1]);
    }
  
    private double getHours(int minute){
        return ceil(minute/60) + minute%60*0.01;
    }
    
    private List<List<Integer>> calTimeAllWay(){
        List<List<Integer>> _timeTable = new ArrayList<>();
        //System.out.println(timeRailway);
        //System.out.println("Length " + timeStart.length);
        for(int i = 0 ; i < timeStart.length ; i++){
            //System.out.println("I : " + i);
            List<Integer> toInsert = new ArrayList<>();
            List<Integer> tempPrice = new ArrayList<>();
            int _startAt = getMinute( timeStart[i] );
            int index = 0;
            toInsert.add(_startAt);
            /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
            timeRun.get(i).get(index%timeRun.get(i).size()),
            index%timeRun.get(i).size())));*/
            do{
                _startAt += Integer.parseInt(this.timeRailway.get(i).get(index%this.timeRailway.get(i).size()));
                if(index%this.timeRailway.get(i).size() == this.timeRailway.get(i).size()-1
                   && _startAt < getMinute(timeStop[i]) ){
                    toInsert.add(_startAt);
                    tempPrice.add(Integer.parseInt(this.timeRailway.get(i).get(index%this.timeRailway.get(i).size()))*this.pricePerMin);
                    //System.out.println("Do1");
                    /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
                    timeRun.get(i).get(index%timeRun.get(i).size()),
                    index%timeRun.get(i).size())));*/
                }
                toInsert.add(_startAt);
                tempPrice.add(Integer.parseInt(this.timeRailway.get(i).get(index%this.timeRailway.get(i).size()))*this.pricePerMin);
                /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
                timeRun.get(i).get(index%timeRun.get(i).size()),
                index%timeRun.get(i).size())));*/
                index++;
                
            }while(_startAt < getMinute(timeStop[i]));
            
            _timeTable.add(toInsert);
            this.priceAll.add(tempPrice);
        }
        //System.out.println("Time " + _timeTable);
        //System.out.println("normal " + _timeTable);
        return _timeTable;
    } 
    
    private boolean goUp(List<Integer> way,int index){
        for(int i=0;i<this.subRailway.size();i++){
            for(int j=0;j<this.subRailway.get(i).size();j++){
                if(this.nameStation.getKey(way.get(index)).equals(this.subRailway.get(i).get(j))){
                    if(j > 0 && this.nameStation.getKey(way.get(index+1)).equals(this.subRailway.get(i).get(j-1))){
                        System.out.println("Down");
                        //ขากลับ
                        return false;
                    }
                    else if(j < this.subRailway.get(i).size()-1 && this.nameStation.getKey(way.get(index+1)).equals(this.subRailway.get(i).get(j+1))){
                        System.out.println("Up");
                        //ขาไป
                        return true;
                    }
                }
            }
        }
        //System.out.println("Error");
        return false;
    }
    
    private List<Pair<String,Integer>> calTimeSomeWay(int _lastTime,List<Integer> way,List<List<Integer>> timeAll,List<List<Integer>> timeAllReverse) throws Exception{
       List<Pair<String,Integer>> _return = new ArrayList<>();
       try{
           
       }
       catch(Exception ex){return null;}
       
       return _return;
    }
    
    private List<List<Integer>> calTimeAllWayReverse(){
        List<List<Integer>> _timeTable = new ArrayList<>();
        //System.out.println(timeRailway);
        //System.out.println("Length " + timeStart.length);
        for(int i = 0 ; i < timeStart.length ; i++){
            //System.out.println("I : " + i);
            List<Integer> toInsert = new ArrayList<>();
            List<Integer> tempPrice = new ArrayList<>();
            int _startAt = getMinute( timeStart[i] );
            int index = 0;
            toInsert.add(_startAt);
            /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
            timeRun.get(i).get(index%timeRun.get(i).size()),
            index%timeRun.get(i).size())));*/
            do{
                _startAt += Integer.parseInt(this.timeRailway.get(i).get(this.timeRailway.get(i).size()-index%this.timeRailway.get(i).size()-1));
                if(index%this.timeRailway.get(i).size() == this.timeRailway.get(i).size()-1
                   && _startAt < getMinute(timeStop[i]) ){
                    toInsert.add(_startAt);
                    tempPrice.add(Integer.parseInt(this.timeRailway.get(i).get(this.timeRailway.get(i).size()-index%this.timeRailway.get(i).size()-1))*this.pricePerMin);
                    //System.out.println("Do2");
                    /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
                    timeRun.get(i).get(timeRun.get(i).size()-index%timeRun.get(i).size()-1),
                    timeRun.get(i).size()-index%timeRun.get(i).size()-1)));*/
                }
                /*System.out.println((String.format("%.2f and %s %d", getHours(_startAt),
                timeRun.get(i).get(timeRun.get(i).size()-index%timeRun.get(i).size()-1),
                timeRun.get(i).size()-index%timeRun.get(i).size()-1)));*/
                toInsert.add(_startAt);
                tempPrice.add(Integer.parseInt(this.timeRailway.get(i).get(this.timeRailway.get(i).size()-index%this.timeRailway.get(i).size()-1))*this.pricePerMin);
                index++;
            }while(_startAt < getMinute(timeStop[i]));
            
            _timeTable.add(toInsert);
            this.priceAllReverse.add(tempPrice);
        }
        //System.out.println("Time " + _timeTable);
        //System.out.println("reverse " + _timeTable);
        return _timeTable;
    } 
    
    public Pair<List<List<List<Pair<String, Integer>>>>,List<List<Integer>>> getPath(String start,String stop) throws Exception{
        if(start == null || stop == null) return null;
        //List<List<String>> path = new ArrayList<>();
        int s = nameStation.get(start);
        int d = nameStation.get(stop);
        List<List<Integer>> wayAllInt = this.printAllPaths(s, d); 
        List<List<Integer>> timeAll = calTimeAllWay();
        List<List<Integer>> timeAllReverse = calTimeAllWayReverse();
        List<List<List<Pair<String,Integer>>>> path = new ArrayList<>();
        for(var x : wayAllInt){
            List<List<Pair<String,Integer>>> temp = new ArrayList<>();
            for(int i=0;i<20;i++){
                List<Pair<String,Integer>> t = calTimeSomeWay(i,x,timeAll,timeAllReverse);
                if(t != null)
                    temp.add(t);
                else{
                    System.out.println("Error but not error1");
                }
                //System.out.println("");
            }
            if(isEmpty(temp)){
                System.out.println("Error but not error");
            }
            else{
                //System.out.println(temp);
                path.add(temp);
            }
        }
        System.out.println("Price" + this.price);
        System.out.println(path);
        //System.out.println(this.price);
        return new Pair<>(path,this.price);
    }
    
    private boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
} 