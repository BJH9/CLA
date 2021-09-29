package edu.handong.csee.isel.cla;

import weka.core.Instances;

public class Cluster2 {
	private Instances instancesByCLA;
	
	public Instances docluster(Instances instances, double percentile, String positiveLabel) {
		instancesByCLA = instances;
		
		double[] highValueOfAttribute = new double[instances.numAttributes()];
		double[] k = new double[instances.numInstances()];
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {
			k[instIdx] = 0;
			
			for(int attrIdx = 0; attrIdx < instances.numAttributes(); attrIdx++) {
				if(attrIdx == instances.classIndex())
					continue;
				if(instances.get(instIdx).value(attrIdx) > highValueOfAttribute[instIdx])
					k[instIdx]++;
			}
		}
	}

}
