package com.example.fietsen.repository;

import com.example.fietsen.model.Fiets;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FietsRepository extends MongoRepository<Fiets, String> {
    List<Fiets> findFietsByMerk(String merk);

    Fiets findFietsByMerkAndModel(String merk, String model);
}
