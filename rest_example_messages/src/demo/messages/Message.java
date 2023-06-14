package demo.messages;

import java.time.Instant;

public class Message {
	private int id;
	private String text;
	private Instant timestamp;

	public Message() {
	}

	public Message(int id, String text, Instant timestamp) {
		this.id = id;
		this.text = text;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", text='" + text + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}
