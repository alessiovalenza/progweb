package it.unitn.disi.wp.progetto.persistence.entities;

import java.sql.Timestamp;

public class Ricetta {

    private long id;
    private Farmaco farmaco;
    private Utente medicoBase;
    private Utente paziente;
    private Utente farmacia;
    private Timestamp emissione;
    private Timestamp evasione;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Farmaco getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(Farmaco farmaco) {
        this.farmaco = farmaco;
    }

    public Utente getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(Utente medicoBase) {
        this.medicoBase = medicoBase;
    }

    public Utente getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Utente farmacia) {
        this.farmacia = farmacia;
    }

    public Timestamp getEmissione() {
        return emissione;
    }

    public void setEmissione(Timestamp Emissione) {
        this.emissione = Emissione;
    }

    public Timestamp getEvasione() {
        return evasione;
    }

    public void setEvasione(Timestamp evasione) {
        this.evasione = evasione;
    }

    public Utente getPaziente() {
        return paziente;
    }

    public void setPaziente(Utente paziente) {
        this.paziente = paziente;
    }
}
