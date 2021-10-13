package edu.handong.csee.isel.cla;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.math3.stat.StatUtils;

import com.google.common.primitives.Doubles;

import weka.core.Instances;

public class CLA {
	private Double[] k;
	private Instances instancesCLA;
	private double cutoff;

	public Double[] getK() {
		return k;
	}

	public Double getKElement(int index) {
		return k[index];
	}
	
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

	public static double[] getHigherValueCutoffs(Instances instances, double percentileCutoff) {
		// compute median values for attributes
		double[] cutoffForHigherValuesOfAttribute = new double[instances.numAttributes()];

		for (int attrIdx = 0; attrIdx < instances.numAttributes(); attrIdx++) {
			if (attrIdx == instances.classIndex())
				continue;
			cutoffForHigherValuesOfAttribute[attrIdx] = StatUtils.percentile(instances.attributeToDoubleArray(attrIdx),
					percentileCutoff);
		}
		return cutoffForHigherValuesOfAttribute;
	}

	public void clustering(Instances instances, double percentileCutoff) {

		double[] higherValue = getHigherValueCutoffs(instances, percentileCutoff); // assign higervalues of instances
		k = new Double[instances.numInstances()]; // create array with size of number of instances

		for (int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {// loop number of instances times
			k[instIdx] = 0.0;

			for (int attIdx = 0; attIdx < instances.numAttributes(); attIdx++) {// loop number of attributes times
				if (attIdx == instances.classIndex())
					continue;

				if (instances.get(instIdx).value(attIdx) > higherValue[attIdx])// if the value is higher than
																				// higherValue,
					k[instIdx]++; // k[] is assigned

			}
		}
	}

	public Instances labelOnInstances(Instances instances, double percentileCutoff, String positiveLabel) {
		
		instancesCLA = new Instances(instances); //assign instances
		cutoff = getMedian(new ArrayList<Double>(new HashSet<Double>(Arrays.asList(getK()))));// cutoff assigned median
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {//loop size of instances times
			if(getKElement(instIdx) > cutoff)//if k[] is higher than median,
				instancesCLA.instance(instIdx).setClassValue(positiveLabel);//it is labeled positive
			else
				instancesCLA.instance(instIdx).setClassValue(getNegLabel(instancesCLA, positiveLabel)); // else getneglabel
		}
		
		return instancesCLA;
	}

}
