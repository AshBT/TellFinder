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
public class LocationTimeVolumeResult {
	String location;
	float lat;
	float lon;
	ArrayList<TimeVolumeResult> timeseries;
	
	public LocationTimeVolumeResult() { }

	public LocationTimeVolumeResult(String location, float lat, float lon,
			ArrayList<TimeVolumeResult> timeseries) {
		super();
		this.location = location;
		this.lat = lat;
		this.lon = lon;
		this.timeseries = timeseries;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public ArrayList<TimeVolumeResult> getTimeseries() {
		return timeseries;
	}

	public void setTimeseries(ArrayList<TimeVolumeResult> timeseries) {
		this.timeseries = timeseries;
	}


}
