package org.example.Domain.dtos.cars;

public class CarResponseDto {
    private Long id;
    private String make;
    private String model;
    private int year;
    private Long ownerId;
    private String createdAt; // String instead of LocalDateTime for JSON
    private String updatedAt;

    public CarResponseDto() {}

    public CarResponseDto(Long id, String make, String model, int year, Long ownerId, String createdAt, String updatedAt) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
