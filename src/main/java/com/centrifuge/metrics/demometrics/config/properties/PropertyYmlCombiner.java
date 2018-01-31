package com.centrifuge.metrics.demometrics.config.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class PropertyYmlCombiner {

	private static final Logger LOG = LoggerFactory.getLogger(PropertyYmlCombiner.class);

	private final Yaml yaml = new Yaml();

	public PropertyYmlCombiner() {
	}

	public Map<String, Object> overrideAndCombine(List<File> files) throws IOException {
		Map<String, Object> combineYmlContent = new LinkedHashMap<String, Object>();
		for (File file : files) {
			InputStream in = null;
			try {
				// read the file into a String
				in = new FileInputStream(file);
				final String entireFile = IOUtils.toString(in);
				
				// load the YML file
				final Map<String, Object> ymlContents = (Map<String, Object>) yaml.load(entireFile);

				// merge into results map
				combineYmlContents(combineYmlContent, ymlContents);
				LOG.info("loaded YML from " + file + ": " + ymlContents);

			} finally {
				if (in != null)
					in.close();
			}
		}
		return combineYmlContent;
	}

	private void combineYmlContents(Map<String, Object> combineYmlContents, Map<String, Object> ymlContents) {

		if (ymlContents == null)
			return;

		for (String key : ymlContents.keySet()) {

			Object yamlValue = ymlContents.get(key);
			if (yamlValue == null) {
				addToCombinedResult(combineYmlContents, key, yamlValue);
				continue;
			}

			Object existingValue = combineYmlContents.get(key);
			if (existingValue != null) {
				if (yamlValue instanceof Map) {
					if (existingValue instanceof Map) {
						combineYmlContents((Map<String, Object>) existingValue, (Map<String, Object>) yamlValue);
					} else if (existingValue instanceof String) {
						throw new IllegalArgumentException(
								"Cannot merge complex element into a simple element: " + key);
					} else {
						throw unknownValueType(key, yamlValue);
					}
				} else if (yamlValue instanceof List) {
					combineLists(combineYmlContents, key, yamlValue);

				} else if (yamlValue instanceof String || yamlValue instanceof Boolean || yamlValue instanceof Double
						|| yamlValue instanceof Integer) {
					LOG.info("overriding value of " + key + " with value " + yamlValue);
					addToCombinedResult(combineYmlContents, key, yamlValue);

				} else {
					throw unknownValueType(key, yamlValue);
				}

			} else {
				if (yamlValue instanceof Map || yamlValue instanceof List || yamlValue instanceof String
						|| yamlValue instanceof Boolean || yamlValue instanceof Integer
						|| yamlValue instanceof Double) {
					LOG.info("adding new key->value: " + key + "->" + yamlValue);
					addToCombinedResult(combineYmlContents, key, yamlValue);
				} else {
					throw unknownValueType(key, yamlValue);
				}
			}
		}
	}

	private IllegalArgumentException unknownValueType(String key, Object yamlValue) {
		final String msg = "Cannot merge element of unknown type: " + key + ": " + yamlValue.getClass().getName();
		LOG.error(msg);
		return new IllegalArgumentException(msg);
	}

	private Object addToCombinedResult(Map<String, Object> mergedResult, String key, Object yamlValue) {
		return mergedResult.put(key, yamlValue);
	}

	private void combineLists(Map<String, Object> mergedResult, String key, Object yamlValue) {
		if (!(yamlValue instanceof List && mergedResult.get(key) instanceof List)) {
			throw new IllegalArgumentException("Cannot merge a list with a non-list: " + key);
		}

		//add it to original list
		List<Object> originalList = (List<Object>) mergedResult.get(key);
		//clear original list and add overrides
		originalList.clear();
		originalList.addAll((List<Object>) yamlValue);
	}

	public String mergeToString(List<File> files) throws IOException {
		return toString(overrideAndCombine(files));
	}

	public String toString(Map<String, Object> merged) {
		return yaml.dump(merged);
	}

}
