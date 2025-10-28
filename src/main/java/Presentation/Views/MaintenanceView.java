package Presentation.Views;

import Domain.Dtos.maintenances.MaintenanceResponseDto;
import Presentation.Models.MaintenanceTableModel;

import javax.swing.*;

public class MaintenanceView {
    private JPanel BasePanel;
    private JPanel FormPanel;
    private JTextField MaintenanceDescriptionField;
    private JTextField MaintenanceTypeField;
    private JTextField MaintenanceCarIDField;
    private JScrollPane MaintenancesTableScroll;
    private JTable MaintenancesTable;
    private JButton AgregarButton;
    private JButton BorrarButton;
    private JButton ClearButton;
    private JButton UpdateButton;
    private JPanel ContentPanel;

    private final MaintenanceTableModel tableModel;
    private final LoadingOverlay loadingOverlay;

    //  Cada tab requiere una referencia al parentFrame para poder implementar el overlay de carga.
    public MaintenanceView(JFrame parentFrame) {
        tableModel = new MaintenanceTableModel();
        MaintenancesTable.setModel(tableModel);

        // Reusable overlay instance
        loadingOverlay = new LoadingOverlay(parentFrame);
    }

    /**
     * Shows or hides the loading overlay.
     */
    public void showLoading(boolean visible) {
        loadingOverlay.show(visible);
    }

    // --- getters ---
    public MaintenanceTableModel getTableModel() { return tableModel; }
    public JPanel getContentPanel() { return ContentPanel; }
    public JTable getMaintenancesTable() { return MaintenancesTable; }
    public JButton getAgregarButton() { return AgregarButton; }
    public JButton getBorrarButton() { return BorrarButton; }
    public JButton getUpdateButton() { return UpdateButton; }
    public JButton getClearButton() { return ClearButton; }
    public JTextField getMaintenanceDescriptionField() { return MaintenanceDescriptionField; }
    public JTextField getMaintenanceTypeField() { return MaintenanceTypeField; }
    public JTextField getMaintenanceCarIDField() { return MaintenanceCarIDField; }

    // --- helper methods ---
    public void clearFields() {
        MaintenanceDescriptionField.setText("");
        MaintenanceTypeField.setText("");
        MaintenanceCarIDField.setText("");
        MaintenancesTable.clearSelection();
    }

    public void populateFields(MaintenanceResponseDto maintenance) {
        MaintenanceDescriptionField.setText(maintenance.getDescription());
        MaintenanceTypeField.setText(maintenance.getType());
        MaintenanceCarIDField.setText(String.valueOf(maintenance.getCarMaintenance().getId()));
    }
}
