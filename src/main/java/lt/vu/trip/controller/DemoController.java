package lt.vu.trip.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("")
    public String all() {
        return "{\"success\":1}";
    }
}
