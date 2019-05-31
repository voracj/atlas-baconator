package baconator.utils;

public class BaconatorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaconatorException(String msg, Throwable e) {
		super(msg, e);
	}
}
