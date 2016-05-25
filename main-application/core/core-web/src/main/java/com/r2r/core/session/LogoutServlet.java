/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.session;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import com.r2r.core.google.access.GoogleSession;
import com.r2r.core.util.GoogleConstant;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alan Hdez
 */
@WebServlet(value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Inject
    private GoogleSession googleSession;

    @Inject
    private UserSession userSession;
    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
     * Gson object to serialize JSON responses to requests to this servlet.
     */
    private static final Gson GSON = new Gson();

    /*
     * Creates a client secrets object from the client_secrets.json file.
     */
    private static GoogleClientSecrets clientSecrets;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        // Only disconnect a connected user.
        //String tokenData = (String) request.getSession().getAttribute("token");
        String tokenData = googleSession.getToken();
        if (tokenData == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GSON.toJson("Current user not connected."));
            userSession.setUser(null);
            request.getSession(false).invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(GoogleConstant.ID_CLIENT, GoogleConstant.SECRET_CLIENT).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(
                                    tokenData, GoogleTokenResponse.class));
            // Execute HTTP GET request to revoke current token.
            System.out.println("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhh!!!!!!!!!!!!!!!!!!");
            /*HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
             .buildGetRequest(new GenericUrl(
             String.format(
             "https://accounts.google.com/o/oauth2/revoke?token=%s",
             credential.getAccessToken()))).execute();*/
            // Reset the user's session.
            request.getSession().removeAttribute("token");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Successfully disconnected."));
            googleSession.setToken(null);
            googleSession.setState(null);
            userSession.setUser(null);
        } catch (IOException e) {
            // For whatever reason, the given token was invalid.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(GSON.toJson("Failed to revoke token for given user."));
        }
        request.getSession(false).invalidate();
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
