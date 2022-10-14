package senac.senacfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import senac.senacfx.db.DbException;
import senac.senacfx.gui.listeners.DataChangeListener;
import senac.senacfx.gui.util.Alerts;
import senac.senacfx.gui.util.Constraints;
import senac.senacfx.gui.util.Utils;
import senac.senacfx.model.entities.Classe;
import senac.senacfx.model.entities.Raca;
import senac.senacfx.model.exceptions.ValidationException;
import senac.senacfx.model.services.DepartmentService;
import senac.senacfx.model.services.SellerService;

import java.net.URL;
import java.util.*;

public class ClasseFormController implements Initializable {

    private Classe entity;

    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    public TextField txtforca;

    @FXML
    private TextField txtResistencia;

    @FXML
    private TextField txtDestreza;
    @FXML
    private TextField txtHP;
    @FXML
    private TextField txtMagia;

    @FXML
    private Label labelErrorNome;

    @FXML
    public Label labelErrorForca;

    @FXML
    private Label labelErrorResistencia;

    @FXML
    private Label labelErrorDestreza;
    @FXML
    private Label labelErrorHP;

    @FXML
    private Label labelErrorMagia;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public ClasseFormController() {
    }

//    private ObservableList<Raca> obsList;

//    //Contolador agora tem uma instancia do departamento
//    public void setSeller(Classe entity){
//        this.entity = entity;
//    }

    public void setServices(SellerService service ){ this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        //validacao manual pois nao esta sendo usado framework para injetar dependencia
        if (entity == null){
            throw new IllegalStateException("Entidade nula");
        }
        if (service == null){
            throw new IllegalStateException("Servico nulo");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Classe getFormData() {
        Classe obj = new Classe();

        ValidationException exception = new ValidationException("Erro na validacao");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")){
            exception.addError("Nome", "campo nao pode ser vazio");
        }
        obj.setNome(txtNome.getText());


        obj.setforca(Utils.tryParseToInt(txtforca.getText()));

        if (txtResistencia.getText() == null || txtResistencia.getText().trim().equals("")){
            exception.addError("Resistencia", "campo nao pode ser vazio");
        }

        obj.setResistencia(Utils.tryParseToInt(txtResistencia.getText()));

        if (txtDestreza.getText() == null || txtDestreza.getText().trim().equals("")){
            exception.addError("Destreza", "campo nao pode ser vazio");
        }
        obj.setDestreza(Utils.tryParseToInt(txtDestreza.getText()));

        if (txtHP.getText() == null || txtHP.getText().trim().equals("")){
            exception.addError("HP", "campo nao pode ser vazio");
        }
        obj.setHP(Utils.tryParseToInt(txtHP.getText()));

        if (txtMagia.getText() == null || txtMagia.getText().trim().equals("")){
            exception.addError("Magia", "campo nao pode ser vazio");
        }
        obj.setMagia(Utils.tryParseToInt(txtMagia.getText()));


        if (exception.getErrors().size() > 0){
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeNodes(); }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 50);
        Constraints.setTextFieldInteger(txtDestreza);
        Constraints.setTextFieldInteger(txtforca);
        Constraints.setTextFieldInteger(txtResistencia);
        Constraints.setTextFieldInteger(txtHP);
        Constraints.setTextFieldInteger(txtMagia);



    }

    public void updateFormData() {

        if (entity == null) {
            throw new IllegalStateException("Entidade nula");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtforca.setText(String.valueOf((entity.getforca())));
        txtResistencia.setText(String.valueOf((entity.getResistencia())));
        txtDestreza.setText(String.valueOf((entity.getDestreza())));
        txtHP.setText(String.valueOf((entity.getHP())));
        txtMagia.setText(String.valueOf((entity.getMagia())));
        Locale.setDefault(Locale.US);

    }

//    public void loadAssociatedObjects(){
//
//        if (departmentService == null){
//            throw new IllegalStateException("DepartmentService was null");
//        }
//
//        List<Raca> list = departmentService.findAll();
//        obsList = FXCollections.observableArrayList(list);
//        comboBoxDepartment.setItems(obsList);
//    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        labelErrorNome.setText((fields.contains("nome") ? errors.get("nome") : ""));
        labelErrorForca.setText((fields.contains("forca") ? errors.get("forca") : ""));
        labelErrorResistencia.setText((fields.contains("Resistencia") ? errors.get("Resistencia") : ""));
        labelErrorDestreza.setText((fields.contains("Destreza") ? errors.get("Destreza") : ""));
        labelErrorHP.setText((fields.contains("HP") ? errors.get("HP") : ""));
        labelErrorMagia.setText((fields.contains("Magia") ? errors.get("Magia") : ""));
        labelErrorNome.getStyleClass().add("button");

    }



    public void setSeller(Classe obj) {
    }

    public void loadAssociatedObjects() {
    }
}
