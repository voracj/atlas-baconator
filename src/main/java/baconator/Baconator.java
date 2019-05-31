package baconator;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import baconator.ext_data.AbstractExternalDataSource;
import baconator.ext_data.DbCachedExternalDataSource;
import baconator.ext_data.DbItemCache;
import baconator.ext_data.ExternalDataClient;
import baconator.history.BaconatorHistorySource;
import baconator.history.BaconatorHistoryStore;
import baconator.history.DbHistory;
import baconator.history.KafkaHistory;
import baconator.history.MultiHistoryStore;

@SpringBootApplication
public class Baconator extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Baconator.class, args);
	}

	@Bean
	public ExternalDataClient baconatorClient(@Value("${baconipsum.url}") String url) {
		return new ExternalDataClient(url);
	}

	@Bean
	public DbItemCache dbItemCache(JdbcTemplate jdbc) {
		return new DbItemCache(jdbc);
	}

	@Bean
	public AbstractExternalDataSource externalDataSource(ExternalDataClient client, DbItemCache cache, @Value("${baconipsum.period_s}") int period) {
		return new DbCachedExternalDataSource(client, cache, period);
	}

	@Bean
	public BaconatorHistoryStore baconatorHistoryStore(JdbcTemplate jdbc, @Value("${kafka_topic}") String topic, ApplicationContext applicationContext) {
		return new MultiHistoryStore(new DbHistory(jdbc), new KafkaHistory(getKafkaProperties(), topic));
	}

	@Bean
	@ConfigurationProperties(prefix = "kafka")
	public Properties getKafkaProperties() {
		return new Properties();
	}

	@Bean
	public BaconatorHistorySource baconatorHistorySource(JdbcTemplate jdbc) {
		return new DbHistory(jdbc);
	}
}
