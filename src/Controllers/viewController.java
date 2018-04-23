package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import java.io.IOException;

class viewController {
//TODO add warning messages as well.

    double xOffset = 0;
    double yOffset = 0;
    /**
    * if controller is not equal to "" then we'll set a new controller for fxmlLoader
    *
    * */
    public void newScreenWithButton(String resource, Button stageName, String title, Object controller) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            if(!controller.equals("")) {
                fxmlLoader.setController(controller);
            }

            Parent root = (Parent) fxmlLoader.load();
            Stage currentStage = (Stage) stageName.getScene().getWindow();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            makeScreenDraggable(root,stage);
            currentStage.close();
            stage.show();
            //   Warning.showAlert("You have been successfully logged out");

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void newScreenWithLabel(String resource, Label stageName, String title, Object controller) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            if(!controller.equals("")) {
                fxmlLoader.setController(controller);
            }
            Parent root = (Parent) fxmlLoader.load();
            Stage currentStage = (Stage) stageName.getScene().getWindow();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            makeScreenDraggable(root,stage);
            currentStage.close();
            stage.show();

         //   Warning.showAlert("You have been successfully logged out");

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void makeScreenDraggable(Parent root, Stage stage) {
        //TODO
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