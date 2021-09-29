package edu.handong.csee.isel.cla;

import weka.core.Instances;

public class Main {
	private Instances instancesCLA;
	public static void main(String[] args) {
		new Main().runner(args);
	}
	
	void runner(String[] args) {
		Cluster cluster = new Cluster();
		
		System.out.println(cluster.loadArff(args[0]));
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		instancesCLA = cluster.loadArff(args[0]);
		System.out.println(cluster.clustering(instancesCLA, 50.0, "FALSE"));
	}
	
	

}
