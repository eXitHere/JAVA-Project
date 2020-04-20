package dfts;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class SaleManager {
    private List<Sale> listSale = new ArrayList<>();
    private List<String> member = new ArrayList<>();
    private List<String> useNotMember = new ArrayList<>();
    public SaleManager(List<String> sale) {
        //System.out.println("Do");
        loadMember(); // Load member from db
        int _isMember = 0,_notMember =0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Integer[] _count = new Integer[10];
        Integer[][] useInMont = new Integer[2][12];
        Integer[] day = new Integer[7];
        
        for(int i=0;i<7;i++){ //init
            day[i] = 0;
        }
        
        for(int i=0;i<2;i++){ //init
            for(int j=0;j<12;j++){
                useInMont[i][j] = 0;
            }
        }
        
        for(int i=0;i<10;i++){ //init
            _count[i] = 0;
        }
        
        //System.out.println("Sale size : " + sale.size());
        for(var x : sale){
            String[] temp = x.split("_");
            this.listSale.add(new Sale(temp[0], temp[1], temp[2], Integer.parseInt(temp[3]), temp[4], Double.parseDouble(temp[5])));
            Sale now = this.listSale.get(this.listSale.size()-1);
            
            if(isMember(now.getPhone())){ //การเป็นสมาชิก  //นับยอดรายเดือนภายใน 1 ปี _
                _isMember++;
                useInMont[0][new Date(now.getDate()).getMonth()]++;
            }
            else{
                //System.out.println(now.getPhone());
                _notMember++;
                this.useNotMember.add(now.getPhone());
                //System.out.println(now.getPhone());
                useInMont[1][new Date(now.getDate()).getMonth()]++;
            }
            day[new Date(now.getDate()).getDay()]++;
            //จำนวนตั๋ว
            //System.out.println(now.getCount());
            _count[now.getCount()-1]++;
            
            
        }
        //System.out.println(_isMember + " --- " + _notMember);
        this.dayUse = Arrays.asList(day);
        this.monthUse = new Pair<List<Integer>,List<Integer>>(Arrays.asList(useInMont[0]),Arrays.asList(useInMont[1]));
        this.memberUse = new Pair<Integer,Integer>(_isMember,_notMember);
        this.listCount = Arrays.asList(_count);
        
        
        /*
        Clear duplicates ! from user sale.bin
        */
        //System.out.println("Size all " + this.useNotMember.size());
        Set<String> removeDuplicates = new LinkedHashSet<String>(this.useNotMember);
       
        this.useNotMember.clear();
       
        this.useNotMember.addAll(removeDuplicates);
        this.useNotMember.sort(Comparator.naturalOrder());
        //System.out.println("after remove : " + this.useNotMember.size());
        //System.out.println(userNotMember);
        //System.out.println("Size : " + userNotMember.size());
    }
    
    public String getDateNeares(){
        long now = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date closest = Collections.min(dates, new Comparator<Date>() {
            public int compare(Date d1, Date d2) {
                long diff1 = Math.abs(d1.getTime() - now);
                long diff2 = Math.abs(d2.getTime() - now);
                return Long.compare(diff1, diff2);
                }
            });
        return format.format(closest);
    }
    
    //ใช้บริการแต่ไม่เป็นสมาชิก
    public List<String> getUseNotMember(){
        //System.out.println("Return size : " + this.useNotMember.size());
        return this.useNotMember;
    }
    
    private List<Date> dates = new ArrayList<Date>();
                
    public List<Double> getDetailByPhone(){
        return this.detailByPhone;
    }
    
    private List<Sale> dataByPhone;
    private List<Double> detailByPhone;
    
    public ObservableList getDataByPhone(String phone) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        this.dates = new ArrayList<Date>();
        this.dataByPhone = new ArrayList<>();
        this.detailByPhone = new ArrayList<>();
        double price = 0;
        double useCount =0;
        for(var x : this.listSale){
            if(x.getPhone().equals(phone)){
                this.dataByPhone.add(x);
                useCount++;
                price += x.getPrice();
                try{
                    this.dates.add(format.parse(x.getDate()));
                }
                catch(Exception e){e.printStackTrace();}
            }
        }
        this.detailByPhone = Arrays.asList(price,useCount);
        return FXCollections.observableArrayList(this.dataByPhone);
    }
    
    private List<Integer> dayUse = new ArrayList<>();
    public List<Integer> getDayUse(){
        return this.dayUse;
    }
    
    private Pair<List<Integer>,List<Integer>> monthUse;
    public Pair<List<Integer>,List<Integer>> getMonthUse(){
        return this.monthUse;
    }
    
    private Pair<Integer,Integer> memberUse ;
    public Pair<Integer,Integer> getMemberUse(){
        return this.memberUse;
    }
    
    private List<Integer> listCount = new ArrayList<>();
    public List<Double> getListCount(){
        double sum = 0;
        List<Double> out = new ArrayList<>();
        for(var x : this.listCount){
            sum+=x;
        }
        for(var x : this.listCount){
            out.add(x/sum*100);
        }
        return out;
    }
    
    private boolean isMember(String phone){
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        for(var x : member){
            if(x.equals(phone)) return true;
        }
        return false;
        // </editor-fold>;
    }
    
    private void loadMember() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        member = new ArrayList<>(); // Clear list
        File file = new File("src/resources/data/");
        File[] files = file.listFiles(new FilenameFilter() {
             
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".user")){
                    return true;
                } else {
                    return false;
                }
            }
        });
        for(File f:files){
            //System.out.println(f.getName());
            String[] name = ((String)f.getName()).split(".user");
            this.member.add(name[0]);
            //System.out.println("member : " + name[0]);
        }
        //System.out.println("Member size : " + member.size());
        // </editor-fold>;
    }
}
