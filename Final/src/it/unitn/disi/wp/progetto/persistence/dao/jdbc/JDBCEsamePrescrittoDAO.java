package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.EsamePrescrittoDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Esame;
import it.unitn.disi.wp.progetto.persistence.entities.EsamePrescritto;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JDBCEsamePrescrittoDAO extends JDBCDAO<EsamePrescritto, Long> implements EsamePrescrittoDAO{

    public JDBCEsamePrescrittoDAO(Connection con) {
        super(con);
    }

    @Override //METODO NON TESTATO, DA TESTARE
    public List<EsamePrescritto> getEsamePrescrittoByPaziente(long Id) throws DAOException{

        List<EsamePrescritto> listOfEsamePrescritto = new ArrayList<>();
/*
        if ((Id != null)) {
            throw new DAOException("Id mandatory fields", new NullPointerException("Id is null"));
        }
*/ // STA ROBA MI DAVA ERORE PERCHÈ I LONG NON PUÒ ESSERE NULL MA FUNZINAVA IN JDBCUtenteDAO
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM esame_prescritto WHERE paziente = ?")){
            stm.setLong(1, Id); // 1-based indexin

            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                    EsamePrescritto esamePrescritto = makeEsamePrescrittoFromRs(rs);
                    listOfEsamePrescritto.add(esamePrescritto);
                }
            }

            return  listOfEsamePrescritto;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM esame_prescritto");
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
    public EsamePrescritto getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM esame_prescritto ep " +
                "JOIN esame e ON ep.esame = e.id " +
                "JOIN utente m ON ep.medicobase = m.id " +
                "JOIN utente p ON ep.paziente = p.id " +
                "WHERE ep.id = ? ")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                return makeEsamePrescrittoFromRs(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }


    @Override
    public List<EsamePrescritto> getAll() throws DAOException {
        List<EsamePrescritto> esami = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM esame_prescritto ep" +
                "JOIN esame e ON ep.esame = e.id" +
                "JOIN utente m ON ep.medicobase = m.id" +
                "JOIN utente p ON ep.paziente = p.id" +
                "ORDER BY ep.prescrizione")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    esami.add(makeEsamePrescrittoFromRs(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return esami;
    }

    private EsamePrescritto makeEsamePrescrittoFromRs(ResultSet rs) throws SQLException {
        EsamePrescritto esamePrescritto = new EsamePrescritto();
        esamePrescritto.setId(rs.getLong(1));
        esamePrescritto.setPrescrizione(rs.getTimestamp(5));
        esamePrescritto.setErogazione(rs.getTimestamp(6));
        esamePrescritto.setEsito(rs.getString(7));

        Esame esame = new Esame();
        esame.setId(rs.getLong(8));
        esame.setNome(rs.getString(9));
        esame.setDescrizione(rs.getString(10));

        Utente medicoBase = new Utente();
        medicoBase.setId(rs.getLong(11));
        medicoBase.setEmail(rs.getString(12));
        medicoBase.setPassword(rs.getString(13));
        medicoBase.setSalt(rs.getLong(14));
        medicoBase.setProv(rs.getString(15));
        medicoBase.setRuolo(rs.getString(16));
        medicoBase.setNome(rs.getString(17));
        medicoBase.setCognome(rs.getString(18));
        medicoBase.setSesso(rs.getString(19).charAt(0));
        medicoBase.setDataNascita(rs.getDate(20).toLocalDate());
        medicoBase.setLuogoNascita(rs.getString(21));
        medicoBase.setCodiceFiscale(rs.getString(22));

        Utente medicoBaseBase = new Utente();
        medicoBaseBase.setId(rs.getLong(23));
        medicoBaseBase.setEmail(null);
        medicoBaseBase.setSalt(-1);
        medicoBaseBase.setPassword(null);
        medicoBaseBase.setProv(null);
        medicoBaseBase.setRuolo(null);
        medicoBaseBase.setNome(null);
        medicoBaseBase.setCognome(null);
        medicoBaseBase.setSesso('\0');
        medicoBaseBase.setDataNascita(null);
        medicoBaseBase.setLuogoNascita(null);
        medicoBaseBase.setCodiceFiscale(null);
        medicoBaseBase.setMedicoBase(null);

        medicoBase.setMedicoBase(medicoBaseBase);

        Utente paziente = new Utente();
        paziente.setId(rs.getLong(24));
        paziente.setEmail(rs.getString(25));
        paziente.setPassword(rs.getString(26));
        paziente.setSalt(rs.getLong(27));
        paziente.setProv(rs.getString(28));
        paziente.setRuolo(rs.getString(29));
        paziente.setNome(rs.getString(30));
        paziente.setCognome(rs.getString(31));
        paziente.setSesso(rs.getString(32).charAt(0));
        paziente.setDataNascita(rs.getDate(33).toLocalDate()); // NON SO SE FUNZIONA
        paziente.setLuogoNascita(rs.getString(34));
        paziente.setCodiceFiscale(rs.getString(35));

        Utente medicoBasePaz = new Utente();
        medicoBasePaz.setId(rs.getLong(36));
        medicoBasePaz.setEmail(null);
        medicoBasePaz.setSalt(-1);
        medicoBasePaz.setPassword(null);
        medicoBasePaz.setProv(null);
        medicoBasePaz.setRuolo(null);
        medicoBasePaz.setNome(null);
        medicoBasePaz.setCognome(null);
        medicoBasePaz.setSesso('\0');
        medicoBasePaz.setDataNascita(null);
        medicoBasePaz.setLuogoNascita(null);
        medicoBasePaz.setCodiceFiscale(null);
        medicoBasePaz.setMedicoBase(null);

        paziente.setMedicoBase(medicoBasePaz);

        esamePrescritto.setEsame(esame);
        esamePrescritto.setMedicoBase(medicoBase);
        esamePrescritto.setPaziente(paziente);

        return esamePrescritto;
    }


}
