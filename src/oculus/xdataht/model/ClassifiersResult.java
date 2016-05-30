/**
Copyright 2016 Uncharted Software Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package oculus.xdataht.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;

/** A set of {@link ClassifierResult}s. */
@XmlRootElement
public class ClassifiersResult {
	
	private ArrayList<ClassifierResult> classifiers;
	
	public ClassifiersResult() { }

	public ClassifiersResult(HashMap<String,HashSet<String>> classifiers) {
		super();
		this.classifiers = new ArrayList<ClassifierResult>();
		for (Entry<String, HashSet<String>>  entry : classifiers.entrySet()) {
			this.classifiers.add(new ClassifierResult(entry.getKey(), entry.getValue()));
		}
	}	
		
	public ArrayList<ClassifierResult> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(ArrayList<ClassifierResult> classifiers) {
		this.classifiers = classifiers;
	}		
}
