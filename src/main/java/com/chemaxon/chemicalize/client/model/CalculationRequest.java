package com.chemaxon.chemicalize.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    private String structure;
    private List<CalculationKey> calculations;
}
