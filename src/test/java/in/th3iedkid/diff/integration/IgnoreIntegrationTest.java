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
import in.th3iedkid.diff.mock.ObjectWithCircularReference;
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.PropertyPath;
import in.th3iedkid.diff.visitor.PrintingVisitor;
import org.testng.annotations.Test;

import static in.th3iedkid.diff.node.NodeAssertions.assertThat;

/** @author Daniel Bechler */
public class IgnoreIntegrationTest
{
	@Test
	public void verify_that_ignore_rules_with_complex_paths_works_properly() throws Exception
	{
		final ObjectWithCircularReference obj1 = new ObjectWithCircularReference("1");
		final ObjectWithCircularReference obj2 = new ObjectWithCircularReference("2");
		final ObjectWithCircularReference obj3 = new ObjectWithCircularReference("3");
		obj1.setReference(obj2);
		obj2.setReference(obj3);

		final ObjectWithCircularReference modifiedObj1 = new ObjectWithCircularReference("1");
		final ObjectWithCircularReference modifiedObj2 = new ObjectWithCircularReference("2");
		final ObjectWithCircularReference modifiedObj3 = new ObjectWithCircularReference("4");
		modifiedObj1.setReference(modifiedObj2);
		modifiedObj2.setReference(modifiedObj3);

		final PropertyPath propertyPath = PropertyPath.buildWith("reference", "reference");

		// verify that the node can be found when it's not excluded
		final ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();
		final Node verification = objectDiffer.compare(obj1, modifiedObj1);
		verification.visit(new PrintingVisitor(obj1, modifiedObj1));
		assertThat(verification).child(propertyPath).hasState(Node.State.CHANGED).hasChildren(1);

		// verify that the node can't be found, when it's excluded
		objectDiffer.getConfiguration().withoutProperty(propertyPath);
		final Node node = objectDiffer.compare(obj1, modifiedObj1);
		node.visit(new PrintingVisitor(obj1, modifiedObj1));
		assertThat(node).child(propertyPath).doesNotExist();
	}
}
