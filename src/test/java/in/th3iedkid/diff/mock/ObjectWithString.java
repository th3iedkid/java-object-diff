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

package in.th3iedkid.diff.mock;

/** @author Daniel Bechler */
@SuppressWarnings({
		"UnusedDeclaration",
		"NonFinalFieldReferencedInHashCode",
		"NonFinalFieldReferenceInEquals"
})
public class ObjectWithString
{
	private String value;

	public ObjectWithString()
	{
	}

	public ObjectWithString(final String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final ObjectWithString that = (ObjectWithString) o;

		if (value != null ? !value.equals(that.value) : that.value != null)
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + value + "]";
	}
}
