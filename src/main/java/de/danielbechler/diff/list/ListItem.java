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

package de.danielbechler.diff.list;

/** @author Daniel Bechler */
public class ListItem
{
	public enum PositionChange
	{
		NONE,
		INSERT,
		REMOVE,
		MOVE
	}

	public static final class Builder
	{
		private ListItem listItem = new ListItem();

		public Builder base(final Object obj, final int position)
		{
			listItem.baseObject = obj;
			listItem.basePosition = position;
			return this;
		}

		public Builder working(final Object obj, final int position)
		{
			listItem.workingObject = obj;
			listItem.workingPosition = position;
			return this;
		}

		public ListItem create()
		{
			final ListItem listItem = this.listItem;
			this.listItem = new ListItem();
			return listItem;
		}
	}

	public static Builder build()
	{
		return new Builder();
	}

	private Object baseObject;
	private int basePosition = -1;
	private Object workingObject;
	private int workingPosition = -1;

	private ListItem()
	{
	}

	public Object getBaseObject()
	{
		return baseObject;
	}

	public Object getWorkingObject()
	{
		return workingObject;
	}

	public int getWorkingPosition()
	{
		return workingPosition;
	}

	public int getBasePosition()
	{
		return basePosition;
	}

	@SuppressWarnings("NonFinalFieldReferenceInEquals")
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

		final ListItem listItem = (ListItem) o;

		if (basePosition != listItem.basePosition)
		{
			return false;
		}
		if (workingPosition != listItem.workingPosition)
		{
			return false;
		}
		if (baseObject != null ? !baseObject.equals(listItem.baseObject) : listItem.baseObject != null)
		{
			return false;
		}
		if (workingObject != null ?
			!workingObject.equals(listItem.workingObject) :
			listItem.workingObject != null)
		{
			return false;
		}

		return true;
	}

	@SuppressWarnings("NonFinalFieldReferencedInHashCode")
	@Override
	public int hashCode()
	{
		int result = baseObject != null ? baseObject.hashCode() : 0;
		result = 31 * result + (workingObject != null ? workingObject.hashCode() : 0);
		result = 31 * result + workingPosition;
		result = 31 * result + basePosition;
		return result;
	}

	public PositionChange getPositionChange()
	{
		if (basePosition == workingPosition)
		{
			return PositionChange.NONE;
		}
		else if (basePosition <= -1)
		{
			return PositionChange.INSERT;
		}
		else if (workingPosition <= -1)
		{
			return PositionChange.REMOVE;
		}
		else
		{
			return PositionChange.MOVE;
		}
	}

	@Override
	public String toString()
	{
		if (workingPosition == -1 && basePosition == -1)
		{
			return "N/A";
		}
		else if (workingPosition >= 0 && basePosition < 0)
		{
			return "+ " + workingPosition + " | " + workingObject;
		}
		else if (basePosition >= 0 && workingPosition < 0)
		{
			return "- " + basePosition + " | " + baseObject;
		}
		else if (workingPosition == basePosition)
		{
			return "= " + workingPosition + " | " + workingObject;
		}
		else
		{
			return basePosition + " -> " + workingPosition + " | " + workingObject;
		}
	}
}
