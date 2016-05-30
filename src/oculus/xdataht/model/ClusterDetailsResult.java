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
public class ClusterDetailsResult {
	private ArrayList<StringMap> memberDetails;
    private ArrayList<IntegerMap> wordHistograms;
	
	public ClusterDetailsResult() { }

	public ClusterDetailsResult(ArrayList<StringMap> memberDetails) {
        super();
        initialize(memberDetails,null);
	}

    public ClusterDetailsResult(ArrayList<StringMap> memberDetails, ArrayList<IntegerMap> wordHistograms) {
        super();
        initialize(memberDetails,wordHistograms);
    }

    private void initialize(ArrayList<StringMap> memberDetails, ArrayList<IntegerMap> wordHistograms) {
        this.memberDetails = memberDetails;
        this.wordHistograms = wordHistograms;
    }

	public ArrayList<StringMap> getMemberDetails() {
		return memberDetails;
	}

    public ArrayList<IntegerMap> getWordHistograms() {
        return wordHistograms;
    }

	public void setMemberDetails(ArrayList<StringMap> memberDetails) {
		this.memberDetails = memberDetails;
	}
    public void setWordHistograms(ArrayList<IntegerMap> wordHistograms) {
        this.wordHistograms = wordHistograms;
    }
}
