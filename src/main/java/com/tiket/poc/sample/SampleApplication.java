package com.tiket.poc.sample;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SampleApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@RestController
	@RequestMapping("/greet")
	static class GreetController {
		@GetMapping("/hello")
		Single<String> hello() {
			LOGGER.info("GreetController::hello");
			return Single.just("Hello")
					.doOnSuccess(s -> LOGGER.info("Greet hello success"))
					.subscribeOn(Schedulers.io());
		}
	}

	@RestController
	@RequestMapping("/tasks")
	static class TaskController {
		@ResponseStatus(code = HttpStatus.CREATED)
		@PostMapping("/dummy")
		Completable start() {
			LOGGER.info("TaskController::dummy");
			return Single.just("Dummy Task")
					.ignoreElement()
					.doOnComplete(() -> LOGGER.info("Task submitted..."))
					.subscribeOn(Schedulers.io());
		}
	}
}
