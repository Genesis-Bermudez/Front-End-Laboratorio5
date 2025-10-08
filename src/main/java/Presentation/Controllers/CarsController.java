package Presentation.Controllers;

import Domain.Dtos.cars.AddCarRequestDto;
import Domain.Dtos.cars.CarResponseDto;
import Domain.Dtos.cars.DeleteCarRequestDto;
import Domain.Dtos.cars.UpdateCarRequestDto;
import Presentation.Observable;
import Presentation.Views.CarsView;
import Services.CarService;
import Utilities.EventType;

import javax.swing.*;

public class CarsController extends Observable {
    private final CarsView carsView;
    private final CarService carService;

    public CarsController(CarsView carsView, CarService carService) {
        this.carsView = carsView;
        this.carService = carService;

        // Observer pattern
        addObserver(carsView.getTableModel());

        // Load initial cars
        var listOfCars = carService.listCars(1L);
        carsView.getTableModel().setCars(listOfCars);

        // Wire buttons
        carsView.setAgregarAction(this::handleAddCar);
        carsView.setBorrarAction(this::handleDeleteCar);
        carsView.setUpdateAction(this::handleUpdateCar);
        carsView.setClearAction(this::handleClear);
    }

    private void handleAddCar() {
        try {
            String make = carsView.getCarMakeField().getText();
            String model = carsView.getCarModelField().getText();
            int year = Integer.parseInt(carsView.getYearTextField().getText());

            AddCarRequestDto dto = new AddCarRequestDto(make, model, year, 1L); // example userId
            CarResponseDto response = carService.addCar(dto, 1L);
            notifyObservers(EventType.CREATED, response);

            carsView.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(carsView.getContentPanel(), "Error adding car: " + e.getMessage());
        }
    }

    private void handleUpdateCar() {
        int selectedRow = carsView.getCarsTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(carsView.getContentPanel(), "Select a car to update");
            return;
        }

        CarResponseDto selectedCar = carsView.getTableModel().getCars().get(selectedRow);

        try {
            String make = carsView.getCarMakeField().getText();
            String model = carsView.getCarModelField().getText();
            int year = Integer.parseInt(carsView.getYearTextField().getText());

            UpdateCarRequestDto dto = new UpdateCarRequestDto(selectedCar.getId(), make, model, year);
            CarResponseDto updated = carService.updateCar(dto, 1L);

            // Update JTable via observer
            notifyObservers(EventType.UPDATED, updated);

            carsView.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(carsView.getContentPanel(), "Error updating car: " + e.getMessage());
        }
    }

    private void handleDeleteCar() {
        int selectedRow = carsView.getCarsTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(carsView.getContentPanel(), "Select a car to delete");
            return;
        }

        CarResponseDto selectedCar = carsView.getTableModel().getCars().get(selectedRow);

        DeleteCarRequestDto dto = new DeleteCarRequestDto(selectedCar.getId());
        boolean success = carService.deleteCar(dto, 1L);
        if (success) {
            notifyObservers(EventType.DELETED, selectedCar.getId());
            carsView.clearForm();
        } else {
            JOptionPane.showMessageDialog(carsView.getContentPanel(), "Error deleting car");
        }
    }

    private void handleClear() {
        carsView.clearForm();
    }
}
