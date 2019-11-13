package it.unitn.disi.wp.progetto.persistence.dao;

import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.EsamePrescritto;

import java.util.List;

public interface  EsamePrescrittoDAO extends DAO<EsamePrescritto, Long>{

    public List<EsamePrescritto> getEsamePrescrittoByPaziente(long Id) throws DAOException;

}
