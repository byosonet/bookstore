/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.google.access;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.gson.Gson;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.google.signin.GoogleEJB;
import com.r2r.core.session.UiSession;
import com.r2r.core.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arturo
 */
@WebServlet(value = "/peopleGoogle")
public class PeopleServlet extends HttpServlet {

    @Inject
    private UiSession uiSession;
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PeopleServlet.class);
    /*
     * Gson object to serialize JSON responses to requests to this servlet.
     */
    private static final Gson GSON = new Gson();
    @Inject
    private GoogleSession googleSession;

    @Inject
    private GoogleEJB googleEJB;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Only fetch a list of people for connected users.
        String tokenData = googleSession.getToken();
        if (tokenData == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GSON.toJson("Current user not connected."));
            return;
        }
        try {
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(googleSession.getIdClient(), googleSession.getSecretClient()).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(
                                    tokenData, GoogleTokenResponse.class));
            // Create a new authorized API client.
            Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(googleSession.getApplicationName())
                    .build();
            // Get a list of people that this user has shared with this app.
            Person person = service.people().get("me").execute();
            try {
                Usuario usuario = googleEJB.mergeUser(getUsuario(person));
                uiSession.setDateLocalizedPattern(request.getLocale());
                uiSession.setDateTimeLocalizedPattern(request.getLocale());
                uiSession.login(usuario.getLogin(), googleSession.getState());
                LOGGER.info("Login correcto");
                //Faces.redirect("index?" + JSFConstant.REDIRECT);
                //response.sendRedirect("index.xhtml");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(GSON.toJson(person));
            } catch (DatabaseException ex) {
                Logger.getLogger(PeopleServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().print(GSON.toJson("Failed to insert/update user data from Google. "
                        + ex.getMessage()));
                LOGGER.error("Failed to insert/update user data from Google.: " + ex);
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(GSON.toJson("Failed to read data from Google. "
                    + e.getMessage()));
            LOGGER.error("Failed to read data from Google: " + e);
        }
    }

    private Usuario getUsuario(Person person) {
        Usuario usuario = new Usuario();
        usuario.setLogin(person.getId());
        usuario.setNombre(person.getName().getGivenName());
        usuario.setAPaterno(person.getName().getFamilyName());
        usuario.setAMaterno(person.getName().getMiddleName());
        usuario.setFoto(Util.convertImageToByteArray(person.getImage().getUrl().replace("sz=50", "sz=200")));
        usuario.setPassword(googleSession.getState());
        usuario.setEmail(getEmail(person.getEmails()));
        return usuario;
    }

    private String getEmail(List<Person.Emails> listEmails) {
        String email = null;
        if (listEmails != null && listEmails.size() > 0) {
            email = listEmails.get(0).getValue();
        }
        return email;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
