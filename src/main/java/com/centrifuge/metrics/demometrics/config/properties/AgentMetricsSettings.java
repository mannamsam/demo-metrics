package com.centrifuge.metrics.demometrics.config.properties;

import java.util.Arrays;

public class AgentMetricsSettings {
	private int reportInterval;
	private String prefix;
	private String[] include;
	private String[] exclude;

	public int getReportInterval() {
		return reportInterval;
	}

	public void setReportInterval(int reportInterval) {
		this.reportInterval = reportInterval;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String[] getInclude() {
		return include;
	}

	public void setInclude(String[] include) {
		this.include = include;
	}

	public String[] getExclude() {
		return exclude;
	}

	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}

	@Override
	public String toString() {
		return "AgentMetricsSettings [\n\t reportInterval=" + reportInterval + ",\n\t prefix=" + prefix + ", \n\t include="
				+ Arrays.toString(include) + ",\n\t exclude=" + Arrays.toString(exclude) + "\n\t]";
	}

}
