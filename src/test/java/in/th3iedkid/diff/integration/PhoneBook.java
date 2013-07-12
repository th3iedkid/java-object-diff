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

import in.th3iedkid.util.Strings;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** @author Daniel Bechler */
public class PhoneBook
{
	public static PhoneBook from(final PhoneBook phoneBook)
	{
		final PhoneBook copy = new PhoneBook(phoneBook.name);
		for (final Contact contact : phoneBook.contacts)
		{
			copy.addContact(Contact.from(contact));
		}
		return copy;
	}

	private String name;
	private List<Contact> contacts = new LinkedList<Contact>();

	public PhoneBook(final String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(final List<Contact> contacts)
	{
		this.contacts = contacts;
	}

	public Contact getContact(final String firstName, final String lastName)
	{
		for (final Contact contact : contacts)
		{
			if (contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName))
			{
				return contact;
			}
		}
		return null;
	}

	public void addContact(final Contact contact)
	{
		this.contacts.add(contact);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(name).append(" Phone Book").append('\n');
		sb.append("-------------").append('\n');
		for (final Contact contact : contacts)
		{
			final String name = Strings.join(" ",
                    contact.getFirstName(),
                    contact.getMiddleName(),
                    contact.getLastName());
			sb.append(name).append(":\n");
			for (final Map.Entry<String, PhoneNumber> entry : contact.getPhoneNumbers().entrySet())
			{
				sb.append("  ")
						.append(entry.getKey())
						.append(": ")
						.append(entry.getValue())
						.append('\n');
			}
		}
		return sb.toString();
	}
}
