package protocol;


import net.java.dev.webdav.jaxrs.methods.PROPFIND;
import net.java.dev.webdav.jaxrs.xml.elements.Error;
import net.java.dev.webdav.jaxrs.xml.elements.*;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLength;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentType;
import net.java.dev.webdav.jaxrs.xml.properties.GetLastModified;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.ws.rs.core.Response.Status.OK;
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
                        new HRef(path)
                ));

                return Response.status(200).entity(st).build();
            }

            MultiStatus st = new MultiStatus(new net.java.dev.webdav.jaxrs.xml.elements.Response(
                    new Status(Response.Status.fromStatusCode(423)),
                    new Error("Locked"),
                    null,
                    null,
                    new HRef(path)
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
    public Response propfind(@DefaultValue(DEPTH_INFINITY)
                             @HeaderParam(DEPTH) String depth,
                             @HeaderParam(HttpHeaders.CONTENT_LENGTH) int contentLength,
                             @PathParam("path") String path,
                             @Context UriInfo uriInfo,
                             @Context Providers providers,
                             @Context HttpHeaders httpHeaders,
                             InputStream entityStream)
            throws IOException {

        if (contentLength > 0) {

            MessageBodyReader<PropFind> reader = providers.getMessageBodyReader(
                    PropFind.class,
                    PropFind.class,
                    new Annotation[0],
                    MediaType.APPLICATION_XML_TYPE
            );

            PropFind entity = reader.readFrom(
                    PropFind.class,
                    PropFind.class,
                    new Annotation[0],
                    MediaType.APPLICATION_XML_TYPE,
                    httpHeaders.getRequestHeaders(),
                    entityStream
            );

            Collection<PropStat> propStats = new ArrayList<>();

            if (entity.getProp() != null) {
                for (Object property : entity.getProp().getProperties()) {
                    System.out.println(property.toString());
                }
            }

            if (entity.getAllProp() != null) {
                System.out.println(entity.getAllProp().toString());
            }

            if (entity.getPropName() != null) {
                // return supported properties
                Date lastModified = new Date(resource.lastModified());
                propStats.add(new PropStat(
                        new Prop(
                                new CreationDate(lastModified)/*,
                                new GetLastModified(lastModified),
                                new GetContentType("application/octet-stream"),
                                new GetContentLength(resource.length())*/
                        ),
                        new Status(Response.Status.fromStatusCode(200))
                ));
            }

            MultiStatus st = new MultiStatus(
                    new net.java.dev.webdav.jaxrs.xml.elements.Response(
                            new HRef(path),
                            null, // or error
                            null,
                            null,
                            propStats
                    )
            );

            return Response.status(200).entity(st).build();
        }

        return Response.status(400).build();
    }
}
