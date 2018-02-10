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

import static org.assertj.core.api.Assertions.assertThat;
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
import org.cp.elements.lang.Constants;
import org.cp.elements.lang.Visitor;
import org.junit.Test;

/**
 * Unit tests for {@link Person}.
 *
 * @author John Blum
 * @see java.time.LocalDateTime
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.elements.enums.Gender
 * @see org.cp.domain.core.model.Person
 * @since 1.0.0
 */
public class PersonTests {

  private LocalDateTime getBirthDateInFuture(int years) {
    return LocalDateTime.now().plusYears(Math.abs(years));
  }

  private LocalDateTime getBirthDateForAge(int age) {
    return LocalDateTime.now().minusYears(Math.abs(age));
  }
  @Test
  public void fromPerson() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .age(21)
      .as(Gender.MALE)
      .identifiedBy(1L);

    Person jonBloomCopy = Person.from(jonBloom);

    assertThat(jonBloomCopy).isNotNull();
    assertThat(jonBloomCopy).isNotSameAs(jonBloom);
    assertThat(jonBloomCopy.getId()).isNull();
    assertThat(jonBloomCopy.getAge().orElse(-1)).isEqualTo(21);
    assertThat(jonBloomCopy.getBirthDate().orElse(null).toLocalDate())
      .isEqualTo(getBirthDateForAge(21).toLocalDate());
    assertThat(jonBloomCopy.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(jonBloomCopy.getFirstName()).isEqualTo("Jon");
    assertThat(jonBloomCopy.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(jonBloomCopy.getLastName()).isEqualTo("Bloom");
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullPersonThrowsException() {

    try {
      Person.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Person is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void newPersonWithStringName() {

    Person person = Person.newPerson("Jon Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getAge().isPresent()).isFalse();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void newPersonWithStringNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(21);

    Person person = Person.newPerson("Jon J Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(21);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void newPersonWithFirstNameAndLastName() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getAge().isPresent()).isFalse();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void newPersonWithFirstNameLastNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(42);

    Person person = Person.newPerson("Jon", "Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(42);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().isPresent()).isFalse();
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void newPersonWithName() {

    Name name = Name.of("Jon", "J", "Bloom");

    Person person = Person.newPerson(name);

    assertThat(person).isNotNull();
    assertThat(person.getAge().isPresent()).isFalse();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void newPersonWithNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(16);

    Name name = Name.of("Jon", "Jason", "Bloom");

    Person person = Person.newPerson(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(16);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void constructPersonWithName() {

    Name name = Name.of("Jon", "J", "Bloom");

    Person person = new Person(name);

    assertThat(person).isNotNull();
    assertThat(person.getAge().isPresent()).isFalse();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test
  public void constructPersonWithNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(100);

    Name name = Name.of("Jon", "Jason", "Bloom");

    Person person = new Person(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(100);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getName()).isEqualTo(name);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPersonWithNullNameThrowsException() {

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
  public void isBornWithBirthDateReturnsTrue() {

    Person person = Person.newPerson("Jon", "Bloom", getBirthDateForAge(21));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isTrue();
    assertThat(person.isBorn()).isTrue();
  }

  @Test
  public void isBornWithoutBirthDateReturnsFalse() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.isBorn()).isFalse();
  }

  @Test
  public void isFemaleWithFemaleReturnsTrue() {

    Person person = Person.newPerson("Sarah", "Bloom").as(Gender.FEMALE);

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);
    assertThat(person.isFemale()).isTrue();
  }

  @Test
  public void isFemaleWithMaleReturnsFalse() {

    Person person = Person.newPerson("Jon", "Bloom").as(Gender.MALE);

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(person.isFemale()).isFalse();
  }

  @Test
  public void isMaleWithFemaleReturnsFalse() {

    Person person = Person.newPerson("Sarah", "Bloom").as(Gender.FEMALE);

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);
    assertThat(person.isMale()).isFalse();
  }

  @Test
  public void isMaleWithMaleReturnsTrue() {

    Person person = Person.newPerson("Jon", "Bloom").as(Gender.MALE);

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(person.isMale()).isTrue();
  }

  @Test
  public void isNeitherFemaleNorMaleWhenGenderIsNotSet() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isFalse();
  }

  @Test
  public void ageIsBasedOnBirthDate() {

    Person person = Person.newPerson("Jon", "Bloom", getBirthDateForAge(21));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isTrue();
    assertThat(person.getAge().orElse(-1)).isEqualTo(21);
  }

  @Test
  public void ageIsUnknownWhenBirthDateIsUnset() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getAge().isPresent()).isFalse();
  }

  @Test
  public void setAndGetBirthDate() {

    LocalDateTime birthDate = LocalDateTime.of(2010, Month.FEBRUARY, 8, 6, 26);

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();

    person.setBirthDate(birthDate);

    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);

    person.setBirthDate(null);

    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.born(birthDate)).isSameAs(person);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setBirthDateToFutureDateThrowsException() {

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
  public void setAndGetGender() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getGender().isPresent()).isFalse();

    person.setGender(Gender.FEMALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);

    person.setGender(Gender.MALE);

    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
  }

  @Test
  public void setGenderToNullIsAllowed() {

    Person person = Person.newPerson("Jon", "Bloom").as(Gender.MALE);

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);

    person.setGender(null);

    assertThat(person.getGender().isPresent()).isFalse();
  }

  @Test
  public void setAndGetId() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getId()).isNull();

    person.setId(1L);

    assertThat(person.getId()).isEqualTo(1L);
    assertThat(person.<Person>identifiedBy(2L)).isSameAs(person);
    assertThat(person.getId()).isEqualTo(2L);

    person.setId(null);

    assertThat(person.getId()).isNull();

    person.setId(1L);

    assertThat(person.getId()).isEqualTo(1L);
  }

  @Test
  public void ageSetsBirthDate() {

    Person person = Person.newPerson("Jon", "Bloom").age(16);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(16);
    assertThat(person.getBirthDate().map(LocalDateTime::toLocalDate).orElse(null))
      .isEqualTo(getBirthDateForAge(16).toLocalDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void ageWithNegativeValueThrowsException() {

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
  public void bornSetsBirthDateAndAge() {

    LocalDateTime birthDate = getBirthDateForAge(42);

    Person person = Person.newPerson("Jon", "Bloom").born(birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getAge().orElse(-1)).isEqualTo(42);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bornInFutureThrowsException() {

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
  public void changeLastNameIsSuccessful() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getLastName()).isEqualTo("Bloom");

    person = person.change("Doe");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getLastName()).isEqualTo("Doe");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeLastNameToNullThrowsException() {

    try {
      Person.newPerson("Jon", "Bloom").change((String) null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Last name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void changeNameIsSuccessful() {

    Person person = Person.newPerson("Don S Juan");

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Don");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("S");
    assertThat(person.getLastName()).isEqualTo("Juan");

    person = person.change(Name.of("Jon", "J", "Bloom"));

    assertThat(person).isNotNull();
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("J");
    assertThat(person.getLastName()).isEqualTo("Bloom");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeNameToNullThrowsException() {

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

    Name jonJasonBloom = Name.of("Jon", "Jason", "Bloom");

    Person person = Person.newPerson(jonJasonBloom)
      .as(Gender.MALE)
      .born(birthDate)
      .identifiedBy(1L);

    assertThat(person).isNotNull();
    assertThat(person.getId()).isEqualTo(1L);
    assertThat(person.getFirstName()).isEqualTo("Jon");
    assertThat(person.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(person.getLastName()).isEqualTo("Bloom");
    assertThat(person.getName()).isEqualTo(jonJasonBloom);
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
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    LocalDateTime birthDate = getBirthDateForAge(43);

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .as(Gender.MALE)
      .born(birthDate)
      .identifiedBy(2L);

    Person personClone = (Person) person.clone();

    assertThat(personClone).isNotNull();
    assertThat(personClone).isNotSameAs(person);
    assertThat(personClone).isEqualTo(person);
    assertThat(personClone.getName()).isEqualTo(person.getName());
    assertThat(personClone.getId()).isNull();
    assertThat(personClone.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(personClone.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(personClone.getAge().orElse(-1)).isEqualTo(43);
  }

  @Test
  @SuppressWarnings("all")
  public void comparedToItselfIsEqualTo() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2000, Month.MAY, 5, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.compareTo(jonBloom)).isEqualTo(0);
  }

  @Test
  public void compareToIsEqualTo() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 9, 23, 0));

    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 9, 23, 0));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.compareTo(jonBloomTwo)).isEqualTo(0);
  }

  @Test
  public void compareToIsGreaterThan() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1974, Month.MAY, 27, 12, 0));

    Person ellieBloom = Person.newPerson(Name.of("Ellie", "A", "Bloom"),
      LocalDateTime.of(2008, Month.AUGUST, 25, 12, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(ellieBloom).isNotNull();
    assertThat(jonBloom.compareTo(ellieBloom)).isGreaterThan(0);
  }

  @Test
  public void compareToIsLessThan() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1974, Month.MAY, 27, 12, 0));

    Person sarahBloom = Person.newPerson(Name.of("Sarah", "E", "Bloom"),
      LocalDateTime.of(1975, Month.JANUARY, 22, 12, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(sarahBloom).isNotNull();
    assertThat(jonBloom.compareTo(sarahBloom)).isLessThan(0);
  }

  @Test
  public void equalsWithEqualPeopleIsTrue() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2000, Month.DECEMBER, 4, 12, 30));

    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2000, Month.DECEMBER, 4, 12, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsWithIdenticalPeopleIsTrue() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1998, Month.MAY, 15, 8, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.equals(jonBloom)).isTrue();
  }

  @Test
  public void equalsWithNearlyEqualPeopleIsFalse() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1995, Month.SEPTEMBER, 5, 11, 30));

    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "Bloom"),
      LocalDateTime.of(1995, Month.SEPTEMBER, 5, 23, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isFalse();
  }

  @Test
  public void equalsWithSimilarPeopleIsFalse() {

    Person johnBlum = Person.newPerson(Name.of("John", "J", "Blum"),
      LocalDateTime.of(1974, Month.MAY, 27, 12, 0));

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1974, Month.MAY, 27, 12, 0));

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

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      getBirthDateForAge(43));

    assertThat(person.hashCode()).isNotEqualTo(0);
  }

  @Test
  public void hashCodeForIdenticalPeopleIsSame() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 11, 10, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.hashCode()).isEqualTo(jonBloom.hashCode());
  }

  @Test
  public void hashCodeForEqualPeopleIsSame() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 11, 10, 30));

    Person jonBloomTwo = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 11, 10, 30));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.hashCode()).isEqualTo(jonBloomTwo.hashCode());
  }

  @Test
  public void hashCodeForDifferentPeopleIsNotEqual() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1974, Month.MAY, 27, 12, 0));

    Person sarahBloom = Person.newPerson(Name.of("Sarah", "E", "Bloom"),
      LocalDateTime.of(1975, Month.JANUARY, 22, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(sarahBloom).isNotNull();
    assertThat(jonBloom).isNotSameAs(sarahBloom);
    assertThat(jonBloom.hashCode()).isNotEqualTo(sarahBloom.hashCode());
  }

  @Test
  public void toStringWithNameIsCorrect() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().isPresent()).isFalse();
    assertThat(person.getGender().isPresent()).isFalse();
    assertThat(person.getMiddleName().isPresent()).isFalse();

    assertThat(person.toString()).isEqualTo(String.format(
      "{ @type = %1$s, firstName = Jon, middleName = %2$s, lastName = Bloom, birthDate = %2$s, gender = %2$s }",
        Person.class.getName(), Constants.UNKNOWN));
  }

  @Test
  public void toStringWithNameAndDateOfBirthIsCorrect() {

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .born(LocalDateTime.of(1999, Month.NOVEMBER, 11, 6, 30, 45));

    assertThat(person).isNotNull();
    assertThat(person.getGender().isPresent()).isFalse();

    assertThat(person.toString()).isEqualTo(String.format(
      "{ @type = %1$s, firstName = Jon, middleName = J, lastName = Bloom, birthDate = 1999-11-11 06:30 AM, gender = %2$s }",
        Person.class.getName(), Constants.UNKNOWN));
  }

  @Test
  public void toStringWithNameGenderAndDateOfBirthIsCorrect() {

    Person person = Person.newPerson(Name.of("Jon", "Jason", "Bloom"))
      .born(LocalDateTime.of(2000, Month.MAY, 19, 23, 30))
      .as(Gender.MALE);

    assertThat(person).isNotNull();

    assertThat(person.toString()).isEqualTo(String.format(
      "{ @type = %s, firstName = Jon, middleName = Jason, lastName = Bloom, birthDate = 2000-05-19 11:30 PM, gender = Male }",
      Person.class.getName()));
  }

  @Test
  public void personIsSerializable() throws IOException, ClassNotFoundException {

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .born(getBirthDateForAge(42))
      .as(Gender.MALE);

    assertThat(person).isNotNull();

    byte[] personBytes = IOUtils.serialize(person);

    assertThat(personBytes).isNotNull();
    assertThat(personBytes).isNotEmpty();

    Person deserializedPerson = IOUtils.deserialize(personBytes);

    assertThat(deserializedPerson).isNotNull();
    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson).isEqualTo(person);
    assertThat(deserializedPerson.getAge().orElse(-1)).isEqualTo(42);
    assertThat(deserializedPerson.getGender().orElse(null)).isEqualTo(Gender.MALE);
  }
}
