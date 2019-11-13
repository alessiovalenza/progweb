package it.unitn.disi.wp.progetto.persistence.entities;

import java.sql.Timestamp;

public class Foto {
    private long id;
    private Timestamp caricamento;
    private Utente paziente;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCaricamento() {
        return caricamento;
    }

    public void setCaricamento(Timestamp caricamento) {
        this.caricamento = caricamento;
    }

    public Utente getPaziente() {
        return paziente;
    }

    public void setPaziente(Utente Paziente) {
        this.paziente = Paziente;
    }
}
