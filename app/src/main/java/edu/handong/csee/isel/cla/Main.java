package edu.handong.csee.isel.cla;

import weka.core.Instances;

public class Main {
	private Instances instancesCL;
	public static void main(String[] args) {
		new Main().runner(args);
	}
	
	void runner(String[] args) {
		Cluster cluster = new Cluster();
		Labeler labeler = new Labeler();
		
		System.out.println(cluster.loadArff(args[0]));
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		instancesCL = cluster.loadArff(args[0]);
		System.out.println(labeler.label(instancesCL, 50.0, "FALSE"));
		
	}
	
	

}
