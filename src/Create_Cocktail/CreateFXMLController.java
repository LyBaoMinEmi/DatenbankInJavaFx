/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Create_Cocktail;

import Main.MainFXMLController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.cocktailDAO;
import model.ingDAO;
import model.ing_unique;
import model.ingredients;
import model.tblCocktailzutaten;
import model.unitDAO;
import model.units;
import util.DAO;
import Main.MainFXMLController;


/**
 * FXML Controller class
 *
 * @author baota
 */
public class CreateFXMLController implements Initializable {

    @FXML
    private TextArea tfCreateRecipe;
    @FXML
    private TextField tfCreateName;
    @FXML
    private Button btnSaveCreate;
    @FXML
    private Button btnAddIngredient;
    @FXML
    private ComboBox<ing_unique> cbIngredientCreate;
    
    @FXML
    private TextField tfQuantityCreate;
    @FXML
    private ComboBox<units> cbUnitCreate;
    private ingDAO ingDAO = new ingDAO();
    private unitDAO unitDAO = new unitDAO();
    private cocktailDAO cocktailDAO = new cocktailDAO();
    @FXML
    private TableColumn<ingredients, String> tvIngCreate;
    @FXML
    private TableColumn<ingredients,String > tvQuantityCreate;
    @FXML
    private TableColumn<ingredients, String> tvUnitCreate;
    @FXML
    private TableView<ingredients> tvCreate;
    ObservableList<ingredients> IngTempList = FXCollections.observableArrayList();
    ObservableList<tblCocktailzutaten> CocktailZutatenList = FXCollections.observableArrayList();
    
    int cocktailNr = 0;
    @FXML
    private Label errorQuantityCreate;
    @FXML
    private Label errorNameCreate;
    @FXML
    private Label errorRecipeCreate;
    @FXML
    private Label infoQuantityCreate;
    
   
    boolean info = true;
    
    private FontAwesomeIconView icon= new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
    @FXML
    private Label errorTableCreate;
    
    private MainFXMLController mainController;
    public void setMainController(MainFXMLController mainController) {
        this.mainController = mainController;
    }
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbIngredientCreate.setItems(ingDAO.getUniqueIngredients());
        cbIngredientCreate.getSelectionModel().selectFirst();
        cbUnitCreate.setItems(unitDAO.getUnits());
        cbUnitCreate.getSelectionModel().selectFirst();
        //FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
        icon.setSize("1.5em");
        infoQuantityCreate.setGraphic(icon);            
        
        icon.setOnMouseClicked(event -> {
            if(info==true){
                infoQuantityCreate.setText("        positive Gleitkommazahl");
                info= false;
            }
            else {
            infoQuantityCreate.setText("");
            info= true;
            }           
        });
        
        
    }

    @FXML
    private void handlebtnSaveCreate(ActionEvent event) {
        //set all text is ""
        errorNameCreate.setText("");
        errorRecipeCreate.setText("");
        errorQuantityCreate.setText("");
        //if field is empty 
        if((tfCreateName.getText().isEmpty())||(tfCreateRecipe.getText().isEmpty())
                || (IngTempList.isEmpty())){
            if (tfCreateName.getText().isEmpty()) 
                errorNameCreate.setText("Cocktailname eintragen");
            if (tfCreateRecipe.getText().isEmpty())//if field recipe is empty
                errorRecipeCreate.setText("Cocktailrezept eintragen");
            if  (IngTempList.isEmpty())
                errorTableCreate.setText("mind. eine Zutat eintragen");          
            
        }
        
        //save all info in the database
        else {
            //insert in tblCocktail
            cocktailNr = cocktailDAO.getMaxCocktailNr()+1;
            String sql = "INSERT INTO tblCocktail (CocktailNr,Cocktail, Zubereitung) "+
                    "VALUES("+cocktailNr+",'"+tfCreateName.getText()+"','"+tfCreateRecipe.getText()+"')";
            System.out.println("sql "+sql);
            DAO.executeDML(sql);
            
            //save info in tblCocktailzutaten
            for (int i = 0; i < IngTempList.size(); i++) {
                CocktailZutatenList.add(new tblCocktailzutaten(IngTempList.get(i).getCocktailIngId(),cocktailNr,
                        IngTempList.get(i).getIngId(),IngTempList.get(i).getQuantity(), unitDAO.getUnitId(IngTempList.get(i).getUnit())));            
            }
                
            //insert in tblCocktailZutaten
            //CocktailZutatenList

            for (int i = 0; i < CocktailZutatenList.size(); i++) {
                String sql2 = "INSERT INTO tblCocktailzutaten "+
                    "VALUES("+CocktailZutatenList.get(i).getCocktailZutatenNr()+","+CocktailZutatenList.get(i).getCocktailNr()+","
                        +CocktailZutatenList.get(i).getZutatenNr()+",'"+CocktailZutatenList.get(i).getMenge()+
                        "',"+CocktailZutatenList.get(i).getEinheitenNr()+")";  
                System.out.println("sql "+sql2);
                DAO.executeDML(sql2);            
            }    
            //clear object
            IngTempList.clear();
            CocktailZutatenList.clear();
            //clear name, recipe after save the created cocktail
            tfCreateName.setText("");
            tfCreateRecipe.setText("");
            //update info in the table view of MainFxml
           // mainController.showCocktails(cocktailDAO.getCocktailsFromSearch("",""));
            mainController.handleBtnSearch1();
        }  
        
        
    }

    @FXML
    private void handleBtnAddIngredient(ActionEvent event) {  
        errorQuantityCreate.setText("");
        //save ingredient info in ein object-array, then show these values in table view
        try{//case Menge type: float
            if(Float.parseFloat(tfQuantityCreate.getText()) >= 0.0){//case Menge non negative float
               cocktailNr = cocktailDAO.getMaxCocktailNr()+1;
                int cocktailZutatenNr = cocktailDAO.getMaxCocktailZutatenNr()+1+IngTempList.size();

                //check if the chosen ingredient is already added in the object or not
                boolean found = false;
                for (int i = 0; i < IngTempList.size(); i++) {
                    if((cbIngredientCreate.getSelectionModel().getSelectedItem().getIngredient().equals(IngTempList.get(i).getIngName())
                    && (cbUnitCreate.getSelectionModel().getSelectedItem().getUnit().equals(IngTempList.get(i).getUnit()) ))){
                        //Save in the ingredient temporary list with the sum quantity
                        Float total = 0.0f;
                        total = Float.parseFloat(tfQuantityCreate.getText()) + IngTempList.get(i).getQuantity();
                        IngTempList.add(new ingredients(IngTempList.get(i).getCocktailIngId(),cbIngredientCreate.getSelectionModel().getSelectedItem().getId(),cbIngredientCreate.getSelectionModel().getSelectedItem().getIngredient(),
                        total,cbUnitCreate.getSelectionModel().getSelectedItem().getUnit()));
                        found = true;
                        //delete the old value
                        IngTempList.remove(i);
                        break;
                    }

                }
                if(!found){
                    IngTempList.add(new ingredients(cocktailZutatenNr,cocktailNr,cbIngredientCreate.getSelectionModel().getSelectedItem().getIngredient(),
                        Float.parseFloat(tfQuantityCreate.getText()),cbUnitCreate.getSelectionModel().getSelectedItem().getUnit()));            
                }
                //display this object in table view

                tvIngCreate.setCellValueFactory(new PropertyValueFactory<ingredients,String>("IngName"));
                tvQuantityCreate.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Quantity")); 
                tvUnitCreate.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Unit")); 
                tvCreate.setItems(IngTempList);  

                

            } else {//case Menge negative float
                errorQuantityCreate.setText("bitte positive Gleitkommazahl eintragen");        
            }            
            
        } catch (NumberFormatException e) {//case: Menge is not float
            errorQuantityCreate.setText("bitte positive Gleitkommazahl eintragen");  
        }  
        catch (NullPointerException e) {
        System.out.println("Null pointer");
        }
         
               
    }

    
    
    

    
    
    
}
