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

import java.util.*;

/** @author Daniel Bechler */
public class ListItemPositionFinder
{
	private final List<?> items;
	private final Map<Object, Queue<Integer>> itemPositionQueueMap = new HashMap<Object, Queue<Integer>>();
	private final Set<Integer> remainingPositions;

	public ListItemPositionFinder(final List<?> items)
	{
		this.items = items;
		if (items != null)
		{
			remainingPositions = new LinkedHashSet<Integer>(items.size());
			for (int i = 0; i < items.size(); i++)
			{
				remainingPositions.add(i);
			}
		}
		else
		{
			remainingPositions = Collections.emptySet();
		}
	}

	public int positionOf(final Object item)
	{
		if (items == null)
		{
			return -1;
		}
		final Queue<Integer> positions;
		if (itemPositionQueueMap.get(item) == null)
		{
			positions = new LinkedList<Integer>();
			int i = 0;
			for (final Object it : this.items)
			{
				if (it == null && item == null)
				{
					positions.add(i);
				}
				else if (it != null && it.equals(item))
				{
					positions.add(i);
				}
				i++;
			}
			itemPositionQueueMap.put(item, positions);
		}
		else
		{
			positions = new LinkedList<Integer>();
		}
		if (!positions.isEmpty())
		{
			final Integer position = positions.remove();
			remainingPositions.remove(position);
			return position;
		}
		return -1;
	}

	public Set<Integer> remainingPositions()
	{
		return Collections.unmodifiableSet(remainingPositions);
	}

//	public List<?> remainingItems()
//	{
//		final List<Object> remainingItems = new ArrayList<Object>(remainingPositions.size());
//		for (final int position : remainingPositions)
//		{
//			remainingItems.add(items.get(position));
//		}
//		return remainingItems;
//	}
}
