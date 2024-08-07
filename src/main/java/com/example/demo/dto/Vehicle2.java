package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vehicle2.ElectricVehicle.class, name = "electric"),
        @JsonSubTypes.Type(value = Vehicle2.FuelVehicle.class, name = "fuel")
})
public class Vehicle2 {
    @Getter
    @Setter
    public String type;

    @Getter
    @Setter
    public String colour;

    public static class ElectricVehicle extends Vehicle2 {
        @Getter
        @Setter
        String chargingTime;
    }

    public static class FuelVehicle extends Vehicle2 {
        @Getter
        @Setter
        String fuelType;
    }
}
