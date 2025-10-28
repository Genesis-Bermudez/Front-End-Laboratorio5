package Domain.Dtos.maintenances;

public class UpdateMaintenanceRequestDto {
    private Long id;
    private String description;
    private String type;

    public UpdateMaintenanceRequestDto() {}

    public UpdateMaintenanceRequestDto(Long id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}