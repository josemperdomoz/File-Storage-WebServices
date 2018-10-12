package org.alfalfa.fx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FileStorageController implements Initializable {

    public ArrayList<FileId> fileList = new ArrayList();
    public FileLoader loader = new FileLoader(); // It is the FileLoader class
    public ObservableList<FileId> olistFileList = FXCollections.observableArrayList();
    @FXML
    public TextField urlInputField;
    @FXML
    public Button urlButton;
    @FXML
    public TextField fileInputField;
    @FXML
    public Button uploadButton;
    @FXML
    public Button downloadButton;
    @FXML
    public Label infoText;
    @FXML
    public Button deleteButton;
    @FXML
    public ListView<FileId> GUIlist;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //ArrayList<FileId> test1 = new ArrayList();
        //fileList.add(new FileId("Jose_5G.txt", "98102nioiu1"));
        //fileList.add(new FileId("11f12_Tenis.txt", "95893ioiu1"));
        olistFileList = FXCollections.observableArrayList(fileList);
        GUIlist.setItems(olistFileList);
        infoText.setText("Connected to File Storage Service");
        
        
        olistFileList.setAll(fileList);
        // TODO
    }    

    @FXML
    private void urlButtonClicked(ActionEvent event) {
        String input = this.urlInputField.getText();
        fileList = loader.getFiles(input);
        olistFileList = FXCollections.observableArrayList(fileList);
        olistFileList.setAll(fileList);
        GUIlist.setItems(olistFileList);
        infoText.setText("Updating File List");
    }

    @FXML
    private void uploadButtonClicked(ActionEvent event) {
        if(this.fileInputField.getText().equals(null))
            this.infoText.setText("Please enter a File Name!");
        String uploadFileName = this.fileInputField.getText();
        String state = loader.uploadFile(uploadFileName);
        this.infoText.setText(state + " successfully uploaded!");
        updateList();
    }

    @FXML
    private void downloadButtonClicked(ActionEvent event) {
        int choice = GUIlist.getSelectionModel().getSelectedIndex();
        loader.downloadFile(choice);
        infoText.setText(GUIlist.getSelectionModel().getSelectedItems().toString() + " Succesfully Downloaded!");
        
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        int choice = GUIlist.getSelectionModel().getSelectedIndex();
        String fileDeleted = loader.deleteFile(choice);
        infoText.setText(GUIlist.getSelectionModel().getSelectedItems().toString() + " has been successfully deleted!");
        updateList();
    }
    
    public void updateList(){
        fileList = loader.getFiles(this.urlInputField.getText());
        olistFileList = FXCollections.observableArrayList(fileList);
        olistFileList.setAll(fileList);
        GUIlist.setItems(olistFileList);
    
    }
    
}
