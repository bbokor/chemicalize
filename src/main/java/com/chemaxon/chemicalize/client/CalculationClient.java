package com.chemaxon.chemicalize.client;

import com.chemaxon.chemicalize.client.model.CalculationRequest;
import com.chemaxon.chemicalize.client.model.CalculationResponse;
import com.chemaxon.chemicalize.exceptionHandler.ErrorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "calculationClient", url = "${calculation.url}")
public interface CalculationClient {

    @PostMapping(value = "/",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE,
            headers = {"x-api-key=${api.key}"})
    List<CalculationResponse> calculate(@RequestBody CalculationRequest request) throws ErrorResponse;

}
