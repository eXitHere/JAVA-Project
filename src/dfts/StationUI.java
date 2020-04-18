package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private String userName = "";
    private boolean isEdit = false;
    private String phoneReview = ""; //Debug!
    private int starVal = 10;
    private boolean isSave = true;
    
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
    
    private ImageView star = new ImageView();
    private Label titleEdit = new Label();
    private TextArea reviewField;
    private Slider slider;
    
    private VBox getAddReview() throws FileNotFoundException{
        this.isEdit = true;
        //System.out.println(this.isEdit);
        VBox body = new VBox();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(body, new Insets(40,0,0,0));
        body.setMaxSize(460, 640);
        body.setPrefSize(460, 640);
        body.setPadding(new Insets(10,10,10,10));
        body.setStyle("-fx-background-color: white;");
        body.setAlignment(Pos.CENTER);
        body.getChildren().clear();
        body.getChildren().add(grid);
        grid.setVgap(10);
        grid.setHgap(10);
        this.titleEdit.setText("สถานี " + this.old);
        this.titleEdit.setStyle("-fx-text-fill: black;");
        this.titleEdit.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 24));
        this.titleEdit.setAlignment(Pos.CENTER);
        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(titleEdit);
        title.setPrefSize(460, 40);
        grid.add(title, 0, 0,2,1);

        Label review = new Label("รีวิวการใช้งาน");
        review.setStyle("-fx-text-fill: black;");
        review.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(review, 0, 1);
        this.reviewField = new TextArea();
        this.reviewField.setPrefSize(440, 300);
        this.reviewField.setPromptText("กรุณาแสดงความคิดเห็น/สามารถเว้นว่างได้");
        this.reviewField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(this.reviewField, 0, 2,2,1);
        
        Label rating = new Label("ความพึงพอใจในการใช้งาน");
        rating.setStyle("-fx-text-fill: black;");
        rating.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(rating, 0, 3);
        
        this.slider = new Slider();
        this.slider.setMin(1);
        this.slider.setMax(10);
        this.slider.setValue(10);
        this.slider.setShowTickLabels(true);
        this.slider.setShowTickMarks(true);
        this.slider.setMajorTickUnit(2);
        this.slider.setMinorTickCount(1);
        this.slider.setBlockIncrement(1);
        grid.add(this.slider, 0, 4);
        grid.add(this.star, 1, 3);
        this.star.prefHeight(40);
        this.star.prefWidth(110);
        this.star.maxHeight(40);
        this.star.maxWidth(110);
        this.star.minHeight(40);
        this.star.minWidth(110);
        setRateImage(10);
        this.starVal = 10;
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                //System.out.println(String.format("%.0f", new_val));
                setRateImage(new_val.intValue());
                starVal = new_val.intValue();
            }
        });
        HBox hbox = new HBox();
        hbox.setPrefSize(440, 100);
        Button btnConfirm = new Button("ส่งความคิดเห็น");
        Button btnCancel = new Button("ออกจากระบบ");
        hbox.getChildren().addAll(btnConfirm,btnCancel);
        Font fontBtn = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20);
        btnConfirm.setFont(fontBtn);
        btnConfirm.setPrefSize(200, 60);
        btnConfirm.setStyle("-fx-background-color: linear-gradient(#05C600, #05C600); -fx-text-fill: white;");
        HBox.setMargin(btnConfirm, new Insets(3, 10, 3, 5));
        btnCancel.setFont(fontBtn);
        btnCancel.setPrefSize(200, 60);
        btnCancel.setStyle("-fx-background-color: linear-gradient(#A88000, #A88000); -fx-text-fill: white;");
        HBox.setMargin(btnCancel, new Insets(3, 0, 3, 0));
        hbox.setAlignment(Pos.CENTER);
        
        reviewField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.contains("_")) {
                reviewField.setText(oldValue);
            }
            if(!oldValue.equals("") && newValue.length()>oldValue.length()) this.isSave = false;
        });
        
        btnConfirm.setOnAction((e)->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DFTS");
            alert.setContentText("ขอบคุณสำหรับความคิดเห็นและความพึงพอใจ");
            alert.setHeaderText("ทางบริษัทจะนำข้อผิดพลาดและความคิดเห็นไปปรับปรุงการให้บริการต่อไป");
            alert.showAndWait();
            this.isSave = true;
            if(!this.phoneReview.equals(""))
                writeOrUpdate(this.old,this.phoneReview,reviewField.getText(),starVal);
        });
        
        btnCancel.setOnAction((e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DFTS");
            alert.setContentText("ระบบจะพาท่านกลับไปยังหน้ารีวิวและออกจากระบบอัตโนมัติ");
            alert.setHeaderText("ต้องการปิดหน้านี้ใช่หรือไม่");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.isEdit = false;
                chageStation(this.old);
            }
        });
        
        Label dot = new Label("           *ขอบคุณสำหรับความคิดเห็นทางบริษัทจะนำข้อมูลกลับไปพัฒนา*");
        dot.setStyle("-fx-text-fill: black;");
        dot.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(dot, 0, 7,2,1);
        
        grid.add(hbox, 0, 5,2,1);
        
        btnConfirm.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnConfirm.setEffect(new DropShadow());
            //temp.setEffect(new Shadow());
        });
        btnConfirm.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnConfirm.setEffect(null);
        });
        btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnCancel.setEffect(new DropShadow());
            //temp.setEffect(new Shadow());
        });
        btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnCancel.setEffect(null);
        });
        
        return body;
    }
    
    private void writeOrUpdate(String station,String phone,String review,int rating){
        int ch;
        String temp ="";
        List<String> listPhone = new  ArrayList<>();
        List<String> listReview = new  ArrayList<>();
        List<Integer> listRating = new ArrayList<>();
        List<String> listDate = new ArrayList<>();
        try(FileReader in = new FileReader(String.format("src/resources/data/%s.review",station));){
            while ((ch=in.read())!=-1){
                if(ch=='|'){
                    //System.out.println(temp);
                    String[] spilt = temp.split("___");
                    if(!phone.equals(spilt[0])){
                        listPhone.add(spilt[0]);
                        listReview.add(spilt[1]);
                        listRating.add(Integer.parseInt(spilt[2]));
                        listDate.add(spilt[3]);
                    }
                    else{
                        //System.out.println("Edit review");
                    }
                    temp ="";
                }
                else{
                    temp+=(char)(ch);
                }
            }    
        }
        catch(Exception e){}
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
        String out = phone+"___"+review+"___"+ Integer.toString(rating) +"___" + formatDate.format(new Date()) +"|" ;
        
        try(FileWriter fw=new FileWriter(String.format("src/resources/data/%s.review",station));){  
            for (int i = 0; i < out.length(); i++) 
                fw.write(out.charAt(i));
            for(int i=0;i<listPhone.size();i++){
                out = listPhone.get(i) +"___"+ listReview.get(i) +"___"+ Integer.toString(listRating.get(i))+"___"+listDate.get(i)+ "|";
                for (int j = 0; j < out.length(); j++) 
                    fw.write(out.charAt(j));
            }
            
        }
        catch(Exception e){ System.out.println("File not found"); }
        //System.out.println("add new review! \n" + out);
    } 
    
    private void setRateImage(int rating){
        String path = "src/resources/images/stations/";
        switch(rating){
            case 1: path += "rate01.png"; break;
            case 2: path += "rate02.png"; break;
            case 3: path += "rate03.png"; break;
            case 4: path += "rate04.png"; break;
            case 5: path += "rate05.png"; break;
            case 6: path += "rate06.png"; break;
            case 7: path += "rate07.png"; break;
            case 8: path += "rate08.png"; break;
            case 9: path += "rate09.png"; break;
            case 10: path += "rate10.png"; break;
        }
        try{
            //System.out.println(path);
            this.star.setImage(new Image(new FileInputStream(path)));
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private void authen() throws FileNotFoundException{
        VBox body = new VBox();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        VBox.setMargin(body, new Insets(40,0,0,0));
        body.setMaxSize(460, 640);
        body.setPrefSize(460, 640);
        body.setPadding(new Insets(10,10,10,10));
        body.setStyle("-fx-background-color: white;");
        body.setAlignment(Pos.CENTER);
        body.getChildren().clear();
        body.getChildren().add(grid);
        
        /*
        Add Phone,Password
        */
        grid.setVgap(10);
        grid.setHgap(10);
        Label phoneNumber = new Label("เบอร์โทรศัพท์");
        phoneNumber.setStyle("-fx-text-fill: black;");
        phoneNumber.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(phoneNumber, 0, 0);

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("กรุณาป้อนเบอร์โทรศัพท์");
        phoneNumberField.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        grid.add(phoneNumberField, 1, 0);
        
        Label password = new Label("รหัสผ่าน");
        password.setStyle("-fx-text-fill: black;");
        password.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        grid.add(password, 0, 1);

        PasswordField  passwordField = new PasswordField ();
        passwordField.setPromptText("กรุณาป้อนรหัสผ่าน");
        passwordField.setFont(new Font(16));
        grid.add(passwordField, 1, 1);
        
        Button btnConfirm = new Button("เข้าสู่ระบบ");
        Button btnCancel = new Button("ยกเลิก");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btnConfirm,btnCancel);
        grid.add(hbBtn, 1, 3);
        
        Font fontBtn = Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Bold.ttf"), 20);
        
        btnConfirm.setFont(fontBtn);
        btnConfirm.setPrefSize(120, 40);
        btnConfirm.setStyle("-fx-background-color: linear-gradient(#05C600, #05C600); -fx-text-fill: white;");
        HBox.setMargin(btnConfirm, new Insets(3, 10, 3, 10));
        
        btnCancel.setFont(fontBtn);
        btnCancel.setPrefSize(120, 40);
        btnCancel.setStyle("-fx-background-color: linear-gradient(#A88000, #A88000); -fx-text-fill: white;");
        HBox.setMargin(btnCancel, new Insets(3, 0, 3, 0));
        
        btnConfirm.setOnAction((e)->{
            try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.user", phoneNumberField.getText()))); ObjectInputStream oi = new ObjectInputStream(fi);){
                User user = (User)oi.readObject();
                if(user.passwordCheck(passwordField.getText())){
                    //this.isSave = false;
                    this.vboxReview.getChildren().clear();
                    this.vboxReview.getChildren().add(getAddReview());
                    this.phoneReview = phoneNumberField.getText();
                    changeReview(this.old);
                    //System.out.println("Phone in btn : " + this.phoneReview);
                    //System.out.println("Pass");
                }
                else{
                    passwordField.setPromptText("เบอร์โทรหรือรหัสผ่านไม่ถูกต้อง");
                    passwordField.clear();
                }
            } catch (Exception ex) {
                
            }
        });
        
        btnCancel.setOnAction((e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DFTS");
            alert.setContentText("ระบบจะพาท่านกลับไปยังหน้ารีวิว");
            alert.setHeaderText("ต้องการปิดหน้านี้ใช่หรือไม่");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.isEdit = false;
                this.phoneReview = "";
                this.isSave = true;
                chageStation(this.old);
            }
        });
        
        phoneNumberField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(phoneNumberField.getLength()>10){
                phoneNumberField.setText(oldValue);
            }
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        this.vboxReview.getChildren().clear();
        this.vboxReview.getChildren().add(body);
        
        btnConfirm.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnConfirm.setEffect(new DropShadow());
            //temp.setEffect(new Shadow());
        });
        btnConfirm.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnConfirm.setEffect(null);
        });
        btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnCancel.setEffect(new DropShadow());
            //temp.setEffect(new Shadow());
        });
        btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnCancel.setEffect(null);
        });
    }
    
    private VBox clickToEdit(VBox data) throws FileNotFoundException{
        VBox body = new VBox();
        VBox hboxBTN = new VBox();
        VBox.setMargin(body, new Insets(40,0,0,0));
        HBox.setMargin(hboxBTN, new Insets(10,10,10,10));
        hboxBTN.setMaxSize(440, 80);
        hboxBTN.setPrefSize(440, 80);
        hboxBTN.setAlignment(Pos.CENTER);
        Label Title = new Label("แสดงความคิดเห็นเพื่อนำไปปรับปรุงสถานีและให้คะแนน");
        Title.setStyle("-fx-text-fill:white");
        Title.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
        Title.setAlignment(Pos.CENTER);
        Hyperlink click = new Hyperlink("-- กรุณาเข้าสู่ระบบก่อนดำเนินการ --");
        click.setStyle("-fx-text-fill:white");
        click.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 18));
        click.setAlignment(Pos.CENTER);
        click.setOnAction((e)->{try {
            this.isEdit = true;
            authen();
            } catch (FileNotFoundException ex) {
            }
        });
        hboxBTN.getChildren().addAll(Title,click);
        VBox.setMargin(Title, new Insets(5,0,5,0));
        body.setMaxSize(460, 640);
        body.setPrefSize(460, 640);
        body.setPadding(new Insets(10,10,10,10));
        body.setStyle("-fx-background-color: white;");
        hboxBTN.setStyle("-fx-background-color: linear-gradient(#042A5A, #0B509B);;");
        body.getChildren().addAll(data,hboxBTN);
        body.setAlignment(Pos.CENTER);
        return body;
    }
    
    public BorderPane getBody(){
        return this.body;
    }
    
    private boolean chageStation(String name){
        if(!this.isSave){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DFTS");
            alert.setContentText("ระบบจะไม่บันทึกข้อมูลของท่านในหน้านี้");
            alert.setHeaderText("ต้องการเปลี่ยนหน้ารีวิวใช่หรือไม่");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                this.isSave = true;
            }
            
        }
        if(this.isSave){
        try(FileInputStream fi = new FileInputStream(new File(String.format("src/resources/data/%s.station", name))); ObjectInputStream oi = new ObjectInputStream(fi)){
            Station pr1;
            pr1 = (Station) oi.readObject();
            //System.out.println(pr1);
            if(!this.isEdit){
                this.vboxReview.getChildren().clear();
                this.vboxReview.getChildren().add(clickToEdit(pr1.getVbox()));   
                //System.out.println("Added" + name);
            }
            else{
                titleEdit.setText("สถานี " + this.New);
                changeReview(name);
                //System.out.println(this.New);
            }
            //System.out.println(pr1);
        }
        catch(Exception e){e.printStackTrace();}
        try{
            this.listPostion.get(this.old).setFill(Color.TRANSPARENT);
            this.listPostion.get(this.New).setFill(Color.web("#F6EC1F")); 
        }catch(Exception e){
            this.listPostion.get(this.old).setFill(Color.web("#F6EC1F")); 
        }
        
        return true;
        }
        return false;
    }
    
    private void changeReview(String name) throws IOException{
        int ch;
        String temp="";
        try(FileReader in = new FileReader(String.format("src/resources/data/%s.review",name));){
            while ((ch=in.read())!=-1){
                if(ch=='|'){
                    //System.out.println(temp);
                    String[] spilt = temp.split("___");
                    if(this.phoneReview.equals(spilt[0])){
                        this.reviewField.setText(spilt[1]);
                        this.starVal = Integer.parseInt(spilt[2]);
                        this.reviewField.setPromptText("เคยส่งความคิดเห็นของสถานีนี้แล้้ว");
                        setRateImage(this.starVal);
                        return;
                    }
                    temp ="";
                }
                else{
                    temp+=(char)(ch);
                }
            }    
        }
        this.reviewField.clear();
        this.reviewField.setPromptText("ยังไม่ได้ส่งความคิดเห็นของสถานีนี้");
        this.starVal = 10;
        setRateImage(this.starVal);
    }
    
    private List<Circle> addButtonToMap() throws FileNotFoundException{ // ปุ่มในแมพ
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
                if(chageStation(this.New)){
                    this.old = name;
                }
                
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
        //this.vboxReview.getChildren().add(getAddReview());
        return list;
        // </editor-fold>;
    }
}
