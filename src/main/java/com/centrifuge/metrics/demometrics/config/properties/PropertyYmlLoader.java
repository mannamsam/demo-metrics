package com.centrifuge.metrics.demometrics.config.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class PropertyYmlLoader {
	
	public static void main(String[] args) throws Exception {
		
		//Get file from resources folder
		ClassLoader classLoader = PropertyYmlLoader.class.getClassLoader();
		
		File file = new File(classLoader.getResource("metrics-agent-base.yml").getFile());
		File overrideFile = new File(classLoader.getResource("metrics-agent-override.yml").getFile());
		
		List<File> files = new ArrayList<File>();
		files.add(file);
		//files.add(overrideFile);
		
		PropertyYmlCombiner properyYmlCombiner = new PropertyYmlCombiner();
		String mergedYmlContent = properyYmlCombiner.mergeToString(files);
		
		AgentSettings agentSettings = loadAgentSettings( mergedYmlContent );
        
		System.out.println(" \n Settings : \n" );
		System.out.println( " Agent Metrics Jmx Settings :" + agentSettings.getMetricsSettings());
        System.out.println( " Agent Kafka Settings :" + agentSettings.getKafkaSettings());
	}
	
	
	
	public static AgentSettings loadAgentSettings( File file ) {
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

			
            AgentSettings agentSettings = mapper.readValue(file, AgentSettings.class);
            //System.out.println(ReflectionToStringBuilder.toString(agentSettings,ToStringStyle.MULTI_LINE_STYLE));            
            
            return agentSettings;
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            return null;
        }
	}
	
	public static AgentSettings loadAgentSettings( String ymlContent ) {
		try {
			
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

			
            AgentSettings agentSettings = mapper.readValue(ymlContent, AgentSettings.class);
            
            return agentSettings;
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            return null;
        }
	}
}
