package baconator.ext_data;

import java.util.List;

import baconator.model.BaconatorData;
import baconator.model.BaconatorItem;

public abstract class AbstractExternalDataSource {
	private final ExternalDataClient client;

	public AbstractExternalDataSource(ExternalDataClient client) {
		this.client = client;
	}

	protected abstract List<BaconatorItem> retrieveItems(int howMuch);

	public BaconatorData retrieveData(int howMuch) {
		long start = System.currentTimeMillis();
		List<BaconatorItem> items = retrieveItems(howMuch);
		long end = System.currentTimeMillis();
		return new BaconatorData(start, end, items);
	}

	protected BaconatorItem retrieveItem() {
		long start = System.currentTimeMillis();
		String data = client.retrieve();
		long end = System.currentTimeMillis();
		return new BaconatorItem(start, end, data);
	}
}
