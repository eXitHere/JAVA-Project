package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Pair;

/*
*
*
* nothing
*
*/

public class StationUI {

    private BorderPane body = new BorderPane();
    private ImageView mainImage;
    private Pane paneImage = new Pane();
    private VBox vboxLeft = new VBox();
    private List<String> nameStation = new ArrayList<>();
    private MyMap<String,Circle> listPostion = new MyMap<>();
    private String old ="";
    private String New ="";
    private VBox vboxReview = new VBox();
    
    public StationUI(List<String> ns) {
        this.nameStation = ns;
        this.old = this.nameStation.get(0);
        try{
            Label Title = new Label("กดในแผนที่เพื่อเลือกดูข้อมูลของแต่ลถานี");
            Title.setStyle("fx-background-color: linear-gradient(#042A5A, #0B509B); -fx-text-fill: white;-fx-border-color: white; -fx-border-width: 1 1 1 1;");
            Title.setPrefSize(400, 60);
            Title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
            Title.setAlignment(Pos.CENTER);
            VBox.setMargin(Title, new Insets(20));
            this.mainImage = new ImageView(new Image(new FileInputStream(new File("src/resources/images/Map-560x560.jpg"))));
            this.paneImage.getChildren().add(this.mainImage);
            this.paneImage.getChildren().addAll(addButtonToMap());
            this.vboxLeft.getChildren().addAll(this.paneImage,Title);
            this.vboxLeft.setPadding(new Insets(40,20,20,20));
            this.vboxLeft.setAlignment(Pos.CENTER);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        this.body.setLeft(this.vboxLeft);
        this.body.setCenter(this.vboxReview);
        this.body.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);");
    }
    
    private void authen(){
        VBox body = new VBox();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        VBox.setMargin(body, new Insets(40,0,0,0));
        body.setMaxSize(460, 640);
        body.setPrefSize(460, 640);
        body.setPadding(new Insets(10,10,10,10));
        body.setStyle("-fx-background-color: green;");
        body.setAlignment(Pos.CENTER);
        body.getChildren().clear();
        body.getChildren().add(grid);
        this.vboxReview.getChildren().clear();
        this.vboxReview.getChildren().add(body);
    }
    
    private VBox clickToEdit(VBox data) throws FileNotFoundException{
        VBox body = new VBox();
        VBox hboxBTN = new VBox();
        VBox.setMargin(body, new Insets(40,0,0,0));
        HBox.setMargin(hboxBTN, new Insets(10,10,10,10));
        hboxBTN.setMaxSize(440, 80);
        hboxBTN.setPrefSize(440, 80);
        hboxBTN.setAlignment(Pos.CENTER);
        Label Title = new Label("แสดงความคิดเห็นเพื่อนับไปปรับปรุงสถานีและให้คะแนน");
        Title.setStyle("-fx-text-fill:white");
        Title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        Title.setAlignment(Pos.CENTER);
        Hyperlink click = new Hyperlink("-- กรุณาเข้าสู่ระบบก่อนดำเนินการ --");
        click.setStyle("-fx-text-fill:white");
        click.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        click.setAlignment(Pos.CENTER);
        click.setOnAction((e)->{authen();});
        hboxBTN.getChildren().addAll(Title,click);
        VBox.setMargin(Title, new Insets(5,0,5,0));
        body.setMaxSize(460, 640);
        body.setPrefSize(460, 640);
        body.setPadding(new Insets(10,10,10,10));
        body.setStyle("-fx-background-color: red;");
        hboxBTN.setStyle("-fx-background-color: blue;");
        body.getChildren().addAll(data,hboxBTN);
        body.setAlignment(Pos.CENTER);
        return body;
    }
    
    public BorderPane getBody(){
        return this.body;
    }
    
    private void chageStation(String name){
        this.vboxReview.getChildren().clear();
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.station", name))); ObjectInputStream oi = new ObjectInputStream(fi)){
            Station pr1;
            pr1 = (Station) oi.readObject();
            System.out.println(pr1);
            this.vboxReview.getChildren().add(clickToEdit(pr1.getVbox()));
            //System.out.println(pr1);
        }
        catch(Exception e){e.printStackTrace();}
    }
    
    private List<Circle> addButtonToMap(){ // ปุ่มในแมพ
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        double shiftW = 0.5;
        double shiftH = 0;
        List<Pair<Double,Double>> pos = new ArrayList<>();
        String temp = "65.520,73.260 235.036,304.723 121.695,201.416 356.947,433.041 301.844,307.157 92.098,135.840 419.156,469.535 150.721,279.095 124.776,500.923 510.253,469.535 191.593,466.269 231.954,425.810 329.836,370.099 235.036,358.802 235.036,250.206 235.036,201.231 327.096,109.140 235.036,128.609 419.156,106.243 217.156,51.388 312.297,51.202 400.203,51.017 491.706,108.955 330.749,201.416 484.661,202.923 72.569,341.064 72.587,421.416 419.156,525.396 75.022,271.864 419.156,293.818 419.156,202.738 419.156,384.897";
        String[] t = temp.split(" ");
        for(var x : t){
            String[] TT = x.split(",");
            //System.out.println(TT[0] + " and " + TT[1]);
            pos.add(new Pair <> (Double.parseDouble(TT[0]),Double.parseDouble(TT[1])));
        }
        
        //System.out.println(pos);
        List<Circle> list = new ArrayList<>();
        for(int i=0;i<pos.size();i++){
            Circle circle = new Circle();
            String name = nameStation.get(i);
            circle.setCenterX(pos.get(i).getKey() + shiftH);
            circle.setCenterY(pos.get(i).getValue()+ shiftW);
            circle.setRadius(8.833f);
            //circle.setFill(Color.web("#FFFFFF"));
            circle.setFill(Color.TRANSPARENT);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                this.New = name;
                this.listPostion.get(this.old).setFill(Color.TRANSPARENT);
                this.listPostion.get(this.New).setFill(Color.web("#F6EC1F"));
                chageStation(this.New);
                this.old = name;
            });

            circle.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                circle.setEffect(new DropShadow());
            });

            circle.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                circle.setEffect(null);
            });
            
            this.listPostion.put(name,circle);
            list.add(circle);
        }
        this.listPostion.get(this.nameStation.get(0)).setFill(Color.web("#F6EC1F"));
        chageStation(this.old);
        return list;
        // </editor-fold>;
    }
}
