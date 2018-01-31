package com.centrifuge.metrics.demometrics.config.properties;

import java.util.Map;

public class KafkaSettings {
	private Map<String, String> brokers;

	public Map<String, String> getBrokers() {
		return brokers;
	}

	public void setBrokers(Map<String, String> brokers) {
		this.brokers = brokers;
	}

	@Override
	public String toString() {
		return "KafkaSettings [\n\t brokers=" + brokers + "\n\t ]";
	}

}
