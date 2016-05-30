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
import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringList implements Iterable<String> {
	private ArrayList<String> list;
	
	public StringList() { }
	public StringList(ArrayList<String> list) {
		setList(list);
	}
	
	public void setList(ArrayList<String> list) { this.list = list; }
	public ArrayList<String> getList() { return list; }
	
	@Override
	public Iterator<String> iterator() {
		return list.iterator();
	}

}
