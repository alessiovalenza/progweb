package it.unitn.disi.wp.progetto.persistence.dao.jdbc;

import it.unitn.disi.wp.progetto.persistence.dao.FotoDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.entities.Foto;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCFotoDAO extends JDBCDAO<Foto, Long> implements FotoDAO{

    public JDBCFotoDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = CON.prepareStatement("SELECT count(*) FROM foto");
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
    public Foto getByPrimaryKey(Long primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM foto f " +
                "JOIN utente u ON f.paziente = u.id " +
                "WHERE u.id = ? ")) {
            stm.setLong(1, primaryKey);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                return makeFotoFromRs(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }

    @Override
    public List<Foto> getAll() throws DAOException {
        List<Foto> esami = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM foto f " +
                "JOIN utente u ON f.paziente = u.id " +
                "ORDER BY u.cognome")) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    esami.add(makeFotoFromRs(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return esami;
    }

    private Foto makeFotoFromRs(ResultSet rs) throws SQLException {
        Foto foto = new Foto();
        foto.setId(rs.getLong(1));
        foto.setCaricamento(rs.getTimestamp(2));

        Utente paziente = new Utente();
        paziente.setId(rs.getLong(4));
        paziente.setEmail(rs.getString(5));
        paziente.setPassword(rs.getString(6));
        paziente.setSalt(rs.getLong(7));
        paziente.setProv(rs.getString(8));
        paziente.setRuolo(rs.getString(9));
        paziente.setNome(rs.getString(10));
        paziente.setCognome(rs.getString(11));
        paziente.setSesso(rs.getString(12).charAt(0));
        paziente.setDataNascita(rs.getDate(13).toLocalDate());
        paziente.setLuogoNascita(rs.getString(14));
        paziente.setCodiceFiscale(rs.getString(15));

        Utente medicoBase = new Utente();
        medicoBase.setId(rs.getLong(16));
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

        paziente.setMedicoBase(medicoBase);

        foto.setPaziente(paziente);

        return foto;
    }
}
