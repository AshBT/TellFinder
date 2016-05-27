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

public class Pair<T, U> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	/**
	 * The object in the first position
	 */
	private T first;
	
	
	
	
	/**
	 * The object in the second position
	 */
	private U second;
	
	
	
	
	/**
	 * 
	 */
	public Pair(T first, U second) {
		setFirst(first);
		setSecond(second);
	}
	
	
	
	
	/**
	 * Set both members of the pair together
	 * 
	 * @param first can be null
	 * @param second can be null
	 */
	public void set(T first, U second) {
		setFirst(first);
		setSecond(second);
	}

	


	/**
	 * @param first the first to set
	 */
	public void setFirst(T first) {

		this.first = first;
	}




	/**
	 * @param second the second to set
	 */
	public void setSecond(U second) {

		this.second = second;
	}




	/**
	 * @return the first
	 */
	public T getFirst() {

		return first;
	}




	/**
	 * @return the second
	 */
	public U getSecond() {

		return second;
	}

	
	
	
	/**
	 * @return a meaningful hash code if the two objects
	 * return meaningful hash codes.  The hash code is the same
	 * regardless of which position each of the paired objects
	 * is stored in.  Very important for reciprocal association!
	 */
	@Override
	public int hashCode() {
		
		return(first.hashCode() ^ second.hashCode());
	}
}
