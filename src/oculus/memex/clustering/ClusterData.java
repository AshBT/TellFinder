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

import java.util.Date;
import java.util.HashMap;

public class ClusterData {
	public int adcount = 0;
	public HashMap<String,Integer> phonelist = new HashMap<String,Integer>();
	public HashMap<String,Integer> emaillist = new HashMap<String,Integer>();
	public HashMap<String,Integer> weblist = new HashMap<String,Integer>();
	public HashMap<String,Integer> namelist = new HashMap<String,Integer>();
	public HashMap<String,Integer> ethnicitylist = new HashMap<String,Integer>();
	public HashMap<String,Integer> locationlist = new HashMap<String,Integer>();
	public HashMap<String,Integer> sourcelist = new HashMap<String,Integer>();
	public HashMap<String,HashMap<String,Integer>> keywordlist = new HashMap<String,HashMap<String,Integer>>();
	public HashMap<Long,Integer> timeseries= new HashMap<Long,Integer>();
	public Date latestAd = new Date(0);
}
