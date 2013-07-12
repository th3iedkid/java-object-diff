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

package in.th3iedkid.diff.accessor;

import in.th3iedkid.diff.path.Element;
import in.th3iedkid.diff.path.MapElement;
import in.th3iedkid.util.Assert;

import java.util.List;
import java.util.Map;

/** @author Daniel Bechler */
public final class MapEntryAccessor extends AbstractAccessor
{
	private final List<?> referenceKeys;
	private final int index;

	public MapEntryAccessor(final List<?> referenceKeys, final int index)
	{
		Assert.notNull(referenceKeys, "referenceKeys");
		if (index < 0 || index > referenceKeys.size() - 1)
		{
			throw new IndexOutOfBoundsException("Index " + index + " is not within the valid range of the given List");
		}
		this.referenceKeys = referenceKeys;
		this.index = index;
	}

	public Element getPathElement()
	{
		return new MapElement(getReferenceKey());
	}

	public void set(final Object target, final Object value)
	{
		final Map<Object, Object> targetMap = objectToMap(target);
		if (targetMap != null)
		{
			targetMap.put(getReferenceKey(), value);
		}
	}

	public Object get(final Object target)
	{
		final Map<?, ?> targetMap = objectToMap(target);
		if (targetMap != null)
		{
			return targetMap.get(getReferenceKey());
		}
		return null;
	}

	private static Map<Object, Object> objectToMap(final Object object)
	{
		if (object == null)
		{
			return null;
		}
		if (object instanceof Map)
		{
			//noinspection unchecked
			return (Map<Object, Object>) object;
		}
		throw new IllegalArgumentException(object.getClass().toString());
	}

	private Object getReferenceKey()
	{
		return referenceKeys.get(index);
	}

	public void unset(final Object target)
	{
		final Map<?, ?> targetMap = objectToMap(target);
		if (targetMap != null)
		{
			targetMap.remove(getReferenceKey());
		}
	}

	@Override
	public String toString()
	{
		return "map key " + getPathElement();
	}

	public Object getKey(final Map<?, ?> target)
	{
		final Map<Object, Object> map = objectToMap(target);
		if (map == null)
		{
			return null;
		}
		final Object referenceKey = getReferenceKey();
		for (final Object key : map.keySet())
		{
			if (key == referenceKey || key.equals(referenceKey))
			{
				return key;
			}
		}
		return null;
	}
}
