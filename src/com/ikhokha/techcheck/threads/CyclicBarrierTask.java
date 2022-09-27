package com.ikhokha.techcheck.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.ikhokha.techcheck.classes.Mover_Mentions;
import com.ikhokha.techcheck.classes.Questions;
import com.ikhokha.techcheck.classes.Shaker_Mentions;
import com.ikhokha.techcheck.classes.Shorter_Than_15;
import com.ikhokha.techcheck.classes.Spam;

public class CyclicBarrierTask implements Runnable{
	
	private File file;
	private final CyclicBarrier cyclicBarrier;
	Map<String, Integer> finalAnswer;
	public Map<String, Integer> resultsMap = new HashMap<>();
	public Map<String, Integer> getFinalAnswer() {
		return finalAnswer;
	}

	public CyclicBarrierTask(File file, CyclicBarrier cyclicBarrier) {
		this.file = file;
		this.cyclicBarrier = cyclicBarrier;
		System.out.println("Create Task to get content from " + file.getAbsolutePath());
	}

	@Override
	public void run() {
		try {
			finalAnswer = this.analyze();
//			System.out.println(finalAnswer.toString());
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
		
	public Map<String, Integer> analyze() {
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {

				new Shorter_Than_15(line).addToMetric(resultsMap);
				new Mover_Mentions(line).addToMetric(resultsMap);
				new Shaker_Mentions(line).addToMetric(resultsMap);
				new Questions(line).addToMetric(resultsMap);
				new Spam(line).addToMetric(resultsMap);
					
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error processing file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
		
		return resultsMap;
		
	}

}
