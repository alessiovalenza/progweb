package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.VisitaMedicoBaseDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;
import it.unitn.disi.wp.progetto.persistence.entities.VisitaMedicoBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCVisitaMedicoBaseDAO extends JDBCDAO<VisitaMedicoBase, Long> implements VisitaMedicoBaseDAO{

    public JDBCVisitaMedicoBaseDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM visita_base");
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
    public VisitaMedicoBase getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM visita_base v " +
                "JOIN utente m ON v.medicobase = m.id " +
                "JOIN utente p ON v.paziente = p.id " +
                "WHERE v.id = ? ")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                return makeVisitaBaseFromRs(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }


    @Override
    public List<VisitaMedicoBase> getAll() throws DAOException {
        List<VisitaMedicoBase> visite = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM visita_base v " +
                "JOIN utente m ON v.medicobase = m.id " +
                "JOIN utente p ON v.paziente = p.id " +
                "ORDER BY v.paziente")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    visite.add(makeVisitaBaseFromRs(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return visite;
    }

    private VisitaMedicoBase makeVisitaBaseFromRs(ResultSet rs) throws SQLException {
        VisitaMedicoBase visita = new VisitaMedicoBase();
        visita.setId(rs.getLong(1));
        visita.setErogazione(rs.getTimestamp(4));
        visita.setAnamnesi(rs.getString(5));

        Utente medicoBase = new Utente();
        medicoBase.setId(rs.getLong(6));
        medicoBase.setEmail(rs.getString(7));
        medicoBase.setPassword(rs.getString(8));
        medicoBase.setSalt(rs.getLong(9));
        medicoBase.setProv(rs.getString(10));
        medicoBase.setRuolo(rs.getString(11));
        medicoBase.setNome(rs.getString(12));
        medicoBase.setCognome(rs.getString(13));
        medicoBase.setSesso(rs.getString(14).charAt(0));
        medicoBase.setDataNascita(rs.getDate(15).toLocalDate());
        medicoBase.setLuogoNascita(rs.getString(16));
        medicoBase.setCodiceFiscale(rs.getString(17));

        Utente medicoBaseBase = new Utente();
        medicoBaseBase.setId(rs.getLong(18));
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
        paziente.setId(rs.getLong(19));
        paziente.setEmail(rs.getString(20));
        paziente.setPassword(rs.getString(21));
        paziente.setSalt(rs.getLong(22));
        paziente.setProv(rs.getString(23));
        paziente.setRuolo(rs.getString(24));
        paziente.setNome(rs.getString(25));
        paziente.setCognome(rs.getString(26));
        paziente.setSesso(rs.getString(27).charAt(0));
        paziente.setDataNascita(rs.getDate(28).toLocalDate());
        paziente.setLuogoNascita(rs.getString(29));
        paziente.setCodiceFiscale(rs.getString(30));

        Utente medicoBasePaz = new Utente();
        medicoBasePaz.setId(rs.getLong(31));
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

        visita.setMedicoBase(medicoBase);
        visita.setPaziente(paziente);

        return visita;
    }
}
