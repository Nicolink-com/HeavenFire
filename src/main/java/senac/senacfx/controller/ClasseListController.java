package senac.senacfx.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import senac.senacfx.application.Main;
import senac.senacfx.db.DbException;
import senac.senacfx.gui.listeners.DataChangeListener;
import senac.senacfx.gui.util.Alerts;
import senac.senacfx.gui.util.Utils;
import senac.senacfx.model.entities.Classe;
import senac.senacfx.model.services.DepartmentService;
import senac.senacfx.model.services.SellerService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClasseListController implements Initializable, DataChangeListener {
    //ao inves de implementar um service = new SellerService(), ficaria acoplamento forte
    //e seria obrigado a instanciar a classe
    private SellerService service;

    @FXML
    private TableView<Classe> tableViewSeller;

    @FXML
    private TableColumn<Classe, Integer> tableColumnId;

    @FXML
    private TableColumn<Classe, String> tableColumnName;

    @FXML
    private TableColumn<Classe, Integer> tableColumnforca;

    @FXML
    private TableColumn<Classe, Integer> tableColumnResistencia;

    @FXML
    private TableColumn<Classe, Integer> tableColumnDestreza;

    @FXML
    private TableColumn<Classe, Integer> tableColumnHP;

    @FXML
    private TableColumn<Classe, Integer> tableColumnMagia;

    @FXML
    private TableColumn<Classe, Classe> tableColumnEDIT;

    @FXML
    private TableColumn<Classe, Classe> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Classe> obsList;

    public ClasseListController(TableColumn<Classe, Integer> tableColumnforca) {
        this.tableColumnforca = tableColumnforca;
    }

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Classe obj = new Classe();
        createDialogForm(obj,"/gui/SellerForm.fxml", parentStage);
    }

    //feito isso usando um set, para injetar dependencia, boa pratica
    //injecao de dependendencia manual, sem framework pra isso
    public void setSellerService(SellerService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();

    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnforca.setCellValueFactory(new PropertyValueFactory<>("forca"));
        tableColumnResistencia.setCellValueFactory(new PropertyValueFactory<>("Resistencia"));
        tableColumnDestreza.setCellValueFactory(new PropertyValueFactory<>("Destreza"));
        tableColumnHP.setCellValueFactory(new PropertyValueFactory<>("HP"));
        tableColumnMagia.setCellValueFactory(new PropertyValueFactory<>("Magia"));


        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView(){
        if (service == null){
            throw new IllegalStateException("Service is null!");
        }
        List<Classe> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Classe obj, String absoluteName, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            ClasseFormController controller = loader.getController();
            controller.setSeller(obj);
            controller.setServices(new SellerService(), new DepartmentService());
            controller.loadAssociatedObjects();
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter seller data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Classe, Classe>() {
            private final Button button = new Button("Editar");
            @Override
            protected void updateItem(Classe obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/gui/SellerForm.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Classe, Classe>() {
            private final Button button = new Button("Remover");

            @Override
            protected void updateItem(Classe obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Classe obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Confirma que quer deletar?");

        if (result.get() == ButtonType.OK){
            if (service == null){
                throw new IllegalStateException("Service estava null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
