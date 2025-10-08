package Presentation.Views;

import Domain.Dtos.cars.CarResponseDto;
import Presentation.IObserver;
import Presentation.Models.CarsTableModel;
import Utilities.EventType;

import javax.swing.*;

public class CarsView implements IObserver {
    private JPanel ContentPanel;
    private JPanel FormPanel;
    private JTable CarsTable;
    private JScrollPane CarsTableScroll;
    private JButton AgregarButton;
    private JButton BorrarButton;
    private JButton ClearButton;
    private JButton UpdateButton;
    private JTextField CarMakeField;
    private JTextField CarModelField;
    private JTextField YearTextField;

    private final CarsTableModel tableModel;
    private final LoadingOverlay loadingOverlay;

    public CarsView(JFrame parentFrame) {
        tableModel = new CarsTableModel();
        CarsTable.setModel(tableModel);

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
    public CarsTableModel getTableModel() { return tableModel; }
    public JPanel getContentPanel() { return ContentPanel; }
    public JTable getCarsTable() { return CarsTable; }
    public JButton getAgregarButton() { return AgregarButton; }
    public JButton getBorrarButton() { return BorrarButton; }
    public JButton getUpdateButton() { return UpdateButton; }
    public JButton getClearButton() { return ClearButton; }
    public JTextField getCarMakeField() { return CarMakeField; }
    public JTextField getCarModelField() { return CarModelField; }
    public JTextField getYearTextField() { return YearTextField; }

    // --- helper methods ---
    public void clearFields() {
        CarMakeField.setText("");
        CarModelField.setText("");
        YearTextField.setText("");
        CarsTable.clearSelection();
    }

    public void populateFields(CarResponseDto car) {
        CarMakeField.setText(car.getMake());
        CarModelField.setText(car.getModel());
        YearTextField.setText(String.valueOf(car.getYear()));
    }

    @Override
    public void update(EventType eventType, Object data) {
        tableModel.update(eventType, data);
    }
}
