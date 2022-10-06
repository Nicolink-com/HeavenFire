package senac.senacfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import senac.senacfx.gui.util.Alerts;
import senac.senacfx.gui.util.Constraints;
import senac.senacfx.model.entities.Personagem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private ComboBox<Personagem> combo1;

    @FXML
    private Button btAll;

    private ObservableList<Personagem> obsList;
    @FXML
    public void onComboBoxPersonAction() {
        Personagem personagem = combo1.getSelectionModel().getSelectedItem();
        System.out.println(personagem);
    }
    @FXML
    public void onBtAllAction() {
        for(Personagem personagem : combo1.getItems()){
            System.out.println(personagem);
        }
    }

    @FXML
    private TextField txt1;
    @FXML
    private TextField txt2;

    @FXML
    private Label labelResult;
    @FXML
    private Button btSum;
    @FXML
    public void onBtSumAction(){
        try{
            Locale.setDefault(Locale.US);
            double n1 = Double.parseDouble(txt1.getText());
            double n2 = Double.parseDouble(txt2.getText());
            double sum = n1 + n2;
            labelResult.setText(String.format("%.2f", sum));
        } catch (NumberFormatException e){
            Alerts.showAlert("Error", "Parse error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private Button bTest;
    @FXML
    public void onBtTestAction(){
        Alerts.showAlert("ERRO", "DEU RUIM", "VAI EXPLODIR", Alert.AlertType.ERROR);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Personagem> list = new ArrayList<>();
        list.add(new Personagem(1, "Antonio", "M",103, 1,1));
        list.add(new Personagem(2, "John", "M", 230,2,2));
        list.add(new Personagem(3, "Rarissa", "F", 150,3,3));

        obsList = FXCollections.observableArrayList(list);
        combo1.setItems(obsList);

        Callback<ListView<Personagem>, ListCell<Personagem>> factory = lv -> new ListCell<Personagem>() {
            @Override
            protected void updateItem(Personagem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getnome());
            }
        };
        combo1.setCellFactory(factory);
        combo1.setButtonCell(factory.call(null));

        Constraints.setTextFieldDouble(txt1);
        Constraints.setTextFieldDouble(txt2);
        Constraints.setTextFieldMaxLength(txt1, 5);
        Constraints.setTextFieldMaxLength(txt2, 5);
        Constraints.setTextFieldInteger(txt1);
        Constraints.setTextFieldInteger(txt2);
    }
}