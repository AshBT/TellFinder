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
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

/** A single classifier / concept and its associated keywords. */
@XmlRootElement
public class ClassifierResult {
	
	private String classifier;
	private ArrayList<String> keywords;
	
	public ClassifierResult() { }
	
	public ClassifierResult(String classifier, Collection<String> keywords) {
		this.classifier = classifier;
		this.keywords = new ArrayList<String>(keywords);
	}
	
	public String getClassifier() 					{ return this.classifier; }
	public void setClassifier(String classifier) 	{ this.classifier = classifier; }
	
	public ArrayList<String> getKeywords() 				{ return this.keywords; }
	public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }
	
}
