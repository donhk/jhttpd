package ninja.donhk;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author frealvar
 * @version 1
 * @since 2018
 */
public class HttpDispatcher {
    private Map<String, HttpHandler> contexts = new HashMap<>();

    public HttpDispatcher() {
        contexts.put("/", new Html());
        contexts.put("/xml", new Xml());
        contexts.put("/json", new Json());
    }

    public Map<String, HttpHandler> getContexts() {
        return contexts;
    }

    /**
     * Main page where the main report is shown
     */
    private class Html implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            //merge content in layout
            String response = String.join(System.lineSeparator()
                    , "<html>"
                    , "<header>"
                    , "	<style>"
                    , "	   h3 {color:red;}"
                    , "	   p {color:blue;}"
                    , "	</style>"
                    , "	<title>This is title</title>"
                    , "</header>"
                    , "<body>"
                    , "<h3>Hello world</h3><p>" + LocalDateTime.now() + "</p>"
                    , "</body>"
                    , "</html>"
            );

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * Draft of xml response
     */
    private class Xml implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<element>Hello from Java, I come from a world you can't imagine</element>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * Draft of json response
     */
    private class Json implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{ \"day\":\"Friday\", \"stage\":\"Morining\", \"time\":\"" + LocalDateTime.now() + "\"};";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
