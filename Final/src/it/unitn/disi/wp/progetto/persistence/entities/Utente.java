package it.unitn.disi.wp.progetto.persistence.entities;

import java.time.LocalDate;

public class Utente {
    public static final String P = "p";
    public static final String MB = "mb";
    public static final String MS = "ms";
    public static final String F = "f";
    public static final String SSP = "ssp";
    public static final String SSN = "ssn";

    private long id;
    private String email;
    private String password;
    private long salt;
    private String prov;
    private String ruolo;
    private String nome;
    private String cognome;
    private char sesso;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String codiceFiscale;
    private Utente medicoBase;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Utente getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(Utente medicoBase) {
        this.medicoBase = medicoBase;
    }
}
