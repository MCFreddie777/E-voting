package Models.Other;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class View {

    private static double xOffset = 0;
    private static double yOffset = 0;


    /**
     * Creates new Scene
     * @param resource FXML File path
     * @param stageName Node in current stage
     * @param title Title of new window
     * @param controller Controller of FXML File
     * @param newStage Boolean value if the created scene is going to be open in new stage
     */
    public static void newView(String resource, Node stageName, String title, Object controller,boolean newStage) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(resource));
            if(!controller.equals("")) {
                fxmlLoader.setController(controller);
            }
            Parent root = (Parent) fxmlLoader.load();
            Stage stage;
            if (newStage){
                Stage currentStage = (Stage) stageName.getScene().getWindow();
                currentStage.close();
                stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
            }
            else {
                stage = (Stage) stageName.getScene().getWindow();
            }
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            makeScreenDraggable(root,stage);
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void makeScreenDraggable(Parent root, Stage stage) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

    }
}