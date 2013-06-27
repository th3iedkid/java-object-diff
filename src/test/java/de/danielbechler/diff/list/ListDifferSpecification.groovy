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

import de.danielbechler.diff.DifferDelegator
import de.danielbechler.diff.Instances
import de.danielbechler.diff.NodeInspector
import spock.lang.Specification
import spock.lang.Unroll

import static de.danielbechler.diff.node.Node.State.*
import static org.mockito.Matchers.any
import static org.mockito.Mockito.*

/**
 * @author Daniel Bechler
 */
class ListDifferSpecification extends Specification
{
  ListDiffer listDiffer;
  ListNode listNode;
  Instances instances;
  DifferDelegator differDelegatorMock;
  NodeInspector nodeInspectorMock;

  def setup()
  {
    nodeInspectorMock = mock(NodeInspector);
    differDelegatorMock = mock(DifferDelegator);
    listDiffer = new ListDiffer(differDelegatorMock, nodeInspectorMock);
  }

  def "should allow Lists as instance type"()
  {
    when:
    listDiffer.compare(null, Instances.of(working, base));

    then:
    notThrown IllegalArgumentException

    where:
    base | working
    []   | []
  }

  @Unroll
  def "should not allow anything other than Lists as instance type (#typeName)"()
  {
    when:
    listDiffer.compare(null, Instances.of(working, base));

    then:
    IllegalArgumentException ex = thrown()
    ex.getMessage() == "Can only handle instances of java.util.List. Got: " + typeName

    where:
    base         | working      | typeName
    1            | 2            | Integer.class.toString()
    1.5          | 2.5          | BigDecimal.class.toString()
    "a"          | "b"          | String.class.toString()
    true         | false        | Boolean.class.toString()
    new Object() | new Object() | Object.class.toString()
  }

  @Unroll
  def "should detect proper list items for base #base and working #working"()
  {
    when:
    listNode = listDiffer.compare(null, Instances.of(working, base));

    then:
    listNode.items == items;

    where:
    base       | working    | items
    null       | ["a"]      | [ListItem.build().base(null, -1).working("a", 0).create()]
    ["a"]      | null       | [ListItem.build().base("a", 0).working(null, -1).create()]
    ["a"]      | ["a", "a"] | [ListItem.build().base("a", 0).working("a", 0).create(), ListItem.build().base(null, -1).working("a", 1).create()]
    ["a", "b"] | ["b", "a"] | [ListItem.build().base("a", 0).working("a", 1).create(), ListItem.build().base("b", 1).working("b", 0).create()]
    ["b", "c"] | ["c", "a"] | [ListItem.build().base("b", 0).working(null, -1).create(), ListItem.build().base("c", 1).working("c", 0).create(), ListItem.build().base(null, -1).working("a", 1).create()]
  }

  def "should honor ignore configuration"()
  {
    setup:
    when(nodeInspectorMock.isIgnored(any(ListNode))).thenReturn(true);

    expect:
    listDiffer.compare(null, Instances.of([], [])).getState() == IGNORED;
  }

  def "should mark node as added if instances are added"()
  {
    expect:
    listDiffer.compare(null, Instances.of([], null)).getState() == ADDED;
  }

  def "should mark node as removed if instances are removed"()
  {
    expect:
    listDiffer.compare(null, Instances.of(null, [])).getState() == REMOVED;
  }

  def "should mark node as untouched if instances are same"()
  {
    setup:
    def value = [];

    expect:
    listDiffer.compare(null, Instances.of(value, value)).getState() == UNTOUCHED;
  }

  def "should compare instances via equals method if equals only is enabled"()
  {
    setup:
    when(nodeInspectorMock.isEqualsOnly(any(ListNode))).thenReturn(true);

    when:
    listNode = listDiffer.compare(null, Instances.of(working, base));

    then:
    listNode.getState() == state
    verifyZeroInteractions(differDelegatorMock)

    where:
    base    | working | state
    []      | []      | UNTOUCHED
    ["foo"] | ["foo"] | UNTOUCHED
    []      | ["foo"] | CHANGED
    ["foo"] | ["bar"] | CHANGED
  }
}
