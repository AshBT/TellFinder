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
package oculus.memex.util;

import java.io.Serializable;

public class Triple<T, U, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	private T first;
	private U second;
	private V third;

	public Triple(T first, U second, V third) {
		setFirst(first);
		setSecond(second);
		setThird(third);
	}

	public void set(T first, U second, V third) {
		setFirst(first);
		setSecond(second);
		setThird(third);
	}

	public void setFirst(T first) {
		this.first = first;
	}
	public void setSecond(U second) {
		this.second = second;
	}
	public void setThird(V third) {
		this.third = third;
	}


	public T getFirst() {
		return first;
	}
	public U getSecond() {
		return second;
	}
	public V getThird() {
		return third;
	}

	/**
	 * @return a meaningful hash code if the two objects
	 * return meaningful hash codes.  The hash code is the same
	 * regardless of which position each of the paired objects
	 * is stored in.  Very important for reciprocal association!
	 */
	@Override
	public int hashCode() {
		
		return(first.hashCode() ^ second.hashCode() ^ third.hashCode());
	}
}
