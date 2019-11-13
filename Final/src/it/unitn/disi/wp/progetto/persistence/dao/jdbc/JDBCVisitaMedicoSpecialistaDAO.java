package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.VisitaMedicoSpecialistaDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;
import it.unitn.disi.wp.progetto.persistence.entities.VisitaMedicoSpecialista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCVisitaMedicoSpecialistaDAO extends JDBCDAO<VisitaMedicoSpecialista, Long> implements VisitaMedicoSpecialistaDAO {
    public JDBCVisitaMedicoSpecialistaDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM visita_specialista");
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
    public VisitaMedicoSpecialista getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM visita_specialista v " +
                "JOIN utente ms ON v.medicospecialista = ms.id " +
                "JOIN utente m ON v.medicobase = m.id " +
                "JOIN utente p ON v.paziente = p.id " +
                "WHERE v.id = ? ")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                return makeVisitaSpecFromRs(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }


    @Override
    public List<VisitaMedicoSpecialista> getAll() throws DAOException {
        List<VisitaMedicoSpecialista> visite = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM visita_specialista v " +
                "JOIN utente ms ON v.medicospecialista = ms.id " +
                "JOIN utente m ON v.medicobase = m.id " +
                "JOIN utente p ON v.paziente = p.id " +
                "ORDER BY v.paziente")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    visite.add(makeVisitaSpecFromRs(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return visite;
    }

    private VisitaMedicoSpecialista makeVisitaSpecFromRs(ResultSet rs) throws SQLException {
        VisitaMedicoSpecialista visita = new VisitaMedicoSpecialista();
        visita.setId(rs.getLong(1));
        visita.setPrescrizione(rs.getTimestamp(5));
        visita.setErogazione(rs.getTimestamp(6));
        visita.setAnamnesi(rs.getString(7));

        Utente medicoSpec = null;
        long idMedicoSpec = rs.getLong(8);
        if(!rs.wasNull()) {
            medicoSpec = new Utente();
            medicoSpec.setId(idMedicoSpec);
            medicoSpec.setEmail(rs.getString(9));
            medicoSpec.setPassword(rs.getString(10));
            medicoSpec.setSalt(rs.getLong(11));
            medicoSpec.setProv(rs.getString(12));
            medicoSpec.setRuolo(rs.getString(13));
            medicoSpec.setNome(rs.getString(14));
            medicoSpec.setCognome(rs.getString(15));
            medicoSpec.setSesso(rs.getString(16).charAt(0));
            medicoSpec.setDataNascita(rs.getDate(17).toLocalDate());
            medicoSpec.setLuogoNascita(rs.getString(18));
            medicoSpec.setCodiceFiscale(rs.getString(19));

            Utente medicoBaseSpec = new Utente();
            medicoBaseSpec.setId(rs.getLong(20));
            medicoBaseSpec.setEmail(null);
            medicoBaseSpec.setSalt(-1);
            medicoBaseSpec.setPassword(null);
            medicoBaseSpec.setProv(null);
            medicoBaseSpec.setRuolo(null);
            medicoBaseSpec.setNome(null);
            medicoBaseSpec.setCognome(null);
            medicoBaseSpec.setSesso('\0');
            medicoBaseSpec.setDataNascita(null);
            medicoBaseSpec.setLuogoNascita(null);
            medicoBaseSpec.setCodiceFiscale(null);
            medicoBaseSpec.setMedicoBase(null);

            medicoSpec.setMedicoBase(medicoBaseSpec);
        }

        Utente medicoBase = new Utente();
        medicoBase.setId(rs.getLong(21));
        medicoBase.setEmail(rs.getString(22));
        medicoBase.setPassword(rs.getString(23));
        medicoBase.setSalt(rs.getLong(24));
        medicoBase.setProv(rs.getString(25));
        medicoBase.setRuolo(rs.getString(26));
        medicoBase.setNome(rs.getString(27));
        medicoBase.setCognome(rs.getString(28));
        medicoBase.setSesso(rs.getString(29).charAt(0));
        medicoBase.setDataNascita(rs.getDate(30).toLocalDate());
        medicoBase.setLuogoNascita(rs.getString(31));
        medicoBase.setCodiceFiscale(rs.getString(32));

        Utente medicoBaseBase = new Utente();
        medicoBaseBase.setId(rs.getLong(33));
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
        paziente.setId(rs.getLong(34));
        paziente.setEmail(rs.getString(35));
        paziente.setPassword(rs.getString(36));
        paziente.setSalt(rs.getLong(37));
        paziente.setProv(rs.getString(38));
        paziente.setRuolo(rs.getString(39));
        paziente.setNome(rs.getString(40));
        paziente.setCognome(rs.getString(41));
        paziente.setSesso(rs.getString(42).charAt(0));
        paziente.setDataNascita(rs.getDate(43).toLocalDate());
        paziente.setLuogoNascita(rs.getString(44));
        paziente.setCodiceFiscale(rs.getString(45));

        Utente medicoBasePaz = new Utente();
        medicoBasePaz.setId(rs.getLong(46));
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

        visita.setMedicoSpecialista(medicoSpec);
        visita.setMedicoBase(medicoSpec);
        visita.setPaziente(paziente);

        return visita;
    }
}
