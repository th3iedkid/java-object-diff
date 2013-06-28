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

import de.danielbechler.diff.annotation.*;

import java.util.*;

/** @author Daniel Bechler */
public class Product
{
	private Map<String, String> titles = new HashMap<String, String>();
	private Map<String, String> infos = new HashMap<String, String>();
	private String cast;
	private String director;
	private String actors;
	private String cinema;

	@ObjectDiffProperty(categories = {"movie"})
	public Map<String, String> getTitles()
	{
		return titles;
	}

	public void setTitles(final Map<String, String> titles)
	{
		this.titles = titles;
	}

	public Map<String, String> getInfos()
	{
		return infos;
	}

	public void setInfos(final Map<String, String> infos)
	{
		this.infos = infos;
	}

	public String getCast()
	{
		return cast;
	}

	public void setCast(final String cast)
	{
		this.cast = cast;
	}

	public String getDirector()
	{
		return director;
	}

	public void setDirector(final String director)
	{
		this.director = director;
	}

	public String getActors()
	{
		return actors;
	}

	public void setActors(final String actors)
	{
		this.actors = actors;
	}

	public String getCinema()
	{
		return cinema;
	}

	public void setCinema(final String cinema)
	{
		this.cinema = cinema;
	}
}
