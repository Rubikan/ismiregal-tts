package at.rubikan.ismiregal.servlet;

import at.rubikan.ismiregal.util.VoiceUtilities;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Andreas Rubik
 */
@WebServlet("/tts/*")
public class DontCareServlet extends HttpServlet {
    private static VoiceUtilities vu = new VoiceUtilities();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String speechText = pathInfo.replace("/", " ").trim();
        if (speechText.length() > 0) {
            File sound = vu.getWav(speechText);

            response.setContentType("audio/x-wav");
            response.setHeader("Content-Length", String.valueOf(sound.length()));
            IOUtils.copy(new FileInputStream(sound), response.getOutputStream());
        } else {
            response.getWriter().print("ERROR");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String speechText = request.getParameter("text").replace(" ", "/");

        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/tts/" + speechText));
    }
}
