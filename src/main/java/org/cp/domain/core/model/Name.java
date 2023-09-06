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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.annotation.ThreadSafe;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling the {@link String full name} of a person, consisting of
 * a {@link String first name}, {@link String last name} and {@link String middle name or middle initial(s)}.
 * <p>
 * This type is immutable, non-extensible and Thread-safe.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.annotation.ThreadSafe
 * @since 0.1.0
 */
@FluentApi
@Immutable
@ThreadSafe
public final class Name implements Cloneable, Comparable<Name>, Nameable<Name>, Serializable, Visitable {

  public static final String DOT_SEPARATOR_REGEX = "\\.";
  public static final String NAME_COMPONENT_SEPARATOR = StringUtils.SINGLE_SPACE;
  public static final String NAME_COMPONENT_SEPARATOR_PATTERN = "\\s+";

  @SuppressWarnings("all")
  protected static final String DEFAULT_MIDDLE_NAME = null;

  @SuppressWarnings("all")
  protected static final String COMPARABLE_MIDDLE_NAME = String.valueOf(DEFAULT_MIDDLE_NAME);

  @Serial
  private static final long serialVersionUID = 83448823861933031L;

  /**
   * Factory method used to construct a new {@link Name} by copying (or cloning) an existing, required {@link Name}.
   *
   * @param name {@link Name} to copy; must not be {@literal null}.
   * @return a {@link Name} initialized from the given {@link Name}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #of(String, String, String)
   */
  @Dsl
  public static @NotNull Name from(@NotNull Name name) {

    Assert.notNull(name, "Name to copy is required");

    return of(name.getFirstName(), name.getMiddleName().orElse(DEFAULT_MIDDLE_NAME), name.getLastName());
  }

  /**
   * Factory method used to construct a new {@link Name} from the given, required {@link Nameable object}.
   *
   * @param nameable {@link Nameable object} used to construct a new {@link Name}; must not be {@literal null}.
   * @return a new {@link Name} initialized with the given {@link Nameable object}.
   * @throws IllegalArgumentException if {@link Nameable} is {@literal null}.
   * @see org.cp.elements.lang.Nameable#getName()
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #from(Name)
   */
  @Dsl
  public static @NotNull Name from(@NotNull Nameable<Name> nameable) {

    Assert.notNull(nameable, "Nameable of Name is required");

    return from(nameable.getName());
  }

  /**
   * Factory method used to construct a new {@link Name} initialized with the given, required {@link String first}
   * and {@link String last name}.
   *
   * @param firstName {@link String} containing a {@literal first name};
   * must not be {@literal null} or {@literal empty}.
   * @param lastName {@link String} containing a {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Name} initialized with the given, required {@link String first} and {@link String last name}.
   * @throws IllegalArgumentException if either the {@link String first} or {@link String last name}
   * are {@literal null} or {@literal empty}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #of(String, String, String)
   */
  @Dsl
  public static @NotNull Name of(@NotNull String firstName, @NotNull String lastName) {
    return of(firstName, DEFAULT_MIDDLE_NAME, lastName);
  }

  /**
   * Factory method used to construct a new {@link Name} initialized with the given, required {@link String first name},
   * optional {@link String middle name or middle initial(s)} and a required {@link String last name}.
   *
   * @param firstName {@link String} containing a {@literal first name};
   * must not be {@literal null} or {@literal empty}.
   * @param middleName {@link String} containing an optional {@literal middle name} or {@literal middle initial(s)}.
   * @param lastName {@link String} containing a {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Name} initialized with the given {@link String first name}, {@link String middle name}
   * and {@link String last name}.
   * @throws IllegalArgumentException if either the {@link String first} or {@link String last name}
   * are {@literal null} or {@literal empty}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #Name(String, String, String)
   */
  @Dsl
  public static @NotNull Name of(@NotNull String firstName, @Nullable String middleName, @NotNull String lastName) {
    return new Name(firstName, middleName, lastName);
  }

  /**
   * Factory method used to parse and split the given {@link String} into the individual components of a {@literal name}
   * to construct a new {@link Name} initialized with the individual components.
   *
   * @param name {@link String} containing the {@literal name} to parse;
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Name} initialized with the given, required {@link String name} parsed and split into
   * individual components of a {@literal name}: {@link String first name}, {@link String last name}
   * and (optionally) {@link String middle name or middle initial(s)}.
   * @throws IllegalArgumentException if either the {@link String first} or {@link String last name}
   * in the given {@link String name} are {@literal null} or {@literal empty}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #of(String, String, String)
   * @see #of(String, String)
   * @see #doParse(String)
   */
  @Dsl
  public static @NotNull Name parse(@NotNull String name) {

    String[] parts = doParse(name);

    return parts.length < 3
      ? of(parts[0], parts[1])
      : of(parts[0], parts[1], parts[2]);
  }

  /**
   * Parses the given {@link String} containing and representing a person's {@literal name}.
   *
   * @param name {@link String} containing the {@literal name} to parse;
   * must not be {@literal null} or {@literal empty}.
   * @return an array of {@link String Strings} containing the components of the given {@literal name},
   * such as {@link String first name} and {@link String last name}.
   * @throws IllegalArgumentException if {@link String name} is {@literal null}, {@literal empty}
   * or minimally, does not consist of both a {@link String first} and {@link String last name}.
   * @see #stripSuffix(String)
   * @see #stripTitle(String)
   */
  private static String[] doParse(@NotNull String name) {

    return Optional.ofNullable(name)
      .filter(StringUtils::hasText)
      .map(StringUtils::trim)
      .map(Name::stripSuffix)
      .map(Name::stripTitle)
      .map(it -> it.split(NAME_COMPONENT_SEPARATOR_PATTERN))
      .filter(nameParts -> nameParts.length >= 2)
      .orElseThrow(() -> newIllegalArgumentException("First and last name are required; was [%s]", name));
  }

  /**
   * Strips any {@link Suffix suffixes} from the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} to evaluate
   * @return the {@link String name} stripped of any {@link Suffix suffixes}.
   * @see org.cp.domain.core.model.Name.Suffix
   */
  private static @NotNull String stripSuffix(@NotNull String name) {

    String nonSuffixName = name;

    for (Suffix suffix : Suffix.values()) {
      int index = nonSuffixName.toLowerCase().indexOf(suffix.name().toLowerCase());
      nonSuffixName = index > -1 ? nonSuffixName.substring(0, index).trim() : nonSuffixName;
    }

    return nonSuffixName;
  }

  /**
   * Strips any {@link Title titles} from the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} to evaluate
   * @return the {@link String name} stripped of any {@link Title titles}.
   * @see org.cp.domain.core.model.Name.Title
   */
  private static @NotNull String stripTitle(@NotNull String name) {

    String nonTitleName = name.replaceAll(DOT_SEPARATOR_REGEX, StringUtils.EMPTY_STRING);

    for (Title title : Title.values()) {
      int index = nonTitleName.toLowerCase().indexOf(title.name().toLowerCase());
      nonTitleName = index > -1 ? nonTitleName.substring(index + title.name().length()).trim() : nonTitleName;
    }

    return nonTitleName;
  }

  private final String firstName;
  private final String lastName;
  private final String middleName;

  /**
   * Constructs a new {@link Name} initialized with the given, required {@link String first name},
   * an optional {@link String middle name or middle initial(s)} and required {@link String last name}.
   *
   * @param firstName {@link String} containing the {@literal first name};
   * must not be {@literal null} of {@literal empty}.
   * @param middleName {@link String} containing an optional {@link String middle name}
   * or {@link String middle initial(s)}.
   * @param lastName {@link String} containing the {@literal last name};
   * must not be {@literal null} of {@literal empty}.
   * @throws IllegalArgumentException if {@link String first name} or {@link String last name} are not specified.
   */
  Name(@NotNull String firstName, @NotNull String middleName, @NotNull String lastName) {

    this.firstName = StringUtils.requireText(firstName, "First name is required");
    this.lastName = StringUtils.requireText(lastName, "Last name is required");
    this.middleName = StringUtils.hasText(middleName) ? middleName : DEFAULT_MIDDLE_NAME;
  }

  /**
   * Return the {@link String first name}.
   *
   * @return a {@link String} containing the {@literal first name}.
   */
  public @NotNull String getFirstName() {
    return this.firstName;
  }

  /**
   * Return the {@link String last name}.
   *
   * @return a {@link String} containing the {@literal last name}.
   */
  public @NotNull String getLastName() {
    return this.lastName;
  }

  /**
   * Return an {@link Optional} {@link String middle name}.
   * <p>
   * May just be the {@literal middle initial(s)}.
   *
   * @return an {@link Optional} containing the {@link String middle name or middle initial(s)}.
   * @see java.util.Optional
   */
  public Optional<String> getMiddleName() {
    return Optional.ofNullable(this.middleName)
      .filter(StringUtils::hasText);
  }

  /**
   * Return this {@link Name}.
   *
   * @return this {@link Name}.
   * @see org.cp.elements.lang.Nameable#getName()
   */
  @Override
  public @NotNull Name getName() {
    return this;
  }

  /**
   * Accepts the given {@link Visitor} to visit this {@link Name}.
   *
   * @param visitor {@link Visitor} used to visit this {@link Name}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  public void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Builder method used to change a person's {@link String last name}.
   * <p>
   * This method returns a new instance of {@link Name} initialized with this {@link Name Name's}
   * {@link #getFirstName() first name}, {@link #getMiddleName() middle name} and the given, new
   * and required {@link String last name}.
   *
   * @param lastName {@link String} containing the new {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Name} from this {@link Name} with the new {@link String last name}.
   * @throws IllegalArgumentException if the new {@link String last name} is {@literal null} or {@literal empty}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #of(String, String, String)
   * @see #getMiddleName()
   * @see #getFirstName()
   */
  @Dsl
  public @NotNull Name change(@NotNull String lastName) {
    return of(this.getFirstName(), this.getMiddleName().orElse(DEFAULT_MIDDLE_NAME), lastName);
  }

  /**
   * Determines whether the given {@link Name} and this {@link Name} are alike.
   * <p>
   * Technically, the {@link Name names} are considered {@literal alike} if they match
   * on either {@link #getFirstName() first name} or {@link #getLastName() last name}.
   *
   * @param name {@link Name} to compare.
   * @return a boolean value indicating whether the given {@link Name} and this {@link Name} are alike.
   * @see #getFirstName()
   * @see #getLastName()
   */
  @NullSafe
  public boolean like(@Nullable Name name) {

    return Objects.nonNull(name)
      && (ObjectUtils.equals(this.getFirstName(), name.getFirstName())
      || ObjectUtils.equals(this.getLastName(), name.getLastName()));
  }

  /**
   * Clones this {@link Name}.
   *
   * @return a clone (copy) of this {@link Name}.
   * @see java.lang.Object#clone()
   * @see #from(Name)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() {
    return from(this);
  }

  /**
   * Compares this {@link Name} with the given, required {@link Name} to determine the natural ordering (sort order)
   * of the {@link Name names}.
   * <p>
   * The natural order of {@link Name names} as determined by this method is {@link #getLastName() last name} first,
   * {@link #getFirstName() first name} last, and then {@link #getMiddleName() middle name or middle initial(s)}
   * used in a tiebreak, in ascending order.
   *
   * @param other {@link Name} to compare with this {@link Name}; must not be {@literal null}.
   * @return a {@link Integer value} indicating the natural order of this {@link Name} relative to
   * the given {@link Name}. A &lt; {@literal 0} value indicates this {@link Name} comes before the given {@link Name}.
   * A &gt; {@literal 0} value indicates this {@link Name} comes after the given {@link Name}. And, {@literal 0}
   * indicates both {@link Name names} are equal.
   * @see org.cp.elements.util.ComparatorResultBuilder
   */
  @Override
  public int compareTo(@NotNull Name other) {

    return ComparatorResultBuilder.<String>create()
      .doCompare(this.getLastName(), other.getLastName())
      .doCompare(this.getFirstName(), other.getFirstName())
      .doCompare(this.getMiddleName().orElse(COMPARABLE_MIDDLE_NAME),
        other.getMiddleName().orElse(COMPARABLE_MIDDLE_NAME))
      .build();
  }

  /**
   * Determines whether this {@link Name} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Name}.
   * @return a boolean value indicating whether this {@link Name} is equal to the given {@link Object}.
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Name that)) {
      return false;
    }

    return ObjectUtils.equals(this.getFirstName(), that.getFirstName())
      && ObjectUtils.equals(this.getLastName(), that.getLastName())
      && ObjectUtils.equalsIgnoreNull(this.getMiddleName().orElse(DEFAULT_MIDDLE_NAME),
        that.getMiddleName().orElse(DEFAULT_MIDDLE_NAME));
  }

  /**
   * Computes the {@link Integer hash code} of this {@link Name}.
   *
   * @return the computed {@link Integer hash code} of this {@link Name}.
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getFirstName(), getMiddleName().orElse(DEFAULT_MIDDLE_NAME), getLastName());
  }

  /**
   * Returns a {@link String} representation of this {@link Name}.
   *
   * @return a {@link String} containing the current state of this {@link Name}.
   */
  @Override
  public @NotNull String toString() {

    StringBuilder name = new StringBuilder(getFirstName());

    getMiddleName().ifPresent(middleName ->
      name.append(NAME_COMPONENT_SEPARATOR).append(middleName));

    name.append(NAME_COMPONENT_SEPARATOR);
    name.append(getLastName());

    return name.toString();
  }

  /**
   * {@link Enum Enumeration} of {@link Name} suffixes.
   *
   * @see java.lang.Enum
   */
  public enum Suffix {

    II,
    III,
    JR,
    SR;

    @Override
    public String toString() {
      return StringUtils.capitalize(name().toLowerCase())
        .concat(StringUtils.DOT_SEPARATOR);
    }
  }

  /**
   * {@link Enum Enumeration} of {@link Name} titles (prefixes).
   *
   * @see java.lang.Enum
   * @see <a href="https://en.wikipedia.org/wiki/English_honorifics">English Honorifics</a>
   */
  public enum Title {

    DR,
    LADY,
    LORD,
    MISS,
    MRS,
    MR,
    MS,
    SIR;

    @Override
    public String toString() {
      return StringUtils.capitalize(name().toLowerCase());
    }
  }
}
