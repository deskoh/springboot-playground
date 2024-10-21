package com.example.demo.controller;

import com.example.demo.BadRequestException;
import com.example.demo.NotFoundException;
import com.example.demo.dto.Vehicle;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @GetMapping("/")
    public List<Vehicle> getVehicles() {
        return List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());
    }

    @GetMapping("/v2")
    public ResponseEntity<List<Vehicle>> getVehicles2() {
        List<Vehicle> result = List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/fuel")
    public Vehicle getVehicles3(HttpServletResponse response) throws IOException {
        response.sendError(400, "help");
        return new Vehicle.FuelVehicle();
    }

    @GetMapping("/electric")
    public Vehicle getElectricVehicle() {
        return new Vehicle.ElectricVehicle();
    }

    @GetMapping("/404")
    public Vehicle notFound() throws NotFoundException {
        throw new NotFoundException();
    }

    @GetMapping("/400")
    public Vehicle badRequest() throws BadRequestException {
        throw new BadRequestException("!!!");
    }

    @GetMapping("/exception")
    public Vehicle exception() throws Exception {
        throw new Exception("detailed error");
    }
}
