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

package de.danielbechler.diff.integration.issues.issue65;

import de.danielbechler.diff.node.*;
import de.danielbechler.diff.path.*;
import de.danielbechler.diff.visitor.*;

import java.util.*;

/** @author Daniel Bechler */
public class ProductComparisonResult<T extends Product> implements ComparisonResult<T>
{
	private Map<String, String> changeSet;
	private final Node differenceNode;
	private final T newObject;
	private final T oldObject;

	public ProductComparisonResult(final T oldObj, final T newObj, final Node resultNode)
	{
		oldObject = oldObj;
		newObject = newObj;
		differenceNode = resultNode;
		initChangeSet();
	}

	public Map<String, String> getChangeSet()
	{
		return changeSet;
	}

	public Node getDifferenceNode()
	{
		return differenceNode;
	}

	public Node.State getDifferenceState()
	{
		return differenceNode.getState();
	}

	public T getNewObject()
	{
		return newObject;
	}

	public T getOldObject()
	{
		return oldObject;
	}

	private void initChangeSet()
	{
		final ToMapPrintingVisitor visitor = new ToMapPrintingVisitor(newObject, oldObject);
		differenceNode.visit(visitor);
		differenceNode.visitChildren(visitor);
		final Map<PropertyPath, String> visitorMap = visitor.getMessages();

		changeSet = new HashMap<String, String>(visitorMap.size());
		for (final Map.Entry<PropertyPath, String> entry : visitorMap.entrySet())
		{
			changeSet.put(entry.getKey().toString(), entry.getValue());
		}
	}
}
