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
public class SimpleGraphRequest {
	private String searchString;
	private String clusterType;
	private int ringCount;
	
	public SimpleGraphRequest() { }

	public SimpleGraphRequest(String searchString, String clusterType, int ringCount) {
		super();
		this.searchString = searchString;
		this.clusterType = clusterType;
		this.setRingCount(ringCount);
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getClusterType() {
		return clusterType;
	}

	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}

	public int getRingCount() {
		return ringCount;
	}

	public void setRingCount(int ringCount) {
		this.ringCount = ringCount;
	}
	
	
}
