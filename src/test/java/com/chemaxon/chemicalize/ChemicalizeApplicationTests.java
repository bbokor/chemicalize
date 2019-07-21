package com.chemaxon.chemicalize;

import com.chemaxon.chemicalize.client.CalculationClient;
import com.chemaxon.chemicalize.controller.UIController;
import com.chemaxon.chemicalize.exceptionHandler.ErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChemicalizeApplicationTests {

    @Autowired
    private CalculationClient calculationClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UIController controller;


    @Test
    public void contextLoads() {

        assertNotNull(controller);
        assertNotNull(calculationClient);
    }

    @Test
    public void calculate() throws Exception {
        mockMvc.perform(get("/").param("structure", "aspirin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    public void calculateInvalid() throws Exception {
        mockMvc.perform(get("/").param("structure", "aspiri"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void calculateUnknownError() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Unknown error");

        mockMvc.perform(get("/").param("structure", "aa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", errorResponse));
    }

    @Test
    public void calculateEmpty() throws Exception {
        mockMvc.perform(get("/").param("structure", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attributeDoesNotExist("error"));
    }

}
