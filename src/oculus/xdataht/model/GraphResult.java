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
public class GraphResult {
	private ArrayList<RestNode> nodes = new ArrayList<RestNode>();
	private ArrayList<RestLink> links = new ArrayList<RestLink>();
	
	public GraphResult() { }

	public GraphResult(ArrayList<RestNode> nodes, ArrayList<RestLink> links) {
		this.nodes = nodes;
		this.links = links;
	}

	public ArrayList<RestNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<RestNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(RestNode r) {
		nodes.add(r);
	}
	
	public ArrayList<RestLink> getLinks() { 
		return links;
	}
	
	public void setLinks(ArrayList<RestLink> links) {
		this.links = links;
	}
	
	public void addLink(RestLink l) {
		links.add(l);
	}
}
