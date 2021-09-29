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

public class Cluster {
	private Instances instancesCLA;
	
	public Instances loadArff(String path){//instance ������ ����
		Instances instances=null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			instances = new Instances(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instances.setClassIndex(instances.numAttributes()-1);
		return instances;
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
	
	public Instances clustering(Instances instances, double percentileCutoff, String positiveLabel) {
	
		instancesCLA = new Instances(instances);
		double[] higherValue  = getHigherValueCutoffs(instances, percentileCutoff);
		Double[] k = new Double[instances.numInstances()];
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {
			k[instIdx] = 0.0;
			
			for(int attIdx = 0; attIdx < instances.numAttributes(); attIdx++) {
				if(attIdx == instances.classIndex())
					continue;
				
				if(instances.get(instIdx).value(attIdx) > higherValue[attIdx])
					k[instIdx]++;
				
			}
		}
		
		double cutoff = getMedian(new ArrayList<Double>(new HashSet<Double>(Arrays.asList(k))));
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {
			if(k[instIdx] > cutoff)
				instancesCLA.instance(instIdx).setClassValue(positiveLabel);
			else
				instancesCLA.instance(instIdx).setClassValue(getNegLabel(instancesCLA, positiveLabel));
		}
		
		return instancesCLA;
	}
	

	
}
