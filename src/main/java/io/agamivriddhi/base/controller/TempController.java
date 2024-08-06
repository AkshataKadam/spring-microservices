package io.agamivriddhi.base.controller;

import io.agamivriddhi.base.service.TempService;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApplicationLayer
public class TempController {

    TempService tempService;

    @Autowired
    TempController(TempService tempService) {
        this.tempService = tempService;
    }

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(tempService.testServiceCall());
    }

}
