package baconator.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import baconator.model.BaconatorData;
import baconator.utils.JsonUtils;

public class DbHistory implements BaconatorHistorySource, BaconatorHistoryStore {
	private final JdbcTemplate jdbc;

	public DbHistory(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Transactional(readOnly = true)
	public BaconatorData find(String runId) {
		String json = jdbc.queryForObject("select json from baconator_history where runId = ?", String.class, runId);
		return JsonUtils.readBaconatorData(json);
	}

	@Transactional
	public void store(BaconatorData o) {
		String json = JsonUtils.writeValueAsString(o);
		jdbc.update("insert into baconator_history (runId, json) VALUES (?, ?)", o.getRunId(), json);
	}
}
