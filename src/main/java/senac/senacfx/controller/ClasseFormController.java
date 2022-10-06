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

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtforca;

    @FXML
    private TextField txtResistencia;

    @FXML
    private TextField txtDestreza;

    @FXML
    private ComboBox<Raca> comboBoxDepartment;
    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private ObservableList<Raca> obsList;

    //Contolador agora tem uma instancia do departamento
    public void setSeller(Classe entity){
        this.entity = entity;
    }

    public void setServices(SellerService service, DepartmentService departmentService){
        this.service = service;
        this.departmentService = departmentService;
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

        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "campo nao pode ser vazio");
        }
        obj.setNome(txtName.getText());

        if (txtforca.getText() == null || txtforca.getText().trim().equals("")){
            exception.addError("email", "campo nao pode ser vazio");
        }
        obj.setforca(Integer.valueOf(txtforca.getText()));

        obj.setResistencia(Utils.tryParseToInt(txtResistencia.getText()));

        if (txtDestreza.getText() == null || txtDestreza.getText().trim().equals("")){
            exception.addError("Destreza", "campo nao pode ser vazio");
        }
        obj.setDestreza(Utils.tryParseToInt(txtDestreza.getText()));

        obj.setRaca(comboBoxDepartment.getValue());

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtDestreza);
        Constraints.setTextFieldMaxLength(txtforca, 60);
        Constraints.setTextFieldInteger(txtResistencia);

        initializeComboBoxDepartment();

    }

    public void updateFormData() {

        if (entity == null) {
            throw new IllegalStateException("Entidade nula");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getNome());
        txtforca.setText(String.valueOf((entity.getforca())));
        Locale.setDefault(Locale.US);

    }

    public void loadAssociatedObjects(){

        if (departmentService == null){
            throw new IllegalStateException("DepartmentService was null");
        }

        List<Raca> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        labelErrorName.setText((fields.contains("nome") ? errors.get("nome") : ""));
        labelErrorEmail.setText((fields.contains("forca") ? errors.get("forca") : ""));
        labelErrorBirthDate.setText((fields.contains("Resistencia") ? errors.get("Resistencia") : ""));
        labelErrorBaseSalary.setText((fields.contains("Destreza") ? errors.get("Destreza") : ""));
        labelErrorName.getStyleClass().add("button");

    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Raca>, ListCell<Raca>> factory = lv -> new ListCell<Raca>() {
            @Override
            protected void updateItem(Raca item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNome());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
