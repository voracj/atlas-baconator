package baconator.model;

import org.junit.Assert;
import org.junit.Test;

public class BaconatorItemTest {

	@Test
	public void testDurationFormat() {
		String duration = new BaconatorItem(0, 12 +  12 * 1000 + 12 * 60 * 1000 + 12 * 3600 * 1000, "[]").getDuration();
		Assert.assertEquals("12h 12m 12s 012ms", duration);
	}

}
