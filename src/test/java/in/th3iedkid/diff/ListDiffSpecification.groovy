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

package in.th3iedkid.diff

import in.th3iedkid.diff.path.CollectionElement
import in.th3iedkid.diff.visitor.PrintingVisitor
import spock.lang.Ignore
import spock.lang.Specification
/**
 * @author Daniel Bechler
 */
class ListDiffSpecification extends Specification
{
  def objectDiffer;

  def setup()
  {
    objectDiffer = ObjectDifferFactory.getInstance();
  }

  @Ignore
  def "detects position switch"()
  {
    Node node;

    when:
    node = objectDiffer.compare(working, base);

    then:
    node.visit(new PrintingVisitor(working, base));
    node.getChild(new CollectionElement("a")).getState() == Node.State.CHANGED;
    node.getChild(new CollectionElement("b")).getState() == Node.State.CHANGED;

    where:
    base       | working
    ["a", "b"] | ["b", "a"]
  }
}
