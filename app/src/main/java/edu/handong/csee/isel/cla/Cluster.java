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
	private Double[] k;
	
	public Double[] getK() {
		return k;
	}

	public Double getKElement(int index) {
		return k[index];
	}
	
	
	public Instances loadArff(String path){
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
	
	
	public void clustering(Instances instances, double percentileCutoff) {
	
		double[] higherValue  = getHigherValueCutoffs(instances, percentileCutoff); //assign higervalues of instances
		k = new Double[instances.numInstances()]; //create array with size of number of instances
		
		for(int instIdx = 0; instIdx < instances.numInstances(); instIdx++) {//loop number of instances times
			k[instIdx] = 0.0;
			
			for(int attIdx = 0; attIdx < instances.numAttributes(); attIdx++) {//loop number of attributes times 
				if(attIdx == instances.classIndex())
					continue;
				
				if(instances.get(instIdx).value(attIdx) > higherValue[attIdx])//if the value is higher than higherValue,  
					k[instIdx]++; // k[] is assigned
				
			}
		}
	}

	
}
