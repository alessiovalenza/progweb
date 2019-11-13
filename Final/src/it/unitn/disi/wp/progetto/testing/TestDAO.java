package it.unitn.disi.wp.progetto.testing;

import it.unitn.disi.wp.progetto.persistence.dao.UtenteDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.progetto.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.progetto.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

public class TestDAO {

    public static void main(String[] args) throws DAOException, DAOFactoryException {

        String email = null;
        String password = null;
        Utente utente = null;

        try{
            DAOFactory daofactory = JDBCDAOFactory.getInstance();
            UtenteDAO user = daofactory.getDAO(UtenteDAO.class);
            utente =  user.getUserByEmail(email);
        }
        catch(DAOException | DAOFactoryException ex1){}

        //String nome = utente.getNome();
        System.out.println("JAMBISSIMO");

    }

}


