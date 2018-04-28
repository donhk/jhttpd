package ninja.donhk;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author frealvar
 * @version 1
 * @since 2018
 */
public class HttpServerDaemon {

    private int port = 9080;
    private String host = "localhost";
    private String address = "http://" + host + ":" + port;
    private ExecutorService httpServerExecutor = null;
    private HttpServer server = null;

    public void startServer() throws IOException {
        System.err.println("Creating instance of HttpServer " + address);
        if (httpServerExecutor == null) {
            httpServerExecutor = Executors.newSingleThreadExecutor(new HttpThreadFactory());
            server = HttpServer.create(new InetSocketAddress(host, port), 0);
            server.setExecutor(httpServerExecutor);

            HttpDispatcher httpDispatcher = new HttpDispatcher();

            System.err.println("Binding contexts");
            //bind web contexts
            for (String ctx : httpDispatcher.getContexts().keySet()) {
                server.createContext(ctx, httpDispatcher.getContexts().get(ctx));
                System.err.println("new Context: " + ctx);
            }

            System.err.println("Starting server at " + address);
            server.start();
            System.out.println("web server started at: " + address);
        } else {
            System.out.println("The web server is already running at: " + address);
        }

    }

    public void stopServer() {
        System.err.println("Stopping HttpServer " + address);
        if (server != null) {
            server.stop(0);
        }
        httpServerExecutor.shutdownNow();
    }

    class HttpThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            return new Thread(r, "httpd");
        }
    }
}
