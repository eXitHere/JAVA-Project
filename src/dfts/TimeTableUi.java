package dfts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TimeTableUi {
    
    private BorderPane body = new BorderPane();
    
    private HBox hboxTop = new HBox();
    private VBox vboxLeft = new VBox();
    private VBox vboxSubMain = new VBox();
    private Button btnShow1 = new Button("ตารางแบบย่อ");
    private Button btnShow2 = new Button("ตารางแบบเต็ม");
    
    private Button btnSub1 = new Button("Green");
    private Button btnSub2 = new Button("Purple");
    private Button btnSub3 = new Button("Red");
    private Button btnSub4 = new Button("Blue");
    
    private Label title = new Label("แบบย่อสายสี...");
    Font font = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 24);
    Font fontR = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24);
    Font fontSmallR = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20);

    private GridPane grid = new GridPane();
    
    private ScrollPane scrollGrid = new ScrollPane();
    
    private int a=2,b=1;
    
    private List<String> nameStation = new ArrayList<>();
    private List<String> nameRailway = new ArrayList<>();
    private List<List<String>> subRailway = new ArrayList<>();
    private List<List<String>> timeRailway = new ArrayList<>();
    
//    public TimeTableUi(List<String> a, List<String> b, List<List<String>> c ,List<List<String>> d) throws FileNotFoundException
//    {
//        this.nameStation = a;
//        this.nameRailway = b;
//        this.subRailway = c;
//        this.timeRailway = d;
//         for(var x : subRailway){
//            for(var y : x){
//                System.out.print(y + " ,");
//            }   
//    public TimeTableUi(List<String> a, List<String> b, List<List<String>> c ,List<List<String>> d) throws FileNotFoundException
//    {
//        this.nameStation = a;
//        this.nameRailway = b;
//        this.subRailway = c;
//        this.timeRailway
//            System.out.println("");    
//      }
 //   }
    
public TimeTableUi(List<String> A, List<String> B, List<List<String>> C ,List<List<String>> D) throws FileNotFoundException {
        this.nameStation = A;
        this.nameRailway = B;
        this.subRailway = C;
        this.timeRailway = D;
        
        this.body.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B)");
        
        this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
        this.btnShow1.setPrefSize(180, 60);
        this.btnShow1.setFont(font);

        this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
        this.btnShow2.setPrefSize(180, 60);
        this.btnShow2.setFont(font);
        
        this.hboxTop.getChildren().addAll(this.btnShow1,this.btnShow2);
        this.hboxTop.setAlignment(Pos.CENTER);
                                    // Top Left buttom right
        this.hboxTop.setPadding(new Insets(40,10,10,160));
        HBox.setMargin(this.btnShow1, new Insets(0,50,0,50));
       
        changeGrid();
        this.btnShow1.setOnAction((ActionEvent e)->{
            //System.out.println("btnShow1 clicked");
            a=1;          
            b=1;
            changeGrid();
        });
        this.btnShow2.setOnAction((ActionEvent e)->{
            //System.out.println("btnShow2 clicked");
            a=2;          
            b=1;
            changeGrid();
        });
        
        this.btnSub1.setOnAction((ActionEvent e)->{
            //System.out.println("btnSub1 clicked");
            b=1;
            changeGrid();
        });
        
        this.btnSub2.setOnAction((ActionEvent e)->{
            //System.out.println("btnSub2 clicked");
            b=2;
            changeGrid();
        });

        this.btnSub3.setOnAction((ActionEvent e)->{
            //System.out.println("btnSub3 clicked");
            b=3;
            changeGrid();
        });

        this.btnSub4.setOnAction((ActionEvent e)->{
            //System.out.println("btnSub4 clicked");
            b=4;
            changeGrid();
        });


        this.btnSub1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: green;");
        this.btnSub1.setPrefSize(200, 60);
        this.btnSub1.setFont(font);
        
        
        this.btnSub2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: purple;");
        this.btnSub2.setPrefSize(200, 60);
        this.btnSub2.setFont(font);

        this.btnSub3.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: red;");
        this.btnSub3.setPrefSize(200, 60);
        this.btnSub3.setFont(font);

        this.btnSub4.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: blue;");
        this.btnSub4.setPrefSize(200, 60);
        this.btnSub4.setFont(font);
        
        vboxLeft.setMargin(this.btnSub1, new Insets(10,0,10,0));
        vboxLeft.setMargin(this.btnSub2, new Insets(5,0,5,0));
        vboxLeft.setMargin(this.btnSub3, new Insets(5,0,5,0));
        vboxLeft.setMargin(this.btnSub4, new Insets(5,0,10,0));
        
        this.vboxLeft.getChildren().addAll(this.btnSub1,this.btnSub2,this.btnSub3,this.btnSub4);
        this.vboxLeft.setPadding(new Insets(80,10,30,10));
        
        this.vboxSubMain.getChildren().addAll(this.title,this.scrollGrid);
        this.title.setFont(fontR);
        this.title.setStyle("-fx-text-fill: white;");
        vboxSubMain.setAlignment(Pos.TOP_CENTER);
        vboxSubMain.setMargin(this.title,new Insets(10));
        vboxSubMain.setMargin(this.scrollGrid, new Insets(0,5,10,5));
        
        this.scrollGrid.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scrollGrid.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scrollGrid.setPrefViewportHeight(670);
        this.scrollGrid.setPrefViewportWidth(500);
        this.scrollGrid.setPrefSize(500,670);
        this.scrollGrid.setFitToWidth(true);
        this.scrollGrid.setFitToHeight(true);
        this.scrollGrid.setContent(this.grid);
        this.grid.setPadding(new Insets(20));
 
        this.body.setTop(this.hboxTop);
        this.body.setLeft(this.vboxLeft);
        this.body.setCenter(this.vboxSubMain);
        
    }
    
    private void changeGrid(){
        Label start = new Label();
        Label end = new Label();
        Label time = new Label();

        //this.grid.setGridLinesVisible(false);
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        this.grid.getChildren().clear();
        //this.grid.setGridLinesVisible(true);
                 
        //System.out.println("a: "+a+" b: "+b);
            
        if(a==1)
        {
            this.grid.getColumnConstraints().add(new ColumnConstraints(260));
            this.grid.getRowConstraints().add(new RowConstraints(40));
            this.grid.getColumnConstraints().add(new ColumnConstraints(260));
            this.grid.getColumnConstraints().add(new ColumnConstraints(260));
            Label titleA =new Label("สถานีต้นทาง");
            Label titleB =new Label("สถานีปลายทาง");
            Label titleC =new Label("ระยะเวลาที่ใช้(นาที)");
            titleA.setPrefSize(258, 38);
            titleA.setStyle("-fx-background-color: linear-gradient(#FFC524, #FFC524); -fx-text-fill: white;");
            titleA.setAlignment(Pos.CENTER);
            titleB.setPrefSize(258, 38);
            titleB.setStyle("-fx-background-color: linear-gradient(#FFD15F, #FFD15F); -fx-text-fill: white;");
            titleB.setAlignment(Pos.CENTER);
            titleC.setPrefSize(258, 38);
            titleC.setStyle("-fx-background-color: linear-gradient(#FFC524, #FFC524); -fx-text-fill: white;");
            titleC.setAlignment(Pos.CENTER);
            titleA.setFont(fontSmallR);
            titleB.setFont(fontSmallR);
            titleC.setFont(fontSmallR);
            this.grid.add(titleA, 0, 0);
            this.grid.add(titleB, 1, 0);
            this.grid.add(titleC, 2, 0);
            grid.setHalignment(titleA, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleB, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleC, javafx.geometry.HPos.CENTER);
            
            for(int i=0; i < subRailway.get(b-1).size(); i++)
            {

                if(i < subRailway.get(b-1).size()-1){
                    this.grid.getRowConstraints().add(new RowConstraints(40));
                    start = new Label(subRailway.get(b-1).get(i));
                    start.setFont(fontSmallR);
                    start.setAlignment(Pos.CENTER);
                    start.setPrefSize(258, 38);
                    if(i%2==0) start.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                    else       start.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                    grid.setHalignment(start, javafx.geometry.HPos.CENTER);
                    this.grid.add(start, 0 , i+1);

                    end = new Label(timeRailway.get(b-1).get(i));
                    this.grid.add(end, 2 , i+1);
                    end.setFont(fontSmallR);
                    end.setAlignment(Pos.CENTER);
                    end.setPrefSize(258, 38);
                    if(i%2==0) end.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                    else       end.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                    grid.setHalignment(end, javafx.geometry.HPos.CENTER);

                }
                if(i>0)
                {
                    time = new Label(subRailway.get(b-1).get(i));
                    time.setFont(fontSmallR);
                    time.setAlignment(Pos.CENTER);
                    time.setPrefSize(258, 38);
                    if(i%2==1) time.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                    else       time.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                    grid.setHalignment(time, javafx.geometry.HPos.CENTER);
                    this.grid.add(time, 1, i);
                }
            }
        
            switch(b)
            {
                case 1:   this.title.setText("ตารางการเดินรถแบบย่อ สาย Green ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
                             break;
                case 2:   this.title.setText("ตารางการเดินรถแบบย่อ สาย Purple ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Purple;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Purple;");
                              break;  
                case 3:   this.title.setText("ตารางการเดินรถแบบย่อ สาย Red ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Red;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Red;");
                             break;
                case 4:   this.title.setText("ตารางการเดินรถแบบย่อ สาย Blue ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Blue;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Blue;");
                            break;
            }
        }
        else if(a==2)
        {     
            Integer startTime = 450;
            Integer hour = startTime/60;
            Integer minute = startTime%60;

            Label showTime = new Label();          
            Label station = new Label();
            
            this.grid.getColumnConstraints().add(new ColumnConstraints(83));
            this.grid.getRowConstraints().add(new RowConstraints(40));
            this.grid.getColumnConstraints().add(new ColumnConstraints(83));

            Label titleD = new Label("สถานี");
            Label titleE = new Label("ขบวน A ");
            titleD.setPrefSize(81, 38);
            titleD.setStyle("-fx-background-color: linear-gradient(#FFC524, #FFC524); -fx-text-fill: white;");
            titleD.setAlignment(Pos.CENTER);
            titleE.setPrefSize(81, 38);
            titleE.setStyle("-fx-background-color: linear-gradient(#FFD15F, #FFD15F); -fx-text-fill: white;");
            titleE.setAlignment(Pos.CENTER);
            titleD.setFont(fontSmallR);
            titleE.setFont(fontSmallR);
            this.grid.add(titleD, 0, 0);
            this.grid.add(titleE, 1, 0);
            grid.setHalignment(titleD, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleE, javafx.geometry.HPos.CENTER);

            for(int i=0 ; i < subRailway.get(b-1).size(); i++)
            {
                this.grid.getRowConstraints().add(new RowConstraints(40));
                station = new Label(subRailway.get(b-1).get(i));
                station.setFont(fontSmallR);
                station.setPrefSize(81, 38);
                if(i%2==0) station.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else       station.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                station.setAlignment(Pos.CENTER);
                grid.setHalignment(station, javafx.geometry.HPos.CENTER);
                this.grid.add(station, 0 , i +1);
            }
            for(int i=0 ; i < subRailway.get(b-1).size() -1 ; i++)
            {
                this.grid.getRowConstraints().add(new RowConstraints(40));
                station = new Label(subRailway.get(b-1).get(i));
                station.setFont(fontSmallR);
                station.setPrefSize(81, 38);
                if(i%2==0) station.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else       station.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                station.setAlignment(Pos.CENTER);
                grid.setHalignment(station, javafx.geometry.HPos.CENTER);
                this.grid.add(station, 0 , (2*subRailway.get(b-1).size()) - i -1);
            }
           
            int count = 0,column = 1;
            
            while(hour < 24)
            {
                showTime = initTime(hour,minute);
                showTime.setFont(fontSmallR);
                this.grid.add(showTime,column,count+1);
                showTime.setPrefSize(81, 38);
                if((column)%2==0) showTime.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else              showTime.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                showTime.setAlignment(Pos.CENTER);
                grid.setHalignment(showTime, javafx.geometry.HPos.CENTER);
                
                if( count < 2*timeRailway.get(b-1).size() )
                {   
                    if(count < timeRailway.get(b-1).size())
                        startTime = startTime + toInt( timeRailway.get(b-1).get(count));
                    else
                        startTime = startTime + toInt( timeRailway.get(b-1).get(2*timeRailway.get(b-1).size() -1 - count));
                  
                    hour = startTime / 60;
                    minute = startTime % 60;
                    count++;
                }
                else
                { 
                    this.grid.getColumnConstraints().add(new ColumnConstraints(83));
                    column++;
                    Label titleEE = new Label("ขบวน A");
                    titleEE.setPrefSize(81, 38);
                    if((column)%2==0) titleEE.setStyle("-fx-background-color: FFC524; -fx-text-fill: white;");
                    else              titleEE.setStyle("-fx-background-color: FFD15F; -fx-text-fill: white;");
                    titleEE.setAlignment(Pos.CENTER);
                    titleEE.setFont(fontSmallR);
                    this.grid.add(titleEE, column, 0);
                    grid.setHalignment(titleEE, javafx.geometry.HPos.CENTER);
                    count = 0;
                }
            }
            
            /*------------------------------ set B --------------------------------------*/
            this.grid.getRowConstraints().add(new RowConstraints(40));
            this.grid.getRowConstraints().add(new RowConstraints(40));
            Label titleDD = new Label("สถานี");
            Label titleF = new Label("ขบวน B ");
            titleDD.setPrefSize(81, 38);
            titleDD.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
            titleDD.setAlignment(Pos.CENTER);
            titleF.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
            titleF.setAlignment(Pos.CENTER);
            titleF.setPrefSize(81, 38);
            titleF.setFont(fontSmallR);
            titleDD.setFont(fontSmallR);
            this.grid.add(titleDD, 0, 2*subRailway.get(b-1).size()+1);
            this.grid.add(titleF,1, 2*subRailway.get(b-1).size()+1);
            grid.setHalignment(titleF, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleDD, javafx.geometry.HPos.CENTER);
               
          
            for(int i=0 ; i < subRailway.get(b-1).size(); i++)
            {
                this.grid.getRowConstraints().add(new RowConstraints(40));
                station = new Label(subRailway.get(b-1).get(subRailway.get(b-1).size() - i - 1));
                station.setFont(fontSmallR);
                station.setPrefSize(81, 38);
                if(i%2==0) station.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else       station.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                station.setAlignment(Pos.CENTER);
                grid.setHalignment(station, javafx.geometry.HPos.CENTER);
                this.grid.add(station, 0 , 2*subRailway.get(b-1).size() + i +2);
            }
            for(int i=1 ; i < subRailway.get(b-1).size()  ; i++)
            {
                this.grid.getRowConstraints().add(new RowConstraints(40));
                station = new Label(subRailway.get(b-1).get(i));
                station.setFont(fontSmallR);
                station.setPrefSize(81, 38);
                if(i%2==1) station.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else       station.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                station.setAlignment(Pos.CENTER);
                grid.setHalignment(station, javafx.geometry.HPos.CENTER);
                this.grid.add(station, 0 , 3*subRailway.get(b-1).size() + i +1);
            }
          
            count = 0;
            column = 1;
            startTime = 450;
            startTime = 450;
            hour = startTime/60;
            minute = startTime%60;
            
            while(hour < 24)
            {
           
                showTime = initTime(hour,minute);
                showTime.setFont(fontSmallR);
                showTime.setPrefSize(81, 38);
                if(column%2==0) showTime.setStyle("-fx-background-color: FFF2CC; -fx-text-fill: black;");
                else            showTime.setStyle("-fx-background-color: FFFFFF; -fx-text-fill: black;");
                showTime.setAlignment(Pos.CENTER);
                this.grid.add(showTime,column, 2*subRailway.get(b-1).size() + count+2);
                grid.setHalignment(showTime, javafx.geometry.HPos.CENTER);
               
                if( count < 2*timeRailway.get(b-1).size() )
                {   
                    if(count < timeRailway.get(b-1).size())
                        startTime = startTime + toInt( timeRailway.get(b-1).get(count));
                    else
                        startTime = startTime + toInt( timeRailway.get(b-1).get(2*timeRailway.get(b-1).size() -1 - count));
                  
                    hour = startTime / 60;
                    minute = startTime % 60;
                    count++;
                }
                else
                { 
                    column++;
                    Label titleDDD = new Label("ขบวน B");
                    titleDDD.setFont(fontSmallR);
                    titleDDD.setPrefSize(81, 38);
                    if((column)%2==0) titleDDD.setStyle("-fx-background-color: FFC524; -fx-text-fill: white;");
                    else              titleDDD.setStyle("-fx-background-color: FFD15F; -fx-text-fill: white;");
                    titleDDD.setAlignment(Pos.CENTER);
                    this.grid.add(titleDDD, column, 2*subRailway.get(b-1).size()+1);
                    grid.setHalignment(titleDDD, javafx.geometry.HPos.CENTER);
                    count = 0;
                }
            }

           
            switch(b)
            {
                case 1:   this.title.setText("ตารางการเดินรถแบบเต็ม สาย Green ");   
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Green;");
                             break;
                case 2:   this.title.setText("ตารางการเดินรถแบบเต็ม สาย Purple ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Purple;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Purple;");
                              break;  
                case 3:   this.title.setText("ตารางการเดินรถแบบเต็ม สาย Red ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Red;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Red;");
                             break;
                case 4:   this.title.setText("ตารางการเดินรถแบบเต็ม สาย Blue ");
                this.btnShow1.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Blue;");
                this.btnShow2.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FFFFFF); -fx-text-fill: Blue;");
                            break;
            }
        }
    }
    
    public BorderPane getBody(){
        return this.body;
    }
    
    private Integer toInt(String value){
        return Integer.parseInt(value);
    }
    
    private Label initTime(Integer h,Integer m)
    {
        Label showTime = new Label();
        if(m >= 10)
            showTime = new Label(h.toString() + ":" + m.toString());
        else
            showTime = new Label(h.toString() + ":0" + m.toString());
        return showTime;
    }
    
}
