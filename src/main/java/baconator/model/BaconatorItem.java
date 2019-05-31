package baconator.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import baconator.utils.JsonUtils;

public class BaconatorItem {
	private long start;
	private long end;
	private String duration;
	private List<String> data;
	@JsonIgnore
	private String originalData;

	public BaconatorItem() {
	}

	public BaconatorItem(long start, long end, String originalData) {
		this.start = start;
		this.end = end;
		this.originalData = originalData;
		this.data = JsonUtils.readList(originalData);
		duration = DateTimeFormatter.ofPattern("H'h' m'm' s's' SSS'ms'").withZone(ZoneId.of("GMT")).format(Instant.ofEpochMilli(end - start));
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public String getDuration() {
		return duration;
	}

	public List<String> getData() {
		return data;
	}

	public String getOriginalData() {
		return originalData;
	}
}
