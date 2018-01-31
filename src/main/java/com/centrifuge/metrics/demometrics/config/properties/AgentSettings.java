package com.centrifuge.metrics.demometrics.config.properties;

public class AgentSettings {
	
	private AgentMetricsSettings metrics;
	private KafkaSettings kafka;
	
	public AgentMetricsSettings getMetricsSettings() {
		return metrics;
	}
	public void setMetrics(AgentMetricsSettings metrics) {
		this.metrics = metrics;
	}
	public KafkaSettings getKafkaSettings() {
		return kafka;
	}
	public void setKafka(KafkaSettings kafka) {
		this.kafka = kafka;
	}
	
}
