package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class VehicleJsonTest {
    @Autowired
    private JacksonTester<Vehicle> jacksonTester;

    @Test
    public void testSerializeElectricVehicle() throws Exception {
        // Given
        var vehicle = new Vehicle.ElectricVehicle();
        vehicle.setColour("blue");
        vehicle.setChargingTime("1h");

        // When
        var json = jacksonTester.write(vehicle);

        // Then
        assertThat(json).isEqualToJson("""
                {"type":"electric","colour":"blue","chargingTime":"1h"}
                """);
    }

    @Test
    public void testSerializeFuelVehicle() throws Exception {
        // Given
        var vehicle = new Vehicle.FuelVehicle();
        vehicle.setColour("red");
        vehicle.setFuelType("diesel");

        // When
        var json = jacksonTester.write(vehicle);

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
        var vehicle = jacksonTester.parseObject(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle.ElectricVehicle.class);

        var electricVehicle = (Vehicle.ElectricVehicle) vehicle;
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
        var vehicle = jacksonTester.parseObject(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle.ElectricVehicle.class);

        var electricVehicle = (Vehicle.ElectricVehicle) vehicle;
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
        var vehicle = jacksonTester.parseObject(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle.FuelVehicle.class);

        var fuelVehicle = (Vehicle.FuelVehicle) vehicle;
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
        var vehicle = jacksonTester.parseObject(json);

        // Then
        assertThat(vehicle.getClass()).isEqualTo(Vehicle.FuelVehicle.class);

        var fuelVehicle = (Vehicle.FuelVehicle) vehicle;
        assertThat(fuelVehicle.getColour()).isEqualTo("blue");
        assertThat(fuelVehicle.getFuelType()).isNull();
    }
}
