package it.unitn.disi.wp.progetto.persistence.entities;

import java.sql.Timestamp;

public class VisitaMedicoSpecialista {
    private long id;
    private Utente medicoSpecialista;
    private Utente medicoBase;
    private Utente paziente;
    private Timestamp prescrizione;
    private Timestamp erogazione;
    private String anamnesi;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Utente getMedicoSpecialista() {
        return medicoSpecialista;
    }

    public void setMedicoSpecialista(Utente medicoSpecialista) {
        this.medicoSpecialista = medicoSpecialista;
    }

    public Utente getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(Utente medicoBase) {
        this.medicoBase = medicoBase;
    }

    public Utente getPaziente() {
        return paziente;
    }

    public void setPaziente(Utente paziente) {
        this.paziente = paziente;
    }

    public Timestamp getPrescrizione() {
        return prescrizione;
    }

    public void setPrescrizione(Timestamp prescrizione) {
        this.prescrizione = prescrizione;
    }

    public Timestamp getErogazione() {
        return erogazione;
    }

    public void setErogazione(Timestamp erogazione) {
        this.erogazione = erogazione;
    }

    public String getAnamnesi() {
        return anamnesi;
    }

    public void setAnamnesi(String anamnesi) {
        this.anamnesi = anamnesi;
    }
}
