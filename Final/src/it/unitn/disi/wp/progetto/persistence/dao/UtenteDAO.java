package it.unitn.disi.wp.progetto.persistence.dao;

import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import java.util.List;

public interface UtenteDAO extends DAO<Utente, Long>{
    
    public Utente getUserByEmail(String email) throws DAOException;
    public Utente getUserByCodiceFiscale(String codiceFiscale) throws DAOException;
    public List<Utente> getPazientiByMedicoBase(Long Id) throws  DAOException; //l'id è del medico di base
    public List<Utente> getUtentiToDoRichiamo(int InfEta, int SupEta, String IdProvincia)throws  DAOException; //estremi inclusi

}