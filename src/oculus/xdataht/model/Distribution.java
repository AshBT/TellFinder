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

@XmlRootElement
public class Distribution {
	private int size;
	private int clusters;
	
	public Distribution() { }
	public Distribution(int size, int clusters) {
		setSize(size);
		setClusters(clusters);
	}
	
	public void setSize(int size) { this.size = size; }
	public int getSize() { return size; }
	
	public void setClusters(int clusters) { this.clusters = clusters; }
	public int getClusters() { return clusters; } 
}
