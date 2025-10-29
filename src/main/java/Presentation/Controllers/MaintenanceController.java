package Presentation.Controllers;

import Domain.Dtos.maintenances.AddMaintenanceRequestDto;
import Domain.Dtos.maintenances.MaintenanceResponseDto;
import Domain.Dtos.maintenances.DeleteMaintenanceRequestDto;
import Domain.Dtos.maintenances.UpdateMaintenanceRequestDto;
import Presentation.Observable;
import Presentation.Views.MaintenanceView;
import Services.MaintenanceService;
import Utilities.EventType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MaintenanceController extends Observable {

    private final MaintenanceView maintenanceView;
    private final MaintenanceService maintenanceService;
    private final Long userID;

    public MaintenanceController(MaintenanceView maintenanceView, MaintenanceService maintenanceService, Long userID) {
        this.maintenanceView = maintenanceView;
        this.maintenanceService = maintenanceService;
        this.userID = userID;

        addObserver(maintenanceView.getTableModel());
        loadMaintenancessAsync();
        addListeners();
    }

    private void loadMaintenancessAsync() {
        maintenanceView.showLoading(true);

        SwingWorker<List<MaintenanceResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<MaintenanceResponseDto> doInBackground() throws Exception {
                return maintenanceService.listMaintenancesAsync(userID).get();
            }

            @Override
            protected void done() {
                try {
                    List<MaintenanceResponseDto> maintenances = get();
                    maintenanceView.getTableModel().setMaintenances(maintenances);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    maintenanceView.showLoading(false);
                }
            }
        };
        worker.execute();
    }

    private void addListeners() {
        maintenanceView.getAgregarButton().addActionListener(e -> handleAddMaintenance());
        maintenanceView.getUpdateButton().addActionListener(e -> handleUpdateMaintenance());
        maintenanceView.getBorrarButton().addActionListener(e -> handleDeleteMaintenance());
        maintenanceView.getClearButton().addActionListener(e -> handleClearFields());
        maintenanceView.getMaintenancesTable().getSelectionModel().addListSelectionListener(this::handleRowSelection);
    }

    // ---------------------------
    // Action Handlers
    // ---------------------------
    private void handleAddMaintenance () {
        String description = maintenanceView.getMaintenanceDescriptionField().getText();
        String type = maintenanceView.getMaintenanceTypeField().getText();
        long carID = Long.parseLong(maintenanceView.getMaintenanceCarIDField().getText());

        if (description.isEmpty() || type.isEmpty() || carID <= 0) {
            JOptionPane.showMessageDialog(maintenanceView.getContentPanel(), "Por favor complete todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AddMaintenanceRequestDto dto = new AddMaintenanceRequestDto(description, type, carID);

        SwingWorker<MaintenanceResponseDto, Void> worker = new SwingWorker<>() {
            @Override
            protected MaintenanceResponseDto doInBackground() throws Exception {
                return maintenanceService.addMaintenanceAsync(dto, userID).get();
            }

            @Override
            protected void done() {
                try {
                    MaintenanceResponseDto maintenance = get();
                    notifyObservers(EventType.CREATED, maintenance);
                    maintenanceView.clearFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    maintenanceView.showLoading(false);
                }
            }
        };

        maintenanceView.showLoading(true);
        worker.execute();
    }

    private void handleUpdateMaintenance() {
        int selectedRow = maintenanceView.getMaintenancesTable().getSelectedRow();
        if (selectedRow < 0) return;

        MaintenanceResponseDto selectedMaintenance = maintenanceView.getTableModel().getMaintenances().get(selectedRow);
        String description = maintenanceView.getMaintenanceDescriptionField().getText();
        String type = maintenanceView.getMaintenanceTypeField().getText();

        if (description.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(maintenanceView.getContentPanel(), "Por favor complete todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UpdateMaintenanceRequestDto dto = new UpdateMaintenanceRequestDto(selectedMaintenance.getId(), description, type);

        SwingWorker<MaintenanceResponseDto, Void> worker = new SwingWorker<>() {
            @Override
            protected MaintenanceResponseDto doInBackground() throws Exception {
                return maintenanceService.updateMaintenanceAsync(dto, userID).get();
            }

            @Override
            protected void done() {
                try {
                    MaintenanceResponseDto updatedMaintenance = get();
                    notifyObservers(EventType.UPDATED, updatedMaintenance);
                    maintenanceView.clearFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    maintenanceView.showLoading(false);
                }
            }
        };

        maintenanceView.showLoading(true);
        worker.execute();
    }

    private void handleDeleteMaintenance() {
        int selectedRow = maintenanceView.getMaintenancesTable().getSelectedRow();
        if (selectedRow < 0) return;

        MaintenanceResponseDto selectedMaintenance = maintenanceView.getTableModel().getMaintenances().get(selectedRow);
        DeleteMaintenanceRequestDto dto = new DeleteMaintenanceRequestDto(selectedMaintenance.getId());

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return maintenanceService.deleteMaintenanceAsync(dto, userID).get();
            }

            @Override
            protected void done() {
                try {
                    Boolean success = get();
                    if (success) notifyObservers(EventType.DELETED, selectedMaintenance.getId());
                    maintenanceView.clearFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    maintenanceView.showLoading(false);
                }
            }
        };

        maintenanceView.showLoading(true);
        worker.execute();
    }

    private void handleClearFields() {
        maintenanceView.clearFields();
    }

    private void handleRowSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int row = maintenanceView.getMaintenancesTable().getSelectedRow();
            if (row >= 0) {
                MaintenanceResponseDto car = maintenanceView.getTableModel().getMaintenances().get(row);
                maintenanceView.populateFields(car);
            }
        }
    }
}
