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
package oculus.xdataht.clustering;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import oculus.xdataht.data.DataRow;
import oculus.xdataht.data.DataTable;
import oculus.xdataht.model.RestFilter;

@XmlRootElement
public class LinkFilter {
	
	public enum Condition {
		LT("less than"),
		LTE("less than or equal to"),
		GT("greater than"),
		GTE("greater than or equal to"),
		EQ("equals"),
		NEQ("not equals"),
		CONTAINS("contains");
		
		private String conditionString;
		Condition(String s) {
			conditionString = s;
		}
		public String getConditionString() { return conditionString; } 
	}
	
	public String filterAttribute;
	public Condition condition;
	public String value;
	public double numValue;
	
	public LinkFilter(String attribute, Condition condition, String value) {
		this.filterAttribute = attribute;
		this.condition = condition;
		this.value = value;
		if (attribute.equals("Cluster Size")) {
			numValue = Double.parseDouble(value);
		}
	}
	
	public LinkFilter(RestFilter rf) {
		this.filterAttribute = rf.getFilterAttribute();
		String condition = rf.getComparator();
		for (Condition c : Condition.values()) {
			if (condition.equals(c.getConditionString())) {
				this.condition = c;
				break;
			}
		}
		this.value = rf.getValue();
		if (this.filterAttribute.equals("Cluster Size")) {
			numValue = Double.parseDouble(value);
		}
	}
	
	public static boolean testNumber(double a, double b, Condition c) {
		switch (c) {
		case LT:
			return a < b;
		case LTE:
			return a <= b;
		case GT:
			return a > b;
		case GTE:
			return a >= b;
		case EQ:
			return a == b;
		case NEQ:
			return a != b;
		default:
				return false;
		}
	}
	
	public static boolean testString(String a, String b, Condition c) {
		String aStr = a != null ? a : "";
		String bStr = b != null ? b : "";
		
		switch(c) {
		case LT:
			return aStr.toLowerCase().compareTo(bStr.toLowerCase()) < 0;
		case LTE:
			return aStr.toLowerCase().compareTo(bStr.toLowerCase()) <= 0;
		case GT:
			return aStr.toLowerCase().compareTo(bStr.toLowerCase()) > 0;
		case GTE:
			return aStr.toLowerCase().compareTo(bStr.toLowerCase()) >= 0;
		case EQ:
			return aStr.toLowerCase().equals(bStr.toLowerCase());
		case NEQ:
			return !aStr.toLowerCase().equals(bStr.toLowerCase());
		case CONTAINS:
			return aStr.toLowerCase().contains(bStr.toLowerCase());
		default:
				return false;
		}
	}
	
	public boolean testCluster(DataTable table, Set<String> memberIds, String datasetName) throws NumberFormatException {
		if (filterAttribute.equals("Cluster Size")) {
			int size = memberIds.size();
			return testNumber(size, Double.parseDouble(value), condition);
		} else {
			if (table != null) {
				boolean bPasses = false;
				for (String id : memberIds ) {
					DataRow originalRow = table.getRowById(id);
					if (originalRow != null) {
						String testValue = originalRow.get(filterAttribute);
						if (value.startsWith("NULL")) value = "NULL";
						bPasses |= testString(testValue, value, condition);
						if (bPasses) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public String getWhereClause() {
		switch(condition) {
		case LT:
			return filterAttribute + "<'" + value + "'";
		case LTE:
			return filterAttribute + "<='" + value + "'";
		case GT:
			return filterAttribute + ">'" + value + "'";
		case GTE:
			return filterAttribute + ">='" + value + "'";
		case EQ:
			return filterAttribute + "='" + value + "'";
		case NEQ:
			return filterAttribute + "!='" + value + "'";
		case CONTAINS:
			return filterAttribute + " like '%" + value + "%'";
		default:
			return "true";
		}
	}
}
