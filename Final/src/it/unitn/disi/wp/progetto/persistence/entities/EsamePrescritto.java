package it.unitn.disi.wp.progetto.persistence.entities;

import java.sql.Timestamp;

public class EsamePrescritto {
    private long id;
    private Esame esame;
    private Utente medicoBase;
    private Utente paziente;
    private Timestamp prescrizione;
    private Timestamp erogazione;
    private String esito;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Esame getEsame() {
        return esame;
    }

    public void setEsame(Esame esame) {
        this.esame = esame;
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

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }
}
