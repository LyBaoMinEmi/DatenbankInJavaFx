/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog_Cocktail;


import Main.MainFXMLController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import model.cocktail;
import model.cocktailDAO;
import model.cocktailFull;
import model.ingDAO;
import model.ing_unique;
import model.ingredients;
import model.unitDAO;
import model.units;
import util.DAO;

/**
 * FXML Controller class
 *
 * @author baota
 */
public class CocktailFXMLController implements Initializable {

    @FXML
    private TextField tfDialogName;
    @FXML
    private TextArea taDialogRecipe;
    @FXML
    private Button btnFirstUpdate;
    
    private cocktail selectedCocktail;
   
    private TextField tfDialogIngredient;
    @FXML
    private Button btnSecondInsert;
    @FXML
    private Button btnSecondDelete;
    @FXML
    private Button btnSecondUpdate;
    private cocktailDAO cocktailDAO = new cocktailDAO();
    private ingDAO ingDAO = new ingDAO();
    private unitDAO unitDAO = new unitDAO();


    @FXML
    private TableView<ingredients> tvDialogIngredients;
    @FXML
    private TableColumn<ingredients, String> clDialogIngredients;
    @FXML
    private TableColumn<ingredients,Float> clDialogQuantity;
    @FXML
    private TableColumn<ingredients, String> clDialogUnit;
    /*
    private TextField tfDialogQuantity;
    private TextField tfDialogUnit;
    */
    //private TextField tfEditIngredient;
    @FXML
    private TextField tfEditQuantity;
    @FXML
    private ComboBox<units> cbEditUnit;
    @FXML
    private ComboBox<ing_unique> cbEditIngredient;
    @FXML
    private Label error;
    @FXML
    private Label infoLabel;
    boolean info = true;
    
    private FontAwesomeIconView icon= new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
    private MainFXMLController mainController;
    public void setMainController(MainFXMLController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEditIngredient.setItems(ingDAO.getUniqueIngredients());
        cbEditUnit.setItems(unitDAO.getUnits());
        cbEditIngredient.getSelectionModel().selectFirst();
        cbEditUnit.getSelectionModel().selectFirst();
        error.setText("");  
        
         icon.setSize("1.5em");
        infoLabel.setGraphic(icon);            
        
        icon.setOnMouseClicked(event -> {
            if(info==true){
                infoLabel.setText("            positive Gleitkommazahl");
                info= false;
            }
            else {
            infoLabel.setText("");
            info= true;
            }           
        });
    }    


    
    public void setCocktail(cocktail selectedCocktail){
       
        this.selectedCocktail = selectedCocktail;
        cocktailFull a = cocktailDAO.getCocktailsFull(selectedCocktail.getId());
        tfDialogName.setText(a.getName());
        taDialogRecipe.setText(a.getRecipe());
        
        clDialogIngredients.setCellValueFactory(new PropertyValueFactory<ingredients,String>("IngName"));
        clDialogQuantity.setCellValueFactory(new PropertyValueFactory<ingredients,Float>("Quantity")); 
        clDialogUnit.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Unit")); 
        tvDialogIngredients.setItems(ingDAO.getIngredients(selectedCocktail.getId()));
        error.setText(""); 


    }
     public void setCocktailIng(cocktail selectedCocktail){
       
        this.selectedCocktail = selectedCocktail;
        clDialogIngredients.setCellValueFactory(new PropertyValueFactory<ingredients,String>("IngName"));
        clDialogQuantity.setCellValueFactory(new PropertyValueFactory<ingredients,Float>("Quantity")); 
        clDialogUnit.setCellValueFactory(new PropertyValueFactory<ingredients,String>("Unit")); 
        tvDialogIngredients.setItems(ingDAO.getIngredients(selectedCocktail.getId()));
        error.setText(""); 

    }
      
    
    @FXML
    private void handleBtnFirstUpdate(ActionEvent event) {
        String sql = "UPDATE tblCocktail "+
                 "SET Cocktail = '"+tfDialogName.getText()+
                "', Zubereitung = '" +taDialogRecipe.getText()+ 
                "' WHERE CocktailNr = "+selectedCocktail.getId();
        System.out.println("sql "+sql);
        DAO.executeDML(sql);
        setCocktail(selectedCocktail);  
        //update in the tableview of the stage "mainCocktail"
        mainController.showCocktails(cocktailDAO.getCocktailsFromSearch("",""));
        mainController.handleBtnSearch1();
        
    }     
    

    @FXML
    private void handleBtnSecondInsert(ActionEvent event) {
        error.setText("");
        //if null pointer
        if((tfEditQuantity == null) || (cbEditUnit == null) || (cbEditIngredient == null)){            
        return;
        }
        
        //check if Menge is float number
        try {    
            // number is a positive float
            if (Float.parseFloat(tfEditQuantity.getText()) >= 0.0) {
                Float total = 0.0f;
                int cocktailZutatenNr = 10000;
                String sql="";
                if (tvDialogIngredients.getItems().size() != 0){
                    for (int i = 0; i < tvDialogIngredients.getItems().size(); i++) {
                //if the ingredient is already in the table view and the unit is the same,
                //the quantity of zutaten should be added together, otherwise add new
                        if ((cbEditIngredient.getSelectionModel().getSelectedItem().getId() == tvDialogIngredients.getItems().get(i).getIngId()) 
                            &&(cbEditUnit.getSelectionModel().getSelectedItem().getUnit().equals(tvDialogIngredients.getItems().get(i).getUnit()))){

                            total = tvDialogIngredients.getItems().get(i).getQuantity()+ Float.parseFloat(tfEditQuantity.getText());
                            cocktailZutatenNr = tvDialogIngredients.getItems().get(i).getCocktailIngId();
                            sql = "UPDATE tblCocktailzutaten "+                 
                             " Set Menge = "+total+                 
                            " WHERE CocktailZutatenNr = "+cocktailZutatenNr+"";
                            break;
                        }
                        else {
                        total = Float.parseFloat(tfEditQuantity.getText());
                        cocktailZutatenNr = cocktailDAO.getMaxCocktailZutatenNr()+1;
                        sql = "INSERT INTO tblCocktailzutaten "+
                            "VALUES("+cocktailZutatenNr+","+selectedCocktail.getId()+","+cbEditIngredient.getSelectionModel().getSelectedItem().getId()
                            +","+total+","+cbEditUnit.getSelectionModel().getSelectedItem().getUnitId()+")";             
                        } 
                    }
                }//there are no ingredient in the table
                else{
                    total = Float.parseFloat(tfEditQuantity.getText());
                    cocktailZutatenNr = cocktailDAO.getMaxCocktailZutatenNr()+1;
                    sql = "INSERT INTO tblCocktailzutaten "+
                        "VALUES("+cocktailZutatenNr+","+selectedCocktail.getId()+","+cbEditIngredient.getSelectionModel().getSelectedItem().getId()
                        +","+total+","+cbEditUnit.getSelectionModel().getSelectedItem().getUnitId()+")";                     
                    }                  
                System.out.println("sql "+sql);
                DAO.executeDML(sql);
                setCocktailIng(selectedCocktail); 
       
        
            } else {//case negative float
                error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen");
                       
            }               

        
        } catch (NumberFormatException e) {//case: not float
            error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen");
             
        }  
        catch (NullPointerException e) {
        System.out.println("Null pointer");
        }
          
        
    }

    @FXML
    private void handleBtnSecondDelete(ActionEvent event) {
        error.setText(""); 
        
        try {//case Menge type: float
            if(Float.parseFloat(tfEditQuantity.getText()) >= 0.0){//case Menge non negative float
                Boolean found = false;
                for (int i = 0; i < tvDialogIngredients.getItems().size(); i++) {
                    if ((cbEditIngredient.getSelectionModel().getSelectedItem().getId() == tvDialogIngredients.getItems().get(i).getIngId()) 
                        &&(cbEditUnit.getSelectionModel().getSelectedItem().getUnit().equals(tvDialogIngredients.getItems().get(i).getUnit()))
                        && (Float.parseFloat(tfEditQuantity.getText())==tvDialogIngredients.getItems().get(i).getQuantity())    
                    ){
                        String sql = "DELETE FROM tblCocktailZutaten WHERE Cocktailzutatennr ="+
                                 tvDialogIngredients.getItems().get(i).getCocktailIngId()+"";

                    System.out.println("sql "+sql);
                    DAO.executeDML(sql);
                    setCocktailIng(selectedCocktail);
                    found = true;
                    break;
                    }}
                if (!found){
                        error.setText("in der Tabelle die zu löschende Zeile auswählen");                 
                    }
            } else //case Menge negative float
                error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen");        
          

       
        } catch (NumberFormatException e) {
            error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen");              
        }
        catch (NullPointerException e) {
        System.out.println("Null pointer");
        }   
        
        
        
    }

    @FXML
    private void handleBtnSecondUpdate(ActionEvent event) {
        error.setText(""); 
        //if user don't choose a row to update
        if(tvDialogIngredients.getSelectionModel().getSelectedItem() ==null){
            error.setText("in der Tabelle die zu aktualisierende Zeile auswählen");
            error.setTextFill(Color.RED);
            return;
        }
        try{//case Menge type: float
            if(Float.parseFloat(tfEditQuantity.getText()) >= 0.0){//case Menge non negative float
                boolean found = false;
                for (int i = 0; i < (tvDialogIngredients.getItems().size()) && (i != tvDialogIngredients.getSelectionModel().getSelectedIndex()) ; i++) {
                //if the ingredient is already in the table view and the unit is the same,
                //
                    if ((cbEditIngredient.getSelectionModel().getSelectedItem().getId() == tvDialogIngredients.getItems().get(i).getIngId()) 
                        &&(cbEditUnit.getSelectionModel().getSelectedItem().getUnit().equals(tvDialogIngredients.getItems().get(i).getUnit()))){
                        found = true;
                        error.setText("in der Tabelle die zu aktualisierende Zeile "+tvDialogIngredients.getItems().get(i).getIngName()+" auswählen");
                        error.setTextFill(Color.RED);  
                        break;
                        
                    }

                }
                if (found == false) {

                    String sql = "UPDATE tblCocktailzutaten "+
                     "SET ZutatenNr = "+ cbEditIngredient.getSelectionModel().getSelectedItem().getId()+
                     ",Menge = "+tfEditQuantity.getText()+
                     ",EinheitenNr = "+cbEditUnit.getSelectionModel().getSelectedItem().getUnitId()+
                    " WHERE CocktailNr = "+selectedCocktail.getId()+
                        " and CocktailZutatenNr = "+tvDialogIngredients.getSelectionModel().getSelectedItem().getCocktailIngId()+"";
                    //System.out.println("sql "+sql);
                    DAO.executeDML(sql);
                    setCocktailIng(selectedCocktail); 
                }
                
            } else {//case Menge negative float
                error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen"); 
            }        
                  
                    
        } catch (NumberFormatException e) {
            error.setText("bitte positive Gleitkommazahl in 'Menge' eintragen");
            error.setTextFill(Color.RED);    
        }
        catch (NullPointerException e) {
        System.out.println("Null pointer");
        }
          
    }
    

    @FXML
    private void handleOnMouseClicked(MouseEvent event) {
        
        ingredients ing = tvDialogIngredients.getSelectionModel().getSelectedItem();
        if(ing == null){
            return;
        }
        /*
        tfEditIngredient.setText(ing.getIngName());
        getUniqueIngredients
         */       
        cbEditIngredient.setItems(ingDAO.getUniqueIngredients());
        for(ing_unique ingr: cbEditIngredient.getItems()){
            if(ingr.getIngredient().equals(ing.getIngName())){
                cbEditIngredient.getSelectionModel().select(ingr);
            }              
        }
        
        tfEditQuantity.setText("" +ing.getQuantity());
        
        cbEditUnit.setItems(unitDAO.getUnits());
        for(units unit: cbEditUnit.getItems()){
            if(unit.getUnit().equals(ing.getUnit())){
                cbEditUnit.getSelectionModel().select(unit);
            }              
        }
        
    }
    
}
