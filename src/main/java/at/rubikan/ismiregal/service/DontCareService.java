package at.rubikan.ismiregal.service;

import at.rubikan.ismiregal.util.VoiceUtilities;
import com.google.gson.Gson;
import marytts.exceptions.MaryConfigurationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Rubikan
 */
@Path("/services")
public class DontCareService {
    private static VoiceUtilities vu = new VoiceUtilities();

    @GET
    @Path("listVoices")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listVoices() throws MaryConfigurationException {
        List<String> voices = vu.getVoices();
        String json = new Gson().toJson(voices);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("getVoiceAsOctetStream")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile() {
        throw new NotImplementedException();
        /*File file = new File("");
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();*/
    }
}
