package demo.sensor;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("robot/battery")
public class MyResource {
	private int value = 100;
	private final double capacity = 43.2;
	private Random random = new Random();

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public SensorValue getSensorValue() {
		value -= random.nextInt(5) + 1; 
		if (value < 0) value = 0;
		return new SensorValue(capacity, value);
	}
	@GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getSensorValueCSV() {
		SensorValue sensorValue = new SensorValue();
		value -= random.nextInt(5) + 1; 
		if (value < 0) value = 0;
        return sensorValue.time + ", " + sensorValue.value;
    }
}
