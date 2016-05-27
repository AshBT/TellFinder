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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GraphRequest {
	private String datasetName;
	private ArrayList<RestLinkCriteria> linkCriteria;
	private ArrayList<RestFilter> filters;
	private String clustersetName;
	private ArrayList<ClusterLevel> existingClusters;
	private boolean onlyLinkedNodes;
	
	public GraphRequest() { }

	public GraphRequest(String datasetName,
			ArrayList<RestLinkCriteria> linkCriteria,
			ArrayList<RestFilter> filters, String clustersetName,
			ArrayList<ClusterLevel> existingClusters, boolean onlyLinkedNodes) {
		super();
		this.datasetName = datasetName;
		this.linkCriteria = linkCriteria;
		this.filters = filters;
		this.clustersetName = clustersetName;
		this.existingClusters = existingClusters;
		this.onlyLinkedNodes = onlyLinkedNodes;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public ArrayList<RestLinkCriteria> getLinkCriteria() {
		return linkCriteria;
	}

	public void setLinkCriteria(ArrayList<RestLinkCriteria> linkCriteria) {
		this.linkCriteria = linkCriteria;
	}

	public ArrayList<RestFilter> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<RestFilter> filters) {
		this.filters = filters;
	}

	public String getClustersetName() {
		return clustersetName;
	}

	public void setClustersetName(String clustersetName) {
		this.clustersetName = clustersetName;
	}

	public ArrayList<ClusterLevel> getExistingClusters() {
		return existingClusters;
	}

	public void setExistingClusters(ArrayList<ClusterLevel> existingClusters) {
		this.existingClusters = existingClusters;
	}

	public boolean getOnlyLinkedNodes() {
		return onlyLinkedNodes;
	}

	public void setOnlyLinkedNodes(boolean onlyLinkedNodes) {
		this.onlyLinkedNodes = onlyLinkedNodes;
	}
	
	
}
