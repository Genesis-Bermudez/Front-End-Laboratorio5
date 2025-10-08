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

    public CarsView() {
        tableModel = new CarsTableModel();
        CarsTable.setModel(tableModel);

        // When a row is selected, populate the form
        CarsTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = CarsTable.getSelectedRow();
            if (selectedRow != -1) {
                CarResponseDto car = tableModel.getCars().get(selectedRow);
                CarMakeField.setText(car.getMake());
                CarModelField.setText(car.getModel());
                YearTextField.setText(String.valueOf(car.getYear()));
            }
        });
    }

    public void setAgregarAction(Runnable action) {
        AgregarButton.addActionListener(e -> action.run());
    }

    public void setBorrarAction(Runnable action) {
        BorrarButton.addActionListener(e -> action.run());
    }

    public void setUpdateAction(Runnable action) {
        UpdateButton.addActionListener(e -> action.run());
    }

    public void setClearAction(Runnable action) {
        ClearButton.addActionListener(e -> action.run());
    }

    public CarsTableModel getTableModel() { return tableModel; }
    public JPanel getContentPanel() { return ContentPanel; }
    public JTable getCarsTable() { return CarsTable; }
    public JTextField getCarMakeField() { return CarMakeField; }
    public JTextField getCarModelField() { return CarModelField; }
    public JTextField getYearTextField() { return YearTextField; }

    @Override
    public void update(EventType eventType, Object data) {
        // Table updates are handled by tableModel observer
    }

    public void clearForm() {
        CarMakeField.setText("");
        CarModelField.setText("");
        YearTextField.setText("");
        CarsTable.clearSelection(); // Clear row selection
    }
}
