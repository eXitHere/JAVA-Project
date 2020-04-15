package dfts.testZone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Button btnShow1 = new Button("แบบย่อ");
    private Button btnShow2 = new Button("แบบเต็ม");
    
    private Button btnSub1 = new Button("สี1");
    private Button btnSub2 = new Button("สี2");
    private Button btnSub3 = new Button("สี3");
    private Button btnSub4 = new Button("สี4");
    
    private Label title = new Label("แบบย่อสายสี...");
    
    private GridPane grid = new GridPane();
    
    private ScrollPane scrollGrid = new ScrollPane();
    
    private int a=0,b=0;
    
    public TimeTableUi() throws FileNotFoundException {
        this.body.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00);");
        
        
        this.btnShow1.setStyle("-fx-background-color: linear-gradient(#148400, #23CC00);");
        this.btnShow1.setPrefSize(300, 200);
        
        this.btnShow1.setOnAction((ActionEvent e)->{
            System.out.println("TEST");
            this.title.setText(String.format("Hello World %d", 50));
            a=1;
            changeGrid();
        });
        
        this.hboxTop.getChildren().addAll(this.btnShow1,this.btnShow2);
        this.hboxTop.setAlignment(Pos.CENTER);
                                    // Top Left buttom right
        this.hboxTop.setPadding(new Insets(10,10,10,10));
        HBox.setMargin(this.btnShow1, new Insets(0,50,0,50));
        
        this.vboxLeft.getChildren().addAll(this.btnSub1,this.btnSub2,this.btnSub3,this.btnSub4);
        this.vboxLeft.setPadding(new Insets(30));
        
        this.vboxSubMain.getChildren().addAll(this.title,this.scrollGrid);
        
        this.scrollGrid.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollGrid.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollGrid.setPrefViewportHeight(500);
        this.scrollGrid.setPrefViewportWidth(500);
        this.scrollGrid.setPrefSize(500,500);
        this.scrollGrid.setFitToWidth(true);
        this.scrollGrid.setFitToHeight(true);
        this.scrollGrid.setContent(this.grid);
        
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
        
        this.grid.setPadding(new Insets(20));
        this.grid.setGridLinesVisible(true);
       
        
        this.body.setTop(this.hboxTop);
        this.body.setLeft(this.vboxLeft);
        this.body.setCenter(this.vboxSubMain);
        
    }
    
    private void changeGrid(){
        this.grid.getChildren().clear();
    }
    
    public BorderPane getBody(){
        return this.body;
    }
}
