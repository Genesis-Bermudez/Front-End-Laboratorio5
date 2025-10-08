package Services;

import Domain.Dtos.RequestDto;
import Domain.Dtos.ResponseDto;
import Domain.Dtos.cars.*;
import java.util.List;

public class CarService extends BaseService {
    public CarService(String host, int port) {
        super(host, port);
    }

    // -------------------------
    // Add Car
    // -------------------------
    public CarResponseDto addCar(AddCarRequestDto dto, Long userId) {
        RequestDto request = new RequestDto(
                "Cars",
                "add",
                gson.toJson((dto)),
                userId.toString()
        );

        ResponseDto response = sendRequest(request);
        return gson.fromJson(response.getData(), CarResponseDto.class);
    }

    // -------------------------
    // Update Car
    // -------------------------
    public CarResponseDto updateCar(UpdateCarRequestDto dto, Long userId) {
        RequestDto request = new RequestDto(
                "Cars",
                "update",
                gson.toJson(dto),
                userId.toString()
        );

        ResponseDto response = sendRequest(request);
        return gson.fromJson(response.getData(), CarResponseDto.class);
    }

    // -------------------------
    // Delete Car
    // -------------------------
    public boolean deleteCar(DeleteCarRequestDto dto, Long userId) {
        RequestDto request = new RequestDto(
                "Cars",
                "delete",
                gson.toJson(dto),
                userId.toString()
        );

        ResponseDto response = sendRequest(request);
        return response.isSuccess();
    }

    // -------------------------
    // List Cars by User
    // -------------------------
    public List<CarResponseDto> listCars(Long userId) {
        RequestDto request = new RequestDto(
                "Cars",
                "list",
                "",
                userId.toString()
        );

        ResponseDto response = sendRequest(request);
        ListCarsResponseDto listResponse = gson.fromJson(response.getData(), ListCarsResponseDto.class);
        return listResponse.getCars();
    }
}