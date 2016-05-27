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

import javax.xml.bind.annotation.XmlRootElement;

/** A single keyword and the classifier / concept it is associated with. */
@XmlRootElement
public class Keyword {
	
	protected String keyword;
	protected String classifier;
	
	public Keyword() { }
	
	public Keyword(String keyword, String classifier) {
		this.keyword = keyword;
		this.classifier = classifier;
	}
	
	public String getKeyword() 				{ return keyword; }
	public void setKeyword(String keyword) 	{ this.keyword = keyword; }
	
	public String getClassifier() 					{ return classifier; }
	public void setClassifier(String classifier) 	{ this.classifier = classifier; }
}
