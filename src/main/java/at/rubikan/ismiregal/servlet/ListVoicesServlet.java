package at.rubikan.ismiregal.servlet;

import at.rubikan.ismiregal.util.VoiceUtilities;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Andreas Rubik
 */
@WebServlet("/services/listVoices")
public class ListVoicesServlet extends HttpServlet {
    private static VoiceUtilities vu = new VoiceUtilities();
    private static Logger log = Logger.getLogger(ListVoicesServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<String> voices = vu.getVoices();
            log.trace(voices);
            String json = new Gson().toJson(voices);

            response.setContentType("application/json");
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("Error getting voices!", e);
            response.getWriter().print("ERROR");
        }
    }
}
