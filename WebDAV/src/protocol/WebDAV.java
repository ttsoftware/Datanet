package protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entities.PropFind;
import net.java.dev.webdav.jaxrs.methods.PROPFIND;
import net.java.dev.webdav.jaxrs.xml.elements.Error;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;
import net.java.dev.webdav.jaxrs.xml.elements.MultiStatus;
import net.java.dev.webdav.jaxrs.xml.elements.Status;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;
import java.io.*;

import static net.java.dev.webdav.jaxrs.Headers.DEPTH;
import static net.java.dev.webdav.jaxrs.Headers.DEPTH_INFINITY;

@Path("/{path: .*}")
public class WebDAV {

    private File resource;

    public WebDAV() {
        resource = new File("/var/www/shared");
    }

    @OPTIONS
    public Response options() {
        return Response.noContent()
                .header("Allow", "GET,DELETE,PROPFIND,OPTIONS,PUT").build();
    }

    @GET
    @Produces("application/octet-stream")
    public Response get(@PathParam("path") String path) throws IOException {

        File requestResource = new File(resource.getAbsolutePath() + "/" + path);

        if (requestResource.isFile()) {
            FileInputStream inputStream = new FileInputStream(requestResource);

            byte[] fileBytes = IOUtils.toByteArray(inputStream);

            return Response.status(200).entity(fileBytes).build();
        }

        return Response.status(404).build();
    }

    @DELETE
    @Produces("application/xml")
    public Response delete(@PathParam("path") String path) throws IOException {

        File requestResource = new File(resource.getAbsolutePath() + "/" + path);

        if (requestResource.exists()) {
            if (requestResource.delete()) {

                MultiStatus st = new MultiStatus(new net.java.dev.webdav.jaxrs.xml.elements.Response(
                        new Status(Response.Status.OK),
                        null, // or error
                        null,
                        null,
                        new HRef(requestResource.getAbsolutePath())
                ));

                return Response.status(200).entity(st).build();
            }

            MultiStatus st = new MultiStatus(new net.java.dev.webdav.jaxrs.xml.elements.Response(
                    new Status(Response.Status.fromStatusCode(423)),
                    new Error("Locked"),
                    null,
                    null,
                    new HRef(requestResource.getAbsolutePath())
            ));

            return Response.status(200).entity(st).build();
        }

        return Response.status(404).build();
    }

    @PUT
    @Produces("application/xml")
    public Response put(@PathParam("path") String path,
                        InputStream body) throws IOException {

        File newResource = new File(resource.getAbsolutePath() + "/" + path);

        File parentResource = newResource.getParentFile();
        while (!parentResource.exists()) {
            if (parentResource.mkdirs()) {
                parentResource = parentResource.getParentFile();
            }
        }

        if (!newResource.exists()) {

            byte[] inputBytes = IOUtils.toByteArray(body);

            FileOutputStream outputStream = new FileOutputStream(newResource);
            outputStream.write(inputBytes);
            outputStream.close();

            return Response.status(200).build();
        }

        return Response.status(409).build();
    }

    @PROPFIND
    @Produces("application/xml")
    public Response propfind(@DefaultValue(DEPTH_INFINITY) @HeaderParam(DEPTH) String depth,
                             @HeaderParam(HttpHeaders.CONTENT_LENGTH) int contentLength,
                             @Context UriInfo uriInfo,
                             @Context Providers providers,
                             @Context HttpHeaders httpHeaders,
                             String body)
            throws IOException {

        ObjectMapper mapper = new XmlMapper();
        try {
            PropFind propFind = mapper.readValue(body, PropFind.class);
            System.out.println(propFind.getAllprop());
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return Response.status(200).entity("<nej></nej>").build();
    }
}
