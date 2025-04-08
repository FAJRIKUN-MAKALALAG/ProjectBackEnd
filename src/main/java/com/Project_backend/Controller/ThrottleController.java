package com.Project_backend.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ThrottleController {

    @PostMapping("/throttle")
    public String receiveThrottle(@RequestBody ThrottleRequest request) {
        System.out.println("Throttle Received: " + request.getValue() + "%");
        return "Received throttle: " + request.getValue();
    }

    static class ThrottleRequest {
        private int value;
        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
    }
}
