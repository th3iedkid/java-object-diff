/*
 * Copyright 2012 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.th3iedkid.diff.mock;

import java.util.Map;
import java.util.TreeMap;

/** @author Daniel Bechler */
public class ObjectWithMap
{
	private Map<String, String> map;

	public ObjectWithMap()
	{
		this(new TreeMap<String, String>());
	}

	public ObjectWithMap(final Map<String, String> map)
	{
		this.map = map;
	}

	public Map<String, String> getMap()
	{
		return map;
	}

	public void setMap(final Map<String, String> map)
	{
		this.map = map;
	}
}
