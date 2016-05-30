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
package oculus.memex.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import oculus.xdataht.model.ClassifierResult;
import oculus.xdataht.model.ClassifiersResult;
import oculus.xdataht.model.ClusterDetailsResult;
import oculus.xdataht.model.Distribution;
import oculus.xdataht.model.GraphResult;
import oculus.xdataht.model.StringList;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
@SuppressWarnings("rawtypes")
public class JAXBContextResolver implements ContextResolver<JAXBContext> {
    private JAXBContext context;
	private Class[] types = { ClusterDetailsResult.class, Distribution.class, GraphResult.class, StringList.class, ClassifiersResult.class, ClassifierResult.class };

    public JAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration
				.mapped()
				.nonStrings("clusterWeight", "size", "clusters")
				.arrays("params", "tableToColumns", "tableToClusterSets", "list", "nodes", "links", "memberDetails", "keywords").build(), types);
    }

    public JAXBContext getContext(Class<?> objectType) {
    	for (Class type : types) {
    		if (type == objectType) {
    			return context;
    		}
    	}
    	return null;
    }

}
