/*
 * Copyright 2016 Author or Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cp.domain.core.model;

import static java.util.stream.StreamSupport.stream;
import static org.cp.elements.util.ArrayUtils.nullSafeArray;
import static org.cp.elements.util.CollectionUtils.nullSafeIterable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.cp.elements.lang.Filter;
import org.cp.elements.lang.NullSafe;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.util.CollectionUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link Family} class is an Abstract Data Type (ADT) modeling a collection of {@link Person people}
 * that make up a family unit.
 *
 * @author John Blum
 * @see java.lang.Iterable
 * @see java.util.Iterator
 * @see org.cp.domain.core.model.Person
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Family implements Iterable<Person> {

  @SuppressWarnings("unchecked")
  private final Set<Person> people = new TreeSet<>((personOne, personTwo) ->
    ComparatorResultBuilder.<Comparable>create()
      .doCompare(personOne.getBirthDate().get(), personTwo.getBirthDate().get())
      .doCompare(personOne.getLastName(), personTwo.getLastName())
      .doCompare(personOne.getFirstName(), personTwo.getFirstName())
      .doCompare(personOne.getMiddleName().orElse(null), personTwo.getMiddleName().orElse(null))
      .build()
  );

  /**
   * Factory method used to construct a new, empty {@link Family}.
   *
   * @return a new instance of {@link Family} containing now {@link Person people}.
   */
  public static Family empty() {
    return new Family();
  }

  /**
   * Null-safe factory method used to construct an instance of {@link Family}
   * initialized with given {@link Person people}.
   *
   * @param people array of {@link Person people} to group together as a {@link Family}.
   * @return a new instance of {@link Family} initialized with the given {@link Person people}.
   * @see org.cp.domain.core.model.Person
   */
  @NullSafe
  public static Family of(Person... people) {
    Family family = new Family();
    Collections.addAll(family.people, nullSafeArray(people, Person.class));
    return family;
  }

  /**
   * Null-safe factory method used to construct an instance of {@link Family}
   * initialized with given {@link Person people}.
   *
   * @param people {@link Iterable} of {@link Person people} to group together as a {@link Family}.
   * @return a new instance of {@link Family} initialized with the given {@link Person people}.
   * @see org.cp.domain.core.model.Person
   * @see java.lang.Iterable
   */
  @NullSafe
  public static Family of(Iterable<Person> people) {
    Family family = new Family();
    CollectionUtils.addAll(family.people, nullSafeIterable(people));
    return family;
  }

  /**
   * Adds the given {@link Person} to this {@link Family} iff the given {@link Person} is not {@literal null}
   * and the {@link Person} is not already part of this {@link Family}.
   *
   * @param person {@link Person} to add to this {@link Family}.
   * @return a boolean value indicating whehther the given {@link Person} was successfully added
   * to this {@link Family}.  Must not be {@literal null}.
   * @see org.cp.domain.core.model.Person
   */
  public boolean add(Person person) {
    return Optional.ofNullable(person).map(this.people::add).orElse(false);
  }

  /**
   * Finds all {@link Person people} in this {@link Family} that match the criteria of the given {@link Filter}.
   *
   * @param predicate {@link Filter} used to find {@link Person people} in this {@link Family} that match the criteria
   * defined by the {@link Filter}.
   * @return all {@link Person people} in this {@link Family} that match the criteria of the given {@link Filter}.
   * @see org.cp.domain.core.model.Person
   * @see org.cp.elements.lang.Filter
   */
  public Set<Person> findBy(Filter<Person> predicate) {

    Set<Person> searchResult = new TreeSet<>();

    stream(this.spliterator(), false).forEach(person -> {
      if (predicate.accept(person)) {
        searchResult.add(person);
      }
    });

    return searchResult;
  }

  /**
   * Determines whether this {@link Family} contains any {@link Person people}.
   *
   * @return a boolean value indicating whether this {@link Family} contains any {@link Person people}.
   */
  public boolean isEmpty() {
    return this.people.isEmpty();
  }

  /**
   * Iterates over the collection of {@link Person people} in this {@link Family}.
   *
   * @return an unmodifiable {@link Iterator} over the collection of {@link Person people} in this {@link Family}.
   * @see java.util.Iterator
   */
  @Override
  public Iterator<Person> iterator() {
    return Collections.unmodifiableSet(this.people).iterator();
  }

  /**
   * Returns a {@link Integer#TYPE int} value with the number of {@link Person people} in this {@link Family}.
   *
   * @return a {@link Integer#TYPE int} value with the number of {@link Person people} in this {@link Family}.
   */
  public int size() {
    return this.people.size();
  }

  /**
   * Returns a {@link String} representation of this {@link Family}.
   *
   * @return a {@link String} containing the {@link Name names} of {@link Person people} in this {@link Family}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder("[");
    AtomicBoolean addComma = new AtomicBoolean(false);

    stream(this.spliterator(), false).forEach(person -> {
      builder.append(addComma.get() ? "; " : StringUtils.EMPTY_STRING);
      builder.append(String.format("%1$s, %2$s%3$s", person.getLastName(), person.getFirstName(),
        person.getMiddleName().map(StringUtils.SINGLE_SPACE::concat).orElse(StringUtils.EMPTY_STRING)));
      addComma.set(true);
    });

    builder.append("]");

    return builder.toString();
  }
}
