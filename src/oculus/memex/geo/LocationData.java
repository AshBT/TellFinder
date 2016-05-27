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
package oculus.memex.geo;

import java.io.Serializable;

public class LocationData implements Serializable {
	private static final long serialVersionUID = 1L;

	public String label;
	public float lat;
	public float lon;
	public long time;
	public LocationData(String label, float lat, float lon, long time) {
		this.label = label;
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

}
