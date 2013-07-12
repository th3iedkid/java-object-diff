package in.th3iedkid.diff.example;

import in.th3iedkid.diff.Configuration;
import in.th3iedkid.diff.ObjectDifferFactory;
import in.th3iedkid.diff.annotation.ObjectDiffProperty;
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.path.PropertyPath;
import in.th3iedkid.diff.visitor.PrintingVisitor;

/** @author Daniel Bechler */
@SuppressWarnings("UnusedDeclaration")
class IgnoreExample
{
	private IgnoreExample()
	{
	}

	public static void main(final String[] args)
	{
		final User base = new User("foo", "1234");
		final User working = new User("foo", "9876");

		final Configuration configuration = new Configuration();

		// (Option 1) Causes the ObjectDiffer to ignore the 'password' property of the root object
		configuration.withoutProperty(PropertyPath.buildWith("password"));

		final Node node = ObjectDifferFactory.getInstance(configuration).compare(working, base);

		node.visit(new PrintingVisitor(working, base));

		// Output with ignore: Property at path '/' has not changed
		// Output without ignore: Property at path '/password' has changed from [ 1234 ] to [ 9876 ]
	}

	public static class User
	{
		private final String name;
		private final String password;

		public User(final String name, final String password)
		{
			this.name = name;
			this.password = password;
		}

		public String getName()
		{
			return name;
		}

		/* (Option 2) This annotation causes the ObjectDiffer to always ignore this property */
		@ObjectDiffProperty(ignore = true)
		public String getPassword()
		{
			return password;
		}
	}
}
