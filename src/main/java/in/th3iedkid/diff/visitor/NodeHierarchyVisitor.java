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
import in.th3iedkid.util.Strings;

/**
 * Prints the hierarchy of the object graph in a human-readable form.
 *
 * @author Daniel Bechler
 */
public class NodeHierarchyVisitor implements Node.Visitor
{
	public static final int UNLIMITED = -1;

	private final int maxDepth;

	@SuppressWarnings({"UnusedDeclaration"})
	public NodeHierarchyVisitor()
	{
		this(UNLIMITED);
	}

	public NodeHierarchyVisitor(final int maxDepth)
	{
		this.maxDepth = maxDepth;
	}

	public void accept(final Node node, final Visit visit)
	{
		if (maxDepth == 0)
		{
			visit.stop();
		}
		final int currentLevel = calculateDepth(node);
		if (maxDepth > 0)
		{
			if (currentLevel <= maxDepth)
			{
				print(node, currentLevel);
			}
			else
			{
				visit.dontGoDeeper();
			}
		}
		else if (maxDepth < 0)
		{
			print(node, currentLevel);
		}
	}

	protected void print(final Node node, final int level)
	{
		final String nodeAsString = node.getPropertyPath() + " ===> " + node.toString();
		final String indentedNodeString = Strings.indent(level, nodeAsString);
		print(indentedNodeString);
	}

	@SuppressWarnings({"MethodMayBeStatic"})
	protected void print(final String text)
	{
		System.out.println(text);
	}

	private static int calculateDepth(final Node node)
	{
		int count = 0;
		Node parentNode = node.getParentNode();
		while (parentNode != null)
		{
			count++;
			parentNode = parentNode.getParentNode();
		}
		return count;
	}
}
