package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.RicettaDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Farmaco;
import it.unitn.disi.wp.progetto.persistence.entities.Ricetta;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCRicettaDAO extends JDBCDAO<Ricetta, Long> implements RicettaDAO{

    public JDBCRicettaDAO(Connection con) {
        super(con);
    }

    @Override //METODO NON TESTATO DA TESTARE
    public List<Ricetta> getRicettaByPaziente(long Id) throws DAOException {

        List<Ricetta> listOfRicetta = new ArrayList<>();
/*
        if ((Id != null)) {
            throw new DAOException("Id mandatory fields", new NullPointerException("Id is null"));
        }
*/ // STA ROBA MI DAVA ERORE PERCHÈ I LONG NON PUÒ ESSERE NULL MA FUNZINAVA IN JDBCUtenteDAO

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricetta WHERE paziente = ?")){
            stm.setLong(1, Id); // 1-based indexin

            try (ResultSet rs = stm.executeQuery()) {

                while(rs.next()){
                    Ricetta ricetta = makeRicettaFromRs(rs);
                    listOfRicetta.add(ricetta);
                }
            }

            return  listOfRicetta;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM ricetta");
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
    public Ricetta getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricetta r " +
                "JOIN farmaco fm ON r.farmaco = fm.id " +
                "JOIN utente m ON r.medicobase = m.id " +
                "JOIN utente p ON r.paziente = p.id " +
                "LEFT OUTER JOIN utente fc ON r.farmacia = fc.id " +
                "WHERE r.id = ?")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                return makeRicettaFromRs(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }


    @Override
    public List<Ricetta> getAll() throws DAOException {
        List<Ricetta> ricette = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricetta r " +
                "JOIN farmaco fm ON r.farmaco = fm.id " +
                "JOIN utente m ON r.medicobase = m.id " +
                "JOIN utente p ON r.paziente = p.id " +
                "LEFT OUTER JOIN utente fc ON r.farmacia = fc.id " +
                "ORDER BY r.paziente")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ricette.add(makeRicettaFromRs(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return ricette;
    }

    private Ricetta makeRicettaFromRs(ResultSet rs) throws SQLException {
        Ricetta ricetta = new Ricetta();
        ricetta.setId(rs.getLong(1));
        ricetta.setEmissione(rs.getTimestamp(6));
        ricetta.setEvasione(rs.getTimestamp(7));

        Farmaco farmaco = new Farmaco();
        farmaco.setId(rs.getLong(8));
        farmaco.setNome(rs.getString(9));
        farmaco.setDescrizione(rs.getString(10));
        farmaco.setPrezzo(rs.getDouble(11));

        Utente medicoBase = new Utente();
        medicoBase.setId(rs.getLong(12));
        medicoBase.setEmail(rs.getString(13));
        medicoBase.setPassword(rs.getString(14));
        medicoBase.setSalt(rs.getLong(15));
        medicoBase.setProv(rs.getString(16));
        medicoBase.setRuolo(rs.getString(17));
        medicoBase.setNome(rs.getString(18));
        medicoBase.setCognome(rs.getString(19));
        medicoBase.setSesso(rs.getString(20).charAt(0));
        medicoBase.setDataNascita(rs.getDate(21).toLocalDate());
        medicoBase.setLuogoNascita(rs.getString(22));
        medicoBase.setCodiceFiscale(rs.getString(23));

        Utente medicoBaseBase = new Utente();
        medicoBaseBase.setId(rs.getLong(24));
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
        paziente.setId(rs.getLong(25));
        paziente.setEmail(rs.getString(26));
        paziente.setPassword(rs.getString(27));
        paziente.setSalt(rs.getLong(28));
        paziente.setProv(rs.getString(29));
        paziente.setRuolo(rs.getString(30));
        paziente.setNome(rs.getString(31));
        paziente.setCognome(rs.getString(32));
        paziente.setSesso(rs.getString(33).charAt(0));
        paziente.setDataNascita(rs.getDate(34).toLocalDate());
        paziente.setLuogoNascita(rs.getString(35));
        paziente.setCodiceFiscale(rs.getString(36));

        Utente medicoBasePaz = new Utente();
        medicoBasePaz.setId(rs.getLong(37));
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

        Utente farmacia = null;
        long idFarmacia = rs.getLong(38);
        if(!rs.wasNull())
        {
            farmacia.setId(idFarmacia);
            farmacia.setEmail(rs.getString(39));
            farmacia.setPassword(rs.getString(40));
            farmacia.setSalt(rs.getLong(41));
            farmacia.setProv(rs.getString(42));
            farmacia.setRuolo(rs.getString(43));
            farmacia.setNome(rs.getString(44));
            farmacia.setLuogoNascita(rs.getString(48));
        }

        ricetta.setFarmaco(farmaco);
        ricetta.setMedicoBase(medicoBase);
        ricetta.setPaziente(paziente);
        ricetta.setFarmacia(farmacia);

        return ricetta;
    }
}
