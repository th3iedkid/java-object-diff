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

import in.th3iedkid.diff.ObjectDifferFactory;
import in.th3iedkid.diff.accessor.Accessor;
import in.th3iedkid.diff.mock.ObjectWithString;
import in.th3iedkid.diff.node.DefaultNode;
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.NamedPropertyElement;
import in.th3iedkid.diff.path.PropertyPath;
import org.testng.annotations.Test;

import static in.th3iedkid.diff.node.NodeAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author Daniel Bechler */
public class NodeAssertionsTest
{
	@Test
	public void testAssertThat_node_does_exist_succeeds_when_node_exists() throws Exception
	{
		final Node node = new DefaultNode(String.class);
		assertThat(node).root().doesExist();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_does_exist_fails_when_node_doesnt_exist() throws Exception
	{
		assertThat(null).root().doesExist();
	}

	@Test
	public void testAssertThat_node_does_not_exist_succeeds_when_node_doesnt_exist() throws Exception
	{
		assertThat(null).root().doesNotExist();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_does_not_exist_fails_when_node_exist() throws Exception
	{
		final Node node = new DefaultNode(String.class);
		assertThat(node).root().doesNotExist();
	}

	@Test
	public void testAssertThat_node_has_children_succeeds_when_children_are_present() throws Exception
	{
		final Node root = new DefaultNode(String.class);
		final Node child = new DefaultNode(root, mock(Accessor.class), String.class);
		root.addChild(child);
		assertThat(root).root().hasChildren();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_has_children_fails_when_no_children_are_present() throws Exception
	{
		final Node node = new DefaultNode(String.class);
		assertThat(node).root().hasChildren();
	}

	@Test
	public void testAssertThat_child_at_property_names_does_exist_succeeds_when_child_exists()
	{
		final Accessor accessor = mock(Accessor.class);
		when(accessor.getPathElement()).thenReturn(new NamedPropertyElement("value"));
		final Node node = new DefaultNode(ObjectWithString.class);
		final Node child = new DefaultNode(node, accessor, String.class);
		node.addChild(child);
		assertThat(node).child("value").doesExist();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_child_at_property_names_does_exist_fails_when_child_doesnt_exist()
	{
		assertThat(null).child("value").doesExist();
	}

	@Test
	public void testAssertThat_child_at_property_path_does_exist_succeeds_when_child_exists()
	{
		final ObjectWithString working = new ObjectWithString("foo");
		final ObjectWithString base = new ObjectWithString("bar");
		final Node node = ObjectDifferFactory.getInstance().compare(working, base);
		assertThat(node).child(PropertyPath.buildWith("value")).doesExist();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_child_at_property_path_does_exist_fails_when_child_doesnt_exist()
	{
		assertThat(null).child(PropertyPath.buildWith("value")).doesExist();
	}

	@Test
	public void testAssertThat_child_at_property_path_builder_does_exist_succeeds_when_child_exist()
	{
		final ObjectWithString working = new ObjectWithString("foo");
		final ObjectWithString base = new ObjectWithString("bar");
		final Node node = ObjectDifferFactory.getInstance().compare(working, base);
		assertThat(node).child(PropertyPath.createBuilder().withRoot().withPropertyName("value")).doesExist();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_child_at_property_path_builder_does_exist_fails_when_child_doesnt_exist()
	{
		assertThat(null).child(PropertyPath.createBuilder().withRoot().withPropertyName("value")).doesExist();
	}

	@Test
	public void testAssertThat_node_has_no_children_succeeds_when_node_has_no_children()
	{
		final Node node = new DefaultNode(String.class);
		assertThat(node).root().hasNoChildren();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_has_no_children_fails_when_node_has_children()
	{
		final Node root = new DefaultNode(String.class);
		final Node child = new DefaultNode(root, mock(Accessor.class), String.class);
		root.addChild(child);
		assertThat(root).root().hasNoChildren();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAssertThat_node_has_children_with_negative_count_throws_IllegalArgumentException()
	{
		final Node node = new DefaultNode(String.class);
		assertThat(node).root().hasChildren(-1);
	}

	@Test
	public void testAssertThat_node_has_changed_state_succeeds_when_node_has_changed()
	{
		final Node node = new DefaultNode(String.class);
		node.setState(Node.State.CHANGED);
		assertThat(node).root().hasState(Node.State.CHANGED);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_has_changed_state_fails_when_node_has_different_state()
	{
		final Node node = new DefaultNode(String.class);
		node.setState(Node.State.UNTOUCHED);
		assertThat(node).root().hasState(Node.State.CHANGED);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAssertThat_node_has_changed_state_fails_when_node_doesnt_exist()
	{
		assertThat(null).root().hasState(Node.State.CHANGED);
	}
}
