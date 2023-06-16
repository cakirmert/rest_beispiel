package demo.sensor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;

@XmlRootElement(name = "battery")
public class SensorValue {
	@XmlElement
	@XmlJavaTypeAdapter(MyInstantAdapter.class)
	public Instant time;
	@XmlElement
	public double value;
	@XmlElement
	public double capacity;

	public SensorValue() {
		time = Instant.now();
		value = 100;
		capacity = 0;
	}

	public SensorValue(double capacity, double value) {
		this();
		this.capacity = capacity;
		this.value = value;
	}
}
