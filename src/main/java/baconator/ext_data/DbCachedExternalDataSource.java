package baconator.ext_data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import baconator.model.BaconatorItem;
import io.reactivex.Flowable;

public class DbCachedExternalDataSource extends AbstractExternalDataSource {
	private static final Logger logger = LoggerFactory.getLogger(DbCachedExternalDataSource.class);
	private final DbItemCache cache;

	public DbCachedExternalDataSource(ExternalDataClient client, DbItemCache cache, int period) {
		super(client);
		this.cache = cache;
		startRetrieval(period);
	}

	private void startRetrieval(int period) {
		Flowable.interval(period, TimeUnit.SECONDS)
				.map(i -> retrieveItem())
				.retry(e -> {
					logger.error(e.getMessage(), e);
					return true;
				})
				.subscribe(cache::offer);
	}

	@Override
	protected List<BaconatorItem> retrieveItems(int howMuch) {
		List<BaconatorItem> ret = new ArrayList<>(howMuch);
		for (int i = 0; i < howMuch; i++) {
			ret.add(cache.take());
		}
		return ret;
	}
}
