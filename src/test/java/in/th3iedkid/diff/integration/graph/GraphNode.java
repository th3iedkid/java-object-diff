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

package in.th3iedkid.diff.integration.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author https://github.com/oplohmann (original author)
 * @author Daniel Bechler (modifications)
 */
@SuppressWarnings({"UnusedDeclaration"})
public class GraphNode
{
	private static final Logger logger = LoggerFactory.getLogger(GraphNode.class);

	private final int id;
	private final String value;
	private final List<GraphNode> children = new LinkedList<GraphNode>();
	private final Map<String, Object> map = new HashMap<String, Object>(2);

	private GraphNode parent;
	private GraphNode directReference;

	public GraphNode(final int id, final String value)
	{
		if (id == -1)
		{
			logger.warn("Careful! Using a default value (-1) for the only field used in " +
					"equals (id) will result in indentity confusion in combination with collections.");
		}
		this.id = id;
		this.value = value;
	}

	public GraphNode(final int id)
	{
		this(id, null);
	}

	/**
	 * @see GraphNode(int, String)
	 * @deprecated You should always provide an ID.
	 */
	@Deprecated
	public GraphNode(final String value)
	{
		this(-1, value);
	}

	/**
	 * @see GraphNode(int, String)
	 * @deprecated You should always provide an ID.
	 */
	@Deprecated
	public GraphNode()
	{
		this(-1, null);
	}

	public void setDirectReference(final GraphNode directReference)
	{
		this.directReference = directReference;
	}

	public void addChild(final GraphNode child)
	{
		addChildren(child);
	}

	public void addChildren(final GraphNode... children)
	{
		this.children.addAll(asList(children));
	}

	public int getId()
	{
		return id;
	}

	public String getValue()
	{
		return value;
	}

	public GraphNode getParent()
	{
		return parent;
	}

	public void setParent(final GraphNode parent)
	{
		this.parent = parent;
	}

	public GraphNode getDirectReference()
	{
		return directReference;
	}

	public List<GraphNode> getChildren()
	{
		return children;
	}

	public Map<String, Object> getMap()
	{
		return map;
	}

	@Override
	public int hashCode()
	{
		return 1;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final GraphNode other = (GraphNode) obj;
		if (id != other.id)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("GraphNode");
		sb.append("{id=").append(id);
		sb.append(", value=").append(value);
		sb.append('}');
		return sb.toString();
	}
}
