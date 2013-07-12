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

package in.th3iedkid.diff.integration;

import in.th3iedkid.diff.ObjectDiffer;
import in.th3iedkid.diff.ObjectDifferFactory;
import in.th3iedkid.diff.mock.ObjectWithString;
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.PropertyPath;
import in.th3iedkid.diff.visitor.NodeHierarchyVisitor;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;

import static in.th3iedkid.diff.node.NodeAssertions.assertThat;

/** @author Daniel Bechler */
public class DeepDiffingCollectionItemChangeIntegrationTest
{
	@Test
	public void test_returns_full_property_graph_of_added_collection_items()
	{
		final Map<String, ObjectWithString> base = Collections.emptyMap();
		final Map<String, ObjectWithString> working = Collections.singletonMap("foo", new ObjectWithString("bar"));

		final ObjectDiffer differ = ObjectDifferFactory.getInstance();
		differ.getConfiguration().withChildrenOfAddedNodes();
		final Node node = differ.compare(working, base);

		node.visit(new NodeHierarchyVisitor());

		assertThat(node).child(PropertyPath.createBuilder()
										   .withRoot()
										   .withMapKey("foo")).hasState(Node.State.ADDED);

		assertThat(node).child(PropertyPath.createBuilder()
										   .withRoot()
										   .withMapKey("foo")
										   .withPropertyName("value")).hasState(Node.State.ADDED);
	}
}
