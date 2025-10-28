package Presentation.Models;

import Domain.Dtos.maintenances.MaintenanceResponseDto;
import Presentation.IObserver;
import Utilities.EventType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceTableModel extends AbstractTableModel implements IObserver {

    private final List<MaintenanceResponseDto> maintenances = new ArrayList<>();
    private final String[] columnNames = {"ID", "Description", "Type", "Car ID"};

    // -------------------------
    // AbstractTableModel methods
    // -------------------------
    @Override
    public int getRowCount() {
        return maintenances.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MaintenanceResponseDto maintenance = maintenances.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> maintenance.getId();
            case 1 -> maintenance.getDescription();
            case 2 -> maintenance.getType();
            case 3 -> maintenance.getCarMaintenance().getId();
            default -> null;
        };
    }

    // -------------------------
    // Observer implementation
    // -------------------------
    @Override
    public void update(EventType eventType, Object data) {
        if (data == null) return;

        switch (eventType) {
            case CREATED -> {
                MaintenanceResponseDto newMaintenance = (MaintenanceResponseDto) data;
                maintenances.add(newMaintenance);
                fireTableRowsInserted(maintenances.size() - 1, maintenances.size() - 1);
            }
            case UPDATED -> {
                MaintenanceResponseDto updatedMaintenance = (MaintenanceResponseDto) data;
                for (int i = 0; i < maintenances.size(); i++) {
                    if (maintenances.get(i).getId().equals(updatedMaintenance.getId())) {
                        maintenances.set(i, updatedMaintenance);
                        fireTableRowsUpdated(i, i);
                        break;
                    }
                }
            }
            case DELETED -> {
                Long deletedId = (Long) data;
                for (int i = 0; i < maintenances.size(); i++) {
                    if (maintenances.get(i).getId().equals(deletedId)) {
                        maintenances.remove(i);
                        fireTableRowsDeleted(i, i);
                        break;
                    }
                }
            }
        }
    }

    // -------------------------
    // Utility methods
    // -------------------------
    public List<MaintenanceResponseDto> getMaintenances() {
        return new ArrayList<>(maintenances);
    }

    public void setMaintenances(List<MaintenanceResponseDto> newMaintenances) {
        maintenances.clear();
        if (newMaintenances != null) maintenances.addAll(newMaintenances);
        fireTableDataChanged();
    }
}