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
import in.th3iedkid.diff.node.Node;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/** @author Daniel Bechler */
public class BeanPropertyComparisonDelegatorShould
{
	private BeanPropertyComparisonDelegator beanPropertyComparisonDelegator;

	@Mock private Node beanNode;
	@Mock private Node propertyNode;
	@Mock private Accessor propertyAccessor;
	@Mock private DifferDelegator delegator;
	@Mock private in.th3iedkid.diff.Configuration configuration;
	@Mock private PropertyNodeFactory propertyNodeFactory;
	@Mock private Instances beanInstances;
	@Mock private Instances propertyInstances;

	@BeforeMethod
	protected void setUp() throws Exception
	{
		initMocks(this);

		beanPropertyComparisonDelegator = new BeanPropertyComparisonDelegator(delegator, configuration);
		beanPropertyComparisonDelegator.setPropertyNodeFactory(propertyNodeFactory);
	}

	@Test
	public void return_node_with_ignored_state_for_ignored_properties()
	{
		when(propertyNodeFactory.createPropertyNode(beanNode, propertyAccessor)).thenReturn(propertyNode);
		when(configuration.isIgnored(propertyNode)).thenReturn(true);

		beanPropertyComparisonDelegator.compare(beanNode, beanInstances, propertyAccessor);

		verify(propertyNode).setState(Node.State.IGNORED);
	}

	@Test
	public void not_access_ignored_properties()
	{
		when(propertyNodeFactory.createPropertyNode(beanNode, propertyAccessor)).thenReturn(propertyNode);
		when(configuration.isIgnored(propertyNode)).thenReturn(true);

		beanPropertyComparisonDelegator.compare(beanNode, beanInstances, propertyAccessor);

		verify(beanInstances, times(0)).access(propertyAccessor);
	}

	@Test
	public void not_delegate_comparison_of_ignored_properties()
	{
		when(propertyNodeFactory.createPropertyNode(beanNode, propertyAccessor)).thenReturn(propertyNode);
		when(configuration.isIgnored(propertyNode)).thenReturn(true);

		beanPropertyComparisonDelegator.compare(beanNode, beanInstances, propertyAccessor);

		verifyZeroInteractions(delegator);
	}

	@Test
	public void delegate_property_comparison()
	{
		when(propertyNodeFactory.createPropertyNode(beanNode, propertyAccessor)).thenReturn(propertyNode);
		when(beanInstances.access(propertyAccessor)).thenReturn(propertyInstances);

		beanPropertyComparisonDelegator.compare(beanNode, beanInstances, propertyAccessor);

		verify(delegator).delegate(beanNode, propertyInstances);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fail_if_constructed_without_delegator()
	{
		new BeanPropertyComparisonDelegator(null, configuration);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fail_if_constructed_without_configuration()
	{
		new BeanPropertyComparisonDelegator(delegator, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fail_if_no_bean_node_was_given() throws Exception
	{
		beanPropertyComparisonDelegator.compare(null, beanInstances, propertyAccessor);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fail_if_no_bean_instances_were_given() throws Exception
	{
		beanPropertyComparisonDelegator.compare(beanNode, null, propertyAccessor);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fail_if_no_property_accessor_was_given() throws Exception
	{
		beanPropertyComparisonDelegator.compare(beanNode, beanInstances, null);
	}
}
