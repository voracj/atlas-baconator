package baconator.statistics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import baconator.model.BaconatorData;
import baconator.model.BaconatorItem;

public class BaconatorStatisticsTest {
	@Test
	public void testIpCount() {
		BaconatorStatistics stats = new BaconatorStatistics();
		stats.store("1.1.1.1");
		stats.store("1.1.1.2");
		stats.store("1.1.1.1");
		Assert.assertEquals(2, stats.getData().getIps().size());
		Assert.assertEquals(2, (int) stats.getData().getIps().get("1.1.1.1"));
		Assert.assertEquals(1, (int) stats.getData().getIps().get("1.1.1.2"));
	}

	@Test
	public void testWordCount() {
		BaconatorStatistics stats = new BaconatorStatistics();
		List<BaconatorItem> items = new ArrayList<>();
		items.add(new BaconatorItem(0, 0, "[\"aaa bbb ccc. bbb aaa.\"]"));
		stats.store(new BaconatorData(0, 0, items));
		Assert.assertEquals(3, stats.getData().getWords().size());
		Assert.assertEquals(2, (int) stats.getData().getWords().get("aaa"));
		Assert.assertEquals(2, (int) stats.getData().getWords().get("bbb"));
		Assert.assertEquals(1, (int) stats.getData().getWords().get("ccc"));
	}
}
