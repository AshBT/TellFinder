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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class TimeLog {
	private Stack<Pair<String,Long>> _timingStack = new Stack<Pair<String,Long>>();
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void pushTime(String operationName) {
		try {
			long time = System.currentTimeMillis();
			for (int i=0; i<_timingStack.size(); i++) System.out.print("\t");
			System.out.println("BEGIN: " + operationName + (_timingStack.size()==0?" " + DATE_FORMAT.format(new Date()):""));
			_timingStack.push(new Pair<String,Long>(operationName, time));
		} catch (Exception e) {e.printStackTrace();}
	}
	public long popTime() {
		return popTime(null);
	}
	public long popTime(String message) {
		long duration = 0;
		try {
			long time = System.currentTimeMillis();
			Pair<String,Long> item = _timingStack.pop();
			for (int i=0; i<_timingStack.size(); i++) System.out.print("\t");
			duration = time-item.getSecond();
			System.out.println("END: " + item.getFirst() + ": " + duration + "ms " + (message==null?"":"(" + message + ")"));
		} catch (Exception e) {e.printStackTrace();}
		return duration;
	}
}
