/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Create_Cocktail.CreateFXMLController;
import Delete_Cocktail.DeleteFXMLController;
import Dialog_Cocktail.CocktailFXMLController;
import View_Cocktail.ViewFXMLController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.cocktail;
import model.cocktailDAO;
import model.cocktailFull;

/**
 * FXML Controller class
 *
 * @author baota
 */
public class MainFXMLController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfIngredient;
    @FXML
    private TableView<cocktail> tvNameRecipe;
    @FXML
    private TableColumn<cocktail, String> clName;
    @FXML
    private TableColumn<cocktail, String> clRecipe;
    private cocktailDAO cocktailDAO = new cocktailDAO();
    private ObservableList<cocktail> list;
    

    @FXML
    private MenuItem miEditCocktail;
    @FXML
    private MenuItem miShowCocktail;
    @FXML
    private MenuItem create;
    @FXML
    private Button btnSearch;
    @FXML
    private MenuItem MiDeleteCocktail;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //show all database
        list = cocktailDAO.getCocktailsFromSearch("","");
        showCocktails(list);
    }    

    public void showCocktails(ObservableList<cocktail> list){
        //ObservableList<cocktail> list = cocktailDAO.getCocktailsFromName(tfName.getText());
        clName.setCellValueFactory(new PropertyValueFactory<cocktail,String>("Name"));
        clRecipe.setCellValueFactory(new PropertyValueFactory<cocktail,String>("Recipe")); 
        tvNameRecipe.setItems(list);      

    }
    public void  handleBtnSearch1() {
        list = cocktailDAO.getCocktailsFromSearch(tfName.getText(),tfIngredient.getText());
        showCocktails(list);
    }


    
    
    @FXML
    private void  handleBtnSearch(ActionEvent event) {
        list = cocktailDAO.getCocktailsFromSearch(tfName.getText(),tfIngredient.getText());
        showCocktails(list);
    }

    @FXML
    private void handlMiEditCocktail(ActionEvent event) {
        cocktail selectedCocktail = tvNameRecipe.getSelectionModel().getSelectedItem();
        if(selectedCocktail == null){
            return;
        }
        int selectedCocktailId = selectedCocktail.getId();
       
        try{        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Dialog_Cocktail/CocktailFXML.fxml"));
            //zuerst laden dann Controller auslesen!!
            Parent root = loader.load();
            CocktailFXMLController ctrl= loader.getController();
            ctrl.setCocktail(selectedCocktail);
            ctrl.setMainController(this);
            Stage cocktailStage = new Stage();
            cocktailStage.setTitle("Edit Cocktail");
            cocktailStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            cocktailStage.setScene(scene);
            cocktailStage.showAndWait();
           
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handlMiShowCocktail(ActionEvent event) {
        cocktail selectedCocktail = tvNameRecipe.getSelectionModel().getSelectedItem();
        if(selectedCocktail == null){
            return;
        }
        int selectedCocktailId = selectedCocktail.getId();
        cocktailFull selectedCocktailFull = cocktailDAO.getCocktailsFull(selectedCocktailId);
        //System.out.println("id: " + selectedCocktailId);
        try{        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Cocktail/ViewFXML.fxml"));
            //zuerst laden dann Controller auslesen!!
            Parent root = loader.load();
            ViewFXMLController ctrl= loader.getController();
            ctrl.setCocktailFull(selectedCocktailFull);
            
            Stage cocktailStageShow = new Stage();
            cocktailStageShow.setTitle("View Cocktail");
            cocktailStageShow.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            cocktailStageShow.setScene(scene);
            cocktailStageShow.showAndWait();
           
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handlMiCreateCocktail(ActionEvent event) {
        
        try{        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Create_Cocktail/CreateFXML.fxml"));
            //zuerst laden dann Controller auslesen!!
            Parent root = loader.load();
            CreateFXMLController ctrl= loader.getController();
            ctrl.setMainController(this);
            
            Stage CreateStage = new Stage();
            CreateStage.setTitle("Create Cocktail");
            CreateStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            CreateStage.setScene(scene);
            CreateStage.showAndWait();
           
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handlMiDeleteCocktail(ActionEvent event) {
        cocktail selectedCocktail = tvNameRecipe.getSelectionModel().getSelectedItem();
        if(selectedCocktail == null){
            return;
        }
        int selectedCocktailId = selectedCocktail.getId();
        cocktailFull selectedCocktailFull = cocktailDAO.getCocktailsFull(selectedCocktailId);
        //System.out.println("id: " + selectedCocktailId);
        try{        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Delete_Cocktail/DeleteFXML.fxml"));
            //zuerst laden dann Controller auslesen!!
            Parent root = loader.load();
            DeleteFXMLController ctrl= loader.getController();
            ctrl.setCocktailFull(selectedCocktailFull);
             ctrl.setMainController(this);//to connect 2 java files
            
            
            Stage cocktailStageDelete = new Stage();
            ctrl.setStage(cocktailStageDelete);//to close stage
            cocktailStageDelete.setTitle("Delete Cocktail");
            cocktailStageDelete.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            cocktailStageDelete.setScene(scene);
            cocktailStageDelete.showAndWait();
           
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    
    
}
