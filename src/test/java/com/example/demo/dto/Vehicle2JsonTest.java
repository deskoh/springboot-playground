package com.example.demo.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class Vehicle2JsonTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<Vehicle> json;

    @Test
    public void testSerializeElectricVehicle() throws Exception {
        // Given
        var vehicle = new Vehicle2.ElectricVehicle();
        vehicle.setType("electric");
        vehicle.setColour("blue");
        vehicle.setChargingTime("1h");

        // When
        var json = objectMapper.writeValueAsString(vehicle);

        // Then
        assertThat(json).isEqualTo("""
                {"type":"electric","colour":"blue","chargingTime":"1h"}
                """.trim());
    }

    @Test
    public void testSerializeFuelVehicle() throws Exception {
        // Given
        var vehicle = new Vehicle2.FuelVehicle();
        vehicle.setType("fuel");
        vehicle.setColour("red");
        vehicle.setFuelType("diesel");

        // When
        var json = objectMapper.writeValueAsString(vehicle);

        // Then
        assertThat(json).isEqualTo("""
                {"type":"fuel","colour":"red","fuelType":"diesel"}
                """.trim());
    }


    @Test
    public void testDeserializeElectricVehicle() throws Exception {
        // Given
        String json = """
                {"type":"electric","colour":"blue","chargingTime":"1h"}
                """;

        // When
        var vehicle = new ObjectMapper().readerFor(Vehicle2.class).readValue(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle2.ElectricVehicle.class);

        var electricVehicle = (Vehicle2.ElectricVehicle) vehicle;
        assertThat(electricVehicle.getType()).isEqualTo("electric");
        assertThat(electricVehicle.getColour()).isEqualTo("blue");
        assertThat(electricVehicle.getChargingTime()).isEqualTo("1h");
    }

    @Test
    public void testDeserializeElectricVehicleWithNullValues() throws Exception {
        // Given
        String json = """
                {"type":"electric","colour":"blue"}
                """;

        // When
        var vehicle = new ObjectMapper().readerFor(Vehicle2.class).readValue(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle2.ElectricVehicle.class);

        var electricVehicle = (Vehicle2.ElectricVehicle) vehicle;
        assertThat(electricVehicle.getType()).isEqualTo("electric");
        assertThat(electricVehicle.getColour()).isEqualTo("blue");
        assertThat(electricVehicle.getChargingTime()).isNull();
    }

    @Test
    public void testDeserializeFuelVehicle() throws Exception {
        // Given
        String json = """
                {"type":"fuel","colour":"blue","fuelType":"diesel"}
                """;

        // When
        var vehicle = new ObjectMapper().readerFor(Vehicle2.class).readValue(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle2.FuelVehicle.class);

        var fuelVehicle = (Vehicle2.FuelVehicle) vehicle;
        assertThat(fuelVehicle.getType()).isEqualTo("fuel");
        assertThat(fuelVehicle.getColour()).isEqualTo("blue");
        assertThat(fuelVehicle.getFuelType()).isEqualTo("diesel");
    }

    @Test
    public void testDeserializeFuelVehicleWithNullValues() throws Exception {
        // Given
        String json = """
                {"type":"fuel","colour":"blue"}
                """;

        // When
        var vehicle = new ObjectMapper().readerFor(Vehicle2.class).readValue(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle2.FuelVehicle.class);

        var fuelVehicle = (Vehicle2.FuelVehicle) vehicle;
        assertThat(fuelVehicle.getType()).isEqualTo("fuel");
        assertThat(fuelVehicle.getColour()).isEqualTo("blue");
        assertThat(fuelVehicle.getFuelType()).isNull();
    }
}
