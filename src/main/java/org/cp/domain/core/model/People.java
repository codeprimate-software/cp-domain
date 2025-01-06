/*
 * Copyright 2017-Present Author or Authors.
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.cp.domain.core.serialization.json.PeopleJsonDeserializer;
import org.cp.domain.core.serialization.json.PeopleJsonSerializer;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Constants;
import org.cp.elements.lang.Integers;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ArrayUtils;
import org.cp.elements.util.CollectionUtils;
import org.cp.elements.util.ComparatorResultBuilder;
import org.cp.elements.util.stream.StreamUtils;

/**
 * Abstract Data Type (ADT) modeling a {@link Iterable collection} of {@link Person people}.
 *
 * @author John Blum
 * @see java.time.LocalDateTime
 * @see java.util.UUID
 * @see org.cp.domain.core.model.Group
 * @see org.cp.domain.core.model.Person
 * @since 0.1.0
 */
@SuppressWarnings("unused")
@JsonSerialize(using = PeopleJsonSerializer.class)
@JsonDeserialize(using = PeopleJsonDeserializer.class)
public class People implements Group<Person> {

  private static final LocalDateTime EPOCH_BIRTH_DATE =
    LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC);

  private static final String EMPTY_NO_ID_GROUP_NAME = "EMPTY NON-IDENTIFIED GROUP";
  private static final String GROUP_ID_NAME = "GROUP ID [%s]";
  private static final String GROUP_OF_NAME = "GROUP of [%s]";
  private static final String LAST_NAME_FIRST_NAME_MIDDLE_NAME = "%1$s, %2$s%3$s";

  /**
   * Factory method used to construct a new, empty {@link Group} of {@link People}.
   *
   * @return a new, empty {@link Group} of {@link People}.
   */
  public static @NotNull People empty() {
    return new People();
  }

  /**
   * Factory method used to construct a new {@link Group} of {@link People} initialized from
   * the given array of {@link Person people}.
   *
   * @param people array of {@link Person people} to {@link Group group} together.
   * @return a new {@link Group} of {@link People} initialized from the given array.
   * @see org.cp.domain.core.model.Person
   * @see #of(Iterable)
   */
  @NullSafe
  public static @NotNull People of(Person... people) {

    People group = new People();

    Collections.addAll(group.people, ArrayUtils.nullSafeArray(people, Person.class));

    return group;
  }

  /**
   * Factory method used to construct a new {@link Group} of {@link People} initialized from
   * the given {@link Iterable} of {@link Person people}.
   *
   * @param people {@link Iterable} of {@link Person people} to {@link Group group} together.
   * @return a new {@link Group} of {@link People} initialized from the given {@link Iterable}.
   * @see org.cp.domain.core.model.Person
   * @see java.lang.Iterable
   * @see #of(Person...)
   */
  @NullSafe
  public static @NotNull People of(Iterable<Person> people) {

    People group = new People();

    CollectionUtils.addAll(group.people, CollectionUtils.nullSafeIterable(people));

    return group;
  }

  /**
   * Factory method used to construct a new {@link Group} of {@link People} consisting of 1 {@link Person}.
   *
   * @param person {@link Person} to add to the {@link Group} of {@link People}.
   * @return a new {@link Group} of {@link People} containing the given, single {@link Person}.
   * @throws IllegalArgumentException if {@link Person} is {@literal null}.
   * @see #of(Person...)
   */
  public static @NotNull People one(Person person) {
    Assert.notNull(person, "Single Person is required");
    return of(person);
  }

  // Stores and orders people by last name first, date of birth (oldest to youngest), then first name and middle initial
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final Set<Person> people = new TreeSet<>((personOne, personTwo) ->
    ComparatorResultBuilder.<Comparable>create()
      .doCompare(personOne.getLastName(), personTwo.getLastName())
      .doCompare(personOne.getBirthDate().orElse(EPOCH_BIRTH_DATE), personTwo.getBirthDate().orElse(EPOCH_BIRTH_DATE))
      .doCompare(personOne.getFirstName(), personTwo.getFirstName())
      .doCompare(personOne.getMiddleName().orElse(Constants.UNDEFINED),
        personTwo.getMiddleName().orElse(Constants.UNDEFINED))
      .build()
  );

  private String name;

  private UUID id;

  /**
   * Returns the {@link UUID identifier} uniquely identifying this {@link Group} of {@link People}.
   *
   * @return the {@link UUID identifier} uniquely identifying this {@link Group} of {@link People}.
   * @see java.util.UUID
   * @see #setId(UUID)
   */
  @Override
  public @Nullable UUID getId() {
    return this.id;
  }

  /**
   * Sets the {@link String identifier} uniquely identifying this {@link Group} of {@link People}.
   *
   * @param id {@link String identifier} uniquely identifying this {@link Group} of {@link People}.
   * @see java.util.UUID
   * @see #getId()
   */
  @Override
  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  /**
   * Returns the {@link String name} for this {@link Group} of {@link People}.
   *
   * @return the {@link String name} for this {@link Group} of {@link People}.
   * @see #named(String)
   */
  @Override
  public @Nullable String getName() {

    String name = this.name;
    String resolvedName = StringUtils.hasText(name) ? name : null;

    resolvedName = resolvedName == null && Objects.nonNull(getId())
      ? GROUP_ID_NAME.formatted(getId())
      : resolvedName;

    if (resolvedName == null) {
      resolvedName = Optional.of(stream()
          .map(Person::getLastName)
          .collect(Collectors.toSet()))
        .filter(set -> Integers.isOne(set.size()))
        .map(set -> GROUP_OF_NAME.formatted(set.iterator().next()))
        .orElse(resolvedName);
    }

    return resolvedName != null ? resolvedName
      : EMPTY_NO_ID_GROUP_NAME;
  }

  @Override
  public boolean isEmpty() {
    return this.people.isEmpty();
  }

  @Override
  @SuppressWarnings("all")
  public Iterator<Person> iterator() {
    return Collections.unmodifiableSet(this.people).iterator();
  }

  @NullSafe
  @Override
  public boolean join(@Nullable Person person) {
    return person != null && this.people.add(person);
  }

  @NullSafe
  @Override
  public boolean leave(@Nullable Person person) {
    return person != null && this.people.remove(person);
  }

  /**
   * Builder method used to set the {@link String name} for this {@link Group} of {@link People}.
   *
   * @param name {@link String} containing the {@literal name} for this {@link Group} of {@link People}.
   * @return this {@link Group} of {@link People}.
   * @see #getName()
   */
  public @NotNull People named(@Nullable String name) {
    this.name = name;
    return this;
  }

  @Override
  public int size() {
    return this.people.size();
  }

  /**
   * Returns a {@link String} representation for this {@link Group} of {@link People}.
   *
   * @return a {@link String} containing the {@link Name names} of all the {@link Person people} in this {@link Group}.
   */
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder("[");

    StreamUtils.stream(this).forEach(person -> {
      builder.append(builder.length() > 1 ? StringUtils.SEMICOLON_SPACE_SEPARATOR : StringUtils.EMPTY_STRING);
      builder.append(toString(person));
    });

    builder.append("]");

    return builder.toString();
  }

  private @NotNull String toString(@NotNull Person person) {

    String resolvedMiddleName = person.getMiddleName()
      .map(StringUtils.SINGLE_SPACE::concat)
      .orElse(StringUtils.EMPTY_STRING);

    return LAST_NAME_FIRST_NAME_MIDDLE_NAME
      .formatted(person.getLastName(), person.getFirstName(), resolvedMiddleName);
  }
}
