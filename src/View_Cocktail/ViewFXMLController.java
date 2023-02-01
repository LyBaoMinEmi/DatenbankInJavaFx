/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Cocktail;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.cocktailFull;
import model.ingDAO;
import model.ingredients;
import util.DAO;

/**
 * FXML Controller class
 *
 * @author baota
 */
public class ViewFXMLController implements Initializable {
/*
    private TableView<ingredients> tvDialogIngredients;
    private TableColumn<ingredients, String> clDialogIngredients;
    private TableColumn<ingredients, String> clDialogQuantity;
    private TableColumn<ingredients, String> clDialogUnit;
    */
    @FXML
    private Label vName;
    @FXML
    private Label vGlass;
    @FXML
    private Label vAlcohol;
    @FXML
    private Label vGroup;
    @FXML
    private Label vRecipe;
    @FXML
    private TableView<ingredients> tvViewIngredients;
    @FXML
    private TableColumn<ingredients, String> clViewIngredients;
    @FXML
    private TableColumn<ingredients, String> clViewQuantity;
    @FXML
    private TableColumn<ingredients, String> clViewUnit;
    private ingDAO ingDAO = new ingDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public void setCocktailFull(cocktailFull selectedCocktailFull){
        vName.setText(selectedCocktailFull.getName());
        vGlass.setText(selectedCocktailFull.getGlass());
        vAlcohol.setText(""+selectedCocktailFull.getAlcohol());
        vGroup.setText(selectedCocktailFull.getGroup());
        vRecipe.setWrapText(true);
        //vRecipe.setPrefRowCount(10);
        //vRecipe.prefWidthProperty().bind(cocktailStageShow.widthProperty());
        vRecipe.setText(selectedCocktailFull.getRecipe());
        
        
        clViewIngredients.setCellValueFactory(new PropertyValueFactory<ingredients,String>("IngName"));
        clViewQuantity.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Quantity")); 
        clViewUnit.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Unit")); 
        tvViewIngredients.setItems(ingDAO.getIngredients(selectedCocktailFull.getId())); 
        
    }


    

    

    
    
}
