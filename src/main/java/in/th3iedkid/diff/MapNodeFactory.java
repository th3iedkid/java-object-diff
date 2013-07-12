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

package in.th3iedkid.diff;

import in.th3iedkid.diff.accessor.Accessor;
import in.th3iedkid.diff.node.MapNode;
import in.th3iedkid.diff.node.Node;

@SuppressWarnings("MethodMayBeStatic")
public class MapNodeFactory
{
	public MapNode createMapNode(final Node parentNode, final Instances instances)
	{
		final Accessor sourceAccessor = instances.getSourceAccessor();
		final Class<?> type = instances.getType();
		return new MapNode(parentNode, sourceAccessor, type);
	}
}
