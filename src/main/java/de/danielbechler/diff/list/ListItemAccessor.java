/*
 * Copyright 2013 Daniel Bechler
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

package de.danielbechler.diff.list;

import de.danielbechler.diff.accessor.*;
import de.danielbechler.diff.path.*;

import java.util.*;

/** @author Daniel Bechler */
public class ListItemAccessor implements Accessor
{
	private List<?> base;
	private List<?> working;
	private ListItem listItem;

	public Object get(final Object target)
	{
		if (target == base && listItem.getBasePosition() >= 0)
		{
			return base.get(listItem.getBasePosition());
		}
		if (target == working && listItem.getWorkingPosition() >= 0)
		{
			return working.get(listItem.getWorkingPosition());
		}
		throw new IllegalArgumentException("List items can only be retrieved from the instances (base and working) that the diff is based on.");
	}

	public void set(final Object target, final Object value)
	{
	}

	public void unset(final Object target)
	{
	}

	public Element getPathElement()
	{
		return null;
	}

	public Set<String> getCategories()
	{
		return null;
	}

	public boolean isIgnored()
	{
		return false;
	}

	public boolean isEqualsOnly()
	{
		return false;
	}
}
