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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.cp.elements.lang.Constants;
import org.cp.elements.lang.Filter;
import org.cp.elements.lang.IdentifierSequence;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.support.UUIDIdentifierSequence;
import org.cp.elements.util.CollectionUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link Family} class is an Abstract Data Type (ADT) modeling a collection of {@link Person people}
 * that make up a family.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Group
 * @see org.cp.domain.core.model.Person
 * @see org.cp.elements.lang.IdentifierSequence
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Family implements Group {

  private static final IdentifierSequence<String> ID_SEQUENCE = new UUIDIdentifierSequence();

  private static final LocalDateTime EPOCH_BIRTH_DATE =
    LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC);

  private static final String FAMILY_NAME_TEMPLATE = "%s Family";

  /**
   * Factory method used to construct a new, empty {@link Family}.
   *
   * @return a new instance of {@link Family} containing no {@link Person people}.
   * @see Family()
   */
  public static Family empty() {
    return new Family();
  }

  /**
   * Generates a new {@link String identifier} that can used to uniquely identify a {@link Family}.
   *
   * @return a generated {@link String identifier} that can used to uniquely identify a {@link Family}.
   * @see org.cp.elements.lang.IdentifierSequence
   * @see java.lang.String
   */
  public static String generateId() {
    return ID_SEQUENCE.nextId();
  }

  /**
   * Factory method used to construct a new instance of {@link Family} initialized with the given {@link Person people}.
   *
   * @param people array of {@link Person people} grouped together as a {@link Family}.
   * @return a new instance of {@link Family} initialized with the given {@link Person people}.
   * @see org.cp.domain.core.model.Person
   * @see #of(Iterable)
   */
  @NullSafe
  public static Family of(Person... people) {

    Family family = new Family();

    Collections.addAll(family.people, nullSafeArray(people, Person.class));

    return family;
  }

  /**
   * Factory method used to construct a new instance of {@link Family} initialized with the given {@link Person people}.
   *
   * @param people {@link Iterable} of {@link Person people} grouped together as a {@link Family}.
   * @return a new instance of {@link Family} initialized with the given {@link Person people}.
   * @see org.cp.domain.core.model.Person
   * @see java.lang.Iterable
   * @see #of(Person...)
   */
  @NullSafe
  public static Family of(Iterable<Person> people) {

    Family family = new Family();

    CollectionUtils.addAll(family.people, nullSafeIterable(people));

    return family;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final Set<Person> people = new TreeSet<>((personOne, personTwo) ->
    ComparatorResultBuilder.<Comparable>create()
      .doCompare(personOne.getBirthDate().orElse(EPOCH_BIRTH_DATE), personTwo.getBirthDate().orElse(EPOCH_BIRTH_DATE))
      .doCompare(personOne.getLastName(), personTwo.getLastName())
      .doCompare(personOne.getFirstName(), personTwo.getFirstName())
      .doCompare(personOne.getMiddleName().orElse(Constants.UNDEFINED),
        personTwo.getMiddleName().orElse(Constants.UNDEFINED))
      .build()
  );

  private String id;
  private String name;

  /**
   * Returns the {@link String identifier} that uniquely identifies this {@link Family}.
   *
   * @return an {@link String identifier} uniquely identifying this {@link Family}.
   * @see java.lang.String
   * @see #setId(String)
   */
  @Override
  public String getId() {
    return this.id;
  }

  /**
   * Sets the {@link String identifier} uniquely identifying this {@link Family}.
   *
   * @param id {@link String identifier} that uniquely identifies this {@link Family}.
   * @see java.lang.String
   * @see #getId()
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the {@link String name} of this {@link Family}.
   *
   * If {@link String name} was not explicitly set then the {@link String name} of this {@link Family}
   * is derived from the {@link String last name} of the first {@link Person} found in this {@link Family}.
   *
   * The {@link String name} of the {@link Family} might be something like {@literal Doe Family}.
   *
   * @return the {@link String name} of this {@link Family}.
   * @see java.lang.String
   * @see #named(String)
   */
  @Override
  public String getName() {

    return Optional.ofNullable(this.name)
      .filter(StringUtils::hasText)
      .orElseGet(() -> Optional.ofNullable(CollectionUtils.findOne(this, Filter.accepting()))
        .map(person -> String.format(FAMILY_NAME_TEMPLATE, person.getLastName()))
        .orElse(null));
  }

  /**
   * Determines whether this {@link Family} contains any {@link Person people}.
   *
   * @return a boolean value indicating whether this {@link Family} contains any {@link Person people}.
   * @see #size()
   */
  public boolean isEmpty() {
    return this.people.isEmpty();
  }

  /**
   * Iterates over the collection of {@link Person people} in this {@link Family}.
   *
   * @return an unmodifiable {@link Iterator} to iterate over the collection of {@link Person people}
   * in this {@link Family}.
   * @see org.cp.domain.core.model.Person
   * @see java.util.Iterator
   */
  @Override
  public Iterator<Person> iterator() {
    return Collections.unmodifiableSet(this.people).iterator();
  }

  /**
   * Adds the given {@link Person} to this {@link Family} iff the given {@link Person} is not {@literal null}
   * and the {@link Person} is not already a member of this {@link Family}.
   *
   * @param person {@link Person} to add; must not be {@literal null}.
   * @return a boolean value indicating whether the given {@link Person} was successfully added
   * to this {@link Family}.
   * @see org.cp.domain.core.model.Person
   * @see #leave(Person)
   */
  public boolean join(Person person) {
    return person != null && this.people.add(person);
  }

  /**
   * Removes the given {@link Person} from this {@link Family}.
   *
   * @param person {@link Person} to remove.
   * @return a boolean value indicating whether the {@link Person} was a member of this {@link Family}
   * and was removed successfully.
   * @see org.cp.domain.core.model.Person
   * @see #join(Person)
   */
  @Override
  public boolean leave(Person person) {
    return person != null && this.people.remove(person);
  }

  /**
   * Builder method used to set the {@link String name} of this {@link Family}.
   *
   * @param name {@link String} containing the name of this {@link Family}.
   * @return this {@link Family}.
   * @see #getName()
   */
  public Family named(String name) {
    this.name = name;
    return this;
  }

  /**
   * Returns a {@link Integer#TYPE int value} specifying the number of {@link Person people} in this {@link Family}.
   *
   * @return a {@link Integer#TYPE int value} specifying the number of {@link Person people} in this {@link Family}.
   * @see #isEmpty()
   */
  public int size() {
    return this.people.size();
  }

  /**
   * Returns a {@link String} representation of this {@link Family}.
   *
   * @return a {@link String} containing the {@link Name names} of all the {@link Person people} in this {@link Family}.
   * @see java.lang.Object#toString()
   * @see java.lang.String
   */
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder("[");

    AtomicBoolean addDelimiter = new AtomicBoolean(false);

    stream(this.spliterator(), false).forEach(person -> {

      builder.append(addDelimiter.get() ? "; " : StringUtils.EMPTY_STRING);
      builder.append(String.format("%1$s, %2$s%3$s", person.getLastName(), person.getFirstName(),
        person.getMiddleName().map(StringUtils.SINGLE_SPACE::concat).orElse(StringUtils.EMPTY_STRING)));
      addDelimiter.set(true);

    });

    builder.append("]");

    return builder.toString();
  }
}
