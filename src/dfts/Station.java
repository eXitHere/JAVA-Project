package dfts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.Serializable;

public class Station implements Serializable{
    private String name = null;
    private int rating = 0; 
    private double persen = 0.0;
    private String review = null;
    private String pathImage="";

    public Station(){
    }
    
    public VBox getVbox() throws FileNotFoundException{
        VBox body = new VBox();
        // Max size 500,500
        try{
            ImageView imageView = new ImageView(new Image(new FileInputStream(new File(pathImage))));
            VBox.setMargin(imageView, new Insets(10,10,10,10));
            body.getChildren().add(imageView);
            Label lbName = new Label("สถานี " + this.name);
            Label lbPersen = new Label(String.format("การใช้บริการสถานีนี้ %.2f %%", this.persen));
            Label lbReview = new Label("\nReview : \n\t"+this.review);
            lbName.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 25));
            lbPersen.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
            lbReview.setFont(Font.loadFont(new FileInputStream("src/resources/fonts/PrintAble4U_Regular.ttf"), 20));
            lbName.setStyle("-fx-text-fill:Blue;");
            lbPersen.setStyle("-fx-text-fill:Black;");
            lbReview.setWrapText(true);
            lbReview.setStyle("-fx-text-fill:Black;");
            lbReview.setMinSize(400, 200);
            lbReview.setPrefSize(400, 200);
            lbReview.setMaxSize(400, 200);
            lbReview.setAlignment(Pos.TOP_LEFT);
            HBox rateBox = new HBox();
            rateBox.setAlignment(Pos.CENTER);
            ImageView imView = setRateImage();
            HBox.setMargin(imView, new Insets(5));
            rateBox.getChildren().addAll(imView);
            body.getChildren().addAll(lbName,rateBox,lbPersen,lbReview);
            body.setAlignment(Pos.TOP_CENTER);
            //System.out.println("Created!");
        }
        catch(Exception ex){
            //System.out.println(ex);
        }
        
        //VBox.setMargin(lbReview, new Insets);

        
        return new VBox(body);
    } 
    
    private ImageView setRateImage() throws FileNotFoundException{
        String path = "src/resources/images/stations/";
        switch(this.rating){
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
            ImageView imageView = new ImageView(new Image(new FileInputStream(path)));
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);
            //System.out.println("Image " + imageView);
            return imageView;
        }
        catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public Station(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPersen(double persen) {
        this.persen = persen;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setPath(String image) {
        this.pathImage = image;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public double getPersen() {
        return persen;
    }

    public String getReview() {
        return review;
    }

    public String getPathImage() {
        return pathImage;
    }
    
    @Override
    public String toString() {
        return "Station{" + "name=" + name + ", rating=" + rating + ", persen=" + persen + ", review=" + review + ", path=" + pathImage +'}';
    }
    
}
