package com.example.fietsen;


import com.example.fietsen.model.Fiets;
import com.example.fietsen.repository.FietsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FietsControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FietsRepository fietsRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenFiets_whenGetFietsenByMerk_thenReturnJsonFiets() throws Exception {

        Fiets fiets1 = new Fiets("Norta", "N125", 130, 150, 2);
        Fiets fiets2 = new Fiets("Norta", "6T", 90, 500, 10);

        List<Fiets> fietsList = new ArrayList<>();
        fietsList.add(fiets1);
        fietsList.add(fiets2);
        given(fietsRepository.findFietsByMerk("Norta")).willReturn(fietsList);

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
        Fiets fiets1 = new Fiets("Batavus", "qsdf64", 20, 600, 20);

        given(fietsRepository.findFietsByMerkAndModel("Batavus", "qsdf64")).willReturn(fiets1);
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
        Fiets fiets1 = new Fiets("Norta", "N125", 130, 150, 2);
        Fiets fiets2 = new Fiets("Batavus", "qsdf64", 20, 600, 20);
        Fiets fiets3 = new Fiets("Norta", "6T", 90, 500, 10);


        List<Fiets> fietsList = new ArrayList<>();
        fietsList.add(fiets1);
        fietsList.add(fiets2);
        fietsList.add(fiets3);

        given(fietsRepository.findAll()).willReturn(fietsList);
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
        Fiets fiets = new Fiets("Norta", "N125", 130, 150, 50);

        given(fietsRepository.findFietsByMerkAndModel("Norta", "N125")).willReturn(fiets);

        Fiets updatedFiets = new Fiets("Norta", "N125", 130, 150, 2);

        mockMvc.perform(put("/fietsen")
                .content(mapper.writeValueAsString(updatedFiets))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merk", is("Norta")))
                .andExpect(jsonPath("$.model", is("N125")))
                .andExpect(jsonPath("$.grootte", is(130)))
                .andExpect(jsonPath("$.prijs", is(150)))
                .andExpect(jsonPath("$.voorraad", is(2)));

    }

    @Test
    public void givenFiets_whenDeleteFiets_thenStatusOk() throws Exception {
        Fiets fietsToBeDeleted = new Fiets("Norta", "N125", 130, 150, 50);

        given(fietsRepository.findFietsByMerkAndModel("Norta", "N125")).willReturn(fietsToBeDeleted);

        mockMvc.perform(delete("/fietsen/merk/{merk}/model/{model}", "Norta", "N125")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoFiets_whenDeleteFiets_thenStatusNotFound() throws Exception {
        Fiets fietsToBeDeleted = new Fiets("Norta", "aaa", 130, 150, 50);

        given(fietsRepository.findFietsByMerkAndModel("Norta", "aaa")).willReturn(null);

        mockMvc.perform(delete("/fietsen/merk/{merk}/model/{model}", "Norta", "aaa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
