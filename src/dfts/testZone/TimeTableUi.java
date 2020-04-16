package dfts.testZone;

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
    
    private int a=1,b=1;
    
    private List<String> nameStation = new ArrayList<>();
    private List<String> nameRailway = new ArrayList<>();
    private List<List<String>> subRailway = new ArrayList<>();
    private List<List<String>> timeRailway = new ArrayList<>();
    
    public TimeTableUi(List<String> a, List<String> b, List<List<String>> c ,List<List<String>> d) throws FileNotFoundException
    {
        this.nameStation = a;
        this.nameRailway = b;
        this.subRailway = c;
        this.timeRailway = d;
//         for(var x : subRailway){
//            for(var y : x){
//                System.out.print(y + " ,");
//            }
//            System.out.println("");    
//      }
    }
    
    public TimeTableUi() throws FileNotFoundException {
        this.body.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00);");
        
        this.btnShow1.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnShow1.setPrefSize(180, 60);
        this.btnShow1.setFont(font);
        
        this.btnShow2.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnShow2.setPrefSize(180, 60);
        this.btnShow2.setFont(font);
        
        this.hboxTop.getChildren().addAll(this.btnShow1,this.btnShow2);
        this.hboxTop.setAlignment(Pos.CENTER);
                                    // Top Left buttom right
        this.hboxTop.setPadding(new Insets(10,10,10,10));
        HBox.setMargin(this.btnShow1, new Insets(0,50,0,50));
        
        /*
        Button btnTime = new Button("ตารางเดินรถ");
        btnTime.setFont(font);
        btnTime.setPrefSize(180, 60);
  ->      btnTime.prefWidthProperty().bind(vbox.widthProperty());
   blue     btnTime.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");  
   red     btnMap.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        ->        VBox.setMargin(btnTime, new Insets(3, 10, 3, 10));
        
        */
        changeGrid();
        this.btnShow1.setOnAction((ActionEvent e)->{
            System.out.println("btnShow1 clicked");
            //this.title.setText(String.format("Hello World %d", 50));
            a=1;          
            b=1;
            changeGrid();
        });
        this.btnShow2.setOnAction((ActionEvent e)->{
            System.out.println("btnShow2 clicked");
            //this.title.setText(String.format("Hello World %d", 50));
            a=2;          
            b=1;
            changeGrid();
        });
        
        this.btnSub1.setOnAction((ActionEvent e)->{
            System.out.println("btnSub1 clicked");
            //this.title.setText(String.format("Hello World %d", 50));     
            b=1;
            changeGrid();
        });
        
        this.btnSub2.setOnAction((ActionEvent e)->{
            System.out.println("btnSub2 clicked");
            //this.title.setText(String.format("Hello World %d", 50));     
            b=2;
            changeGrid();
        });

        this.btnSub3.setOnAction((ActionEvent e)->{
            System.out.println("btnSub3 clicked");
            //this.title.setText(String.format("Hello World %d", 50));     
            b=3;
            changeGrid();
        });

        this.btnSub4.setOnAction((ActionEvent e)->{
            System.out.println("btnSub4 clicked");
            //this.title.setText(String.format("Hello World %d", 50));     
            b=4;
            changeGrid();
        });


        this.btnSub1.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnSub1.setPrefSize(120, 40);
        this.btnSub1.setFont(font);
        
        this.btnSub2.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnSub2.setPrefSize(120, 40);
        this.btnSub2.setFont(font);

        this.btnSub3.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnSub3.setPrefSize(120, 40);
        this.btnSub3.setFont(font);

        this.btnSub4.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;");
        this.btnSub4.setPrefSize(120, 40);
        this.btnSub4.setFont(font);
        
        vboxLeft.setMargin(this.btnSub1, new Insets(10,0,10,0));
        vboxLeft.setMargin(this.btnSub2, new Insets(10,0,10,0));
        vboxLeft.setMargin(this.btnSub3, new Insets(10,0,10,0));
        vboxLeft.setMargin(this.btnSub4, new Insets(10,0,10,0));
        
        this.vboxLeft.getChildren().addAll(this.btnSub1,this.btnSub2,this.btnSub3,this.btnSub4);
        this.vboxLeft.setPadding(new Insets(80,30,30,30));
        
        this.vboxSubMain.getChildren().addAll(this.title,this.scrollGrid);
        this.title.setFont(fontR);
        this.title.setStyle("-fx-text-fill: white;");
        vboxSubMain.setAlignment(Pos.TOP_CENTER);
        vboxSubMain.setMargin(this.title,new Insets(10));
        vboxSubMain.setMargin(this.scrollGrid, new Insets(0,5,10,10));
        
        this.scrollGrid.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollGrid.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollGrid.setPrefViewportHeight(500);
        this.scrollGrid.setPrefViewportWidth(500);
        this.scrollGrid.setPrefSize(500,500);
        this.scrollGrid.setFitToWidth(true);
        this.scrollGrid.setFitToHeight(true);
        this.scrollGrid.setContent(this.grid);
        this.grid.setPadding(new Insets(20));

        
        /*------------------------------------table-----------------------------
        this.grid.getColumnConstraints().add(new ColumnConstraints(300));
        this.grid.getRowConstraints().add(new RowConstraints(40));
        this.grid.getColumnConstraints().add(new ColumnConstraints(300));
        this.grid.getRowConstraints().add(new RowConstraints(40));
        this.grid.getColumnConstraints().add(new ColumnConstraints(300));
        this.grid.getRowConstraints().add(new RowConstraints(40));
        Label a = new Label("Test");
       
        a.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        
        grid.setHalignment(a, javafx.geometry.HPos.CENTER);
        this.grid.add(a, 0, 0);
        this.grid.add(new Label("Test2"), 2, 2);
        this.grid.add(new Button("Test2"), 1, 2);
        
        this.grid.setGridLinesVisible(true);
        /*---------------------------------------------------------------------*/
        
        this.body.setTop(this.hboxTop);
        this.body.setLeft(this.vboxLeft);
        this.body.setCenter(this.vboxSubMain);
        
    }
    
    private void changeGrid(){
        this.grid.setGridLinesVisible(false);
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        this.grid.getChildren().clear();
        this.grid.setGridLinesVisible(false);
        this.grid.getChildren().clear();
        this.grid.setGridLinesVisible(true);
                 
        System.out.println("a: "+a+" b: "+b);
        System.out.println(this.subRailway.size());  
           for(var x : this.subRailway){
            for(var y : x){
                System.out.print(y + " ,");
            }
            System.out.println("");    
      }
        if(a==1)
        {
            
            this.grid.getColumnConstraints().add(new ColumnConstraints(280));
            this.grid.getRowConstraints().add(new RowConstraints(40));
            this.grid.getColumnConstraints().add(new ColumnConstraints(280));
            this.grid.getColumnConstraints().add(new ColumnConstraints(280));
            Label titleA =new Label("สถานีต้นทาง");
            Label titleB =new Label("สถานีปลายทาง");
            Label titleC =new Label("ระยะเวลาที่ใช้");
            titleA.setFont(fontSmallR);
            titleB.setFont(fontSmallR);
            titleC.setFont(fontSmallR);
            this.grid.add(titleA, 0, 0);
            this.grid.add(titleB, 1, 0);
            this.grid.add(titleC, 2, 0);
            grid.setHalignment(titleA, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleB, javafx.geometry.HPos.CENTER);
            grid.setHalignment(titleC, javafx.geometry.HPos.CENTER);
            
//            this.grid.getRowConstraints().add(new RowConstraints(40));
        //    this.grid.getRowConstraints().add(new RowConstraints(40));
        
//            Label a = new Label("Test");
//
//            grid.setHalignment(a, javafx.geometry.HPos.CENTER);
//            this.grid.add(a, 0, 0);
//            this.grid.add(new Label("Test2"), 2, 2);
//            this.grid.add(new Button("Test2"), 1, 2);
        
            if(b==1)
            {
                this.title.setText("ตารางการเดินรถแบบย่อ สาย Green ");
              

            }
            else if(b==2)
            {
                this.title.setText("ตารางการเดินรถแบบย่อ สาย Purple ");
            }
            else if(b==3)
            {
                this.title.setText("ตารางการเดินรถแบบย่อ สาย Red ");
            }
            else if(b==4)
            {
                this.title.setText("ตารางการเดินรถแบบย่อ สาย Blue ");
            }
        }
        else if(a==2)
        {
            if(b==1)
            {
               this.title.setText("ตารางการเดินรถแบบเต็ม สาย Green ");
            }
            else if(b==2)
            {
                this.title.setText("ตารางการเดินรถแบบเต็ม สาย Purple ");
            }
            else if(b==3)
            {
                this.title.setText("ตารางการเดินรถแบบเต็ม สาย Red ");
            }
            else if(b==4)
            {
                this.title.setText("ตารางการเดินรถแบบเต็ม สาย Blue ");
            }
        }
    }
    
    public BorderPane getBody(){
        return this.body;
    }
}
