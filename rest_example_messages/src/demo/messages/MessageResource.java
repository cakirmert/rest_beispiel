package demo.messages;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("messages")
public class MessageResource {
	private static Map<Integer, Message> map;
	private static AtomicInteger counter = new AtomicInteger();

	static {
		map = new ConcurrentHashMap<>();
		Instant instant = Instant.now();
		map.put(1, new Message(1, "Nachricht A", instant));
		map.put(2, new Message(2, "Nachricht B", instant.plusSeconds(1)));
		map.put(3, new Message(3, "Nachricht C", instant.plusSeconds(2)));
		counter.set(3);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllMessagesAsText() {
		StringBuilder stringBuilder = new StringBuilder();
		map.values().stream()
				.sorted((m1, m2) -> -m1.getTimestamp().compareTo(m2.getTimestamp()))
				.forEach(m -> stringBuilder.append(m.toString() + "\n"));
		return stringBuilder.toString();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMessageAsText(@PathParam("id") int id) {
		Message message = map.get(id);
		if (message == null) {
			return Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(message.toString()).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAllMessagesAsHtml() {
		StringBuilder stringBuilder = new StringBuilder("<html><head><meta charset=\"UTF-8\"></head><body><table border='1'>");
		map.values().stream()
				.sorted((m1, m2) -> -m1.getTimestamp().compareTo(m2.getTimestamp()))
				.forEach(m -> stringBuilder.append("<tr><td>")
						.append(m.getId())
						.append("</td><td>")
						.append(m.getText())
						.append("</td><td>")
						.append(m.getTimestamp())
						.append("</td></tr>")
				);
		stringBuilder.append("</table></body></html>");
		return stringBuilder.toString();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_HTML)
	public Response getMessageAsHtml(@PathParam("id") int id) {
		Message message = map.get(id);
		if (message == null) {
			return Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
		StringBuilder stringBuilder = new StringBuilder("<html><head><meta charset=\"UTF-8\"></head><body>");
		stringBuilder
				.append(message.getId())
				.append("<br>")
				.append(message.getText())
				.append("<br>")
				.append(message.getTimestamp())
				.append("</body></html>");
		return Response.ok(stringBuilder.toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessagesAsJson() {
		List<Message> list = map.values().stream()
				.sorted((m1, m2) -> -m1.getTimestamp().compareTo(m2.getTimestamp()))
				.collect(Collectors.toList());
		return list;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessageAsJson(@PathParam("id") int id) {
		Message message = map.get(id);
		if (message == null) {
			return Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(message).build();
	}

	@GET
	@Path("limited")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessagesAsJsonLimited(@QueryParam("n") @DefaultValue("3") int n) {
		n = n <= 0 ? 3 : n;
		List<Message> list = map.values().stream()
				.sorted((m1, m2) -> -m1.getTimestamp().compareTo(m2.getTimestamp()))
				.limit(n)
				.collect(Collectors.toList());
		return list;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newMessage(@Context UriInfo uriInfo, Message message) {
		int id = counter.incrementAndGet();
		message.setId(id);
		message.setTimestamp(Instant.now());
		map.put(id, message);
		URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMessage(@PathParam("id") int id, Message message) {
		if (map.get(id) == null) {
			return Response.noContent().status(Response.Status.NOT_FOUND).build();
		} else {
			message.setId(id);
			message.setTimestamp(Instant.now());
			map.put(id, message);
			return Response.noContent().status(Response.Status.OK).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response deleteMessage(@PathParam("id") int id) {
		if (map.get(id) != null) {
			map.remove(id);
			return Response.noContent().status(Response.Status.OK).build();
		} else {
			return Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newMessageFromForm(@Context UriInfo uriInfo, @FormParam("text") String text) {
		int id = counter.incrementAndGet();
		Message message = new Message(id, text, Instant.now());
		map.put(id, message);
		URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		return Response.created(location).status(Response.Status.MOVED_PERMANENTLY).build();
	}
}
