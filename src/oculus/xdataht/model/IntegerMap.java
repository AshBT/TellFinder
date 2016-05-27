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

import java.util.HashMap;

public class IntegerMap {
    private HashMap<String,Integer> map;

    public IntegerMap() { }
    public IntegerMap(HashMap<String,Integer> map) {
        setmap(map);
    }

    public void setmap(HashMap<String,Integer> map) { this.map = map; }
    public HashMap<String,Integer> getmap() { return map; }

    public void put(String key, Integer value) {
        if (map == null) {
            map = new HashMap<String,Integer>();
        }
        map.put(key, value);
    }

    public Integer get(String key) {
        if (map == null) {
            return null;
        } else {
            return map.get(key);
        }
    }
}
