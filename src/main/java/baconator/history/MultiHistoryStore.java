package baconator.history;

import java.util.Arrays;
import java.util.List;

import baconator.model.BaconatorData;

public class MultiHistoryStore implements BaconatorHistoryStore {
	private final List<BaconatorHistoryStore> stores;

	public MultiHistoryStore(BaconatorHistoryStore... stores) {
		this.stores = Arrays.asList(stores);
	}

	public void store(BaconatorData o) {
		for (BaconatorHistoryStore store : stores) {
			store.store(o);
		}
	}

}
