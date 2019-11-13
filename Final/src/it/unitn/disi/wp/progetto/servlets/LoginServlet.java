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
    int a;
    private String email;
    private String password;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in LoginServletSession doPost() method");
        email = request.getParameter("username");
        password = request.getParameter("password");

        Utente user = null;
        List<Utente> listOfPazienti = null;
        try {
            System.out.println("qui1");
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            UtenteDAO userdao = daoFactory.getDAO(UtenteDAO.class);
            user = userdao.getUserByEmail(email);
            System.out.println("qui2");

            listOfPazienti = userdao.getPazientiByMedicoBase(user.getId());

            System.out.println("qui3");
        } catch (DAOFactoryException ex) {
        } catch (DAOException ex2) {
        }

        System.out.println(user.getNome());

        if(listOfPazienti == null){
            System.out.println("stocazzo");
        }
        System.out.println(listOfPazienti.size());

        for(int i=0;i<listOfPazienti.size();i++){
            System.out.println(("lol"));
            System.out.println(listOfPazienti.get(i).getNome());
        }

        /*
        try {
            String emaill = user.getEmail(); //non esegue
            System.out.println(emaill);
        } catch (Exception ex2){}


        if(checkCredentials(username, pwd)) {
            System.out.println("check passed");
            HttpSession Session = request.getSession();
            Session.setAttribute("user", my_username);
            Session.setMaxInactiveInterval(60);
            response.sendRedirect("index.jsp");
        }else{
            request.setAttribute("message", "Username o Password errati");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String url = "/index.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    public boolean checkCredentials(String username, String password) {
        return (this.email.equals(username) && this.password.equals(password));
    }
}