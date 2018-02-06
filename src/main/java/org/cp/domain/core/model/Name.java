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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serializable;
import java.util.Optional;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a full name containing first name, last name and middle name (or initial(s)).
 *
 * This type is non-extensible and immutable.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 1.0.0
 */
@Immutable
public final class Name implements Cloneable, Comparable<Name>, Nameable<Name>, Serializable, Visitable {

  public static final String NAME_PART_SEPARATOR = StringUtils.SINGLE_SPACE;

  protected static final String DEFAULT_MIDDLE_NAME = null;

  private static final long serialVersionUID = 83448823861933031L;

  private final String firstName;
  private final String lastName;
  private final String middleName;

  /**
   * Factory method used to construct a new instance of {@link Name} by copying (or cloning) the existing {@link Name}.
   *
   * @param name {@link Name} to copy.
   * @return a new instance of {@link Name} initialized from the given {@link Name}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see #of(String, String, String)
   * @see org.cp.domain.core.model.Name
   */
  public static Name of(Name name) {

    return Optional.ofNullable(name)
      .map(it -> of(it.getFirstName(), it.getMiddleName().orElse(DEFAULT_MIDDLE_NAME), it.getLastName()))
      .orElseThrow(() -> newIllegalArgumentException("Name is required"));
  }

  /**
   * Factory method used to construct a new instance of {@link Name} for the given {@link Nameable}.
   *
   * @param nameable {@link Nameable} object used to construct a {@link Name}.
   * @return a new instance of {@link Name} initialized with the given {@link Nameable} object.
   * @throws IllegalArgumentException if {@link Nameable} is {@literal null}.
   * @see org.cp.elements.lang.Nameable#getName()
   * @see org.cp.domain.core.model.Name
   * @see #of(String, String, String)
   */
  public static Name of(Nameable<Name> nameable) {

    return Optional.ofNullable(nameable)
      .map(Nameable::getName)
      .map(Name::of)
      .orElseThrow(() -> newIllegalArgumentException("Nameable of Name is required"));
  }

  /**
   * Factory method used to parse the given {@link String} into the individual parts of a name
   * and then construct a new instance of {@link Name} initialized with the individual name components.
   *
   * @param name {@link String} containing the name to parse.
   * @return a new instance of {@link Name} initialized with the given {@link String name}
   * parsed into individual name components (i.e. first name, last name and middle name or initial(s)).
   * @throws IllegalArgumentException if either first or last name are not specified.
   * @see #of(String, String, String)
   * @see #of(String, String)
   * @see #parseName(String)
   */
  public static Name of(String name) {

    String[] parts = parseName(name);

    return (parts.length < 3 ? of(parts[0], parts[1]) : of(parts[0], parts[1], parts[2]));
  }

  /**
   * Factory method used to construct a new instance of {@link Name} initialized with the given {@link String first}
   * and {@link String last} names.
   *
   * @param firstName {@link String} containing the first name.
   * @param lastName {@link String} containing the last name.
   * @return a new instance of {@link Name} initialized to the given {@link String first} and {@link String last names}.
   * @throws IllegalArgumentException if {@link String first} or {@link String last name} are not specified.
   * @see #of(String, String, String)
   */
  public static Name of(String firstName, String lastName) {
    return of(firstName, DEFAULT_MIDDLE_NAME, lastName);
  }

  /**
   * Factory method used to construct a new instance of {@link Name} initialized with the given {@link String first},
   * {@link String middle} and {@link String last names}.
   *
   * @param firstName {@link String} containing the first name.
   * @param middleName {@link String} containing the middle name; May just be the initial(s) or even {@literal null}.
   * @param lastName {@link String} containing the last name.
   * @return a new instance of {@link Name} initialized to the given {@link String first}, {@link String middle}
   * and {@link String last names}.
   * @throws IllegalArgumentException if {@link String first} or {@link String last name} are not specified.
   * @see #Name(String, String, String)
   */
  public static Name of(String firstName, String middleName, String lastName) {
    return new Name(firstName, middleName, lastName);
  }

  /**
   * Parses the given {@link String} representing a name.
   *
   * @param name {@link String} containing the name to parse.
   * @return an array of {@link String Strings} containing the component parts of a name.
   * @throws IllegalArgumentException if {@code name} is {@literal null}
   * or does not minimally consist of both a first and last name.
   */
  private static String[] parseName(String name) {

    return Optional.ofNullable(name)
      .filter(StringUtils::hasText)
      .map(StringUtils::trim)
      .map(Name::stripSuffix)
      .map(Name::stripTitle)
      .map(it -> it.split("\\s+"))
      .filter(nameParts -> nameParts.length >= 2)
      .orElseThrow(() -> newIllegalArgumentException("First and last name are required; was [%s]", name));
  }

  /**
   * Strips any {@link Suffix Suffixes} from the given {@link String name}.
   *
   * @param name {@link String} containing the name to evaluate
   * @return the {@link String name} stripped of any {@link Suffix suffixes}.
   * @see org.cp.domain.core.model.Name.Suffix
   */
  private static String stripSuffix(String name) {

    for (Suffix suffix : Suffix.values()) {

      int index = name.toLowerCase().indexOf(suffix.name().toLowerCase());

      name = index > -1 ? name.substring(0, index).trim() : name;
    }

    return name;
  }

  /**
   * Strips any {@link Title Titles} from the given {@link String name}.
   *
   * @param name {@link String} containing the name to evaluate
   * @return the {@link String name} stripped of any {@link Title titles}.
   * @see org.cp.domain.core.model.Name.Title
   */
  private static String stripTitle(String name) {

    name = name.replaceAll("\\.", "");

    for (Title title : Title.values()) {

      int index = name.toLowerCase().indexOf(title.name().toLowerCase());

      name = index > -1 ? name.substring(index + title.name().length()).trim() : name;
    }

    return name;
  }

  /**
   * Constructs a new instance of {@link Name} initialized with the given {@link String first name},
   * {@link String middle name} (or initial(s) or even {@literal null}) and {@link String last name}.
   *
   * @param firstName {@link String} containing the first name.
   * @param middleName {@link String} containing the middle name; May just be the initial(s) or even {@literal null}.
   * @param lastName {@link String} containing the last name.
   * @throws IllegalArgumentException if {@link String first} or {@link String last name} are not specified.
   */
  Name(String firstName, String middleName, String lastName) {

    Assert.hasText(firstName, "First name is required");
    Assert.hasText(lastName, "Last name is required");

    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = Optional.ofNullable(middleName).filter(StringUtils::hasText).orElse(DEFAULT_MIDDLE_NAME);
  }

  /**
   * Return the {@link String first name}.
   *
   * @return a {@link String} containing the first name.
   * @see java.lang.String
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Return the {@link String last name}.
   *
   * @return a {@link String} containing the last name.
   * @see java.lang.String
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Return an {@link Optional} {@link String middle name}.
   *
   * May just be the initial(s).
   *
   * @return an {@link Optional} containing the {@link String middle name}.
   * @see java.util.Optional
   * @see java.lang.String
   */
  public Optional<String> getMiddleName() {
    return Optional.ofNullable(this.middleName).filter(StringUtils::hasText);
  }

  /**
   * Returns this {@link Name}.
   *
   * @return this {@link Name}.
   * @see org.cp.elements.lang.Nameable#getName()
   */
  @Override
  public Name getName() {
    return this;
  }

  /**
   * Accepts a {@link Visitor} visiting this {@link Name}.
   *
   * @param visitor {@link Visitor} accepted to visit this {@link Name}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Builder method used to change the last name.
   *
   * This method returns a new instance of {@link Name} initialized with this {@link Name Name's}
   * {@link #getFirstName() first name}, {@link #getMiddleName() middle name} and the given,
   * new {@link String last name}.
   *
   * @param lastName {@link String} containing the new last name.
   * @return a new instance of {@link Name} with the new {@link String last name}.
   * @see #of(String, String, String)
   * @see #getFirstName()
   * @see #getMiddleName()
   */
  public Name change(String lastName) {
    return of(this.getFirstName(), this.getMiddleName().orElse(DEFAULT_MIDDLE_NAME), lastName);
  }

  /**
   * Determines whether the given {@link Name} and this {@link Name} are alike in anyway.
   *
   * Technically, the {@link Name names} are considered alike if they match on either {@link #getFirstName() first name}
   * or {@link #getLastName() last name}.
   *
   * @param name {@link Name} to compare.
   * @return a boolean value indicating whether the given {@link Name} and this {@link Name} are alike in anyway.
   */
  public boolean like(Name name) {

    return Optional.ofNullable(name)
      .map(it ->
        ObjectUtils.equals(this.getFirstName(), it.getFirstName())
          || ObjectUtils.equals(this.getLastName(), it.getLastName()))
      .orElse(false);
  }

  /**
   * Clones this {@link Name}.
   *
   * @return a clone (copy) of this {@link Name}.
   * @throws CloneNotSupportedException if the clone operation is not supported.
   * @see #of(String, String, String)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return of(this);
  }

  /**
   * Compares this {@link Name} with the other given {@link Name} to determine the natural ordering (sort)
   * of the {@link Name names}.
   *
   * The nature order of {@link Name names} as determined by this method is last name, first name
   * and then middle name (or initial(s)) in ascending order.
   *
   * @param other {@link Name} being compared with this {@link Name}.
   * @return a integer value indicating the natural order of this {@link Name} relative to the other {@link Name}.
   * A &lt; 0 indicates this {@link Name} comes before the other {@link Name}, &gt; 0 indicates this {@link Name}
   * comes after the other {@link Name} and 0 indicates both {@link Name names} are equal.
   * @see org.cp.elements.util.ComparatorResultBuilder
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Name other) {

    return ComparatorResultBuilder.<String>create()
      .doCompare(this.getLastName(), other.getLastName())
      .doCompare(this.getFirstName(), other.getFirstName())
      .doCompare(this.getMiddleName().orElse("null"), other.getMiddleName().orElse("null"))
      .build();
  }

  /**
   * Determines whether this {@link Name} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} being evaluated for equality with this {@link Name}.
   * @return a boolean value indicating whether this {@link Name} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Name)) {
      return false;
    }

    Name that = (Name) obj;

    return ObjectUtils.equals(this.getFirstName(), that.getFirstName())
      && ObjectUtils.equals(this.getMiddleName(), that.getMiddleName())
      && ObjectUtils.equals(this.getLastName(), that.getLastName());
  }

  /**
   * Computes the hash code of this {@link Name}.
   *
   * @return the computed hash code of this {@link Name} as an {@link Integer}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getFirstName());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getMiddleName());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getLastName());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Name}.
   *
   * @return a {@link String} containing the current state of this {@link Name}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder name = new StringBuilder(getFirstName());

    getMiddleName().ifPresent(middleName -> name.append(NAME_PART_SEPARATOR).append(middleName));

    name.append(NAME_PART_SEPARATOR);
    name.append(getLastName());

    return name.toString();
  }

  /**
   * Enumeration representing a {@link Name} suffix.
   */
  @SuppressWarnings("unused")
  public enum Suffix {

    JR,
    SR;

    @Override
    public String toString() {
      return StringUtils.capitalize(name().toLowerCase()).concat(StringUtils.DOT_SEPARATOR);
    }
  }

  /**
   * Enumeration representing a {@link Name} title prefix.
   *
   * @see <a href="https://en.wikipedia.org/wiki/English_honorifics">English Honorifics</a>
   */
  @SuppressWarnings("unused")
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
