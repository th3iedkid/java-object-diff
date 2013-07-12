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

import in.th3iedkid.diff.ObjectDiffer;
import in.th3iedkid.diff.ObjectDifferFactory;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/** @author Daniel Bechler */
public class ObjectDifferFactoryTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testGetInstanceWithNullConfiguration() throws Exception
	{
		ObjectDifferFactory.getInstance(null);
	}

	@Test
	public void testGetInstanceWithConfiguration() throws Exception
	{
		final in.th3iedkid.diff.Configuration configuration = new in.th3iedkid.diff.Configuration();
		final ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance(configuration);
		assertThat(objectDiffer.getConfiguration(), IsEqual.equalTo(configuration));
	}

	@Test
	public void testGetInstance() throws Exception
	{
		final ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();
		assertThat(objectDiffer, IsNull.notNullValue());
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testConstruction()
	{
		new ObjectDifferFactory();
	}
}
