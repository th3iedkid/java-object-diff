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

package in.th3iedkid.diff.integration.graph;

import in.th3iedkid.diff.Configuration;
import in.th3iedkid.diff.ObjectDiffer;
import in.th3iedkid.diff.ObjectDifferFactory;
import in.th3iedkid.diff.mock.ObjectWithNestedObject;
import in.th3iedkid.diff.node.NodeAssertions;
import in.th3iedkid.diff.node.Node;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static in.th3iedkid.diff.Configuration.CircularReferenceMatchingMode.EQUALS_METHOD;

/** @author Daniel Bechler */
public class CircularReferenceDetectionBasedOnEqualsTest
{
	private ObjectDiffer objectDiffer;

	@BeforeMethod
	public void setUp() throws Exception
	{
		final Configuration configuration = new Configuration();
		configuration.withChildrenOfAddedNodes();
		configuration.matchCircularReferencesUsing(EQUALS_METHOD);
		objectDiffer = ObjectDifferFactory.getInstance(configuration);
	}

	@Test
	public void detectsCircularReference_whenEncounteringSameObjectTwice() throws Exception
	{
		final ObjectWithNestedObject object = new ObjectWithNestedObject("foo");
		object.setObject(object);
		final Node node = objectDiffer.compare(object, null);
		NodeAssertions.assertThat(node).child("object").isCircular();
	}

	@Test
	public void detectsCircularReference_whenEncounteringDifferentButEqualObjectsTwice() throws Exception
	{
		final ObjectWithNestedObject object = new ObjectWithNestedObject("foo", new ObjectWithNestedObject("foo"));
		final Node node = objectDiffer.compare(object, null);
		NodeAssertions.assertThat(node).child("object").isCircular();
	}
}
