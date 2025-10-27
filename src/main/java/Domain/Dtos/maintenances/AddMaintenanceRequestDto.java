package Domain.Dtos.maintenances;

public class AddMaintenanceRequestDto {

    private Long iD;
    private String description;
    private String type;
    private Long carId;

    public AddMaintenanceRequestDto() {}

    public AddMaintenanceRequestDto(Long iD, String description, String type, Long carId) {
        this.iD = iD;
        this.description = description;
        this.type = type;
        this.carId = carId;
    }

    public Long getId() { return iD; }
    public void setId(Long iD) { this.iD = iD; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
}