package server;
import file.KascadeFile;
import file.parser.KascadeParser;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

@Path("/{trackhash}")
public class PeerServlet {

    @GET
    @Produces("application/octet-stream")
    public Response responseBlock(@PathParam("trackhash") String trackhash,
                                @HeaderParam("Range") String range)
            throws IOException {

        String[] ranges = range.substring(6).split("-");
        int rangeMin = Integer.parseInt(ranges[0]);
        int rangeMax = Integer.parseInt(ranges[1]);

        File[] files = new File("/var/www/shared/hashes").listFiles();

        for (File file : files) {
            if (file.isDirectory() && file.getName().equals(trackhash)) {

                KascadeParser kascadeParser = new KascadeParser("/var/www/shared/kascades");
                ArrayList<KascadeFile> kascadeFiles = kascadeParser.getFiles();

                for (KascadeFile kascadeFile : kascadeFiles) {
                    if (kascadeFile.getTrackhash().equals(file.getName())) {
                        int blocksize = kascadeFile.getBlocksize();

                        if (blocksize == (rangeMax - rangeMin)+1) {
                            int blockIndex = rangeMin / blocksize;
                            String blockFilename = kascadeFile.getBlockhashes()[blockIndex];

                            File blockFile = new File("/var/www/shared/hashes/" + file.getName() + "/" + blockFilename);
                            if (blockFile.exists()) {

                                FileInputStream inputStream = new FileInputStream(blockFile);
                                return Response.status(206).entity(IOUtils.toByteArray(inputStream)).build();
                            }
                        }
                    }
                }
            }
        }

        return Response.status(404).entity("404 - file not found.").build();
    }
}
