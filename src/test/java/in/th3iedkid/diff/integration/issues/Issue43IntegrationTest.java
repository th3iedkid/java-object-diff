package in.th3iedkid.diff.integration.issues;

import in.th3iedkid.diff.Configuration;
import in.th3iedkid.diff.ObjectDifferFactory;
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.PropertyPath;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** https://github.com/SQiShER/java-object-diff/issues/43 */
@SuppressWarnings("ALL")
public class Issue43IntegrationTest
{
	private class Thing
	{
		private final String a;
		private final String b;

		public Thing(final String a, final String b)
		{
			this.a = a;
			this.b = b;
		}

		public String getA()
		{
			return a;
		}

		public String getB()
		{
			return b;
		}

		@Override
		public boolean equals(final Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (!(o instanceof Thing))
			{
				return false;
			}

			final Thing thing = (Thing) o;

			if (a != null ? !a.equals(thing.a) : thing.a != null)
			{
				return false;
			}
			if (b != null ? !b.equals(thing.b) : thing.b != null)
			{
				return false;
			}

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = a != null ? a.hashCode() : 0;
			result = 31 * result + (b != null ? b.hashCode() : 0);
			return result;
		}
	}

	private class ThingHolder
	{
		private final Set<Thing> things;
		private final String ignore;
		private final String include;

		private ThingHolder(final Set<Thing> things, final String ignore, final String include)
		{
			this.things = things;
			this.ignore = ignore;
			this.include = include;
		}

		public Set<Thing> getThings()
		{
			return things;
		}

		public String getIgnore()
		{
			return ignore;
		}

		public String getInclude()
		{
			return include;
		}

		@Override
		public boolean equals(final Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (!(o instanceof ThingHolder))
			{
				return false;
			}

			final ThingHolder that = (ThingHolder) o;

			if (ignore != null ? !ignore.equals(that.ignore) : that.ignore != null)
			{
				return false;
			}
			if (include != null ? !include.equals(that.include) : that.include != null)
			{
				return false;
			}
			if (things != null ? !things.equals(that.things) : that.things != null)
			{
				return false;
			}

			return true;
		}

		@Override
		public int hashCode()
		{
			int result = things != null ? things.hashCode() : 0;
			result = 31 * result + (ignore != null ? ignore.hashCode() : 0);
			result = 31 * result + (include != null ? include.hashCode() : 0);
			return result;
		}
	}

	@Test
	public void shouldDiffThings()
	{
		final List<String> propertyNames = asList("things", "include");
		in.th3iedkid.diff.Configuration configuration = new Configuration();
		for (final String name : propertyNames)
		{
			final PropertyPath propertyPath = PropertyPath.buildWith(name);
			configuration = configuration.withEqualsOnlyProperty(propertyPath)
										 .withPropertyPath(propertyPath);
		}

		final Thing thingOne = new Thing("a", "b");
		final Thing thingTwo = new Thing("aa", "bb");

		final ThingHolder first = new ThingHolder(singleton(thingOne), "ignore", "include");
		final ThingHolder second = new ThingHolder(singleton(thingTwo), "ignore this change", "include");
		final Node compareResults = ObjectDifferFactory.getInstance(configuration).compare(first, second);

		assertThat(compareResults.isChanged(), is(true));
	}
}
