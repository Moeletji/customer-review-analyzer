package com.ikhokha.techcheck.classes;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ikhokha.techcheck.interfaces.Metric;

public class Spam implements Metric {
	
	private String line;
	
	public Spam(String _line) {
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
		String regex1 = "http";
		Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(this.getLine());
		if (matcher1.find()) {

			incOccurrence(countMap, "SPAM");
		
		}
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
