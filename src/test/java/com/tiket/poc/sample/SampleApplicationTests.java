package com.tiket.poc.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = "spring.mvc.async.request-timeout=10s")
public class SampleApplicationTests {

	@Autowired
	private WebTestClient client;



	@Test
	public void contextLoads() {
	}

	@Test
	public void testSayHello() {
		client.get().uri("http://localhost:8080/greet/hello")
				.exchange().expectBody(String.class).isEqualTo("Hello");
	}

	@Test
	public void testDummyTask() {
		client.post().uri("http://localhost:8080/tasks/dummy")
				.exchange().expectStatus().isCreated();
	}

	/**
	 * Test long running task (11 seconds) which spawn longer than configured
	 * {@code spring.mvc.async.request-timeout} which is 10 seconds.
	 */
	@Test
	public void testLongRunning() {
		client.mutate().responseTimeout(Duration.of(20, ChronoUnit.SECONDS)).build()
				.post().uri("http://localhost:8080/tasks/long")
				.exchange().expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
