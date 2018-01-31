package com.centrifuge.metrics.demometrics.config;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

/**
 * Creates {@link com.codahale.metrics.MetricRegistry} instance for the
 * application to add metrics and also reports metrics data to a file
 * 
 * @author Ram Mannam
 * @version 1.0
 *
 */
@Configuration
public class MetricsConfig {

	@Value("${metrics.config.csv.output.directory}")
	private String csvOutputDirectory;

	@Bean
	public MetricRegistry metricRegistry() {
		MetricRegistry metricRegistry = new MetricRegistry();

		return metricRegistry;
	}

	@PostConstruct
	public void consoleReporter() {
		
		File outputDirectory = new File(csvOutputDirectory);
		if (!outputDirectory.exists()) {
			outputDirectory.mkdir();
		}
		
		/*
		final CsvReporter reporter = CsvReporter.forRegistry(metricRegistry()).convertRatesTo(TimeUnit.MINUTES)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build(outputDirectory);
		*/
		
		
		final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry()).convertRatesTo(TimeUnit.MINUTES)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		
		/*
		final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry())
				.outputTo(LoggerFactory.getLogger(this.getClass()))
				.convertRatesTo(TimeUnit.MINUTES)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		*/

		reporter.start(1, TimeUnit.MINUTES);
		
		
		final JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry()).convertDurationsTo(TimeUnit.MINUTES) 
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		jmxReporter.start();
	}

}
