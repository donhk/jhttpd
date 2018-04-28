package ninja.donhk;

public class Main {

    public static void main(String[] args) throws Exception {
        HttpServerDaemon httpd = new HttpServerDaemon();
        httpd.startServer();
        Thread.sleep(1000 * 60);
        httpd.stopServer();
    }
}
