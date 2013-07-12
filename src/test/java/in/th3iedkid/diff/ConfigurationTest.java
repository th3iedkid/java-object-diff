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

import in.th3iedkid.diff.mock.ObjectWithCollection;
import in.th3iedkid.diff.mock.ObjectWithString;
import in.th3iedkid.diff.mock.ObjectWithStringAndCompareTo;
import in.th3iedkid.diff.node.TypeAwareNode;
import in.th3iedkid.diff.path.PropertyPath;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/** @author Daniel Bechler */
public class ConfigurationTest
{
	@Mock
	private TypeAwareNode node;

	private in.th3iedkid.diff.Configuration configuration;

	@BeforeMethod
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		configuration = new in.th3iedkid.diff.Configuration();
	}

	@Test
	public void testIsIgnored() throws Exception
	{

	}

	@Test
	public void testIsIncluded() throws Exception
	{

	}

	@Test
	public void testIsExcluded() throws Exception
	{

	}

	@Test
	public void testIsEqualsOnly() throws Exception
	{

	}

	@Test
	public void testIsReturnable() throws Exception
	{

	}

	@Test
	public void testIsIgnoredWithConfiguredPropertyPath() throws Exception
	{
		final PropertyPath propertyPath = PropertyPath.buildWith("value");
		when(node.getPropertyPath()).thenReturn(propertyPath);
		configuration.withoutProperty(propertyPath);
		assertThat(configuration.isIgnored(node), is(true));
	}

	@Test
	public void testIsIgnoredWithPropertyThatShouldNotBeIgnored() throws Exception
	{
		assertThat(configuration.isIgnored(node), is(false));
	}

	@Test
	public void testIsIgnoredWithCategory() throws Exception
	{
		when(node.getCategories()).thenReturn(java.util.Collections.singleton("foo"));
		assertThat(configuration.isIgnored(node), is(false));
		configuration.withoutCategory("foo");
		assertThat(configuration.isIgnored(node), is(true));
	}

    @SuppressWarnings({"unchecked"})
    @Test
    public void testIsCompareToOnlyWithConfiguredPropertyType() throws Exception
    {
        final Class aClass = ObjectWithStringAndCompareTo.class;
        when(node.getType()).thenReturn(aClass);
        configuration.withCompareToOnlyType(aClass);
        assertThat(configuration.isCompareToOnly(node), is(true));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testIsCompareToOnlyWithConfiguredPropertyTypeNotComparable() throws Exception
    {
        final Class aClass = ObjectWithString.class;
        when(node.getType()).thenReturn(aClass);
        configuration.withCompareToOnlyType(aClass);
        assertThat(configuration.isCompareToOnly(node), is(false));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testIsCompareToOnlyWithComparableType() throws Exception
    {
        final Class aClass = BigDecimal.class;
        when(node.getType()).thenReturn(aClass);
        assertThat(configuration.isCompareToOnly(node), is(true));
    }

	@Test
	public void testIsEqualsOnlyWithConfiguredPropertyPath() throws Exception
	{
		final PropertyPath propertyPath = PropertyPath.buildWith("value");
		when(node.getPropertyPath()).thenReturn(propertyPath);
		configuration.withEqualsOnlyProperty(propertyPath);
		assertThat(configuration.isEqualsOnly(node), is(true));
	}

	@SuppressWarnings({"unchecked"})
	@Test
	public void testIsEqualsOnlyWithConfiguredPropertyType() throws Exception
	{
		final Class aClass = ObjectWithString.class;
		when(node.getType()).thenReturn(aClass);
		configuration.withEqualsOnlyType(aClass);
		assertThat(configuration.isEqualsOnly(node), is(true));
	}

	@SuppressWarnings({"unchecked"})
	@Test
	public void testIsEqualsOnlyWithSimpleType() throws Exception
	{
		final Class aClass = String.class;
		when(node.getType()).thenReturn(aClass);
		assertThat(configuration.isEqualsOnly(node), is(true));
	}

	@Test
	public void testIsEqualsOnlyWithAccessorFlag() throws Exception
	{
		when(node.isEqualsOnly()).thenReturn(true);
		assertThat(configuration.isEqualsOnly(node), is(true));
	}

	@SuppressWarnings({"unchecked"})
	@Test
	public void testIsEqualsOnlyWithTypeThatShouldNotBeComparedUsingEquals() throws Exception
	{
		final Class aClass = ObjectWithCollection.class;
		when(node.getType()).thenReturn(aClass);
		assertThat(configuration.isEqualsOnly(node), is(false));
	}

	@Test
	public void testIsIntrospectibleWithUntouchedNonEqualsOnlyNodeReturnsFalse()
	{
		when(node.getType()).then(returnClass(ObjectWithString.class));
		when(node.isUntouched()).thenReturn(true);
		assertThat(configuration.isIntrospectible(node)).isTrue();
	}

	@Test
	public void testIsIntrospectibleReturnsTrueForAddedNodeIfChildrenOfAddedNodesAreEnabled()
	{
		configuration.withChildrenOfAddedNodes();
		when(node.isAdded()).thenReturn(true);
		assertThat(configuration.isIntrospectible(node)).isTrue();
	}

	@Test
	public void testIsIntrospectibleReturnsFalseForAddedNodeIfChildrenOfAddedNodesAreDisabled()
	{
		configuration.withoutChildrenOfAddedNodes();
		when(node.isAdded()).thenReturn(true);
		assertThat(configuration.isIntrospectible(node)).isFalse();
	}

	@Test
	public void testIsIntrospectibleReturnsTrueForRemovedNodeIfChildrenOfRemovedNodesAreEnabled()
	{
		configuration.withChildrenOfRemovedNodes();
		when(node.isRemoved()).thenReturn(true);
		assertThat(configuration.isIntrospectible(node)).isTrue();
	}

	@Test
	public void testIsIntrospectibleReturnsFalseForRemovedNodeIfChildrenOfRemovedNodesAreDisabled()
	{
		configuration.withoutChildrenOfRemovedNodes();
		when(node.isRemoved()).thenReturn(true);
		assertThat(configuration.isIntrospectible(node)).isFalse();
	}

    @SuppressWarnings({"TypeMayBeWeakened"})
	private static <T> Answer<Class<T>> returnClass(final Class<T> aClass)
	{
		return new Answer<Class<T>>()
		{
			public Class<T> answer(final InvocationOnMock invocation) throws Throwable
			{
				return aClass;
			}
		};
	}
}
