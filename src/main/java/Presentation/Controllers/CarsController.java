package Presentation.Controllers;

import Domain.Dtos.cars.AddCarRequestDto;
import Domain.Dtos.cars.CarResponseDto;
import Domain.Dtos.cars.DeleteCarRequestDto;
import Domain.Dtos.cars.UpdateCarRequestDto;
import Presentation.Observable;
import Presentation.Views.CarsView;
import Services.CarService;
import Utilities.EventType;
import java.util.List;

public class CarsController extends Observable {

    private final CarsView carsView;
    private final CarService carService;

    public CarsController(CarsView carsView, CarService carService) {
        this.carService = carService;
        this.carsView = carsView;

        this.carsView.getTableModel().setCars(carService.listCars(1L));
    }

    // -------------------------
    // CRUD Methods
    // -------------------------
    public void addCar(AddCarRequestDto dto, Long userId) {
        CarResponseDto response = carService.addCar(dto, userId);
        notifyObservers(EventType.CREATED, response);
    }

    public void updateCar(UpdateCarRequestDto dto, Long userId) {
        CarResponseDto response = carService.updateCar(dto,  userId);
        notifyObservers(EventType.UPDATED, response);
    }

    public void deleteCar(DeleteCarRequestDto dto, Long userId) {
        boolean success = carService.deleteCar(dto, userId);
        if (success) {
            notifyObservers(EventType.DELETED, dto.getId());
        }
    }

    public List<CarResponseDto> listCars(Long userId) {
        return carService.listCars(userId);
    }
}
