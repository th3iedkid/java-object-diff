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

package in.th3iedkid.diff.visitor;

import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.PropertyPath;
import in.th3iedkid.util.Assert;

/** @author Daniel Bechler */
public class PropertyVisitor implements Node.Visitor
{
	private final PropertyPath propertyPath;

	private Node node;

	public PropertyVisitor(final PropertyPath propertyPath)
	{
		Assert.notNull(propertyPath, "propertyPath");
		this.propertyPath = propertyPath;
	}

	public void accept(final Node node, final Visit visit)
	{
		final PropertyPath differencePath = node.getPropertyPath();
		if (propertyPath.isParentOf(differencePath))
		{
			if (propertyPath.equals(differencePath))
			{
				this.node = node;
				visit.stop();
			}
		}
		else
		{
			visit.dontGoDeeper();
		}
	}

	public Node getNode()
	{
		return node;
	}
}
