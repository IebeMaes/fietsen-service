package com.example.fietsen;


import com.example.fietsen.model.Fiets;
import com.example.fietsen.repository.FietsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FietsControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FietsRepository fietsRepository;

    private Fiets fiets1 = new Fiets("Norta", "N125", 130, 150, 2);
    private Fiets fiets2 = new Fiets("Batavus", "qsdf64", 20, 600, 20);
    private Fiets fietsToBeDeleted = new Fiets("Norta", "6T", 90, 500, 10);

    @BeforeEach
    public void beforeAllTests() {
        fietsRepository.deleteAll();
        fietsRepository.save(fiets1);
        fietsRepository.save(fiets2);
        fietsRepository.save(fietsToBeDeleted);

    }

    @AfterEach
    public void afterAllTests() {
        fietsRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenFiets_whenGetFietsenByMerk_thenReturnJsonFiets() throws Exception {
        mockMvc.perform(get("/fietsen/merk/{merk}", "Norta"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].merk", is("Norta")))
                .andExpect(jsonPath("$[0].model", is("N125")))
                .andExpect(jsonPath("$[0].grootte", is(130)))
                .andExpect(jsonPath("$[0].prijs", is(150)))
                .andExpect(jsonPath("$[0].voorraad", is(2)))
                .andExpect(jsonPath("$[1].merk", is("Norta")))
                .andExpect(jsonPath("$[1].model", is("6T")))
                .andExpect(jsonPath("$[1].grootte", is(90)))
                .andExpect(jsonPath("$[1].prijs", is(500)))
                .andExpect(jsonPath("$[1].voorraad", is(10)));
    }

    @Test
    public void givenFiets_whenGetFietsByMerkAndModel_thenReturnJsonFiets() throws Exception {
        mockMvc.perform(get("/fietsen/merk/{merk}/model/{model}", "Batavus", "qsdf64"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("Batavus")))
                .andExpect(jsonPath("$.model", is("qsdf64")))
                .andExpect(jsonPath("$.grootte", is(20)))
                .andExpect(jsonPath("$.prijs", is(600)))
                .andExpect(jsonPath("$.voorraad", is(20)));
    }

    @Test
    public void givenFiets_whenGetFietsen_thenReturnJsonFiets() throws Exception {
        mockMvc.perform(get("/fietsen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].merk", is("Norta")))
                .andExpect(jsonPath("$[0].model", is("N125")))
                .andExpect(jsonPath("$[0].grootte", is(130)))
                .andExpect(jsonPath("$[0].prijs", is(150)))
                .andExpect(jsonPath("$[0].voorraad", is(2)))
                .andExpect(jsonPath("$[1].merk", is("Batavus")))
                .andExpect(jsonPath("$[1].model", is("qsdf64")))
                .andExpect(jsonPath("$[1].grootte", is(20)))
                .andExpect(jsonPath("$[1].prijs", is(600)))
                .andExpect(jsonPath("$[1].voorraad", is(20)))
                .andExpect(jsonPath("$[2].merk", is("Norta")))
                .andExpect(jsonPath("$[2].model", is("6T")))
                .andExpect(jsonPath("$[2].grootte", is(90)))
                .andExpect(jsonPath("$[2].prijs", is(500)))
                .andExpect(jsonPath("$[2].voorraad", is(10)));
    }

    @Test
    public void givenPostFiets_thenReturnJsonFiets() throws Exception {
        Fiets fietsToAdd = new Fiets("Apple", "Ifiets2", 15, 3600, 10);

        mockMvc.perform(post("/fietsen")
                .content(mapper.writeValueAsString(fietsToAdd))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("Apple")))
                .andExpect(jsonPath("$.model", is("Ifiets2")))
                .andExpect(jsonPath("$.grootte", is(15)))
                .andExpect(jsonPath("$.prijs", is(3600)))
                .andExpect(jsonPath("$.voorraad", is(10)));

    }

    @Test
    public void givenFiets_whenPutFiets_thenReturnJsonReview() throws Exception {
        Fiets updatedFiets = new Fiets("Norta", "N125", 130, 150, 50);

        mockMvc.perform(put("/fietsen")
                .content(mapper.writeValueAsString(updatedFiets))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("Norta")))
                .andExpect(jsonPath("$.model", is("N125")))
                .andExpect(jsonPath("$.grootte", is(130)))
                .andExpect(jsonPath("$.prijs", is(150)))
                .andExpect(jsonPath("$.voorraad", is(50)));

    }

    @Test
    public void givenFiets_whenDeleteFiets_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/fietsen/merk/{merk}/model/{model}", "Norta", "6T")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoFiets_whenDeleteFiets_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/fietsen/merk/{merk}/model/{model}", "Norta", "TEST")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
