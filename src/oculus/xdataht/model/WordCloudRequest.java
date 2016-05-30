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
public class WordCloudRequest {
	private int width;
	private int height;
	ArrayList<WordCount> wordCounts;
	
	public WordCloudRequest() { }

	public WordCloudRequest(int width, int height,
			ArrayList<WordCount> wordCounts) {
		super();
		this.width = width;
		this.height = height;
		this.wordCounts = wordCounts;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<WordCount> getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(ArrayList<WordCount> wordCounts) {
		this.wordCounts = wordCounts;
	}
}
