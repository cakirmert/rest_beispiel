// REST example
// http://localhost:8080/demo/myresource/

import demo.sensor.MyApplication;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.glassfish").setLevel(Level.SEVERE);

		URI baseUri = new URI("http://0.0.0.0:8080/demo");
		ResourceConfig config = ResourceConfig.forApplicationClass(MyApplication.class);
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

		System.out.println("http://localhost:8080/demo/");
		System.out.println("ENTER stoppt den Server.");
		System.in.read();
		server.shutdownNow();
	}
}
