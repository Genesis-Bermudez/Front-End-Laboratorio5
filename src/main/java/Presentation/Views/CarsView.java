package Presentation.Views;

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

    private final CarsTableModel tableModel;

    public CarsView() {
        tableModel = new CarsTableModel();
        CarsTable.setModel(tableModel);
    }

    public CarsTableModel getTableModel() {
        return tableModel;
    }

    public JPanel getContentPanel() {
        return ContentPanel;
    }

    public JTable getCarsTable() {
        return CarsTable;
    }

    @Override
    public void update(EventType eventType, Object data) {

    }
}
