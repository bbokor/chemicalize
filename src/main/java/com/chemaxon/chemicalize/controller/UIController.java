package com.chemaxon.chemicalize.controller;

import com.chemaxon.chemicalize.client.CalculationClient;
import com.chemaxon.chemicalize.client.model.CalculationKey;
import com.chemaxon.chemicalize.client.model.CalculationRequest;
import com.chemaxon.chemicalize.client.model.CalculationResponse;
import com.chemaxon.chemicalize.exceptionHandler.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
@Slf4j
public class UIController {
    private final CalculationClient calculationClient;

    @GetMapping
    public String calculate(@ModelAttribute("calculationRequest") CalculationRequest calculationRequest, Model model) {
        log.info("Request parameters from ui: {}", calculationRequest);
        if (!StringUtils.isEmpty(calculationRequest.getStructure())) {
            calculationRequest.setCalculations(Collections.singletonList(CalculationKey.BASIC));
            try {
                List<CalculationResponse> result = calculationClient.calculate(calculationRequest);
                model.addAttribute("result", result);
                log.info("Calculation result: {}", result);
            } catch (ErrorResponse e) {
                log.error("Error on calling calculation service: {}", e.getMessage());
                model.addAttribute("error", e);
            }
        }
        return "index";
    }

}
