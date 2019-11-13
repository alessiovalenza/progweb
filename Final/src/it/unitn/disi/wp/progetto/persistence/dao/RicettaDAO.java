package it.unitn.disi.wp.progetto.persistence.dao;

import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Ricetta;

import java.util.List;

public interface  RicettaDAO extends DAO<Ricetta, Long>{

    public List<Ricetta> getRicettaByPaziente(long Id) throws DAOException ;
}
