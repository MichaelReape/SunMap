package com.sunmap.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SunmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunmapApplication.class, args);
	}

	@Bean
	RestClient restClient(RestClient.Builder builder) {
		return builder
				.baseUrl("https://nominatim.openstreetmap.org")
				.defaultHeader("User-Agent", "SunMap/0.1 (example@example.com)")
				.build();
	}
}

@RestController
class GeocodeController {

	// Inject the RestClient
	private final RestClient restClient;

	// Constructor
	GeocodeController(RestClient restClient) {
		this.restClient = restClient;
	}

	// Define methods to handle geocoding requests here
	///geocode endpoint
	@GetMapping("/geocode")
	// geocode method returns a map of geocoding results
	public Map<String, Object> geocode(@RequestParam String address) {
		// Call the Nominatim API
		// list of maps
		List<Map<String, Object>> results = restClient.get()
				.uri(uri -> uri.path("/search")
						.queryParam("q", address)
						.queryParam("format", "json")
						.build())
				.retrieve()
				.body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
				});
		// Check if results are empty
		if (results == null || results.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results");
		}
		// Get the first result as the response
		Map<String, Object> first = results.get(0);
		// Return a trimmed response
		return Map.of(
				"lat", first.get("lat"),
				"lon", first.get("lon"),
				"displayName", first.get("display_name"));
	}
}
