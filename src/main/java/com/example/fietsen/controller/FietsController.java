package com.example.fietsen.controller;

import com.example.fietsen.model.Fiets;
import com.example.fietsen.repository.FietsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class FietsController {

    @Autowired
    private FietsRepository fietsRepository;

    @PostConstruct
    public void fillDB(){
        if(fietsRepository.count()==0){
            fietsRepository.save(new Fiets("Norta", "N125", 130, 150, 2));
            fietsRepository.save(new Fiets("Batavus", "qsdf64", 20, 600, 20));
            fietsRepository.save(new Fiets("Apple", "Ifiets2", 15, 3600, 10));
            fietsRepository.save(new Fiets("Samsung", "bike S12 fold", 90, 2000, 30));
        }
        System.out.println("fiets test: " + fietsRepository.findAll());
        System.out.println("merk test: " + fietsRepository.findFietsByMerk("Norta"));
    }

    @GetMapping("/fietsen")
    public List<Fiets> getFietsen() {
        return fietsRepository.findAll();
    }

    @GetMapping("/fietsen/merk/{merk}")
    public List<Fiets> getFietsenByMerk(@PathVariable String merk) {
        return fietsRepository.findFietsByMerk(merk);
    }

    @GetMapping("/fietsen/merk/{merk}/model/{model}")
    public Fiets getFietsenByMerkAndModel(@PathVariable String merk, @PathVariable String model) {
        return fietsRepository.findFietsByMerkAndModel(merk, model);
    }

    @GetMapping("/fietsen/model/{model}")
    public Fiets getFietsByModel(@PathVariable String model) {
        return fietsRepository.findFietsByModel(model);
    }

    @PostMapping("/fietsen")
    public Fiets addFiets(@RequestBody Fiets fiets) {
        fietsRepository.save(fiets);
        return fiets;
    }

    @PutMapping("/fietsen")
    public Fiets updateFiets(@RequestBody Fiets updatedFiets) {
        Fiets retrievedFiets = fietsRepository.findFietsByMerkAndModel(updatedFiets.getMerk(), updatedFiets.getModel());

        retrievedFiets.setGrootte(updatedFiets.getGrootte());
        retrievedFiets.setPrijs(updatedFiets.getPrijs());
        retrievedFiets.setVoorraad(updatedFiets.getVoorraad());

        fietsRepository.save(retrievedFiets);

        return retrievedFiets;

    }

    @DeleteMapping("/fietsen/merk/{merk}/model/{model}")
    public ResponseEntity deleteFiets(@PathVariable String merk, @PathVariable String model) {
        Fiets fiets = fietsRepository.findFietsByMerkAndModel(merk, model);
        if (fiets != null) {
            fietsRepository.delete(fiets);
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
