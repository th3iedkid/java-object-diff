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

package de.danielbechler.diff.integration.issues.issue65;

import de.danielbechler.diff.*;

import java.util.*;

/** @author Daniel Bechler */
public class ComparisonPolicy
{
	public static final ComparisonPolicy MOVIE_DEFAULT_POLICY = new ComparisonPolicy("titles", "infos", "cast", "director", "actors", "cinema");

	private Configuration configuration;
	private final List<String> properties;

	public ComparisonPolicy(final String... propertiesToInclude)
	{
		configuration = new Configuration();
		properties = Arrays.asList(propertiesToInclude);
//		for (final String property : properties)
//		{
//			configuration = configuration.withPropertyPath(PropertyPath.buildWith(property));
//			configuration = configuration.withChildrenOfAddedNodes();
//		}
//		configuration.withPropertyPath(PropertyPath.createBuilder()
//												   .withRoot()
//												   .withPropertyName("titles")
//												   .withMapKey("de")
//												   .build());
//		configuration.withPropertyPath(PropertyPath.createBuilder()
//												   .withRoot()
//												   .withPropertyName("titles")
//												   .withMapKey("en")
//												   .build());
		configuration.withCategory("movie");
//		configuration.property(PropertyPath.buildWith("titles")).includeAll().but(mapEntryWithKey("en"));
	}

	public Configuration getConfiguration()
	{
		return configuration;
	}

	public List<String> getProperties()
	{
		return properties;
	}
}
