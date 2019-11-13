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
        email = request.getParameter("username");
        password = request.getParameter("password");

        Utente utente = null;
        try {
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            UtenteDAO userdao = daoFactory.getDAO(UtenteDAO.class);
            utente = userdao.getUserByEmail(email);


        } catch (DAOFactoryException | DAOException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("name", utente.getNome());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}