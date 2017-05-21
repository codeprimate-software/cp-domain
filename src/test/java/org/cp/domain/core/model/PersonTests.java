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

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.cp.elements.enums.Gender;
import org.cp.elements.io.IOUtils;
import org.cp.elements.lang.Visitor;
import org.junit.Test;

/**
 * Unit tests for {@link Person}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.core.model.Person
 * @since 1.0.0
 */
public class PersonTests {

  protected LocalDateTime getBirthDateForAge(int age) {
    return LocalDateTime.now().minusYears(Math.abs(age));
  }

  protected LocalDateTime getBirthDateInFuture(int years) {
    return LocalDateTime.now().plusYears(years);
  }

  @Test
  public void newPersonWithStringName() {
    Person person = Person.newPerson("Jon Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void newPersonWithStringNameAndBirthDate() {
    LocalDateTime birthDate = LocalDateTime.of(2000, Month.APRIL, 1, 12, 30);
    Person person = Person.newPerson("Jon J Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void newPersonWithFirstNameAndLastName() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void newPersonWithFirstNameLastNameAndDateOfBirth() {
    LocalDateTime birthDate = LocalDateTime.of(1999, Month.MAY, 15, 12, 30);
    Person person = Person.newPerson("Jon", "Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void newPersonWithName() {
    Name name = Name.of("Jon", "J", "Bloom");
    Person person = Person.newPerson(name);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void newPersonWithNameAndDateOfBirth() {
    LocalDateTime birthDate = LocalDateTime.of(1998, Month.MAY, 10, 8, 0);
    Name name = Name.of("Jon", "Jason", "Bloom");
    Person person = Person.newPerson(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void constructPersonWithName() {
    Name name = Name.of("Jon", "J", "Bloom");
    Person person = new Person(name);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void constructPersonWithNameAndDateOfBirth() {
    LocalDateTime birthDate = LocalDateTime.of(1993, Month.OCTOBER, 3, 20, 0);
    Name name = Name.of("Jon", "Jason", "Bloom");
    Person person = new Person(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPersonWithNullNameThrowsIllegalArgumentException() {
    try {
      new Person(null);
    }
    catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessage("Name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void personIsBornReturnsTrue() {
    Person person = Person.newPerson("Jon", "Bloom", LocalDateTime.of(1983, Month.SEPTEMBER, 1, 12, 0));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isTrue();
    assertThat(person.isBorn()).isTrue();
  }

  @Test
  public void personIsBornReturnsFalse() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.isBorn()).isFalse();
  }

  @Test
  public void personIsFemale() {
    Person person = Person.newPerson("Sarah", "Bloom");

    person.setGender(Gender.FEMALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);
    assertThat(person.isFemale()).isTrue();
    assertThat(person.isMale()).isFalse();
  }

  @Test
  public void personIsMale() {
    Person person = Person.newPerson("Jon", "Bloom");

    person.setGender(Gender.MALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isTrue();
  }

  @Test
  public void personIsNonGender() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isFalse();
  }

  @Test
  public void setAndGetIdIsCorrect() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getId()).isNull();

    person.setId(1L);

    assertThat(person.getId()).isEqualTo(1L);

    person.setId(2L);

    assertThat(person.getId()).isEqualTo(2L);

    person.setId(null);

    assertThat(person.getId()).isNull();
  }

  @Test
  public void personsAgeIsCorrect() {
    Person person = Person.newPerson("Jon", "Bloom", getBirthDateForAge(21));

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(21);
  }

  @Test
  public void setAndGetBirthDateIsCorrect() {
    LocalDateTime birthDate = LocalDateTime.of(2010, Month.NOVEMBER, 9, 7, 30);
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();

    person.setBirthDate(birthDate);

    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);

    person.setBirthDate(null);

    assertThat(person.getBirthDate().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBirthDateToFutureDateThrowsIllegalArgumentException() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Person.BIRTH_DATE_PATTERN);
    LocalDateTime futureBirthDate = getBirthDateInFuture(1);

    try {
      Person.newPerson("Jon", "Bloom").setBirthDate(futureBirthDate);
    }
    catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessage("Birth date [%1$s] must be on or before today [%2$s]",
        formatter.format(futureBirthDate.toLocalDate()), formatter.format(LocalDateTime.now()));

      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetGenderIsCorrect() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getGender().isPresent()).isFalse();

    person.setGender(Gender.FEMALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);

    person.setGender(Gender.MALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);

    person.setGender(null);

    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void personAgeSetsBirthDate() {
    Person person = Person.newPerson("Jon", "Bloom").age(16);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(16);
    assertThat(person.getBirthDate().map(LocalDateTime::toLocalDate).orElse(null))
      .isEqualTo(getBirthDateForAge(16).toLocalDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void personAgeWithNegativeAgeThrowsIllegalArgumentException() {
    try {
      Person.newPerson("Jon", "Bloom").age(-1);
    }
    catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessage("Age [-1] must be greater than 0");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void personBornSetsBirthDate() {
    LocalDateTime birthDate = getBirthDateForAge(42);
    Person person = Person.newPerson("Jon", "Bloom").born(birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getAge().orElse(-1)).isEqualTo(42);
  }

  @Test(expected = IllegalArgumentException.class)
  public void personBornWithFutureDateThrowsIllegalArgumentException() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Person.BIRTH_DATE_PATTERN);
    LocalDateTime futureBirthDate = getBirthDateInFuture(42);

    try {
      Person.newPerson("Jon", "Bloom").born(futureBirthDate);
    }
    catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessage("Birth date [%1$s] must be on or before today [%2$s]",
        formatter.format(futureBirthDate.toLocalDate()), formatter.format(LocalDate.now()));

      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void personLastNameChangeIsSuccessful() {
    Person person = Person.newPerson(Name.of("Jon", "Bloom"));

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getLastName()).isEqualTo("Bloom");

    person = person.change("Doe");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getLastName()).isEqualTo("Doe");
  }

  @Test
  public void personNameChangeIsSuccessful() {
    Person person = Person.newPerson("Don S Juan");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Don");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("S");
    assertThat(person.getLastName()).isEqualTo("Juan");

    person = person.change(Name.of("Jon", "Jason", "Bloom"));

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test(expected = IllegalArgumentException.class)
  public void personChangeWithNullNameThrowsIllegalArgumentException() {
    try {
      Person.newPerson("Jon", "Bloom").change((Name) null);
    }
    catch (IllegalArgumentException expected) {
      assertThat(expected).hasMessage("Name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void initializesPersonWithFluentApiCorrectly() {
    LocalDateTime birthDate = getBirthDateForAge(43);
    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom")).born(birthDate).as(Gender.MALE);

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getAge().orElse(-1)).isEqualTo(43);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
  }

  @Test
  public void acceptIsCorrect() {
    Visitor mockVisitor = mock(Visitor.class);
    Person person = Person.newPerson("Jon", "Bloom");

    person.accept(mockVisitor);

    verify(mockVisitor, times(1)).visit(eq(person));
  }

  @Test
  public void compareToItselfIsEqual() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),LocalDateTime.of(2001, Month.FEBRUARY, 1, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.compareTo(jonBloom)).isEqualTo(0);
  }

  @Test
  public void compareToIsEqual() {
    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));
    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.compareTo(jonBloomTwo)).isEqualTo(0);
  }

  @Test
  public void compareToIsGreater() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(1974, Month.MAY, 27, 12, 0));
    Person ellieBloom = Person.newPerson(Name.of("Ellie", "A", "Bloom"), LocalDateTime.of(2008, Month.AUGUST, 25, 12, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(ellieBloom).isNotNull();
    assertThat(jonBloom.compareTo(ellieBloom)).isGreaterThan(0);
  }

  @Test
  public void compareToIsLessThan() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(1974, Month.MAY, 27, 12, 0));
    Person saraBloom = Person.newPerson(Name.of("Sarah", "E", "Bloom"), LocalDateTime.of(1975, Month.JANUARY, 22, 12, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(saraBloom).isNotNull();
    assertThat(jonBloom.compareTo(saraBloom)).isLessThan(0);
  }

  @Test
  public void equalsWithEqualPeopleIsTrue() {
    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));
    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsWithIdenticalPeopleIsTrue() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 21, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.equals(jonBloom)).isTrue();
  }

  @Test
  public void equalsWithNearlyEqualPeopleIsFalse() {
    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));
    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 35));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isFalse();
  }

  @Test
  public void equalsWithSimilarPeopleIsFalse() {
    Person johnBlum = Person.newPerson(Name.of("John", "J", "Blum"), LocalDateTime.of(1974, Month.MAY, 27, 12, 0));
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(1874, Month.MAY, 27, 23, 0));

    assertThat(johnBlum).isNotNull();
    assertThat(jonBloom).isNotNull();
    assertThat(johnBlum).isNotSameAs(jonBloom);
    assertThat(johnBlum.equals(jonBloom)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsFalse() {
    assertThat(Person.newPerson("Jon", "Bloom", getBirthDateForAge(16)).equals(null)).isFalse();
  }

  @Test
  public void hashCodeForPersonIsNotZero() {
    assertThat(Person.newPerson(Name.of("Jon", "J", "Bloom"), getBirthDateForAge(42)).hashCode()).isNotEqualTo(0);
  }

  @Test
  public void hashCodeForIdenticalPeopleIsSame() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 21, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.hashCode()).isEqualTo(jonBloom.hashCode());
  }

  @Test
  public void hashCodeForEqualPeopleIsSame() {
    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));
    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(2017, Month.MAY, 19, 22, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.hashCode()).isEqualTo(jonBloomTwo.hashCode());
  }

  @Test
  public void hashCodeForDifferentPeopleIsNotEqual() {
    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"), LocalDateTime.of(1974, Month.MAY, 27, 12, 0));
    Person sarahBloom = Person.newPerson(Name.of("Sarah", "E", "Bloom"), LocalDateTime.of(1975, Month.JANUARY, 22, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(sarahBloom).isNotNull();
    assertThat(jonBloom).isNotSameAs(sarahBloom);
    assertThat(jonBloom.hashCode()).isNotEqualTo(sarahBloom.hashCode());
  }

  @Test
  public void toStringWithNameIsCorrect() {
    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.toString()).isEqualTo(String.format(
      "{ @type = %s, firstName = Jon, middleName = unknown, lastName = Bloom, birthDate = unknown, gender = unknown }",
        Person.class.getName()));
  }

  @Test
  public void toStringWithNameGenderAndDateOfBirthIsCorrect() {
    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .born(LocalDateTime.of(2000, Month.MAY, 19, 23, 30))
      .as(Gender.MALE);

    assertThat(person).isNotNull();
    assertThat(person.toString()).isEqualTo(String.format(
      "{ @type = %s, firstName = Jon, middleName = J, lastName = Bloom, birthDate = 2000-05-19 11:30 PM, gender = Male }",
      Person.class.getName()));
  }

  @Test
  public void personIsSerializable() throws IOException, ClassNotFoundException {
    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom")).born(getBirthDateForAge(42)).as(Gender.MALE);

    assertThat(person).isNotNull();

    byte[] personBytes = IOUtils.serialize(person);

    assertThat(personBytes).isNotNull();
    assertThat(personBytes).isNotEmpty();

    Person deserializedPerson = IOUtils.deserialize(personBytes);

    assertThat(deserializedPerson).isNotNull();
    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson).isEqualTo(person);
  }
}
