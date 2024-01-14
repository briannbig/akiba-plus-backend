package com.github.briannbig.akiba.api;


import com.github.briannbig.akiba.api.dto.SavingDto;
import com.github.briannbig.akiba.api.dto.SavingPlanDto;
import com.github.briannbig.akiba.api.request.SavingCreateRequest;
import com.github.briannbig.akiba.api.request.SavingPlanCreateRequest;
import com.github.briannbig.akiba.service.SavingsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * @author Brian Barasa
 */
@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "Savings")

public class SavingsController {

    private final SavingsService savingsService;

    @Autowired
    public SavingsController(SavingsService savingsService) {
        this.savingsService = savingsService;
    }


    @GetMapping("/")
    public ResponseEntity<List<SavingPlanDto>> getAllSavings() {
        var savingPlans = savingsService.allSavingPlans().stream().map(SavingPlanDto::from).toList();
        return savingPlans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(savingPlans);
    }

    @PostMapping("/")
    public ResponseEntity<SavingPlanDto> addSavingPlan(@RequestBody SavingPlanCreateRequest request) {
        try {
            Optional<SavingPlanDto> savingPlanDtoOptional = savingsService.addSavingPlan(request).map(SavingPlanDto::from);
            return savingPlanDtoOptional.map(savingPlanDto -> ResponseEntity.status(HttpStatus.CREATED).body(savingPlanDto)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
        } catch (Exception e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_MODIFIED, e.getMessage()
            )).build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<SavingDto> addSaving(@PathVariable String id, @RequestBody SavingCreateRequest request) {
        try {
            Optional<SavingDto> savingDtoOptional = savingsService.addSaving(request).map(SavingDto::from);
            return savingDtoOptional.map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
        } catch (Exception e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_MODIFIED, e.getMessage()
            )).build();
        }
    }

    @GetMapping("/{id}/savings")
    public ResponseEntity<List<SavingDto>> getPlanSavings(@PathVariable String id) {
        List<SavingDto> savings = savingsService.getPlanSavings(id).stream().map(SavingDto::from).toList();
        return savings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(savings);
    }


}
