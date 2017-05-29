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
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cp.elements.enums.Gender;
import org.junit.Test;

/**
 * Unit tests for {@link Family}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.core.model.Family
 * @see org.cp.domain.core.model.Name
 * @see org.cp.domain.core.model.Person
 * @since 1.0.0
 */
public class FamilyTests {

  private Person jonDoe =
    Person.newPerson(Name.of("Jon", "R", "Doe"), birthDate(1974, Month.MAY, 27)).as(Gender.MALE);

  private Person janeDoe =
    Person.newPerson(Name.of("Jane", "R", "Doe"), birthDate(1975, Month.JANUARY, 22)).as(Gender.FEMALE);

  private Person cookieDoe =
    Person.newPerson(Name.of("Cookie", "Doe"), birthDateForAge(9)).as(Gender.FEMALE);

  private Person froDoe =
    Person.newPerson(Name.of("Fro", "R", "Doe"), birthDateForAge(21)).as(Gender.MALE);

  private Person hoeDoe =
    Person.newPerson(Name.of("Hoe", "R", "Doe"), birthDateForAge(24)).as(Gender.FEMALE);

  private Person joeDoe =
    Person.newPerson(Name.of("Joe", "R", "Doe"), birthDateForAge(28)).as(Gender.MALE);

  private Person pieDoe =
    Person.newPerson(Name.of("Pie", "Doe"), birthDateForAge(16)).as(Gender.FEMALE);

  private Person sourDoe =
    Person.newPerson(Name.of("Sour", "Doe"), birthDateForAge(17)).as(Gender.MALE);

  private LocalDateTime birthDateForAge(int age) {
    return LocalDateTime.now().minusYears(Math.abs(age));
  }

  private LocalDateTime birthDate(int year, Month month, int day) {
    return LocalDateTime.of(year, month, day, 0, 0);
  }

  @Test
  public void emptyFamilyIsCorrect() {
    Family family = Family.empty();

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
  }

  @Test
  public void ofArrayOfPeopleIsCorrect() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);
  }

  @Test
  public void ofArrayOfOnePersonIsCorrect() {
    Family family = Family.of(jonDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);
  }

  @Test
  public void ofArrayOfNoneIsCorrect() {
    Family family = Family.of();

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
  }

  @Test
  public void ofNullArrayHandlesNull() {
    Family family = Family.of((Person[]) null);

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
  }

  @Test
  public void ofIterableOfPeopleIsCorrect() {
    Family family = Family.of(Arrays.asList(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe));

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);
  }

  @Test
  public void ofIterableOfOneIsCorrect() {
    Family family = Family.of(Collections.singletonList(jonDoe));

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);
  }

  @Test
  public void ofIterableOfNoneIsCorrect() {
    Family family = Family.of(Collections.emptyList());

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
  }

  @Test
  public void ofNullIterableHandlesNull() {
    Family family = Family.of((Iterable<Person>) null);

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
  }

  @Test
  public void addPersonReturnsTrue() {
    Family family = Family.of(jonDoe, janeDoe, pieDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(3);
    assertThat(family).contains(jonDoe, janeDoe, pieDoe);

    Person jackHandy = Person.newPerson(Name.of("Jack", "Handy"), birthDateForAge(42)).as(Gender.MALE);

    assertThat(family.add(jackHandy)).isTrue();
    assertThat(family).hasSize(4);
    assertThat(family).contains(jonDoe, janeDoe, pieDoe, jackHandy);
  }

  @Test
  public void addNullPersonReturnsFalse() {
    Family family = Family.of(jonDoe, janeDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(2);
    assertThat(family).contains(jonDoe, janeDoe);
    assertThat(family.add(null)).isFalse();
    assertThat(family).hasSize(2);
  }

  @Test
  public void addExistingPersonReturnsFalse() {
    Family family = Family.of(jonDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);

    Person anotherJonDoe = Person.newPerson(jonDoe.getName(), jonDoe.getBirthDate().orElse(null));

    assertThat(anotherJonDoe).isEqualTo(jonDoe);
    assertThat(anotherJonDoe).isNotSameAs(jonDoe);
    assertThat(family.add(anotherJonDoe)).isFalse();
    assertThat(family).hasSize(1);
  }

  @Test
  public void findByFindsAllAdults() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    Set<Person> searchResults = family.findBy(person -> person.getAge().orElse(Integer.MIN_VALUE) >= 18);

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(5);
    assertThat(searchResults).contains(jonDoe, janeDoe, froDoe, hoeDoe, joeDoe);
  }

  @Test
  public void findByFindsAllFemales() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    Set<Person> searchResults = family.findBy(Person::isFemale);

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(4);
    assertThat(searchResults).contains(janeDoe, cookieDoe, hoeDoe, pieDoe);
  }

  @Test
  public void findByFindsOnePerson() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    Set<Person> searchResults = family.findBy(person -> "Jon".equals(person.getFirstName()));

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(1);
    assertThat(searchResults).contains(jonDoe);
  }

  @Test
  public void findByFindsNoPeople() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    Set<Person> searchResults = family.findBy(person -> "Handy".equals(person.getLastName()));

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).isEmpty();
  }

  @Test
  public void isEmptyReturnsTrue() {
    assertThat(Family.empty().isEmpty()).isTrue();
  }

  @Test
  public void isEmptyReturnsFalse() {
    assertThat(Family.of(jonDoe).isEmpty()).isFalse();
  }

  @Test
  public void iteratorOfPeople() {
    Family family = Family.of(cookieDoe, jonDoe, joeDoe, froDoe, hoeDoe, janeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    List<Person> people = new ArrayList<>();

    stream(family.spliterator(), false).forEach(people::add);

    assertThat(people).hasSize(family.size());
    assertThat(people).containsExactly(jonDoe, janeDoe, joeDoe, hoeDoe, froDoe, sourDoe, pieDoe, cookieDoe);
  }

  @Test
  public void iteratorOfOnePerson() {
    Family family = Family.of(jonDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);

    Iterator<Person> person = family.iterator();

    assertThat(person).isNotNull();
    assertThat(person.hasNext()).isTrue();
    assertThat(person.next()).isEqualTo(jonDoe);
    assertThat(person.hasNext()).isFalse();
  }

  @Test
  public void iteratorOfNoPeople() {
    assertThat(Family.empty().iterator().hasNext()).isFalse();
  }

  @Test
  public void sizeReturnsEight() {
    Family family = Family.of(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(8);
    assertThat(family).contains(jonDoe, janeDoe, cookieDoe, froDoe, hoeDoe, joeDoe, pieDoe, sourDoe);
    assertThat(family.size()).isEqualTo(8);
  }

  @Test
  public void sizeReturnsOne() {
    Family family = Family.of(jonDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);
    assertThat(family.size()).isEqualTo(1);
  }

  @Test
  public void sizeReturnsZero() {
    assertThat(Family.empty().size()).isEqualTo(0);
  }

  @Test
  public void toStringWithPeopleIsSuccessful() {
    Family family = Family.of(jonDoe, janeDoe, froDoe, cookieDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(4);
    assertThat(family).contains(jonDoe, janeDoe, froDoe, cookieDoe);
    assertThat(family.toString()).isEqualTo("[Doe, Jon R; Doe, Jane R; Doe, Fro R; Doe, Cookie]");
  }

  @Test
  public void toStringWithOnePersonIsSuccessful() {
    Family family = Family.of(jonDoe);

    assertThat(family).isNotNull();
    assertThat(family).hasSize(1);
    assertThat(family).contains(jonDoe);
    assertThat(family.toString()).isEqualTo("[Doe, Jon R]");
  }

  @Test
  public void toStringWithNoPeopleIsSuccessful() {
    Family family = Family.empty();

    assertThat(family).isNotNull();
    assertThat(family).isEmpty();
    assertThat(family.toString()).isEqualTo("[]");
  }
}
