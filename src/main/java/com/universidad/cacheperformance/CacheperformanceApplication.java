
package com.universidad.cacheperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheperformanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				CacheperformanceApplication.class,
				args
		);
	}
}
