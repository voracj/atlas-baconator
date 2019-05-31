package baconator.ext_data;

import java.util.LinkedList;
import java.util.Queue;

import baconator.model.BaconatorItem;

public class TestItemCache extends DbItemCache {
	private Queue<BaconatorItem> queue = new LinkedList<>();

	TestItemCache() {
		super(null);
	}

	@Override
	protected int getAvailableCount() {
		return 0;
	}

	@Override
	protected synchronized BaconatorItem takeInternal() {
		return queue.remove();
	}

	@Override
	protected synchronized void offerInternal(BaconatorItem o) {
		queue.offer(o);
	}
}
