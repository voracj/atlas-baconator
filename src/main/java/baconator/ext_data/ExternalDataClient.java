package baconator.ext_data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import baconator.utils.BaconatorException;

public class ExternalDataClient {
	private final HttpClient client;
	private final HttpRequest request;

	public ExternalDataClient(String url) {
		client = HttpClient.newBuilder().build();
		request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(10))
				.header("Content-Type", "application/json")
				.GET()
				.build();
	}

	public String retrieve() {
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return response.body();
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BaconatorException(e.getMessage(), e);
		}

	}
}
