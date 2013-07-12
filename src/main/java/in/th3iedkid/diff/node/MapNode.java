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

package in.th3iedkid.diff.node;

import in.th3iedkid.diff.accessor.Accessor;
import in.th3iedkid.diff.accessor.MapEntryAccessor;
import in.th3iedkid.diff.accessor.exception.ItemNotIndexedException;

import java.util.LinkedList;
import java.util.List;

/** @author Daniel Bechler */
public class MapNode extends DefaultNode
{
	private final List<Object> referenceKeys = new LinkedList<Object>();

	public MapNode(final Node parentNode, final Accessor accessor, final Class<?> valueType)
	{
		super(parentNode, accessor, valueType);
	}

	public int indexKey(final Object key)
	{
		if (isIndexed(key))
		{
			return indexOf(key);
		}
		referenceKeys.add(key);
		return indexOf(key);
	}

	@SuppressWarnings({"unchecked"})
	public Accessor accessorForKey(final Object key)
	{
		if (isIndexed(key))
		{
			return new MapEntryAccessor(referenceKeys, indexOf(key));
		}
		throw new ItemNotIndexedException(key);
	}

	private boolean isIndexed(final Object key)
	{
		return referenceKeys.contains(key);
	}

	private int indexOf(final Object key)
	{
		return referenceKeys.indexOf(key);
	}

	@Override
	public final boolean isMapNode()
	{
		return true;
	}

	@Override
	public final MapNode toMapNode()
	{
		return this;
	}
}
