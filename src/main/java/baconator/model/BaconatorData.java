package baconator.model;

import java.util.ArrayList;
import java.util.List;

public class BaconatorData {
	private String runId;
	private long start;
	private long end;
	private List<BaconatorItem> items;

	public BaconatorData() {
	}

	public BaconatorData(long start, long end, List<BaconatorItem> items) {
		this.start = start;
		this.end = end;
		this.items = new ArrayList<>(items);
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public List<BaconatorItem> getItems() {
		return items;
	}
}