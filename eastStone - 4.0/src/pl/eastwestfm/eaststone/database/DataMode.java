package pl.eastwestfm.eaststone.database;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum DataMode {

	SQLITE("sqlite"),
	YAML("yaml"),
	JSON("json");
	
	@Getter
	private String mode;
	
	private static final Map<String, DataMode> BY_NAME = new HashMap<String, DataMode>();
	
	private DataMode(String mode) {
		this.mode = mode;
	}
	
	public static DataMode getDataMode(String datamode) {
		return BY_NAME.get(datamode.toLowerCase());
	}
	
	static {
		for(DataMode mode : values()) {
			BY_NAME.put(mode.name().toLowerCase(), mode);
		}
	}
	
}
