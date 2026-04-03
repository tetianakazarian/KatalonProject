package config

import groovy.json.JsonSlurper

class ConfigLoader {

	private static Map customConfig
	private static String configPath
	
	ConfigLoader(String configPath) {
		this.configPath = configPath
	}

	static Map load() {
		if (customConfig != null) {
			return customConfig
		}

		File file = new File(configPath)

		if (!file.exists()) {
			throw new RuntimeException("Config file not found: ${configPath}")
		}

		customConfig = new JsonSlurper().parse(file) as Map
		return customConfig
	}
}