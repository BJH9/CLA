package edu.handong.csee.isel.cla;

import weka.core.Instances;

public class Main {
	private Instances instancesCLA;
	private Instances instancesCLAMI;

	public static void main(String[] args) {
		new Main().runner(args);
	}

	void runner(String[] args) {
		CLA cla = new CLA();
		CLAMI clami = new CLAMI();

		System.out.println("--------unlabeled dataset----------");
		System.out.println("");
		System.out.println(Util.loadArff(args[0]));
		for (int i = 0; i < 5; i++)
			System.out.println("");

		instancesCLA = Util.loadArff(args[0]);
		cla.doClustering(instancesCLA, 50.0);
		System.out.println("-----------cla result-----------");
		System.out.println("");
		System.out.println(cla.labelOnInstances(instancesCLA, 50.0, "FALSE"));
		for (int i = 0; i < 5; i++)
			System.out.println("");
		
		instancesCLAMI = Util.loadArff(args[0]);
		System.out.println("---------clami result----------");
		System.out.println("");
		clami.doClustering(instancesCLAMI, 50.0);
		clami.labelOnInstances(instancesCLAMI, 50.0, "FALSE");
		clami.selectMetrices(instancesCLAMI);
		System.out.println(clami.selectInstances(instancesCLAMI));

	}

}
