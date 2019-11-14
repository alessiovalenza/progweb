package it.unitn.disi.wp.progetto.servlets;

import it.unitn.disi.wp.progetto.persistence.dao.UtenteDAO;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.progetto.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.progetto.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.progetto.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.progetto.persistence.entities.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private String email;
    private String password;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in LoginServlet doPost method");
        email = request.getParameter("email");
        password = request.getParameter("password");

        Utente utente = doAuthN(email, password);

        if (utente == null){
            request.setAttribute("error", "Username o Password errati. Riprovare");
            doGet(request, response);
        }else{
            request.setAttribute("name", utente.getNome());
            request.setAttribute("surname", utente.getCognome());
            getServletContext().getRequestDispatcher("/testing/loginCompleted.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("in LoginServlet doGet method");

        String url = "/login.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private Utente checkUsername(String email, String password){
        Utente utente = null;
        try {
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            UtenteDAO userdao = daoFactory.getDAO(UtenteDAO.class);
            utente = userdao.getUserByEmail(email);
        } catch (DAOFactoryException | DAOException ex) {
            ex.printStackTrace();
        }

        return utente;
    }

    private boolean checkPassword(Utente utente){
        boolean retval = false;
        if (utente != null){
            /**
             * qui va implementata la verifica della password
             */
            retval = true;
        }
        return retval;
    }

    private Utente doAuthN(String email, String password){
        Utente utente = checkUsername(email, password);
        boolean authN = checkPassword(utente);
        utente = authN ? utente : null;
        return utente;
    }
}