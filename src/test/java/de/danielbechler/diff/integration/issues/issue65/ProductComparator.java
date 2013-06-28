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
import de.danielbechler.diff.node.*;

import java.util.*;

/** @author Daniel Bechler */
public class ProductComparator implements MyComparator
{
	private final ObjectDiffer differ;

	public ProductComparator(final ComparisonPolicy policy)
	{
		differ = ObjectDifferFactory.getInstance(policy.getConfiguration());
	}

	public <T extends Product> ComparisonResult<T> compare(final T newProduct,
														   final T oldProduct) throws ComparisonException
	{
		final Node differenceNode = differ.compare(newProduct, oldProduct);
		return new ProductComparisonResult<T>(oldProduct, newProduct, differenceNode);
	}

	public static void main(final String[] args) throws ComparisonException
	{
		final Product oldProduct = new Product();
		oldProduct.setTitles(Collections.singletonMap("en", "Dances with Wolves"));

		final Product newProduct = new Product();
		newProduct.setTitles(Collections.singletonMap("en", "Dances with Wolves"));
		newProduct.setTitles(Collections.singletonMap("de", "Der mit dem Wolf tanzt"));

		final ProductComparator comparator = new ProductComparator(ComparisonPolicy.MOVIE_DEFAULT_POLICY);
		final ComparisonResult<Product> comparisonResult = comparator.compare(newProduct, oldProduct);

		System.out.println(comparisonResult.getChangeSet());
	}
}
