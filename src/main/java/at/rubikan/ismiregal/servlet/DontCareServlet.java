package at.rubikan.ismiregal.servlet;

import at.rubikan.ismiregal.util.VoiceUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author Andreas Rubik
 */
@WebServlet("/ismiregal/*")
public class DontCareServlet extends HttpServlet {
    private static VoiceUtilities vu = new VoiceUtilities();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String speechText = pathInfo.replace("/", " ").trim();
        vu.getWav(speechText);

        File folder = new File(System.getProperty("java.io.tmpdir"));
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    System.out.println("File " + listOfFile.getName());
                } else if (listOfFile.isDirectory()) {
                    System.out.println("Directory " + listOfFile.getName());
                }
            }
        }
    }
}
