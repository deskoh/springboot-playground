package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vehicle.ElectricVehicle.class, name = "electric"),
        @JsonSubTypes.Type(value = Vehicle.FuelVehicle.class, name = "fuel")
})
public class Vehicle {
    @Getter
    @Setter
    public String colour;

    public static class ElectricVehicle extends Vehicle {
        @Getter
        @Setter
        String chargingTime;
    }

    public static class FuelVehicle extends Vehicle {
        @Getter
        @Setter
        String fuelType;
    }
}
