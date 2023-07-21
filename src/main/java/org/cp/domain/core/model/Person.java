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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.cp.elements.enums.Gender;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Constants;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.Renderable;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.Id;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.support.AbstractVersionedObject;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) defining and modeling a person, or human being.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.time.LocalDate
 * @see java.time.LocalDateTime
 * @see java.util.UUID
 * @see org.cp.elements.enums.Gender
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @see org.cp.elements.lang.annotation.Id
 * @see org.cp.elements.lang.support.AbstractVersionedObject
 * @since 0.1.0
 */
@FluentApi
public class Person extends AbstractVersionedObject<Person, UUID>
    implements Cloneable, Comparable<Person>, Identifiable<Long>, Nameable<Name>, Renderable, Serializable, Visitable {

  @Serial
  private static final long serialVersionUID = -8623980477296948648L;

  protected static final String BIRTH_DATE_PATTERN = "yyyy-MM-dd";
  protected static final String BIRTH_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm a";
  protected static final String DATE_OF_DEATH_PATTERN = BIRTH_DATE_PATTERN;
  protected static final String DATE_TIME_OF_DEATH_PATTERN = BIRTH_DATE_TIME_PATTERN;

  // JSON String representation
  protected static final String PERSON_TO_STRING =
    "{ @type = %1$s, firstName = %2$s, middleName = %3$s, lastName = %4$s,"
      + " birthDate = %5$s, dateOfDeath = %6$s, gender = %7$s }";

  /**
   * Factory method used to construct a new {@link Person} copied from the given, required {@link Person}.
   *
   * @param person {@link Person} to copy; must not be {@literal null}.
   * @return a new {@link Person} copied from and initialized with the given {@link Person}.
   * @throws IllegalArgumentException if {@link Person} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newPerson(Name, LocalDateTime)
   * @see #as(Gender)
   */
  @Dsl
  public static @NotNull Person from(@NotNull Person person) {

    Assert.notNull(person, "Person to copy is required");

    return newPerson(person.getName(), person.getBirthDate().orElse(null))
      .as(person.getGender().orElse(null));
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with the given, required {@link Name}.
   *
   * @param name {@link Name} of the {@link Person}; must not be {@literal null}.
   * @return a new {@link Person} initialized with the given {@link Name}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.core.model.Name
   * @see #Person(Name)
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull Name name) {
    return new Person(name);
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with the given, required {@link Name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link Name} of the {@link Person}; must not be {@literal null}.
   * @param birthDate {@link LocalDateTime} specifying the {@link Person person's} {@literal date of birth}.
   * @return a new {@link Person} initialized with the given, required {@link Name}
   * and {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.core.model.Name
   * @see #Person(Name, LocalDateTime)
   * @see java.time.LocalDateTime
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull Name name, @Nullable LocalDateTime birthDate) {
    return new Person(name, birthDate);
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of the {@link Person};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Person} initialized with the given, required {@link String name}.
   * @throws IllegalArgumentException if either the {@link String first name} or {@link String last name}
   * of the person were not given.
   * @see org.cp.domain.core.model.Name#parse(String)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newPerson(Name)
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull String name) {
    return newPerson(Name.parse(name));
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with the given, required {@link String name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link String} containing the {@literal name} of the {@link Person};
   * must not be {@literal null} or {@literal empty}.
   * @param birthDate {@link LocalDateTime} declaring the {@link Person person's} {@literal date of birth}.
   * @return a new {@link Person} initialized with the given, required {@link String name}
   * and {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if either the {@link String first name} or {@link String last name}
   * of the person were not given.
   * @see org.cp.domain.core.model.Name#parse(String)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newPerson(Name, LocalDateTime)
   * @see java.time.LocalDateTime
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull String name, @Nullable LocalDateTime birthDate) {
    return newPerson(Name.parse(name), birthDate);
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with
   * the given {@link String first name} and {@link String last name}.
   *
   * @param firstName {@link String} containing the {@link Person person's} {@literal first name};
   * must not be {@literal null} or {@literal empty}.
   * @param lastName {@link String} containing the {@link Person person's} {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Person} initialized with the given, required {@link String first name}
   * and {@link String last name}.
   * @throws IllegalArgumentException if either the {@link String first name} or {@link String last name}
   * of the person were not given.
   * @see org.cp.domain.core.model.Name#of(String, String)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newPerson(Name)
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull String firstName, @NotNull String lastName) {
    return newPerson(Name.of(firstName, lastName));
  }

  /**
   * Factory method used to construct a new {@link Person} initialized with the given,
   * required {@link String first name}, {@link String last name} and {@link LocalDateTime date of birth}.
   *
   * @param firstName {@link String} containing the {@link Person person's} {@literal first name};
   * must not be {@literal null} or {@literal empty}.
   * @param lastName {@link String} containing the {@link Person person's} {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @param birthDate {@link LocalDateTime} specifying the {@link Person person's} {@literal date of birth}.
   * @return a new {@link Person} initialized with the given, required {@link String first name}
   * and {@link String last name} as well as {@link LocalDateTime date of birth}.
   * @throws IllegalArgumentException if either the {@link String first name} or {@link String last name}
   * of the person were not given.
   * @see org.cp.domain.core.model.Name#of(String, String)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newPerson(Name, LocalDateTime)
   * @see java.time.LocalDateTime
   */
  @Dsl
  public static @NotNull Person newPerson(@NotNull String firstName, @NotNull String lastName,
      @Nullable LocalDateTime birthDate) {

    return newPerson(Name.of(firstName, lastName), birthDate);
  }

  private Gender gender;

  private LocalDateTime birthDate;
  private LocalDateTime dateOfDeath;

  @Id
  private Long id;

  private final Name name;

  /**
   * Constructs a new {@link Person} initialized with the given, required {@link Name}
   * and no {@link LocalDateTime date of birth}.
   *
   * @param name {@link Name} of the {@link Person}; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see #Person(Name, LocalDateTime)
   */
  public Person(@NotNull Name name) {
    this(name, null);
  }

  /**
   * Constructs a new {@link Person} initialized with the given, required {@link Name}
   * and {@link LocalDateTime date of birth}.
   *
   * @param name {@link Name} of the {@link Person}; must not be {@literal null}.
   * @param birthDate {@link LocalDateTime birth date} of the {@link Person}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.domain.core.model.Name
   * @see java.time.LocalDateTime
   */
  public Person(@NotNull Name name, @Nullable LocalDateTime birthDate) {

    this.name = ObjectUtils.requireObject(name, "Name is required");
    this.birthDate = birthDate;
  }

  /**
   * Determines whether this {@link Person} is alive.
   *
   * @return a boolean value indicating whether this {@link Person} is alive.
   * @see #getDateOfDeath()
   */
  public boolean isAlive() {
    return getDateOfDeath().isEmpty();
  }

  /**
   * Determines whether this {@link Person} has been born yet.
   * <p>
   * This {@link Person} may have a {@link #getBirthDate() birth date} in the future representing an expected
   * {@link LocalDateTime date of birth} for an unborn child.
   *
   * @return a boolean value indicating whether this {@link Person} has been born yet.
   * @see java.time.LocalDateTime#now()
   * @see #getBirthDate()
   */
  public boolean isBorn() {

    return getBirthDate()
      .filter(birthDate -> birthDate.isBefore(LocalDateTime.now()))
      .isPresent();
  }

  /**
   * Determines whether this {@link Person} is {@link Gender#FEMALE female}.
   *
   * @return a boolean value indicating whether this {@link Person} is {@link Gender#FEMALE female}.
   * @see org.cp.elements.enums.Gender#FEMALE
   * @see #getGender()
   */
  public boolean isFemale() {

    return getGender()
      .filter(Gender.FEMALE::equals)
      .isPresent();
  }

  /**
   * Determines whether this {@link Person} is {@link Gender#MALE male}.
   *
   * @return a boolean value indicating whether this {@link Person} is {@link Gender#MALE male}.
   * @see org.cp.elements.enums.Gender#MALE
   * @see #getGender()
   */
  public boolean isMale() {

    return getGender()
      .filter(Gender.MALE::equals)
      .isPresent();
  }

  /**
   * Determines whether this {@link Person} identifies as {@link Gender#NON_BINARY}.
   *
   * @return a boolean value indicating whether this {@link Person} identifies as {@link Gender#NON_BINARY}.
   * @see org.cp.elements.enums.Gender#NON_BINARY
   * @see #getGender()
   */
  public boolean isNonBinary() {

    return getGender()
      .filter(Gender.NON_BINARY::equals)
      .isPresent();
  }

  /**
   * Sets the {@link Long identifier} uniquely identifying this {@link Person}.
   *
   * @param id {@link Long identifier} uniquely identifying this {@link Person}.
   * @see org.cp.elements.lang.Identifiable#setId(Comparable)
   * @see java.lang.Long
   */
  @Override
  public void setId(@Nullable Long id) {
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
  public @Nullable Long getId() {
    return this.id;
  }

  /**
   * Given this {@link Person person's} {@link #getBirthDate() date of birth},
   * determine this {@link Person person's {@link Integer age}.
   * <p>
   * @return the {@link Integer age} of this {@link Person} based on his or her {@link #getBirthDate() date of birth}
   * with consideration of the {@link Person Person's} {@link #getDateOfDeath() date of death}.
   * @see java.time.Period#between(LocalDate, LocalDate)
   * @see java.time.Period#getYears()
   * @see java.time.LocalDate#now()
   * @see #getDateOfDeath()
   * @see #getBirthDate()
   */
  public Optional<Integer> getAge() {

    return getBirthDate()
      .map(birthDate -> {

        LocalDate endDate = getDateOfDeath()
          .map(LocalDateTime::toLocalDate)
          .orElseGet(LocalDate::now);

        return Period.between(birthDate.toLocalDate(), endDate);

      })
      .map(Period::getYears)
      .map(years -> Math.max(years, 0));
  }

  /**
   * Sets this {@link Person person's} {@link LocalDateTime date of birth}.
   * <p>
   * A {@link Person person's} {@link LocalDateTime date of birth} represents
   * the {@link LocalDateTime actual date of birth}, and not the {@link LocalDateTime expected date of birth}.
   * Therefore, a {@link Person} cannot be {@link #isBorn() born} before {@link LocalDateTime#now() today}.
   *
   * @param birthDate {@link LocalDateTime} declaring this {@link Person person's} {@literal date of birth}.
   * @throws IllegalArgumentException if the {@link LocalDateTime birth date} is in the future.
   * @see java.time.LocalDateTime
   * @see #isBorn()
   */
  public void setBirthDate(@Nullable LocalDateTime birthDate) {

    if (birthDate != null) {

      LocalDateTime now = LocalDateTime.now();

      Assert.isFalse(birthDate.isAfter(now), () -> {

        DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern(BIRTH_DATE_PATTERN);

        return String.format("Birth date [%1$s] must be on or before today [%2$s]",
          birthDateFormatter.format(birthDate.toLocalDate()), birthDateFormatter.format(now.toLocalDate()));
      });
    }

    this.birthDate = birthDate;
  }

  /**
   * Returns this {@link Person person's} {@link LocalDateTime date of birth}.
   * <p>
   * A {@link Person person's} {@link LocalDateTime date of birth} represents
   * the {@link LocalDateTime actual date of birth}, and not the {@link LocalDateTime expected date of birth}.
   * Therefore, a {@link Person} cannot be {@link #isBorn() born} before {@link LocalDateTime#now() today}.
   *
   * @return an {@link Optional} containing this {@link Person person's} {@link LocalDateTime date of birth}.
   * @see java.time.LocalDateTime
   * @see java.util.Optional
   */
  public Optional<LocalDateTime> getBirthDate() {
    return Optional.ofNullable(this.birthDate);
  }

  /**
   * Set's the {@link Person person's} {@link LocalDateTime date of death}.
   *
   * @param dateOfDeath {@link LocalDateTime} declaring this {@link Person person's} {@literal date of death}.
   * @throws IllegalArgumentException if the {@link LocalDateTime date of death} is before
   * the {@link #getBirthDate() date of birth} or is in the future.
   * @see java.time.LocalDateTime#now()
   * @see java.time.LocalDateTime
   * @see #getBirthDate()
   */
  public void setDateOfDeath(@Nullable LocalDateTime dateOfDeath) {

    if (dateOfDeath != null) {

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_OF_DEATH_PATTERN);

      getBirthDate().ifPresent(birthDate -> Assert.isFalse(dateOfDeath.isBefore(birthDate), () ->
          String.format("Date of death [%1$s] cannot be before the person's date of birth [%2$s]",
            formatter.format(dateOfDeath.toLocalDate()), formatter.format(birthDate.toLocalDate()))));

      LocalDateTime now = LocalDateTime.now();

      Assert.isFalse(dateOfDeath.isAfter(now), () ->
        String.format("A person's date of death [%s] cannot be known in the future",
          formatter.format(dateOfDeath.toLocalDate())));
    }

    this.dateOfDeath = dateOfDeath;
  }

  /**
   * Returns this {@link Person person's} {@link LocalDateTime date of death}.
   *
   * @return an {@link Optional} containing this {@link Person person's} {@link LocalDateTime date of death}.
   * @see java.time.LocalDateTime
   * @see java.util.Optional
   */
  public Optional<LocalDateTime> getDateOfDeath() {
    return Optional.ofNullable(this.dateOfDeath);
  }

  /**
   * Sets this {@link Person person's} {@link Gender}.
   *
   * @param gender {@link Gender} of this {@link Person}.
   * @see org.cp.elements.enums.Gender
   */
  public void setGender(@Nullable Gender gender) {
    this.gender = gender;
  }

  /**
   * Returns this {@link Person person's} {@link Gender}.
   *
   * @return an {@link Optional} containing this {@link Person person's} {@link Gender}.
   * @see org.cp.elements.enums.Gender
   * @see java.util.Optional
   */
  public Optional<Gender> getGender() {
    return Optional.ofNullable(this.gender);
  }

  /**
   * Returns the {@link Name full name} of this {@link Person person}.
   *
   * @return the {@link Name} of this {@link Person person}; never {@literal null}.
   * @see org.cp.domain.core.model.Name
   */
  @NullSafe
  @Override
  public @NotNull Name getName() {
    return this.name;
  }

  /**
   * Returns this {@link Person person's} {@link String first name}.
   *
   * @return a {@link String} containing this {@link Person person's} {@literal first name}.
   * @see org.cp.domain.core.model.Name#getFirstName()
   * @see #getName()
   */
  public @NotNull String getFirstName() {
    return getName().getFirstName();
  }

  /**
   * Returns this {@link Person person's} {@link String last name}.
   *
   * @return a {@link String} containing this {@link Person person's} {@literal last name}.
   * @see org.cp.domain.core.model.Name#getLastName()
   * @see #getName()
   */
  public @NotNull String getLastName() {
    return getName().getLastName();
  }

  /**
   * Returns this {@link Person person's} {@link String middle name}.
   *
   * @return an {@link Optional} containing this {@link Person person's} {@literal middle name or initial(s)}.
   * @see org.cp.domain.core.model.Name#getMiddleName()
   * @see java.util.Optional
   * @see #getName()
   */
  public Optional<String> getMiddleName() {
    return getName().getMiddleName();
  }

  /**
   * Builder method used to estimate and set this {@link Person person's}
   * {@link #setBirthDate(LocalDateTime) date of birth} based on his or her {@link Integer age}.
   * <p>
   * Additionally, this {@link Person person's} {@link LocalDateTime date of birth} is set to
   * the current {@literal month}, {@literal day}, {@literal hour}, {@literal minute} and {@literal second}.
   *
   * @param age {@link Integer} value specifying this {@link Person person's} age.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link Integer age} is less than {@literal 0}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #born(LocalDateTime)
   */
  @Dsl
  public @NotNull Person age(int age) {

    Assert.isTrue(age >= 0, "Age [%s] must be greater than equal to 0", age);

    return born(LocalDateTime.now().minusYears(age));
  }

  /**
   * Builder method used to set this {@link Person person's} {@link Gender}.
   *
   * @param gender {@link Gender} of this {@link Person}.
   * @return this {@link Person}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.elements.enums.Gender
   * @see #setGender(Gender)
   */
  @Dsl
  public @NotNull Person as(@Nullable Gender gender) {
    setGender(gender);
    return this;
  }

  /**
   * Builder method used to identify this {@link Person} as a {@link Gender#FEMALE}.
   *
   * @return this {@link Person}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.elements.enums.Gender#FEMALE
   * @see #as(Gender)
   */
  @Dsl
  public @NotNull Person asFemale() {
    as(Gender.FEMALE);
    return this;
  }

  /**
   * Builder method used to identify this {@link Person} as a {@link Gender#MALE}.
   *
   * @return this {@link Person}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.elements.enums.Gender#MALE
   * @see #as(Gender)
   */
  @Dsl
  public @NotNull Person asMale() {
    as(Gender.MALE);
    return this;
  }

  /**
   * Builder method used to identify this {@link Person} as a {@link Gender#NON_BINARY} individual.
   *
   * @return this {@link Person}.
   * @see org.cp.elements.enums.Gender#NON_BINARY
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Gender)
   */
  @Dsl
  public @NotNull Person asNonBinary() {
    as(Gender.NON_BINARY);
    return this;
  }

  /**
   * Builder method used to set this {@link Person person's} {@link LocalDateTime date of birth}.
   *
   * @param birthDate {@link LocalDateTime} specifying this {@link Person person's} date of birth.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link LocalDateTime birth date} is in the future.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #setBirthDate(LocalDateTime)
   * @see java.time.LocalDateTime
   */
  @Dsl
  public @NotNull Person born(@Nullable LocalDateTime birthDate) {
    setBirthDate(birthDate);
    return this;
  }

  /**
   * Builder method used to change this {@link Person person's} {@link Name name}.
   *
   * @param name new {@link Name} for this {@link Person}.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if {@link Name} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.core.model.Name
   * @see #newPerson(Name)
   */
  @Dsl
  public @NotNull Person change(@NotNull Name name) {
    return newPerson(ObjectUtils.requireObject(name, "Name is required"));
  }

  /**
   * Builder method used to change this {@link Person person's} {@link String last name}.
   *
   * @param lastName {@link String} containing this {@link Person person's} new {@literal last name};
   * must not be {@literal null} or {@literal empty}.
   * @return this {@link Person}.
   * @see org.cp.domain.core.model.Name#change(String)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #change(Name)
   * @see #getName()
   */
  @Dsl
  public @NotNull Person change(@NotNull String lastName) {
    return change(getName().change(lastName));
  }

  /**
   * Builder method used to set this {@link Person person's} {@link LocalDateTime date of death}.
   *
   * @param dateOfDeath {@link LocalDateTime} indicating this {@link Person's} date of death.
   * @return this {@link Person}.
   * @throws IllegalArgumentException if the {@link LocalDateTime date of death} is in the future
   * or is before the {@link #getBirthDate() date of birth}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #setDateOfDeath(LocalDateTime)
   * @see java.time.LocalDateTime
   */
  @Dsl
  public @NotNull Person died(@Nullable LocalDateTime dateOfDeath) {
    setDateOfDeath(dateOfDeath);
    return this;
  }

  /**
   * Accepts the given {@link Visitor} visiting this {@link Person} to perform whatever data access operations
   * are required on this {@link Person} by the application at time of visitation.
   *
   * @param visitor {@link Visitor} visiting this {@link Person}; must not be {@literal null}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  public void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Clones this {@link Person}.
   *
   * Copies the {@link Person person's} {@link Name}, {@link LocalDateTime date of birth} and {@link Gender}.
   *
   * @return a new {@link Person} cloned from this {@link Person}.
   * @throws CloneNotSupportedException if the {@literal clone} operation is not supported.
   * @see #from(Person)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link Person} to another {@link Person} in order to determine the natural ordering (sort order)
   * in a list of {@link Person people}.
   * <p>
   * This method determines the natural ordering (sort order) for a list of {@link Person people}
   * using {@link #getLastName()}  last name}, {@link #getFirstName() first name}, {@link #getMiddleName() middle name},
   * or the person's initial(s) if present, and {@link #getBirthDate() date of birth}, in ascending order.
   *
   * @param that {@link Person} being compared with the given {@link Person}; must not be {@literal null}.
   * @return an {@link Integer#TYPE} value indicating the natural order of this {@link Person} relative to
   * the other {@link Person}. A value less than {@literal 0} indicates this {@link Person} comes before
   * the other {@link Person}. A value greater than {@literal 0} indicates this {@link Person} comes after
   * the other {@link Person}. And, {@literal 0} represents that both {@link Person people} are equal.
   * @see org.cp.elements.util.ComparatorResultBuilder
   * @see #getBirthDate()
   * @see #getName()
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull Person that) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getName(), that.getName())
      .doCompare(this.getBirthDate().orElse(null), that.getBirthDate().orElse(null))
      .build();
  }

  /**
   * Determines whether this {@link Person} is equal to the given {@link Object}.
   * <p>
   * Equality for {@link Person people} is determined based on the natural identifier of a {@link Person}.
   * <p>
   * A {@link Person} can usually be uniquely identified by his or her {@link #getBirthDate() date of birth},
   * {@link #getFirstName() first name}, optionally {@link #getMiddleName() middle name or initial(s)}
   * and {@link #getLastName() last name}.
   * <p>
   * A {@link Person} cannot be identified by his or her {@link Name} only since multiple {@link Person people}
   * can have the same {@link Name}. Additionally, more than 1 {@link Person} can also have the same
   * {@link LocalDateTime date of birth}. Therefore, both {@link Name} and {@link LocalDateTime date of birth}
   * are needed to properly identify a {@link Person}.
   * <p>
   * It is possible that 2 or more {@link Person people} can have the same {@link Name}
   * and {@link LocalDateTime date of birth}. However, the likely-hood of such occurrences should be rather small
   * in the general population covered by the application. If it is possible that the application will process
   * {@link Person people} that have the same {@link Name} and {@link LocalDateTime date of birth}, then the user
   * should override the {@literal equals} method.
   *
   * @param obj {@link Object} to evaluated for equality with this {@link Person}.
   * @return a boolean value indicating whether this {@link Person} is equal to the given {@link Object}.
   * @see #getBirthDate()
   * @see #getName()
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Person that)) {
      return false;
    }

    return ObjectUtils.equals(this.getBirthDate(), that.getBirthDate())
      && ObjectUtils.equals(this.getName(), that.getName());
  }

  /**
   * Computes the {@link Integer hash code} of this {@link Person}.
   * <p>
   * Like the {@link #equals(Object)} method, the {@link Integer hash code} is determined from
   * the {@link Person person's} {@link #getBirthDate() date of birth} and {@link #getName() name}.
   *
   * @return the computed {@link Integer hash code} of this {@link Person}.
   * @see #getBirthDate()
   * @see #getName()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getBirthDate(), getName());
  }

  /**
   * Returns a {@link String} representation of this {@link Person}.
   *
   * @return a {@link String} containing the current state of this {@link Person}.
   * @see #getBirthDate()
   * @see #getDateOfDeath()
   * @see #getGender()
   * @see #getName()
   */
  @Override
  public @NotNull String toString() {

    String resolvedMiddleName = getMiddleName().orElse(Constants.UNKNOWN);

    return String.format(PERSON_TO_STRING, getClass().getName(),
      getFirstName(), resolvedMiddleName, getLastName(), birthDateAsString(), dateOfDeathAsString(),
        getGender().map(Gender::toString).orElse(Constants.UNKNOWN));
  }

  private @NotNull String birthDateAsString() {

    return getBirthDate()
      .map(birthDate -> DateTimeFormatter.ofPattern(BIRTH_DATE_TIME_PATTERN).format(birthDate))
      .orElse(Constants.UNKNOWN);
  }

  private @NotNull String dateOfDeathAsString() {

    return getDateOfDeath()
      .map(dateOfDeath -> DateTimeFormatter.ofPattern(DATE_TIME_OF_DEATH_PATTERN).format(dateOfDeath))
      .orElse(Constants.UNKNOWN);
  }
}
