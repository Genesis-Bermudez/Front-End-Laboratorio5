package Domain.Dtos.maintenances;

import Domain.Dtos.cars.CarResponseDto;

public class MaintenanceResponseDto {
    private Long id;
    private String description;
    private String type;
    private CarResponseDto carMaintenance;

    public MaintenanceResponseDto() {}

    public MaintenanceResponseDto(Long id, String description, String type, CarResponseDto carMaintenance) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.carMaintenance = carMaintenance;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public CarResponseDto getCarMaintenance() { return carMaintenance; }
    public void setCarMaintenance(CarResponseDto carMaintenance) {this.carMaintenance = carMaintenance; }
}
