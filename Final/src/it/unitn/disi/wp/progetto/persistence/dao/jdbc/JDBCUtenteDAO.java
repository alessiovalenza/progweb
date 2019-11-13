package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.UtenteDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtenteDAO extends JDBCDAO<Utente, Long> implements UtenteDAO {

    public JDBCUtenteDAO(Connection con) {
        super(con);
    }


    @Override
    public Utente getUserByEmail(String email) throws DAOException {
        if ((email == null)) {
            throw new DAOException("Email is mandatory fields", new NullPointerException("email is null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE email = ?")) {
            stm.setString(1, email); // 1-based indexing

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Utente user = makeUtenteFromRs(rs);

                return user;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user by email", ex);
        }
    }

    @Override//QUESTO METODO NON E' STATO TESTATO
    public Utente getUserByCodiceFiscale(String codiceFiscale) throws DAOException{
        if ((codiceFiscale == null)) {
            throw new DAOException("codiceFiscale is mandatory fields", new NullPointerException("codiceFiscale is null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE codicefiscale = ?")) {
            stm.setString(1, codiceFiscale); // 1-based indexing

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Utente user = makeUtenteFromRs(rs);

                return user;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user by codiceFiscale", ex);
        }
    }

    @Override
    public List<Utente> getPazientiByMedicoBase(Long Id) throws DAOException{

        List<Utente> listOfPazienti = new ArrayList<>();

        if ((Id == null)) {
            throw new DAOException("Id mandatory fields", new NullPointerException("Id is null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE medicobase = ?")) {
            stm.setLong(1, Id); // 1-based indexin

            try (ResultSet rs = stm.executeQuery()) {

                int i=0;
                while(rs.next()){
                    Utente user = makeUtenteFromRs(rs);
                    listOfPazienti.add(user);
                    i++;
                }

            }

            return  listOfPazienti;

        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list", ex);
        }
    }

    @Override // QUESTO METODO NON E' STATO TESTATO
    public List<Utente> getUtentiToDoRichiamo(int InfEta, int SupEta, String IdProvincia)throws  DAOException{

        //List<Utente> listOfPazientiNatiGiusti = new ArrayList<>();
        List<Utente> listOfPazienti = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE date_part('year', age(utente.datanascita)) >= ? AND date_part('year', age(utente.datanascita)) <= ? AND utente.idprovincia = ?; ")) {
            stm.setInt(1, InfEta); // 1-based indexin
            stm.setInt(2, SupEta); // 1-based indexin
            stm.setString(3, IdProvincia); // 1-based indexin

            try (ResultSet rs = stm.executeQuery()) {
                while(rs.next()){
                    Utente user = makeUtenteFromRs(rs);
                    listOfPazienti.add(user);
                }
            }
/*
            for(int i=0 ; i<listOfPazientiNatiGiusti.size() ; i++){

                LocalDate dataNascita = listOfPazientiNatiGiusti.get(i).getDataNascita();
                Period diff=Period.between(dataNascita, LocalDate.now());
                int age = diff.getYears();

                if(age>=InfEta && age<=SupEta){
                    listOfPazientiIdonei.add(listOfPazientiNatiGiusti.get(i));
                }
            }*/

            return  listOfPazienti;

        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM utente");
            ResultSet counter = stmt.executeQuery();
            if (counter.next()) {
                return counter.getLong(1); // 1-based indexing
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count users", ex);
        }

        return 0L;
    }

    @Override
    public Utente getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE id = ?")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Utente user = makeUtenteFromRs(rs);

                return user;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }

    @Override
    public List<Utente> getAll() throws DAOException {
        List<Utente> users = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente ORDER BY cognome")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Utente user = makeUtenteFromRs(rs);

                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return users;
    }

    private Utente makeUtenteFromRs(ResultSet rs) throws SQLException {
        Utente user = new Utente();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setSalt(rs.getLong("salt"));
        user.setRuolo(rs.getString("ruolo"));

        if(user.getRuolo().equals(Utente.P) || user.getRuolo().equals(Utente.MB) || user.getRuolo().equals(Utente.MS)) {
            user.setProv(rs.getString("idprovincia"));
            user.setNome(rs.getString("nome"));
            user.setCognome(rs.getString("cognome"));
            user.setSesso(rs.getString("sesso").charAt(0));
            user.setDataNascita(rs.getDate("datanascita").toLocalDate());
            user.setLuogoNascita(rs.getString("luogonascita"));
            user.setCodiceFiscale(rs.getString("codicefiscale"));

            Utente medicoBase = new Utente();
            medicoBase.setId(-1);
            medicoBase.setEmail(null);
            medicoBase.setSalt(-1);
            medicoBase.setPassword(null);
            medicoBase.setProv(null);
            medicoBase.setRuolo(null);
            medicoBase.setNome(null);
            medicoBase.setCognome(null);
            medicoBase.setSesso('\0');
            medicoBase.setDataNascita(null);
            medicoBase.setLuogoNascita(null);
            medicoBase.setCodiceFiscale(null);
            medicoBase.setMedicoBase(null);

            user.setMedicoBase(medicoBase);
        }
        else if(user.getRuolo().equals(Utente.F)) {
            user.setProv(rs.getString("provincia"));
            user.setNome(rs.getString("nome"));
            user.setLuogoNascita(rs.getString("luogonascita"));
        }
        else if(user.getRuolo().equals(Utente.SSP))
        {
            user.setProv(rs.getString("provincia"));
        }

        return user;
    }
}
