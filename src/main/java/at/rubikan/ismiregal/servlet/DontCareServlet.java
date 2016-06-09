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
@WebServlet("/IstMirEgal/*")
public class DontCareServlet extends HttpServlet {
    private static VoiceUtilities vu = new VoiceUtilities();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String speechText = pathInfo.replace("/", " ").trim();
        File sound = vu.getWav(speechText);

        response.setContentType("audio/x-wav");
        response.setHeader("Content-Length", String.valueOf(sound.length()));
        IOUtils.copy(new FileInputStream(sound), response.getOutputStream());
    }
}
