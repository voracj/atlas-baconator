package baconator.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import baconator.ext_data.AbstractExternalDataSource;
import baconator.history.BaconatorHistorySource;
import baconator.history.BaconatorHistoryStore;
import baconator.model.BaconatorData;
import baconator.statistics.BaconatorStatistics;

@RestController
public class BaconatorController {
	private final AbstractExternalDataSource externalDataSource;
	private final BaconatorStatistics statistics;
	private final BaconatorHistoryStore historyStore;
	private final BaconatorHistorySource historySource;

	public BaconatorController(AbstractExternalDataSource externalDataSource, BaconatorHistoryStore historyStore, BaconatorHistorySource historySource) {
		this.externalDataSource = externalDataSource;
		statistics = new BaconatorStatistics();
		this.historyStore = historyStore;
		this.historySource = historySource;
	}

	@RequestMapping(value = "/give-me-bacon/{howMuch}", method = RequestMethod.GET)
	public BaconatorData get(@PathVariable(value = "howMuch") int howMuch, HttpServletRequest request) {
		BaconatorData ret = externalDataSource.retrieveData(howMuch);
		String runId = UUID.randomUUID().toString();
		ret.setRunId(runId);
		statistics.store(request.getRemoteAddr());
		statistics.store(ret);
		historyStore.store(ret);
		return ret;
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public BaconatorStatistics.Data statistics() {
		return statistics.getData();
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	private static class ResourceNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	@RequestMapping(value = "/history/{runId}", method = RequestMethod.GET)
	public BaconatorData history(@PathVariable(value = "runId") String runId) {
		try {
			return historySource.find(runId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		}
	}
}
