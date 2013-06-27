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

package de.danielbechler.diff.list

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Daniel Bechler
 */
class ListItemSpecification extends Specification
{
  @Unroll
  def "should indicate position change #positionChange when base index is #basePosition and working index is #workingPosition"()
  {
    expect:
    ListItem.build()
            .base("foo", basePosition)
            .working("bar", workingPosition)
            .create().positionChange == positionChange;

    where:
    basePosition | workingPosition | positionChange
    0            | 0               | ListItem.PositionChange.NONE
    5            | 5               | ListItem.PositionChange.NONE
    -1           | 0               | ListItem.PositionChange.INSERT
    -50          | 4               | ListItem.PositionChange.INSERT
    0            | -1              | ListItem.PositionChange.REMOVE
    10           | -42             | ListItem.PositionChange.REMOVE
    0            | 1               | ListItem.PositionChange.MOVE
    10           | 5               | ListItem.PositionChange.MOVE
  }

  @Unroll
  def "should have pretty string representation (#string)"()
  {
    expect:
    def item = ListItem.build()
                       .base(baseObject, basePosition)
                       .working(workingObject, workingPosition)
                       .create()
    item.toString() == string

    where:
    baseObject | basePosition | workingObject | workingPosition | string
    null       | -1           | null          | 1               | "N/A"
    "foo"      | 0            | "foo"         | 0               | "= 0 | foo"
    "foo"      | 0            | "foo"         | 1               | "0 -> 1 | foo"
    "foo"      | 1            | "foo"         | 0               | "1 -> 0 | foo"
    null       | -1           | "foo"         | 0               | "+ 0 | foo"
    "foo"      | 0            | null          | -1              | "- 0 | foo"
  }

  def "should do something"()
  {
    expect:
    a == b

    where:
    a       | b
    "hallo" | "welt"
  }
}
