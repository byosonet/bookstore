/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.google.access;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import com.r2r.core.util.GoogleConstant;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arturo
 */
@WebServlet(value = "/connect")
public class ConnectServlet extends HttpServlet {

    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
     * Gson object to serialize JSON responses to requests to this servlet.
     */
    private static final Gson GSON = new Gson();
    @Inject
    private GoogleSession googleSession;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setLocale(request.getLocale());

        // Only connect a user that is not already connected.
        //String tokenData = (String) request.getSession().getAttribute("token");
        String tokenData = (String) googleSession.getToken();
        if (tokenData != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Current user is already connected."));
            return;
        }
        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
        if (!request.getParameter("state").equals(googleSession.getState())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GSON.toJson("Invalid state parameter."));
            return;
        }
        // Normally the state would be a one-time use token, however in our
        // simple case, we want a user to be able to connect and disconnect
        // without reloading the page.  Thus, for demonstration, we don't
        // implement this best practice.
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        getContent(request.getInputStream(), resultStream);
        String code = new String(resultStream.toByteArray(), "UTF-8");

        try {
            // Upgrade the authorization code into an access and refresh token.
            GoogleTokenResponse tokenResponse
                    = new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                            GoogleConstant.ID_CLIENT, GoogleConstant.SECRET_CLIENT, code, "postmessage").execute();

            // You can read the Google user ID in the ID token.
            // This sample does not use the user ID.
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            String gplusId = idToken.getPayload().getSubject();

            // Store the token in the session for later use.
            googleSession.setToken(tokenResponse.toString());
            request.getSession().setAttribute("token", tokenResponse.toString());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(GSON.toJson("Successfully connected user."));

        } catch (TokenResponseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(GSON.toJson("Failed to upgrade the authorization code."));
            e.printStackTrace();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(GSON.toJson("Failed to read token data from Google. "
                    + e.getMessage()));
            e.printStackTrace();
        }
    }
    /*
     * Read the content of an InputStream.
     *
     * @param inputStream the InputStream to be read.
     * @return the content of the InputStream as a ByteArrayOutputStream.
     * @throws IOException
     */

    static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
            throws IOException {
        // Read the response into a buffered stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int readChar;
        while ((readChar = reader.read()) != -1) {
            outputStream.write(readChar);
        }
        reader.close();
    }
}
