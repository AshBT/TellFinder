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

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClustersDetailsResult {
	private HashMap<Integer, ClusterDetailsResult> memberDetails;
	
	public ClustersDetailsResult() { }

	public ClustersDetailsResult(HashMap<Integer, ClusterDetailsResult> memberDetails) {
		super();
		this.memberDetails = memberDetails;
	}

	public HashMap<Integer, ClusterDetailsResult> getMemberDetails() {
		return memberDetails;
	}

	public void setMemberDetails(HashMap<Integer, ClusterDetailsResult> memberDetails) {
		this.memberDetails = memberDetails;
	}	
}