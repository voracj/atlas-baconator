package baconator.history;

import baconator.model.BaconatorData;

public interface BaconatorHistorySource {
	public BaconatorData find(String runId);
}
