package com.tiket.poc.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
}
