package com.ikhokha.techcheck.classes;

import java.util.Map;

import com.ikhokha.techcheck.interfaces.Metric;

public class Shorter_Than_15 implements Metric {
	
	private String line;
	
	public Shorter_Than_15(String _line) {
		this.line = _line;
	}
	
	/**
	 * This method increments a counter by 1 for a match type on the countMap. Uninitialized keys will be set to 1
	 * @param countMap the map that keeps track of counts
	 * @param key the key for the value to increment
	 */
	private void incOccurrence(Map<String, Integer> countMap, String key) {
		
		countMap.putIfAbsent(key, 0);
		countMap.put(key, countMap.get(key) + 1);
	}

	@Override
	public void addToMetric(Map<String, Integer> countMap) {
		// TODO Auto-generated method stub
		if (this.getLine().length() < 15) {
		
			incOccurrence(countMap, "SHORTER_THAN_15");

		} 
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
