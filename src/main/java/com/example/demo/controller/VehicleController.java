package com.example.demo.controller;

import com.example.demo.dto.Vehicle;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final ObjectMapper objectMapper;
    private final List<Vehicle> data = List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());

    public VehicleController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/v1")
    public List<Vehicle> getVehicles() {
        return List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());
    }

    @GetMapping("/v2")
    public ResponseEntity<List<Vehicle>> getVehicles2() {
        return ResponseEntity.status(200).body(data);
    }

    @GetMapping("/v3")
    public ResponseEntity<List<Object>> getVehicles3() {
        List<Object> result = List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/v4")
    public ResponseEntity<Object> getVehicles4() {
        return ResponseEntity.status(200).body(data);
    }

    @GetMapping(path = "/v5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getVehicles5() {
        TypeReference<List<Vehicle>> typeReference = new TypeReference<>() {};
        String serializedResult = this.objectMapper.writerFor(typeReference).writeValueAsString(data);
        return ResponseEntity.status(200).body(serializedResult);
    }

    @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> test() throws Exception {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    @GetMapping(path = "/test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> test2() throws Exception {
        throw new Exception("other exeption");
    }
}
