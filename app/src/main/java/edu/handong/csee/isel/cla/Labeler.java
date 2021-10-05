package edu.handong.csee.isel.cla;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.math3.stat.StatUtils;

import com.google.common.primitives.Doubles;

import weka.core.Instances;

public class Labeler {
	private Instances instancesCLA;
	private double cutoff;
	
	public static double[] getDoublePrimitive(ArrayList<Double> values) {
		return Doubles.toArray(values);
	}
	
	static public double getPercentile(ArrayList<Double> values, double percentile) {
		return StatUtils.percentile(getDoublePrimitive(values), percentile);
	}
	
	static public double getMedian(ArrayList<Double> values) {
		return getPercentile(values, 50);
	}
	
	static public String getNegLabel(Instances instances, String positiveLabel) {
		if (instances.classAttribute().numValues() == 2) {
			int posIndex = instances.classAttribute().indexOfValue(positiveLabel);
			if (posIndex == 0)
				return instances.classAttribute().value(1);
			else
				return instances.classAttribute().value(0);
		} else {
			System.err.println("Class labels must be binary");
			System.exit(0);
		}
		return null;
	}

	public Instances label(Instances instances, double percentileCutoff, String positiveLabel) {
		
		Cluster cluster = new Cluster();
		cluster.clustering(instances, 50.0);
		instancesCLA = new Instances(instances); //assign instances
		cutoff = getMedian(new ArrayList<Double>(new HashSet<Double>(Arrays.asList(cluster.getK()))));// cutoff assigned median
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {//loop size of instances times
			if(cluster.getKElement(instIdx) > cutoff)//if k[] is higher than median,
				instancesCLA.instance(instIdx).setClassValue(positiveLabel);//it is labeled positive
			else
				instancesCLA.instance(instIdx).setClassValue(getNegLabel(instancesCLA, positiveLabel)); // else getneglabel
		}
		
		return instancesCLA;
	}

}
