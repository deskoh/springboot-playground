package com.example.demo.controller;

import com.example.demo.BadRequestException;
import com.example.demo.NotFoundException;
import com.example.demo.dto.Vehicle;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/200")
    public ResponseEntity<List<Vehicle>> getVehicles() {
        List<Vehicle> result = List.of(new Vehicle.ElectricVehicle(), new Vehicle.FuelVehicle());
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/401")
    public Vehicle error400(HttpServletResponse response) throws IOException {
        response.sendError(401, "unauth");
        return null;
    }

    @GetMapping("/409")
    public Vehicle error409() throws ResponseStatusException {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "conflict message");
    }

    @GetMapping("/403")
    public ResponseEntity<List<Vehicle>> error403() throws ResponseStatusException {
        // Build the response entity with no body (content-length 0).
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/404")
    public Vehicle error404() throws NotFoundException {
        throw new NotFoundException("custom not found message");
    }

    @GetMapping("/400")
    public Vehicle error400() throws BadRequestException {
        throw new BadRequestException("custom bad request message");
    }

    @GetMapping("/exception")
    public Vehicle exception() throws Exception {
        throw new Exception("exception handled by RestControllerAdvice");
    }
}
