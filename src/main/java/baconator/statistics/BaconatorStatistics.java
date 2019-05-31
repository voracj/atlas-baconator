package baconator.statistics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import baconator.model.BaconatorData;
import baconator.model.BaconatorItem;

public class BaconatorStatistics {
	public class Data {
		private final ConcurrentMap<String, Integer> ips = new ConcurrentHashMap<>();
		private final ConcurrentMap<String, Integer> words = new ConcurrentHashMap<>();

		public ConcurrentMap<String, Integer> getIps() {
			return ips;
		}

		public ConcurrentMap<String, Integer> getWords() {
			return words;
		}
	}

	private final Data data = this.new Data();

	public void store(String ip) {
		data.ips.compute(ip, BaconatorStatistics::inc);
	}

	public void store(BaconatorData ret) {
		for (BaconatorItem item : ret.getItems()) {
			for (String itemData : item.getData()) {
				for (String word : itemData.split("\\W+")) {
					data.words.compute(word, BaconatorStatistics::inc);
				}
			}
		}
	}

	private static Integer inc(String ignorekey, Integer current) {
		if (current == null) {
			return 1;
		} else {
			return current + 1;
		}
	}

	public Data getData() {
		return data;
	}
}
