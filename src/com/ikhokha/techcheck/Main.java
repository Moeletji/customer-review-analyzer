package com.ikhokha.techcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ikhokha.techcheck.threads.CyclicBarrierTask;

public class Main {

	public static void main(String[] args) {
		
		int numberOfThreads = 2;
		
		//Concurrency
		CyclicBarrier barrier = new CyclicBarrier(numberOfThreads);
		System.out.println("Number of parties required to trip the barrier = " +
                barrier.getParties());
		
		// Create Tasks
		List<CyclicBarrierTask> tasks = new ArrayList<>();
		
		
		Map<String, Integer> totalResults = new HashMap<>();
				
		File docPath = new File("docs");
		File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));

		for (File commentFile : commentFiles) {
			
			CyclicBarrierTask task = new CyclicBarrierTask(commentFile, barrier);
			Thread t = new Thread(task);
			t.start();
			tasks.add(task);
			
			try {
				barrier.await(5, TimeUnit.SECONDS);
			} catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// number of parties waiting at the barrier
	        System.out.println("Number of parties waiting at the barrier "+
	                "at this point = " + barrier.getNumberWaiting());
		
	        // Collect Results
	        Map<String, Integer> fileResults = new HashMap<>();
	        for (int i = 0; i < tasks.size(); i++) {
	        	fileResults = tasks.get(i).getFinalAnswer();
	        }
	        addReportResults(fileResults, totalResults);   
		}
        
		System.out.println("RESULTS\n=======");
		totalResults.forEach((k,v) -> System.out.println(k + " : " + v));
	}
	
	/**
	 * This method adds the result counts from a source map to the target map 
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {

		if (source != null) {
			for (Map.Entry<String, Integer> entry : source.entrySet()) {
				int total = target.getOrDefault(entry.getKey(), 0);
				total = total + entry.getValue();
				target.put(entry.getKey(), total);
			}
		}	
	}
}
