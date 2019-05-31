package baconator.history;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import baconator.model.BaconatorData;
import baconator.utils.JsonUtils;

public class KafkaHistory implements BaconatorHistoryStore {
	private final Producer<String, String> producer;
	private final String topic;

	public KafkaHistory(Properties props, String topic) {
		producer = new KafkaProducer<>(props);
		this.topic = topic;
	}

	public void store(BaconatorData o) {
		String json = JsonUtils.writeValueAsString(o);
		producer.send(new ProducerRecord<String, String>(topic, o.getRunId(), json));
	}
}
