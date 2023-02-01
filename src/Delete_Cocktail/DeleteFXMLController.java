/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delete_Cocktail;


import Main.MainFXMLController;
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
import javafx.stage.Stage;
import model.cocktailDAO;
import model.cocktailFull;
import model.ingDAO;
import model.ingredients;
import util.DAO;
import Main.MainFXMLController;

/**
 * FXML Controller class
 *
 * @author baota
 */
public class DeleteFXMLController implements Initializable {

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
    private cocktailDAO cocktailDAO = new cocktailDAO();
    private MainFXMLController mainController;
    @FXML
    private Button btnDeleteCocktail;
    int cocktailNr;
   
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setMainController(MainFXMLController mainController) {
        this.mainController = mainController;
    }
    public void setCocktailFull(cocktailFull selectedCocktailFull){
        cocktailNr = selectedCocktailFull.getId();
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

    @FXML
    private void handleBtnDeleteCocktail(ActionEvent event) {
        
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setHeaderText("Bestätigung");
        confirmationDialog.setContentText("Wollen Sie diesen Cocktail '"+vName.getText()+"' löschen?");

        ButtonType deleteButton = new ButtonType("Löschen");
        ButtonType cancelButton = new ButtonType("Stornieren");
        confirmationDialog.getButtonTypes().setAll(deleteButton, cancelButton);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == deleteButton) {
                //Delete from tblCocktailZutaten
                String sql = "DELETE FROM tblCocktail WHERE Cocktailnr ="+
                               cocktailNr  +"";
                System.out.println("sql "+sql);
                DAO.executeDML(sql);
                //Delete from tblCocktailZutaten
                String sql2 = "DELETE FROM tblCocktailzutaten WHERE Cocktailnr ="+
                               cocktailNr  +"";
                System.out.println("sql "+sql2);
                DAO.executeDML(sql2);
                //close stage after clicking delete btn
                stage.close();
                //update the table view in #MainCocktail
                mainController.showCocktails(cocktailDAO.getCocktailsFromSearch("",""));
                
                
            }
        });
    }
   public void setStage(Stage stage){
       this.stage = stage;
   }
}
