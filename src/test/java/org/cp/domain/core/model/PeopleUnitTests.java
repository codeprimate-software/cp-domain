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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import org.cp.elements.lang.Identifiable;
import org.cp.elements.util.stream.StreamUtils;

/**
 * Unit Tests for {@link People}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.core.model.Group
 * @see org.cp.domain.core.model.People
 * @see org.cp.domain.core.model.Person
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class PeopleUnitTests {

  private final Person jonDoe = Person.newPerson(Name.of("Jon", "R", "Doe"))
    .born(birthDate(1974, Month.MAY, 27))
    .asMale();

  private final Person janeDoe = Person.newPerson(Name.of("Jane", "R", "Doe"))
    .born(birthDate(1975, Month.JANUARY, 22))
    .asFemale();

  private final Person bobDoe = Person.newPerson(Name.of("Bob", "Doe"))
    .born(birthDateForAge(32))
    .asMale();

  private final Person cookieDoe = Person.newPerson(Name.of("Cookie", "Doe"))
    .born(birthDateForAge(9))
    .asFemale();

  private final Person dillDoe = Person.newPerson(Name.of("Dill", "Doe"))
    .born(birthDateForAge(7))
    .asMale();

  private final Person froDoe = Person.newPerson(Name.of("Fro", "Doe"))
    .born(birthDateForAge(21))
    .asMale();

  private final Person hoeDoe = Person.newPerson(Name.of("Hoe", "R", "Doe"))
    .born(birthDateForAge(24))
    .asFemale();

  private final Person joeDoe = Person.newPerson(Name.of("Joe", "R", "Doe"))
      .born(birthDateForAge(28))
      .asMale();

  private final Person lanDoe = Person.newPerson(Name.of("Lan", "Doe"), birthDateForAge(29)).asMale();

  private final Person moeDoe = Person.newPerson(Name.of("Moe", "R", "Doe"))
    .born(birthDateForAge(30))
    .asMale();

  private final Person pieDoe = Person.newPerson(Name.of("Pie", "Doe"), birthDateForAge(16)).asFemale();

  private final Person sourDoe = Person.newPerson(Name.of("Sour", "Doe"), birthDateForAge(13)).asMale();

  private void assertNew(Identifiable<?> target) {

    assertThat(target).isNotNull();
    assertThat(target.getId()).isNull();
    assertThat(target.isNew()).isTrue();
  }

  private <ID extends Comparable<ID>> void assertNotNew(Identifiable<ID> target, ID expectedId) {

    assertThat(target).isNotNull();
    assertThat(target.isNotNew()).isTrue();
    assertThat(target.getId()).isEqualTo(expectedId);
  }

  private LocalDateTime birthDate(int year, Month month, int day) {
    return LocalDateTime.of(year, month, day, 0, 0);
  }

  private LocalDateTime birthDateForAge(int age) {
    return LocalDateTime.now().minusYears(Math.abs(age));
  }

  private People fromFamily() {

    People group = People.of(this.jonDoe, this.janeDoe, this.cookieDoe, this.froDoe, this.joeDoe, this.lanDoe,
      this.pieDoe, this.sourDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(8);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.lanDoe, this.joeDoe, this.froDoe,
      this.pieDoe, this.sourDoe, this.cookieDoe);

    return group;
  }

  @Test
  public void emptyGroupOfPeople() {

    People group = People.empty();

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
  }

  @Test
  public void generatesUniqueIds() {

    assertThat(Stream.generate(People::generateId)
      .limit(100)
      .collect(Collectors.toSet()))
      .hasSize(100);
  }

  @Test
  public void groupAnArrayOfPeople() {

    People group = People.of(this.jonDoe, this.janeDoe, this.cookieDoe, this.froDoe, this.hoeDoe, this.joeDoe,
      this.lanDoe, this.moeDoe, this.pieDoe, this.sourDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(10);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.moeDoe, this.lanDoe, this.joeDoe, this.hoeDoe,
      this.froDoe, this.pieDoe, this.sourDoe, this.cookieDoe);
  }

  @Test
  public void groupAnArrayOfOnePerson() {

    People group = People.of(this.jonDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(this.jonDoe);
  }

  @Test
  public void groupAnArrayOfNoPeople() {

    People group = People.of();

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
  }

  @Test
  public void groupNullArrayIsNullSafe() {

    People group = People.of((Person[]) null);

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
  }

  @Test
  public void groupOfIterableOfPeople() {

    People group = People.of(Arrays.asList(this.jonDoe, this.janeDoe, this.cookieDoe, this.froDoe, this.hoeDoe,
      this.joeDoe, this.lanDoe, this.moeDoe, this.pieDoe, this.sourDoe));

    assertThat(group).isNotNull();
    assertThat(group).hasSize(10);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.moeDoe, this.lanDoe, this.joeDoe, this.hoeDoe,
      this.froDoe, this.pieDoe, this.sourDoe, this.cookieDoe);
  }

  @Test
  public void groupOfIterableOfOnePerson() {

    People group = People.of(Collections.singletonList(this.jonDoe));

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(this.jonDoe);
  }

  @Test
  public void groupOfIterableOfNoPeople() {

    People group = People.of(Collections.emptyList());

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
  }

  @Test
  public void groupOfNullIterableIsNullSafe() {

    People group = People.of((Iterable<Person>) null);

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
  }

  @Test
  public void setAndGetId() {

    UUID idOne = UUID.randomUUID();
    UUID idTwo = UUID.randomUUID();

    People group = People.empty();

    assertNew(group);

    group.setId(idOne);

    assertNotNew(group, idOne);
    assertThat(group.<People>identifiedBy(idTwo)).isSameAs(group);
    assertNotNew(group, idTwo);

    group.setId(null);

    assertNew(group);
  }

  @Test
  public void getNameWhenSetUsesName() {

    People group = People.of(this.jonDoe, this.janeDoe)
      .named("Doe Does")
      .identifiedBy(UUID.randomUUID());

    assertThat(group).isNotNull();
    assertThat(group).isNotEmpty();
    assertThat(group.getId()).isNotNull();
    assertThat(group.getName()).isEqualTo("Doe Does");
  }

  @Test
  public void getNameWhenUnsetUsesId() {

    UUID id = UUID.randomUUID();

    People group = People.of(this.jonDoe, this.janeDoe)
      .named("  ")
      .identifiedBy(id);

    assertThat(group).isNotNull();
    assertThat(group).isNotEmpty();
    assertThat(group.getId()).isNotNull();
    assertThat(group.getName()).isEqualTo(String.format("GROUP ID [%s]", id));
  }

  @Test
  public void getNameWhenUnsetHavingNoIdUsesSingleLastName() {

    People group = People.of(this.jonDoe, this.janeDoe)
      .named("")
      .identifiedBy(null);

    assertThat(group).isNotNull();
    assertThat(group).isNotEmpty();
    assertThat(group.getId()).isNull();
    assertThat(group.getName()).isEqualTo("GROUP of [Doe]");
  }

  @Test
  public void getNameOfEmptyGroup() {

    People group = People.empty()
      .named(null)
      .identifiedBy(null);

    assertThat(group).isNotNull();
    assertThat(group).isEmpty();
    assertThat(group.getId()).isNull();
    assertThat(group.getName()).isEqualTo("EMPTY NON-IDENTIFIED GROUP");
  }

  @Test
  public void addPersonReturnsTrue() {

    People group = People.of(this.jonDoe, this.janeDoe, this.pieDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(3);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.pieDoe);

    Person jackHandy = Person.newPerson("Jack", "Handy")
      .born(birthDateForAge(40))
      .asMale();

    assertThat(group.join(jackHandy)).isTrue();
    assertThat(group).hasSize(4);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.pieDoe, jackHandy);

    Person albertEinstein = Person.newPerson("Albert", "Einstein")
      .born(birthDate(1879, Month.MARCH, 14))
      .asMale();

    assertThat(group.join(albertEinstein)).isTrue();
    assertThat(group).hasSize(5);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.pieDoe, albertEinstein, jackHandy);

    Person imaPigg = Person.newPerson("Ima", "Pigg")
      .born(birthDate(1945, Month.JULY, 1))
      .asFemale();

    assertThat(group.join(imaPigg)).isTrue();
    assertThat(group).hasSize(6);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.pieDoe, albertEinstein, jackHandy, imaPigg);
  }

  @Test
  public void addNullPersonIsNullSafeReturnsFalse() {

    People group = People.of(this.jonDoe, this.janeDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(2);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe);
    assertThat(group.join(null)).isFalse();
    assertThat(group).hasSize(2);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe);
  }

  @Test
  public void addExistingPersonReturnsFalse() {

    People group = People.of(this.jonDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(this.jonDoe);

    Person jonDoeClone = Person.from(this.jonDoe);

    assertThat(jonDoeClone).isEqualTo(this.jonDoe);
    assertThat(jonDoeClone).isNotSameAs(this.jonDoe);
    assertThat(group.join(jonDoeClone)).isFalse();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(this.jonDoe);

    assertThat(StreamUtils.stream(group)
      .filter(person -> person == jonDoeClone)
      .findFirst())
      .isNotPresent();
  }

  @Test
  public void findByFindsAdults() {

    People group = fromFamily();

    Set<Person> searchResults = group.findBy(person -> person.getAge().orElse(Integer.MIN_VALUE) >= 18);

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(5);
    assertThat(searchResults)
      .containsExactlyInAnyOrder(this.jonDoe, this.janeDoe, this.froDoe, this.joeDoe, this.lanDoe);
  }

  @Test
  public void findByFindsFemales() {

    People group = fromFamily();

    Set<Person> searchResults = group.findBy(Person::isFemale);

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(3);
    assertThat(searchResults).containsExactlyInAnyOrder(this.janeDoe, this.cookieDoe, this.pieDoe);
  }

  @Test
  public void findByFindsMaleChildren() {

    People group = fromFamily();

    Set<Person> searchResults = group.findBy(person ->
      person.isMale() && person.getAge().orElse(Integer.MAX_VALUE) < 18);

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(1);
    assertThat(searchResults).containsExactlyInAnyOrder(this.sourDoe);
  }

  @Test
  public void findByFindsNoPeople() {

    People group = fromFamily();

    Set<Person> searchResults = group.findBy(person -> "Handy".equals(person.getLastName()));

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).isEmpty();
  }

  @Test
  public void findByFindsOnePerson() {

    People group = fromFamily();

    Set<Person> searchResults = group.findBy(person -> "Jon".equals(person.getFirstName()));

    assertThat(searchResults).isNotNull();
    assertThat(searchResults).hasSize(1);
    assertThat(searchResults).containsExactlyInAnyOrder(this.jonDoe);
  }

  @Test
  public void findOneFindsSinglePerson() {

    People group = fromFamily();

    Optional<Person> person = group.findOne(Person::isFemale);

    assertThat(person).isNotNull();
    assertThat(person).isPresent();
    assertThat(person.orElse(null)).isEqualTo(this.janeDoe);
  }

  @Test
  public void findOneFindsNoPerson() {

    People group = fromFamily();

    Optional<Person> person = group.findOne(it -> "Handy".equals(it.getLastName()));

    assertThat(person).isNotNull();
    assertThat(person).isNotPresent();
  }

  @Test
  public void isEmptyWithEmptyGroupReturnsTrue() {
    assertThat(People.empty().isEmpty()).isTrue();
  }

  @Test
  public void isEmptyWithNonEmptyGroupReturnsFalse() {
    assertThat(People.of(this.jonDoe).isEmpty()).isFalse();
  }

  @Test
  public void iteratorOfPeople() {

    People group = People.of(this.cookieDoe, this.jonDoe, this.joeDoe, this.froDoe, this.lanDoe, this.janeDoe,
      this.sourDoe, this.pieDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(8);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.lanDoe, this.joeDoe, this.froDoe,
      this.pieDoe, this.sourDoe, this.cookieDoe);

    List<Person> people = new ArrayList<>();

    StreamUtils.stream(group).forEach(people::add);

    assertThat(people).hasSameSizeAs(group);
    assertThat(people).containsExactly(this.jonDoe, this.janeDoe, this.lanDoe, this.joeDoe, this.froDoe,
      this.pieDoe, this.sourDoe, this.cookieDoe);
  }

  @Test
  public void iteratorOfOnePerson() {

    People group = People.of(this.jonDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(this.jonDoe);

    Iterator<Person> personIterator = group.iterator();

    assertThat(personIterator).isNotNull();
    assertThat(personIterator).hasNext();
    assertThat(personIterator.next()).isEqualTo(this.jonDoe);
    assertThat(personIterator).isExhausted();
  }

  @Test
  public void iteratorOfNoPeople() {
    assertThat(People.empty().iterator()).isExhausted();
  }

  @Test
  public void removePeopleReturnsTrue() {

    People people = fromFamily();

    Arrays.asList(this.janeDoe, this.cookieDoe, this.pieDoe, this.sourDoe).forEach(person ->
      assertThat(people.leave(person))
        .describedAs("Failed to remove person [%s]", person)
        .isTrue());

    assertThat(people).hasSize(4);
    assertThat(people).containsExactly(this.jonDoe, this.lanDoe, this.joeDoe, this.froDoe);
  }

  @Test
  public void removeSinglePersonReturnsTrue() {

    People group = fromFamily();

    assertThat(group.leave(this.joeDoe)).isTrue();
    assertThat(group).hasSize(7);
    assertThat(group).containsExactly(this.jonDoe, this.janeDoe, this.lanDoe, this.froDoe,
      this.pieDoe, this.sourDoe, this.cookieDoe);
  }

  @Test
  public void removePersonFromEmptyGroupReturnsFalse() {
    assertThat(People.empty().leave(this.jonDoe)).isFalse();
  }

  @Test
  public void removeNullPersonIsNullSafeReturnsFalse() {

    People group = fromFamily();

    assertThat(group).hasSize(8);
    assertThat(group.leave((Person) null)).isFalse();
    assertThat(group).hasSize(8);
  }

  @Test
  public void removeNonExistingPersonReturnsFalse() {

    People group = fromFamily();

    assertThat(group).hasSize(8);
    assertThat(group.leave(person -> "Handy".equals(person.getLastName()))).isFalse();
    assertThat(group).hasSize(8);
  }

  @Test
  public void removePersonWithNoBirthDateGenderOrMiddleNameFromFamilyOfOneIsSuccessful() {

    Person jackHandy = Person.newPerson("Jack", "Handy");

    assertThat(jackHandy.getAge()).isNotPresent();
    assertThat(jackHandy.getBirthDate()).isNotPresent();
    assertThat(jackHandy.getGender()).isNotPresent();
    assertThat(jackHandy.getMiddleName()).isNotPresent();

    People group = People.of(jackHandy);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group).containsExactly(jackHandy);
    assertThat(group.leave(jackHandy)).isTrue();
    assertThat(group).isEmpty();
  }

  @Test
  public void sizeReturnsEight() {

    People group = fromFamily();

    assertThat(group).isNotNull();
    assertThat(group).hasSize(8);
    assertThat(group.size()).isEqualTo(8);
  }

  @Test
  public void sizeReturnsOne() {

    People group = People.of(this.jonDoe);

    assertThat(group).isNotNull();
    assertThat(group).hasSize(1);
    assertThat(group.size()).isOne();
  }

  @Test
  public void sizeReturnsZero() {

    People group = People.empty();

    assertThat(group).hasSize(0);
    assertThat(People.empty().size()).isZero();
  }

  @Test
  public void toStringWithPeople() {

    People people = People.of(this.jonDoe, this.janeDoe, this.cookieDoe, this.pieDoe, this.sourDoe);

    assertThat(people).isNotNull();
    assertThat(people).hasSize(5);
    assertThat(people).containsExactly(this.jonDoe, this.janeDoe, this.pieDoe, this.sourDoe, this.cookieDoe);
    assertThat(people.toString()).isEqualTo("[Doe, Jon R; Doe, Jane R; Doe, Pie; Doe, Sour; Doe, Cookie]");
  }

  @Test
  public void toStringWithOnePerson() {

    People people = People.of(this.jonDoe);

    assertThat(people).isNotNull();
    assertThat(people).hasSize(1);
    assertThat(people).containsExactly(this.jonDoe);
    assertThat(people.toString()).isEqualTo("[Doe, Jon R]");
  }

  @Test
  public void toStringWithNoPeople() {

    People people = People.empty();

    assertThat(people).isNotNull();
    assertThat(people).isEmpty();
    assertThat(people.toString()).isEqualTo("[]");
  }
}
