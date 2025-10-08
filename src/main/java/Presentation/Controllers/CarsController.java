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
import java.util.List;

public class CarsController extends Observable {
    private final CarsView carsView;
    private final CarService carService;

    public CarsController(CarsView carsView, CarService carService) {
        this.carsView = carsView;
        this.carService = carService;

        addObserver(carsView.getTableModel());
        loadCarsAsync();
        attachViewListeners();
    }

    private void loadCarsAsync() {
        carsView.showLoading(true);

        Thread.ofVirtual().start(() -> {
            try {
                List<CarResponseDto> cars = carService.listCarsAsync(1L).get();
                SwingUtilities.invokeLater(() -> {
                    carsView.getTableModel().setCars(cars);
                    carsView.showLoading(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> carsView.showLoading(false));
            }
        });
    }

    private void attachViewListeners() {
        // Add
        carsView.getAgregarButton().addActionListener(e -> {
            String make = carsView.getCarMakeField().getText();
            String model = carsView.getCarModelField().getText();
            int year = Integer.parseInt(carsView.getYearTextField().getText());

            AddCarRequestDto dto = new AddCarRequestDto(make, model, year, 1L);
            carsView.showLoading(true);

            Thread.ofVirtual().start(() -> {
                try {
                    var car = carService.addCarAsync(dto, 1L).get();
                    SwingUtilities.invokeLater(() -> {
                        notifyObservers(EventType.CREATED, car);
                        carsView.clearFields();
                        carsView.showLoading(false);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> carsView.showLoading(false));
                }
            });
        });

        // Update
        carsView.getUpdateButton().addActionListener(e -> {
            int selectedRow = carsView.getCarsTable().getSelectedRow();
            if (selectedRow < 0) return;

            CarResponseDto selectedCar = carsView.getTableModel().getCars().get(selectedRow);
            String make = carsView.getCarMakeField().getText();
            String model = carsView.getCarModelField().getText();
            int year = Integer.parseInt(carsView.getYearTextField().getText());

            UpdateCarRequestDto dto = new UpdateCarRequestDto(selectedCar.getId(), make, model, year);

            carsView.showLoading(true);

            Thread.ofVirtual().start(() -> {
                try {
                    var updatedCar = carService.updateCarAsync(dto, 1L).get();
                    SwingUtilities.invokeLater(() -> {
                        notifyObservers(EventType.UPDATED, updatedCar);
                        carsView.clearFields();
                        carsView.showLoading(false);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> carsView.showLoading(false));
                }
            });
        });

        // Delete
        carsView.getBorrarButton().addActionListener(e -> {
            int selectedRow = carsView.getCarsTable().getSelectedRow();
            if (selectedRow < 0) return;

            CarResponseDto selectedCar = carsView.getTableModel().getCars().get(selectedRow);
            DeleteCarRequestDto dto = new DeleteCarRequestDto(selectedCar.getId());

            carsView.showLoading(true);

            Thread.ofVirtual().start(() -> {
                try {
                    boolean success = carService.deleteCarAsync(dto, 1L).get();
                    SwingUtilities.invokeLater(() -> {
                        if (success) notifyObservers(EventType.DELETED, selectedCar.getId());
                        carsView.clearFields();
                        carsView.showLoading(false);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> carsView.showLoading(false));
                }
            });
        });

        // Clear
        carsView.getClearButton().addActionListener(e -> carsView.clearFields());

        // Row selection
        carsView.getCarsTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = carsView.getCarsTable().getSelectedRow();
                if (row >= 0) {
                    CarResponseDto car = carsView.getTableModel().getCars().get(row);
                    carsView.populateFields(car);
                }
            }
        });
    }
}
