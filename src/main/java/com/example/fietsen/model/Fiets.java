package com.example.fietsen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fietsen")
public class Fiets {
    @Id
    private String id;
    private String merk;
    private String model;
    private Integer grootte;
    private Integer prijs;
    private Integer voorraad;

    public Fiets() {
    }

    public Fiets(String merk, String model, Integer grootte, Integer prijs, Integer voorraad) {
        this.merk = merk;
        this.model = model;
        this.grootte = grootte;
        this.prijs = prijs;
        this.voorraad = voorraad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getGrootte() {
        return grootte;
    }

    public void setGrootte(Integer grootte) {
        this.grootte = grootte;
    }

    public Integer getPrijs() {
        return prijs;
    }

    public void setPrijs(Integer prijs) {
        this.prijs = prijs;
    }

    public Integer getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(Integer voorraad) {
        this.voorraad = voorraad;
    }
}

