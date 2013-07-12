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
import in.th3iedkid.diff.node.Node;
import in.th3iedkid.diff.visitor.PrintingVisitor;

/** @author Daniel Bechler */
public class GettingStarted
{
	private GettingStarted()
	{
	}

	public static void main(final String[] args)
	{
		helloWorldExample();
		phoneBookExample();
	}

	private static void helloWorldExample()
	{
		final ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();

		final String working = "Hello";
		final String base = "World";
		final Node root = objectDiffer.compare(working, base);

		root.visit(new PrintingVisitor(working, base));
	}

	private static void phoneBookExample()
	{
	final PhoneBook phoneBook = new PhoneBook("Breaking Bad");

	final Contact walterWhite = new Contact("Walter", "White");
	walterWhite.setPhoneNumber("Home", new PhoneNumber("1", "505", "316-7871"));
	walterWhite.setPhoneNumber("Work", new PhoneNumber("1", "505", "456-3788"));
	phoneBook.addContact(walterWhite);

	final Contact jessePinkman = new Contact("Jesse", "Pinkman");
	jessePinkman.setPhoneNumber("Home", new PhoneNumber("1", "505", "234-4628"));
	phoneBook.addContact(jessePinkman);

	final PhoneBook modifiedPhoneBook = PhoneBook.from(phoneBook);
	modifiedPhoneBook.getContact("Jesse", "Pinkman").setMiddleName("Bruce");
	modifiedPhoneBook.getContact("Walter", "White").setMiddleName("Hartwell");

	final ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();
	final Node root = objectDiffer.compare(modifiedPhoneBook, phoneBook);
	final Node.Visitor visitor = new PrintingVisitor(modifiedPhoneBook, phoneBook);
	root.visit(visitor);
	}
}
