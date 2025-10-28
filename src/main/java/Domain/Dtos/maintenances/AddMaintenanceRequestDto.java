package Domain.Dtos.maintenances;

public class AddMaintenanceRequestDto {

    private String description;
    private String type;
    private Long carId;

    public AddMaintenanceRequestDto() {}

    public AddMaintenanceRequestDto(String description, String type, Long carId) {
        this.description = description;
        this.type = type;
        this.carId = carId;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
}