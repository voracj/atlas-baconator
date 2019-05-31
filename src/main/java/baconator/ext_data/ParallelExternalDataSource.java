package baconator.ext_data;

import java.util.ArrayList;
import java.util.List;

import baconator.model.BaconatorItem;
import baconator.utils.BaconatorException;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class ParallelExternalDataSource extends AbstractExternalDataSource {
	public ParallelExternalDataSource(ExternalDataClient client) {
		super(client);
	}

	@Override
	protected List<BaconatorItem> retrieveItems(int howMuch) {
		List<BaconatorItem> ret = new ArrayList<>(howMuch);
		final Throwable[] error = new Throwable[] {null};
		Flowable.range(0, howMuch)
				.parallel()
				.runOn(Schedulers.io())
				.map(i -> retrieveItem())
				.sequential()
				.blockingSubscribe(ret::add,
						e -> error[0] = e);

		if (error[0] != null) {
			throw new BaconatorException(error[0].getMessage(), error[0]);
		}
		return ret;
	}
}