package baconator.ext_data;

import java.util.ArrayList;
import java.util.List;

import baconator.model.BaconatorItem;

public class SequentialExternalDataSource extends AbstractExternalDataSource {
	public SequentialExternalDataSource(ExternalDataClient client) {
		super(client);
	}

	@Override
	protected List<BaconatorItem> retrieveItems(int howMuch) {
		List<BaconatorItem> ret = new ArrayList<>(howMuch);
		for (int i = 0; i < howMuch; i++) {
			ret.add(retrieveItem());
		}
		return ret;
	}
}
