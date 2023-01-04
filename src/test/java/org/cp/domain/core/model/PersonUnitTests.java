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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import org.cp.elements.enums.Gender;
import org.cp.elements.io.IOUtils;
import org.cp.elements.lang.Constants;
import org.cp.elements.lang.Visitor;

/**
 * Unit Tests for {@link Person}.
 *
 * @author John Blum
 * @see java.time.LocalDateTime
 * @see java.util.UUID
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.core.model.Person
 * @see org.cp.elements.enums.Gender
 * @since 1.0.0
 */
public class PersonUnitTests {

  private void assertFemale(Person person) {

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.FEMALE);
    assertThat(person.isFemale()).isTrue();
    assertThat(person.isMale()).isFalse();
    assertThat(person.isNonBinary()).isFalse();
  }

  private void assertMale(Person person) {

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isTrue();
    assertThat(person.isNonBinary()).isFalse();
  }

  private void assertNonBinary(Person person) {

    assertThat(person).isNotNull();
    assertThat(person.getGender().orElse(null)).isEqualTo(Gender.NON_BINARY);
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isFalse();
    assertThat(person.isNonBinary()).isTrue();
  }

  private void assertName(Person person, Name expectedName) {

    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(expectedName);
  }

  private void assertName(Person person, String expectedFirstName, String expectedMiddleName, String expectedLastName) {

    assertThat(person).isNotNull();
    assertName(person.getName(), expectedFirstName, expectedMiddleName, expectedLastName);
  }

  private void assertName(Name name, String expectedFirstName, String expectedMiddleName, String expectedLastName) {

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo(expectedFirstName);
    assertThat(name.getLastName()).isEqualTo(expectedLastName);
    assertThat(name.getMiddleName().orElse(null)).isEqualTo(expectedMiddleName);
  }

  private void assertNoGender(Person person) {

    assertThat(person).isNotNull();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.isFemale()).isFalse();
    assertThat(person.isMale()).isFalse();
    assertThat(person.isNonBinary()).isFalse();
  }

  private void assertNoVersion(Person person) {

    assertThat(person).isNotNull();

    assertThatIllegalStateException()
      .isThrownBy(person::getVersion)
      .withMessage("Version [null] was not initialized")
      .withNoCause();
  }

  private LocalDateTime getBirthDateForAge(int age) {
    return LocalDateTime.now().minusYears(Math.abs(age));
  }

  private LocalDateTime getDateTimeInFuture(int years) {
    return LocalDateTime.now().plusYears(Math.abs(years));
  }

  @Test
  public void fromPerson() {

    UUID version = UUID.randomUUID();

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .age(21)
      .asMale()
      .atVersion(version)
      .identifiedBy(1L);

    Person jonBloomCopy = Person.from(jonBloom);

    assertThat(jonBloomCopy).isNotNull();
    assertThat(jonBloomCopy).isNotSameAs(jonBloom);
    assertThat(jonBloomCopy.getAge().orElse(-1)).isEqualTo(21);
    assertThat(jonBloomCopy.getBirthDate()
      .map(LocalDateTime::toLocalDate)
      .orElse(null))
      .isEqualTo(getBirthDateForAge(21).toLocalDate());
    assertThat(jonBloomCopy.getDateOfDeath()).isNotPresent();
    assertThat(jonBloomCopy.getGender().orElse(null)).isEqualTo(Gender.MALE);
    assertThat(jonBloomCopy.getId()).isNull();
    assertName(jonBloomCopy, "Jon", "J", "Bloom");
    assertNoVersion(jonBloomCopy);
  }

  @Test
  public void fromNullPersonThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Person.from(null))
      .withMessage("Person to copy is required")
      .withNoCause();
  }

  @Test
  public void newPersonWithName() {

    Name name = Name.of("Jon", "J", "Bloom");

    Person person = Person.newPerson(name);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(0)).isZero();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertThat(person.getName()).isEqualTo(name);
    assertName(person, "Jon", "J", "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void newPersonWithNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(16);

    Name name = Name.of("Jon", "Jason", "Bloom");

    Person person = Person.newPerson(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(16);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertThat(person.getName()).isEqualTo(name);
    assertName(person, "Jon", "Jason", "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void newPersonWithStringName() {

    Person person = Person.newPerson("Jon Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(0)).isZero();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertName(person, "Jon", null, "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void newPersonWithStringNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(21);

    Person person = Person.newPerson("Jon J Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(21);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertName(person, "Jon", "J", "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void newPersonWithFirstNameAndLastName() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(0)).isZero();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertName(person, "Jon", null, "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void newPersonWithFirstNameLastNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(42);

    Person person = Person.newPerson("Jon", "Bloom", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(42);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertName(person, "Jon", null, "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void constructPersonWithName() {

    Name name = Name.of("Jon", "J", "Bloom");

    Person person = new Person(name);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(0)).isZero();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertThat(person.getName()).isEqualTo(name);
    assertName(person, "Jon", "J", "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void constructPersonWithNameAndDateOfBirth() {

    LocalDateTime birthDate = getBirthDateForAge(100);

    Name name = Name.of("Jon", "Jason", "Bloom");

    Person person = new Person(name, birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(100);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getId()).isNull();
    assertThat(person.getName()).isEqualTo(name);
    assertName(person, "Jon", "Jason", "Bloom");
    assertNoVersion(person);
  }

  @Test
  public void constructPersonWithNoName() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Person(null))
      .withMessage("Name is required")
      .withNoCause();
  }

  @Test
  public void isAliveWithNoDateOfDeathReturnsTrue() {

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.isAlive()).isTrue();
  }

  @Test
  public void isAliveWithDateOfDeathReturnsFalse() {

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();

    person.setDateOfDeath(LocalDateTime.now().minusYears(1));

    assertThat(person.getDateOfDeath()).isPresent();
    assertThat(person.isAlive()).isFalse();
  }

  @Test
  public void isBornWithBirthDateReturnsTrue() {

    Person person = Person.newPerson("Some", "Person", getBirthDateForAge(21));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isPresent();
    assertThat(person.isBorn()).isTrue();
  }

  @Test
  public void isBornWithNoBirthDateReturnsFalse() {

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.isBorn()).isFalse();
  }

  @Test
  public void isFemaleAsFemaleReturnsTrue() {

    assertFemale(Person.newPerson("Sarah", "Bloom").as(Gender.FEMALE));
    assertFemale(Person.newPerson("Ellie", "Bloom").asFemale());
  }

  @Test
  public void isFemaleAsNonFemaleReturnsFalse() {

    Arrays.asList(Gender.MALE, Gender.NON_BINARY).forEach(gender -> {

      Person person = Person.newPerson("Some", "Person").as(gender);

      assertThat(person.getGender().orElse(null)).isEqualTo(gender);
      assertThat(person.isFemale()).isFalse();
    });
  }

  @Test
  public void isMaleAsMaleReturnsTrue() {

    assertMale(Person.newPerson("Jon", "Bloom").as(Gender.MALE));
    assertMale(Person.newPerson("John", "Blum").asMale());
  }

  @Test
  public void isMaleAsNonMaleReturnsFalse() {

    Arrays.asList(Gender.FEMALE, Gender.NON_BINARY).forEach(gender -> {

      Person person = Person.newPerson("Some", "Person").as(gender);

      assertThat(person.getGender().orElse(null)).isEqualTo(gender);
      assertThat(person.isMale()).isFalse();
    });
  }

  @Test
  public void isNonBinaryAsNonBinaryReturnsTrue() {

    assertNonBinary(Person.newPerson("Some", "Person").as(Gender.NON_BINARY));
    assertNonBinary(Person.newPerson("Another", "Person").asNonBinary());
  }

  @Test
  public void isNonBinaryAsNotNonBinaryReturnsFalse() {

    Arrays.asList(Gender.FEMALE, Gender.MALE).forEach(gender -> {

      Person person = Person.newPerson("Some", "Person").as(gender);

      assertThat(person.getGender().orElse(null)).isEqualTo(gender);
      assertThat(person.isNonBinary()).isFalse();
    });
  }

  @Test
  public void isGenderWhenNullIsNullSafeReturnsFalse() {
    assertNoGender(Person.newPerson("Some", "Person"));
  }

  @Test
  public void ageIsBasedOnBirthDate() {

    Person person = Person.newPerson("Some", "Person", getBirthDateForAge(21));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getAge().orElse(-1)).isEqualTo(21);
  }

  @Test
  public void ageIsCorrectWhenDateOfDeathIsSet() {

    Person person = Person.newPerson("Some", "Person", getBirthDateForAge(100));

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getAge().orElse(-1)).isEqualTo(100);

    person.setDateOfDeath(person.getBirthDate()
      .map(birthDate -> birthDate.plusYears(96))
      .orElse(null));

    assertThat(person.getBirthDate()).isPresent();
    assertThat(person.getDateOfDeath()).isPresent();
    assertThat(person.getAge().orElse(-1)).isEqualTo(96);
  }

  @Test
  public void ageIsUnknownWhenBirthDateIsUnset() {

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getAge().isPresent()).isFalse();
  }

  @Test
  public void ageIsUnknownWhenBirthDateIsUnsetAndDateOfDeathIsSet() {

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();

    person.setDateOfDeath(LocalDateTime.now().minusYears(5));

    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isPresent();
    assertThat(person.getAge().isPresent()).isFalse();
  }

  @Test
  public void ageIsZeroWhenBirthDateIsInFuture() {

    Person person = spy(Person.newPerson("Unborn", "Child"));

    doReturn(Optional.of(LocalDateTime.now().plusYears(1).plusMonths(9L))).when(person).getBirthDate();

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isPresent();
    assertThat(person.getBirthDate().orElse(null)).isAfter(LocalDateTime.now());
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getAge().orElse(-1)).isZero();

    verify(person, times(2)).getDateOfDeath();
    verify(person, times(3)).getBirthDate();
    verify(person, times(1)).getAge();
    verifyNoMoreInteractions(person);
  }

  @Test
  public void setAndGetBirthDate() {

    LocalDateTime birthDate = LocalDateTime.of(2012, Month.JANUARY, 17, 9, 30);

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isNotPresent();

    person.setBirthDate(birthDate);

    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);

    person.setBirthDate(null);

    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.born(birthDate)).isSameAs(person);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.born(null)).isSameAs(person);
    assertThat(person.getBirthDate()).isNotPresent();
  }

  @Test
  public void setBirthDateToFutureDate() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Person.BIRTH_DATE_PATTERN);

    LocalDateTime futureBirthDate = getDateTimeInFuture(1);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Person.newPerson("Some", "Person").setBirthDate(futureBirthDate))
      .withMessage("Birth date [%1$s] must be on or before today [%2$s]",
        formatter.format(futureBirthDate.toLocalDate()), formatter.format(LocalDateTime.now()))
      .withNoCause();
  }

  @Test
  public void setAndGetDateOfDeath() {

    LocalDateTime dateOfDeath = LocalDateTime.of(2011, Month.MAY, 31, 9, 30, 15);

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();

    person.setDateOfDeath(dateOfDeath);

    assertThat(person.getDateOfDeath().orElse(null)).isEqualTo(dateOfDeath);

    person.setDateOfDeath(null);

    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.died(dateOfDeath)).isSameAs(person);
    assertThat(person.getDateOfDeath().orElse(null)).isEqualTo(dateOfDeath);
    assertThat(person.died(null)).isSameAs(person);
    assertThat(person.getDateOfDeath()).isNotPresent();
  }

  @Test
  public void setDateOfDeathAfterBirthDate() {

    LocalDateTime birthDate =
      LocalDateTime.of(1945, Month.NOVEMBER, 13, 11, 30, 15);

    LocalDateTime dateOfDeath =
      LocalDateTime.of(2013, Month.FEBRUARY, 13, 23, 45, 30);

    Person person = Person.newPerson("Some", "Person", birthDate);

    assertThat(person).isNotNull();
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);

    person.setDateOfDeath(dateOfDeath);

    assertThat(person.getDateOfDeath().orElse(null)).isEqualTo(dateOfDeath);
    assertThat(person.getAge().orElse(-1))
      .isEqualTo(Period.between(birthDate.toLocalDate(), dateOfDeath.toLocalDate()).getYears());
  }

  @Test
  public void setDateOfDeathBeforeBirthDate() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Person.DATE_OF_DEATH_PATTERN);

    LocalDateTime birthDate =
      LocalDateTime.of(2011, Month.JANUARY, 21, 23, 45, 30);

    LocalDateTime dateOfDeath = birthDate.minusDays(1);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Person.newPerson("Some", "Person", birthDate).died(dateOfDeath))
      .withMessage("Date of death [%s] cannot be before the person's date of birth [%s]",
        formatter.format(dateOfDeath.toLocalDate()), formatter.format(birthDate.toLocalDate()))
      .withNoCause();
  }

  @Test
  public void setDateOfDeathToFutureDate() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Person.DATE_OF_DEATH_PATTERN);

    LocalDateTime dateOfDeath = getDateTimeInFuture(2);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Person.newPerson("Some", "Person").setDateOfDeath(dateOfDeath))
      .withMessage("A person's date of death [%s] cannot be known in the future",
        formatter.format(dateOfDeath))
      .withNoCause();
  }

  @Test
  public void setAndGetGender() {

    Person person = Person.newPerson("Some", "Person");

    assertNoGender(person);

    person.setGender(Gender.FEMALE);

    assertFemale(person);

    person.setGender(Gender.MALE);

    assertMale(person);

    person.setGender(Gender.NON_BINARY);

    assertNonBinary(person);
  }

  @Test
  public void setGenderToNullIsNullSafe() {

    Person person = Person.newPerson("Some", "Person").as(Gender.MALE);

    assertMale(person);

    person.setGender(null);

    assertNoGender(person);
  }

  @Test
  public void setAndGetId() {

    Person person = Person.newPerson("Some", "Person");

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
  public void setAndGetVersion() {

    Person person = Person.newPerson("Some", "Person");

    assertNoVersion(person);

    UUID version = UUID.randomUUID();
    UUID newVersion = UUID.randomUUID();

    person.setVersion(version);

    assertThat(person.getVersion()).isEqualTo(version);
    assertThat(person.atVersion(newVersion)).isSameAs(person);
    assertThat(person.getVersion()).isEqualTo(newVersion);
  }

  @Test
  public void ageSetsBirthDate() {

    Person person = Person.newPerson("Jon", "Bloom").age(16);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isEqualTo(16);
    assertThat(person.getBirthDate()
      .map(LocalDateTime::toLocalDate)
      .orElse(null))
      .isEqualTo(getBirthDateForAge(16).toLocalDate());
  }

  @Test
  public void ageSetToNegativeValueThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Person.newPerson("Jon", "Bloom").age(-1))
      .withMessage("Age [-1] must be greater than equal to 0")
      .withNoCause();
  }

  @Test
  public void ageSetToZeroSetsBirthDate() {

    Person person = Person.newPerson("Jon", "Bloom").age(0);

    assertThat(person).isNotNull();
    assertThat(person.getAge().orElse(-1)).isZero();
    assertThat(person.getBirthDate()
      .map(LocalDateTime::toLocalDate)
      .orElse(null))
      .isEqualTo(getBirthDateForAge(0).toLocalDate());
  }

  @Test
  public void asGenderCallsSetGender() {

    Person person = Person.newPerson("Some", "Person");

    assertNoGender(person);
    assertThat(person.as(Gender.FEMALE)).isSameAs(person);
    assertFemale(person);
    assertThat(person.as(Gender.MALE)).isSameAs(person);
    assertMale(person);
    assertThat(person.as(Gender.NON_BINARY)).isSameAs(person);
    assertNonBinary(person);
    assertThat(person.as(null)).isSameAs(person);
    assertNoGender(person);
  }

  @Test
  public void asFemaleSetsGender() {

    Person person = Person.newPerson("Ellie", "Bloom");

    assertNoGender(person);
    assertThat(person.asFemale()).isSameAs(person);
    assertFemale(person);
  }

  @Test
  public void asMaleSetsGender() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertNoGender(person);
    assertThat(person.asMale()).isSameAs(person);
    assertMale(person);
  }

  @Test
  public void asNonBinarySetsGender() {

    Person person = Person.newPerson("Some", "Person");

    assertNoGender(person);
    assertThat(person.asNonBinary()).isSameAs(person);
    assertNonBinary(person);
  }

  @Test
  public void bornSetsBirthDateAndAge() {

    LocalDateTime birthDate = getBirthDateForAge(42);

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();

    person = spy(person);

    assertThat(person.born(birthDate)).isSameAs(person);
    assertThat(person.getAge().orElse(-1)).isEqualTo(42);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);

    verify(person, times(1)).born(eq(birthDate));
    verify(person, times(1)).setBirthDate(eq(birthDate));
    verify(person, times(1)).getAge();
    verify(person, times(2)).getBirthDate();
    verify(person, times(1)).getDateOfDeath();
    verifyNoMoreInteractions(person);
  }

  @Test
  public void changeLastName() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertName(person, "Jon", null, "Bloom");

    Person newPerson = person.change("Doe");

    assertName(newPerson, "Jon", null, "Doe");
    assertThat(newPerson).isNotSameAs(person);
  }

  @Test
  public void changeLastNameToNullThrowsIllegalArgumentException() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertName(person, "Jon", null, "Bloom");

    assertThatIllegalArgumentException()
      .isThrownBy(() -> person.change((String) null))
      .withMessage("Last name is required")
      .withNoCause();

    assertName(person, "Jon", null, "Bloom");
  }

  @Test
  public void changeName() {

    Person person = Person.newPerson("Don S Juan");

    assertName(person, "Don", "S", "Juan");

    Person newPerson = person.change(Name.of("Jon", "J", "Bloom"));

    assertName(newPerson, "Jon", "J", "Bloom");
    assertThat(newPerson).isNotSameAs(person);
  }

  @Test
  public void changeNameToNullThrowsIllegalArgumentException() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertName(person, "Jon", null, "Bloom");

    assertThatIllegalArgumentException()
      .isThrownBy(() -> person.change((Name) null))
      .withMessage("Name is required")
      .withNoCause();

    assertName(person, "Jon", null, "Bloom");
  }

  @Test
  public void diedSetsDateOfDeath() {

    LocalDateTime dateOfDeath = LocalDateTime.now().minusYears(5);

    Person person = Person.newPerson("Some", "Person");

    assertThat(person).isNotNull();

    person = spy(person);

    doNothing().when(person).setDateOfDeath(any());

    assertThat(person.died(dateOfDeath)).isSameAs(person);

    verify(person, times(1)).died(eq(dateOfDeath));
    verify(person, times(1)).setDateOfDeath(eq(dateOfDeath));
    verifyNoMoreInteractions(person);
  }

  @Test
  public void initializesPersonWithFluentApiCorrectly() {

    LocalDateTime birthDate = getBirthDateForAge(48);

    Name jonJasonBloom = Name.of("Jon", "Jason", "Bloom");

    UUID version = UUID.randomUUID();

    Person person = Person.newPerson(jonJasonBloom)
      .asMale()
      .atVersion(version)
      .born(birthDate)
      .identifiedBy(1L);

    assertName(person, "Jon", "Jason", "Bloom");
    assertThat(person.getAge().orElse(-1)).isEqualTo(48);
    assertThat(person.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertMale(person);
    assertThat(person.getId()).isOne();
    assertThat(person.getVersion()).isEqualTo(version);
    assertThat(person.isAlive()).isTrue();
    assertThat(person.isBorn()).isTrue();
    assertThat(person.isNew()).isFalse();
    assertThat(person.isNotNew()).isTrue();
  }

  @Test
  public void acceptIsCorrect() {

    Visitor mockVisitor = mock(Visitor.class);

    Person person = Person.newPerson("Jon", "Bloom");

    person.accept(mockVisitor);

    verify(mockVisitor, times(1)).visit(eq(person));
    verifyNoMoreInteractions(mockVisitor);
  }

  @Test
  public void cloneCopiesPerson() throws CloneNotSupportedException {

    LocalDateTime birthDate = getBirthDateForAge(49);

    UUID version = UUID.randomUUID();

    Person person = Person.newPerson(Name.of("Some", "Random", "Person"))
      .asMale()
      .born(birthDate)
      .died(birthDate.plusYears(40))
      .atVersion(version)
      .identifiedBy(2L);

    Person personClone = (Person) person.clone();

    assertThat(personClone).isNotNull();
    assertThat(personClone).isNotSameAs(person);
    assertThat(personClone).isEqualTo(person);
    assertThat(personClone.getAge().orElse(-1)).isEqualTo(49);
    assertThat(personClone.getBirthDate().orElse(null)).isEqualTo(birthDate);
    assertThat(personClone.getDateOfDeath()).isNotPresent();
    assertMale(personClone);
    assertThat(personClone.getId()).isNull();
    assertThat(personClone.getName()).isEqualTo(person.getName());
    assertNoVersion(personClone);
  }

  @Test
  @SuppressWarnings("all")
  public void comparedToSelf() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2000, Month.MAY, 5, 0, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.compareTo(jonBloom)).isZero();
  }

  @Test
  public void compareToEqualPeople() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 9, 23, 0));

    Person jonBloomTwo = Person.newPerson(jonBloomOne.getName(), jonBloomOne.getBirthDate().orElse(null));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.compareTo(jonBloomTwo)).isZero();
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
  public void equalsWithEqualPeopleReturnsTrue() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2000, Month.DECEMBER, 4, 12, 30));

    Person jonBloomTwo = Person.newPerson(jonBloomOne.getName(), jonBloomOne.getBirthDate().orElse(null));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isTrue();
  }

  @Test
  public void equalsWithEffectivelyEqualPeopleReturnsTrue() {

    LocalDateTime birthDate = getBirthDateForAge(18);

    Person jonBloomOne = Person.newPerson("Jon", "Bloom", birthDate).asMale();
    Person jonBloomTwo = Person.newPerson("Jon", "Bloom", birthDate).asFemale();

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsWithIdenticalPeopleReturnsTrue() {

    Person jonBloom = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(1998, Month.MAY, 15, 8, 0));

    assertThat(jonBloom).isNotNull();
    assertThat(jonBloom.equals(jonBloom)).isTrue();
  }

  @Test
  public void equalsWithNearlyEqualPeopleReturnsFalse() {

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
  public void equalsWithSimilarPeopleReturnsFalse() {

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
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Person.newPerson("Jon", "Bloom", getBirthDateForAge(16)).equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(Person.newPerson("Jon", "Bloom").equals("Jon Bloom")).isFalse();
  }

  @Test
  public void hashCodeForPersonIsNotZero() {

    Person person =
      Person.newPerson(Name.of("Jon", "J", "Bloom"), getBirthDateForAge(43));

    assertThat(person.hashCode()).isNotZero();
  }

  @Test
  public void hashCodeForIdenticalPeopleIsSame() {

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 11, 10, 0));

    assertThat(person).isNotNull();
    assertThat(person).hasSameHashCodeAs(person);
  }

  @Test
  public void hashCodeForEqualPeopleIsSame() {

    Person jonBloomOne = Person.newPerson(Name.of("Jon", "J", "Bloom"),
      LocalDateTime.of(2018, Month.FEBRUARY, 11, 10, 30));

    Person jonBloomTwo = Person.newPerson(jonBloomOne.getName(), jonBloomOne.getBirthDate().orElse(null));

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne).hasSameHashCodeAs(jonBloomTwo);
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
    assertThat(jonBloom).doesNotHaveSameHashCodeAs(sarahBloom);
  }

  @Test
  public void toStringWithNameIsCorrect() {

    Person person = Person.newPerson("Jon", "Bloom");

    assertThat(person).isNotNull();
    assertThat(person.getAge()).isNotPresent();
    assertThat(person.getBirthDate()).isNotPresent();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getName().toString()).isEqualTo("Jon Bloom");
    assertName(person, "Jon", null, "Bloom");

    String expected = "{ @type = %1$s, firstName = Jon, middleName = %2$s, lastName = Bloom,"
      + " birthDate = %2$s, dateOfDeath = %2$s, gender = %2$s }";

    assertThat(person.toString()).isEqualTo(String.format(expected, Person.class.getName(), Constants.UNKNOWN));
  }

  @Test
  public void toStringWithNameAndDateOfBirthIsCorrect() {

    Person person = Person.newPerson(Name.of("Jon", "J", "Bloom"))
      .born(LocalDateTime.of(1999, Month.NOVEMBER, 11, 6, 30, 45));

    assertThat(person).isNotNull();
    assertThat(person.getDateOfDeath()).isNotPresent();
    assertThat(person.getGender()).isNotPresent();
    assertThat(person.getName().toString()).isEqualTo("Jon J Bloom");

    String expected = "{ @type = %1$s, firstName = Jon, middleName = J, lastName = Bloom,"
      + " birthDate = 1999-11-11 06:30 AM, dateOfDeath = %2$s, gender = %2$s }";

    assertThat(person.toString()).isEqualTo(String.format(expected, Person.class.getName(), Constants.UNKNOWN));
  }

  @Test
  public void toStringWithNameGenderDateOfBirthAndDateOfDeathIsCorrect() {

    Person person = Person.newPerson(Name.of("Some", "Random", "Person"))
      .asMale()
      .born(LocalDateTime.of(2000, Month.MAY, 19, 23, 30, 45))
      .died(LocalDateTime.of(2019, Month.DECEMBER, 31, 23, 59, 59));

    assertThat(person).isNotNull();

    String expected = "{ @type = %s, firstName = Some, middleName = Random, lastName = Person,"
      + " birthDate = 2000-05-19 11:30 PM, dateOfDeath = 2019-12-31 11:59 PM, gender = Male }";

    assertThat(person.toString()).isEqualTo(String.format(expected, Person.class.getName()));
  }

  @Test
  public void personIsSerializable() throws IOException, ClassNotFoundException {

    LocalDateTime birthDate = getBirthDateForAge(42);
    LocalDateTime dateOfDeath = birthDate.plusYears(27);

    Name someRandomPerson = Name.of("Some", "Random", "Person");

    UUID version = UUID.randomUUID();

    Person person = Person.newPerson(someRandomPerson)
      .atVersion(version)
      .asMale()
      .born(birthDate)
      .died(dateOfDeath)
      .identifiedBy(1L);

    assertThat(person).isNotNull();

    byte[] personBytes = IOUtils.serialize(person);

    assertThat(personBytes).isNotNull();
    assertThat(personBytes).isNotEmpty();

    Person deserializedPerson = IOUtils.deserialize(personBytes);

    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson).isEqualTo(person);
    assertName(deserializedPerson, someRandomPerson);
    assertThat(deserializedPerson.getAge().orElse(-1)).isEqualTo(27);
    assertThat(deserializedPerson.getDateOfDeath().orElse(null)).isEqualTo(dateOfDeath);
    assertMale(deserializedPerson);
  }
}
