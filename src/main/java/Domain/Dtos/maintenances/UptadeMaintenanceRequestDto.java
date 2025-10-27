package Domain.Dtos.maintenances;

public class UptadeMaintenanceRequestDto {
    private Long id;
    private String description;
    private String type;
    private Car carMaintenance;

    public UpdateMaintenanceRequestDto() {}

    public UpdateMaintenanceRequestDto(Long id, String description, String type, Car carMaintenance) {
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
    public Car getCarMaintenance() { return carMaintenance; }
    public void setCarMaintenance(Car carMaintenance) {this.carMaintenance = carMaintenance; }
}