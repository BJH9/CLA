package edu.handong.csee.isel.cla;

import weka.core.Instances;

public class Main {
	private Instances instancesCLA;
	public static void main(String[] args) {
		new Main().runner(args);
	}
	
	void runner(String[] args) {
		CLA cla = new CLA();
		
		System.out.println(Util.loadArff(args[0]));
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		instancesCLA = Util.loadArff(args[0]);
		cla.clustering(instancesCLA, 50.0);
		System.out.println(cla.label(instancesCLA, 50.0, "FALSE"));
		
	}
	
	

}
