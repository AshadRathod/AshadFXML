/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Users;

/**
 *
 * @author ashad
 */

public class DetailedModelViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="labelID"
    private Text labelID; // Value injected by FXMLLoader

    @FXML // fx:id="labelValue"
    private Text labelValue; // Value injected by FXMLLoader

    @FXML // fx:id="image"
    private ImageView image; // Value injected by FXMLLoader

    @FXML
    void backButtonAction(ActionEvent event) {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }
                    
    }
    
    Users selectedModel;
    Scene previousScene;
    
    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        buttonBack.setDisable(false);

    }

    public void initData(Users model) {
        selectedModel = model;
        labelID.setText(model.getId().toString());
        labelValue.setText(model.getName());

        try {
            // path points to /resource/images/
            String imagename = "/resource/images/" + model.getName() + ".png";
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            image.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert buttonBack != null : "fx:id=\"buttonBack\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert labelID != null : "fx:id=\"labelID\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert labelValue != null : "fx:id=\"labelValue\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailedModelView.fxml'.";

    }
}
