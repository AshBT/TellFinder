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
package oculus.memex.clustering;

public class AttributeValue {
	public String attribute;
	public String value;
	public AttributeValue(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}
	public int hashCode() {
		return attribute.hashCode()+value.hashCode();
	}
	public boolean equals(Object obj) {
		return ((AttributeValue)obj).attribute.equals(attribute) && ((AttributeValue)obj).value.equals(value);
	}

}
