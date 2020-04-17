package dfts.testZone;

import dfts.AboutMeUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AbouMeUIDrive extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        AboutMeUI ui = new AboutMeUI(stage);
        
        stage.setScene(new Scene(ui.getBody(),1080,720));
        stage.show();
    }
    
}
