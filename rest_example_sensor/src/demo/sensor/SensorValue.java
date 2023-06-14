package demo.sensor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;

@XmlRootElement
public class SensorValue {
	@XmlElement
	@XmlJavaTypeAdapter(MyInstantAdapter.class)
	public Instant time;
	public double value;

	public SensorValue() {
		time = Instant.now();
		value = Math.random() * 100;
	}

	@Override
	public String toString() {
		return "SensorValue{" +
				"time=" + time +
				", value=" + value +
				'}';
	}
}
