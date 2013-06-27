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

import de.danielbechler.diff.*;
import de.danielbechler.diff.node.*;

import java.util.*;

/** @author Daniel Bechler */
public class ListDiffer implements Differ<ListNode>
{
	private final DifferDelegator differDelegator;
	private final NodeInspector nodeInspector;
	private ListNodeFactory nodeFactory = new ListNodeFactory();

	public ListDiffer(final DifferDelegator differDelegator, final NodeInspector nodeInspector)
	{
		this.differDelegator = differDelegator;
		this.nodeInspector = nodeInspector;
	}

	public ListNode compare(final Node parentNode, final Instances instances)
	{
		assertThatInstancesHaveListType(instances);

		final ListNode listNode = nodeFactory.createListNode(parentNode, instances);

		if (nodeInspector.isIgnored(listNode))
		{
			listNode.setState(Node.State.IGNORED);
		}
		else if (nodeInspector.isEqualsOnly(listNode))
		{
			if (instances.areEqual())
			{
				listNode.setState(Node.State.UNTOUCHED);
			}
			else
			{
				determineListItems(instances, listNode);
				listNode.setState(Node.State.CHANGED);
			}
		}
		else if (instances.hasBeenAdded())
		{
			determineListItems(instances, listNode);
			listNode.setState(Node.State.ADDED);

		}
		else if (instances.hasBeenRemoved())
		{
			determineListItems(instances, listNode);
			listNode.setState(Node.State.REMOVED);
		}
		else if (instances.areSame())
		{
			listNode.setState(Node.State.UNTOUCHED);
		}
		else
		{
			determineListItems(instances, listNode);
			final List<ListItem> listItems = listNode.getItems();
			for (ListItem listItem : listItems)
			{

				differDelegator.delegate(parentNode, instances);
			}
		}

		return listNode;
	}

	private static void determineListItems(final Instances instances, final ListNode listNode)
	{
		final List<?> base = instances.getBase(List.class);
		final List<?> working = instances.getWorking(List.class);
		final ListItemPositionFinder workingPositionFinder = new ListItemPositionFinder(working);
		if (base != null)
		{
			int basePosition = 0;
			for (final Object baseItem : base)
			{
				final int workingPosition = workingPositionFinder.positionOf(baseItem);
				Object workingItem = null;
				if (workingPosition > -1)
				{
					workingItem = working.get(workingPosition);
				}
				listNode.addItem(baseItem, workingItem, basePosition, workingPosition);
				basePosition++;
			}
		}
		if (working != null)
		{
			final Set<Integer> workingPositions = workingPositionFinder.remainingPositions();
			for (final int workingPosition : workingPositions)
			{
				final Object workingItem = working.get(workingPosition);
				listNode.addItem(null, workingItem, -1, workingPosition);
			}
		}
	}

	private static void assertThatInstancesHaveListType(final Instances instances)
	{
		if (!List.class.isAssignableFrom(instances.getType()))
		{
			final String typeName = instances.getType().toString();
			final String message = "Can only handle instances of java.util.List. Got: " + typeName;
			throw new IllegalArgumentException(message);
		}
	}

	public void setNodeFactory(final ListNodeFactory nodeFactory)
	{
		this.nodeFactory = nodeFactory;
	}
}
