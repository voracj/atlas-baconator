package baconator.ext_data;

import java.util.Map;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import baconator.model.BaconatorItem;
import baconator.utils.BaconatorException;

public class DbItemCache {
	private static final Logger logger = LoggerFactory.getLogger(DbItemCache.class);

	private final Semaphore semaphore;
	private final JdbcTemplate jdbc;

	public DbItemCache(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
		semaphore = new Semaphore(getAvailableCount());
	}

	protected int getAvailableCount() {
		Integer ret = jdbc.queryForObject("select count(*) from baconator_cache", Integer.class);
		logger.info("Already available items in cache: {}", ret);
		return ret;
	}

	@Transactional
	public BaconatorItem take() {
		try {
			logger.info("Available items before take: {}", semaphore.availablePermits());
			semaphore.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BaconatorException(e.getMessage(), e);
		}
		try {
			return takeInternal();
		} catch (Exception e) {
			//rollback semaphore as well
			semaphore.release();
			throw e;
		}
	}

	@Transactional
	public void offer(BaconatorItem o) {
		offerInternal(o);
		semaphore.release();
		logger.info("Available items after offer: {}", semaphore.availablePermits());
	}

	protected synchronized BaconatorItem takeInternal() {
		Map<String, Object> row = jdbc.queryForObject("select ID, START, END, ORIGINALDATA from baconator_cache order by id limit 1 for update", new ColumnMapRowMapper());
		jdbc.update("delete from baconator_cache where id = ?", row.get("ID"));
		return new BaconatorItem((Long) row.get("START"), (Long) row.get("END"), (String) row.get("ORIGINALDATA"));
	}

	protected synchronized void offerInternal(BaconatorItem o) {
		jdbc.update("insert into baconator_cache (start, end, originalData) VALUES (?, ?, ?)", o.getStart(), o.getEnd(), o.getOriginalData());
	}

}