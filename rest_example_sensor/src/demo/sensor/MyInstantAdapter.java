package demo.sensor;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Instant;

public class MyInstantAdapter extends XmlAdapter<String, Instant> {
	@Override
	public Instant unmarshal(String s) throws Exception {
		return s != null ? Instant.parse(s) : null;
	}

	@Override
	public String marshal(Instant instant) throws Exception {
		return instant != null ? instant.toString() : null;
	}
}
