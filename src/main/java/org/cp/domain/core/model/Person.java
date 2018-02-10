/*
 * Copyright 2017 Author or Authors.
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

import static org.cp.elements.lang.Constants.UNKNOWN;
import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.cp.elements.enums.Gender;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Id;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) defining and modeling a person.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.time.LocalDateTime
 * @see org.cp.elements.enums.Gender
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.Id
 * @since 1.0.0
 */
public class Person implements Cloneable, Comparable<Person>, Identifiable<Long>, Nameable<Name>,
    Serializable, Visitable {

  private static final long serialVersionUID = -1532809025522184045L;

  protected static final String BIRTH_DATE_PATTERN = "yyyy-MM-dd";
  protected static final String BIRTH_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm a";

  /**
   * Factory method used to construct a new instance of {@link Person} copied from and initialized with
   * the given {@link Person}.
   *
   * @param person {@link Person} to copy.
   * @return a new instance of {@link Person} copied from and initialized with the given {@link Person}.
   * @throws IllegalArgumentException if {@link Person} is {@literal null}.
   * @see #newPerson(Name, LocalDateTime)
   * @see #as(Gender)
   */
  public static Person from(Person person) {

    return Optional.ofNullable(person)
      .map(it -> newPerson(it.getName(), it.getBirthDate().orElse(null)).as(it.getGender().orElse(null)))
      .orElseThrow(() -> newIllegalArgumentException("Person is required"));
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with given {@link String name}.
   *
   * @param name {@link String} containing the name of the {@link Person}.
   * @return a new instance of {@link Person} initialized with the given {@link String name}.
   * @throws IllegalArgumentException if either {@link String first} or {@link String last name} are not specified.
   * @see org.cp.domain.core.model.Name#of(String)
   * @see java.lang.String
   * @see #newPerson(Name)
   */
  public static Person newPerson(String name) {
    return newPerson(Name.of(name));
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with given {@link String name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link String} containing the name of the {@link Person}.
   * @param birthDate {@link LocalDateTime} specifying the {@link Person person's} date of birth.
   * @return a new instance of {@link Person} initialized with the given {@link String name}
   * and {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if either {@link String first} or {@link String last name} are not specified.
   * @see org.cp.domain.core.model.Name#of(String)
   * @see java.lang.String
   * @see java.time.LocalDateTime
   * @see #newPerson(Name, LocalDateTime)
   */
  public static Person newPerson(String name, LocalDateTime birthDate) {
    return newPerson(Name.of(name), birthDate);
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with
   * a {@link String first} and {@link String last name}.
   *
   * @param firstName {@link String} containing the {@link Person person's} first name.
   * @param lastName {@link String} containing the {@link Person person's} last name.
   * @return a new instance of {@link Person} initialized with a {@link String first} and {@link String last name}.
   * @throws IllegalArgumentException if either {@link String first} or {@link String last name} are not specified.
   * @see org.cp.domain.core.model.Name#of(String, String)
   * @see java.lang.String
   * @see #newPerson(Name)
   */
  public static Person newPerson(String firstName, String lastName) {
    return newPerson(Name.of(firstName, lastName));
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with a {@link String first}
   * and {@link String last name} as well as {@link LocalDateTime date of birth}.
   *
   * @param firstName {@link String} containing the {@link Person person's} first name.
   * @param lastName {@link String} containing the {@link Person person's} last name.
   * @param birthDate {@link LocalDateTime} specifying the {@link Person person's} date of birth.
   * @return a new instance of {@link Person} initialized with a {@link String first} and {@link String last name)
   * as well as {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if either {@link String first} or {@link String last name} are not specified.
   * @see org.cp.domain.core.model.Name#of(String, String)
   * @see java.lang.String
   * @see java.time.LocalDateTime
   * @see #newPerson(Name, LocalDateTime)
   */
  public static Person newPerson(String firstName, String lastName, LocalDateTime birthDate) {
    return newPerson(Name.of(firstName, lastName), birthDate);
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with the given {@link Name}.
   *
   * @param name {@link Name} of the {@link Person}.
   * @return a new instance of {@link Person} initialized with the given {@link Name}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see #Person(Name)
   */
  public static Person newPerson(Name name) {
    return new Person(name);
  }

  /**
   * Factory method used to construct a new instance of {@link Person} initialized with the given {@link Name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link Name} of the {@link Person}.
   * @param birthDate {@link LocalDateTime} specifying the {@link Person person's} date of birth.
   * @return a new instance of {@link Person} initialized with the given {@link Name}
   * and {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see java.time.LocalDateTime
   * @see #Person(Name, LocalDateTime)
   */
  public static Person newPerson(Name name, LocalDateTime birthDate) {
    return new Person(name, birthDate);
  }

  private Gender gender;

  private LocalDateTime birthDate;

  @Id
  private Long id;

  private Name name;

  /**
   * Constructs a new instance of {@link Person} initialized with the given {@link Name}.
   *
   * @param name {@link Name} of the {@link Person}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see #Person(Name, LocalDateTime)
   */
  public Person(Name name) {
    this(name, null);
  }

  /**
   * Constructs a new instance of {@link Person} initialized with the given {@link Name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link Name} of the {@link Person}.
   * @param birthDate {@link LocalDateTime birth date} of the {@link Person}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see java.time.LocalDateTime
   */
  public Person(Name name, LocalDateTime birthDate) {

    Assert.notNull(name, "Name is required");

    this.name = name;
    this.birthDate = birthDate;
  }

  /**
   * Determines whether this {@link Person} has been born.
   *
   * This is a simple implementation that just determines whether the {@link #getBirthDate() birth date}
   * for this {@link Person} has been set or not (i.e. is "present"), particularly since
   * the {@link #getBirthDate() birth date} of a {@link Person} cannot be set in the future.
   *
   * The absence of a {@link #getBirthDate() birth date} can be taken to mean this {@link Person} is unborn,
   * and therefore this method returns {@literal false} in that case.
   *
   * @return a boolean value indicating whether this {@link Person} has been born.
   * @see #getBirthDate()
   */
  public boolean isBorn() {
    return getBirthDate().isPresent();
  }

  /**
   * Determines whether this {@link Person} is {@link Gender#FEMALE female}.
   *
   * @return a boolean value indicating whether this {@link Person} is {@link Gender#FEMALE female}.
   * @see org.cp.elements.enums.Gender#FEMALE
   * @see #getGender()
   */
  public boolean isFemale() {
    return getGender().filter(Gender.FEMALE::equals).isPresent();
  }

  /**
   * Determines whether this {@link Person} is {@link Gender#MALE male}.
   *
   * @return a boolean value indicating whether this {@link Person} is {@link Gender#MALE male}.
   * @see org.cp.elements.enums.Gender#MALE
   * @see #getGender()
   */
  public boolean isMale() {
    return getGender().filter(Gender.MALE::equals).isPresent();
  }

  /**
   * Sets the {@link Long identifier} uniquely identifying this {@link Person}.
   *
   * @param id {@link Long identifier} uniquely identifying this {@link Person}.
   * @see org.cp.elements.lang.Identifiable#setId(Comparable)
   * @see java.lang.Long
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the {@link Long identifier} uniquely identifying this {@link Person}.
   *
   * @return the {@link Long identifier} uniquely identifying this {@link Person}.
   * @see org.cp.elements.lang.Identifiable#getId()
   * @see java.lang.Long
   */
  @Override
  public Long getId() {
    return this.id;
  }

  /**
   * Given this {@link Person person's} {@link #getBirthDate() date of birth},
   * determine this {@link Person person's} age.
   *
   * @return the age of this {@link Person} based on his/her {@link #getBirthDate() date of birth}.
   * @see java.time.LocalDate#now()
   * @see java.time.Period#between(LocalDate, LocalDate)
   * @see java.time.Period#getYears()
   * @see #getBirthDate()
   */
  public Optional<Integer> getAge() {
    return getBirthDate().map(birthDate -> Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears());
  }

  /**
   * Sets this {@link Person person's} {@link LocalDateTime date of birth}.
   *
   * @param birthDate {@link LocalDateTime} indicating this {@link Person person's} date of birth;
   * may be {@literal null}.
   * @throws IllegalArgumentException if {@link LocalDateTime birth date} is after this very instant.
   * @see java.time.LocalDateTime
   */
  public void setBirthDate(LocalDateTime birthDate) {

    if (birthDate != null) {

      LocalDateTime now = LocalDateTime.now();

      Assert.isTrue(now.compareTo(birthDate) >= 0, () -> {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BIRTH_DATE_PATTERN);

        return String.format("Birth date [%1$s] must be on or before today [%2$s]",
          formatter.format(birthDate.toLocalDate()), formatter.format(now.toLocalDate()));
      });
    }

    this.birthDate = birthDate;
  }

  /**
   * Returns this {@link Person person's} {@link LocalDateTime date of birth}.
   *
   * @return an {@link Optional} with this {@link Person person's} {@link LocalDateTime date of birth}.
   * @see java.time.LocalDateTime
   * @see java.util.Optional
   */
  public Optional<LocalDateTime> getBirthDate() {
    return Optional.ofNullable(this.birthDate);
  }

  /**
   * Sets this {@link Person person's} {@link Gender}.
   *
   * @param gender {@link Gender} of this {@link Person}; may be {@literal null}.
   * @see org.cp.elements.enums.Gender
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }

  /**
   * Returns this {@link Person person's} {@link Gender}.
   *
   * @return an {@link Optional} with this {@link Person person's }{@link Gender}.
   * @see org.cp.elements.enums.Gender
   * @see java.util.Optional
   */
  public Optional<Gender> getGender() {
    return Optional.ofNullable(this.gender);
  }

  /**
   * Returns this {@link Person person's} {@link String first name}.
   *
   * @return a {@link String} containing this {@link Person person's} first name.
   * @see org.cp.domain.core.model.Name#getFirstName()
   * @see java.lang.String
   * @see #getName()
   */
  public String getFirstName() {
    return getName().getFirstName();
  }

  /**
   * Returns this {@link Person person's} {@link String last name}.
   *
   * @return a {@link String} containing this {@link Person person's} last name.
   * @see org.cp.domain.core.model.Name#getLastName()
   * @see java.lang.String
   * @see #getName()
   */
  public String getLastName() {
    return getName().getLastName();
  }

  /**
   * Returns this {@link Person person's} {@link String middle name}.
   *
   * @return an {@link Optional} containing this {@link Person person's} middle name.
   * @see org.cp.domain.core.model.Name#getMiddleName()
   * @see java.lang.String
   * @see java.util.Optional
   * @see #getName()
   */
  public Optional<String> getMiddleName() {
    return getName().getMiddleName();
  }

  /**
   * Returns the {@link Name full name} of this {@link Person person}.
   *
   * @return a {@link Name} object containing the full name of this {@link Person person}; never {@literal null}.
   * @see org.cp.domain.core.model.Name
   */
  @Override
  public Name getName() {
    return this.name;
  }

  /**
   * Builder method used to set this {@link Person person's} {@link #setBirthDate(LocalDateTime) birth date}
   * based on his/her age.
   *
   * Additionally, this {@link Person person's} {@link LocalDateTime date of birth} is set to
   * the current month, day, hour, minute and second.
   *
   * @param age integer value specifying this {@link Person person's} age.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link Integer#TYPE age} is less than equal to {@literal 0}.
   * @see #born(LocalDateTime)
   */
  public Person age(int age) {

    Assert.isTrue(age > 0, "Age [%s] must be greater than 0", age);

    return born(LocalDateTime.now().minusYears(age));
  }

  /**
   * Builder method used to set this {@link Person person's} {@link Gender}.
   *
   * @param gender {@link Gender} of this {@link Person}.
   * @return this {@link Person}.
   * @see org.cp.elements.enums.Gender
   * @see #setGender(Gender)
   */
  public Person as(Gender gender) {
    setGender(gender);
    return this;
  }

  /**
   * Builder method used to set this {@link Person person's} {@link LocalDateTime date of birth}.
   *
   * @param birthDate {@link LocalDateTime} specifying this {@link Person person's} date of birth.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link LocalDateTime birth date} is after this very instant.
   * @see java.time.LocalDateTime
   * @see #setBirthDate(LocalDateTime)
   */
  public Person born(LocalDateTime birthDate) {
    setBirthDate(birthDate);
    return this;
  }

  /**
   * Builder method used to change this {@link Person person's} {@link Name name}.
   *
   * @param name new {@link Name} for this {@link Person}.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   */
  public Person change(Name name) {

    Assert.notNull(name, "Name is required");

    this.name = name;

    return this;
  }

  /**
   * Builder method used to change this {@link Person person's} {@link String last name}.
   *
   * @param lastName {@link String} containing this {@link Person person's} new last name.
   * @return this {@link Person}.
   * @see org.cp.domain.core.model.Name#change(String)
   * @see java.lang.String
   * @see #getName()
   */
  public Person change(String lastName) {
    this.name = getName().change(lastName);
    return this;
  }

  /**
   * Accepts the given {@link Visitor} visiting this {@link Person} to perform whatever data access operations
   * are required on this {@link Person} by the application.
   *
   * @param visitor {@link Visitor} visiting this {@link Person}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Clones this {@link Person}.
   *
   * @return a new {@link Person} cloned from this {@link Person}.
   * @throws CloneNotSupportedException if the clone operation is not supported.
   * @see #from(Person)
   */
  @Override
  @SuppressWarnings("all")
  public Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link Person} to another {@link Person} in order to determine the nature ordering (sort)
   * for a group of {@link Person people}.
   *
   * The natural order for a group of {@link Person people} as determined by this method is last name, first name,
   * middle name (or initials if present) and date of birth in ascending order.
   *
   * @param other {@link Person} being compared relative to this {@link Person}.
   * @return a integer value indicating the natural order of this {@link Person} relative to the other {@link Person}.
   * Less than {@literal 0} indicates this {@link Person} comes before the other {@link Person},
   * Greater than {@literal 0} indicates this {@link Person} comes after the other {@link Person},
   * and {@literal 0} represents that both {@link Person people} are equal.
   * @see org.cp.elements.util.ComparatorResultBuilder
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(Person other) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getName(), other.getName())
      .doCompare(this.getBirthDate().orElse(null), other.getBirthDate().orElse(null))
      .build();
  }

  /**
   * Determines whether this {@link Person} is equal to the given {@link Object}.
   *
   * Equality for {@link Person people} is determined based on the natural identifier of a {@link Person}.
   * A {@link Person} can be, usually, uniquely identified by his/her {@link #getBirthDate() date of birth},
   * {@link #getFirstName() first name}, optionally {@link #getMiddleName() middle name} or initial(s)
   * and {@link #getLastName() last name}.
   *
   * @param obj {@link Object} being evaluated for equality with this {@link Person}.
   * @return a boolean value indicating whether this {@link Person} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   * @see java.lang.Boolean#TYPE
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Person)) {
      return false;
    }

    Person that = (Person) obj;

    return ObjectUtils.equals(this.getBirthDate(), that.getBirthDate())
      && ObjectUtils.equals(this.getName(), that.getName());
  }

  /**
   * Computes the hash code of this {@link Person}.
   *
   * Like the {@link #equals(Object)} method, the hash code is determined from the {@link Person person's}
   * {@link #getBirthDate() date of birth} and {@link #getName() name}.
   *
   * @return the computed hash code of this {@link Person} as an {@link Integer}.
   * @see java.lang.Object#hashCode()
   * @see java.lang.Integer
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getBirthDate());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getName());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Person}.
   *
   * @return a {@link String} containing the current state of this {@link Person}.
   * @see java.lang.Object#toString()
   * @see java.lang.String
   * @see #getBirthDate()
   * @see #getGender()
   * @see #getName()
   */
  @Override
  public String toString() {

    String birthDateAsString = getBirthDate()
      .map(birthDate -> DateTimeFormatter.ofPattern(BIRTH_DATE_TIME_PATTERN).format(birthDate))
      .orElse(UNKNOWN);

    return String.format(
      "{ @type = %1$s, firstName = %2$s, middleName = %3$s, lastName = %4$s, birthDate = %5$s, gender = %6$s }",
        getClass().getName(), getFirstName(), getMiddleName().orElse(UNKNOWN), getLastName(), birthDateAsString,
          getGender().map(Gender::toString).orElse(UNKNOWN));
  }
}
