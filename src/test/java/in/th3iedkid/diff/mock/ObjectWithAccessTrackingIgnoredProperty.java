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

import in.th3iedkid.diff.annotation.ObjectDiffProperty;

/** @author Daniel Bechler */
@SuppressWarnings({"MethodMayBeStatic", "UnusedDeclaration"})
public class ObjectWithAccessTrackingIgnoredProperty
{
	private boolean value;
	public boolean accessed;

	@ObjectDiffProperty(ignore = true)
	public boolean getValue()
	{
		this.accessed = true;
		return this.value;
	}

	public void setValue(final boolean value)
	{
		this.value = value;
		this.accessed = true;
	}
}
