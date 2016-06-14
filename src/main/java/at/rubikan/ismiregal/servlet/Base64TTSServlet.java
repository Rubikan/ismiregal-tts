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
import java.util.Base64;

/**
 * @author Andreas Rubik
 */
@WebServlet("/services/base64tts/*")
public class Base64TTSServlet extends HttpServlet {
    private static VoiceUtilities vu = new VoiceUtilities();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String speechText = pathInfo.replace("/", " ").trim();
            if (speechText.length() > 0) {
                File sound = vu.getWav(speechText);
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(sound));
                String base64 = Base64.getEncoder().encodeToString(bytes);
                response.getWriter().print(base64);
            } else {
                response.getWriter().print("ERROR");
            }
        } catch (Exception e) {
            response.getWriter().print("ERROR");
        }
    }
}
