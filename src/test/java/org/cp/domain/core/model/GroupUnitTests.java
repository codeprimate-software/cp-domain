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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.cp.domain.core.enums.Gender;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.util.ArrayUtils;
import org.cp.elements.util.CollectionUtils;

/**
 * Unit Tests for {@link Group}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.core.model.Group
 * @see org.cp.domain.core.model.Person
 * @since 0.1.0
 */
public class GroupUnitTests {

  private Group<Person> mockGroup(Person... people) {
    return mockGroup("MockGroup", people);
  }

  @SuppressWarnings("unchecked")
  private Group<Person> mockGroup(String name, Person... people) {

    Group<Person> mockGroup = mock(Group.class, name);

    doAnswer(invocation -> ArrayUtils.asIterator(people)).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).spliterator();

    return mockGroup;
  }

  @Test
  public void acceptsVisitor() {

    Visitor mockVisitor = mock(Visitor.class);

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPersonOne, mockPersonTwo);

    doCallRealMethod().when(mockGroup).accept(any(Visitor.class));

    mockGroup.accept(mockVisitor);

    verify(mockGroup, times(1)).accept(eq(mockVisitor));
    verify(mockPersonOne, times(1)).accept(eq(mockVisitor));
    verify(mockPersonTwo, times(1)).accept(eq(mockVisitor));
    verifyNoMoreInteractions(mockPersonOne, mockPersonTwo);
    verifyNoInteractions(mockVisitor);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsAll() {

    Predicate<Person> mockPredicate = mock(Predicate.class);

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPersonOne, mockPersonTwo);

    doReturn(true).when(mockPredicate).test(any(Person.class));
    doCallRealMethod().when(mockGroup).count(any(Predicate.class));

    assertThat(mockGroup.count(mockPredicate)).isEqualTo(2);

    verify(mockGroup, times(1)).count(eq(mockPredicate));
    verify(mockPredicate, times(1)).test(eq(mockPersonOne));
    verify(mockPredicate, times(1)).test(eq(mockPersonTwo));
    verifyNoInteractions(mockPersonOne, mockPersonTwo);
    verifyNoMoreInteractions(mockPredicate);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsNone() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPersonOne, mockPersonTwo);

    doCallRealMethod().when(mockGroup).count(any(Predicate.class));

    assertThat(mockGroup.count(person -> false)).isZero();

    verify(mockGroup, times(1)).count(isA(Predicate.class));
    verifyNoInteractions(mockPersonOne, mockPersonTwo);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsOne() {

    Predicate<Person> mockPredicate = mock(Predicate.class);

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);
    Person mockPersonThree = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPersonOne, mockPersonTwo, mockPersonThree);

    doReturn(true, false).when(mockPredicate).test(any());
    doCallRealMethod().when(mockGroup).count(any(Predicate.class));

    assertThat(mockGroup.count(mockPredicate)).isOne();

    verify(mockGroup, times(1)).count(eq(mockPredicate));
    verify(mockPredicate, times(1)).test(eq(mockPersonOne));
    verify(mockPredicate, times(1)).test(eq(mockPersonTwo));
    verify(mockPredicate, times(1)).test(eq(mockPersonThree));
    verifyNoInteractions(mockPersonOne, mockPersonTwo, mockPersonThree);
    verifyNoMoreInteractions(mockPredicate);
  }

  @Test
  public void countWithNullPredicate() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).count(any());

    assertThatIllegalArgumentException()
      .isThrownBy(() -> mockGroup.count(null))
      .withMessage("Predicate is required")
      .withNoCause();

    verify(mockGroup, times(1)).count(isNull());
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void containsPersonReturnsTrue() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).contains(any());
    doReturn(Optional.of(mockPerson)).when(mockGroup).findOne(any(Predicate.class));

    assertThat(mockGroup.contains(mockPerson)).isTrue();

    verify(mockGroup, times(1)).contains(eq(mockPerson));
    verify(mockGroup, times(1)).findOne(isNotNull(Predicate.class));
    verifyNoMoreInteractions(mockGroup);
    verifyNoInteractions(mockPerson);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void containsPersonReturnsFalse() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).contains(any());
    doReturn(Optional.empty()).when(mockGroup).findOne(any(Predicate.class));

    assertThat(mockGroup.contains(mockPerson)).isFalse();

    verify(mockGroup, times(1)).contains(eq(mockPerson));
    verify(mockGroup, times(1)).findOne(isNotNull(Predicate.class));
    verifyNoMoreInteractions(mockGroup);
    verifyNoInteractions(mockPerson);
  }

  @Test
  public void containsNullIsNullSafeReturnsFalse() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).contains(any());

    assertThat(mockGroup.contains(null)).isFalse();

    verify(mockGroup, times(1)).contains(isNull());
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void differenceOfGroups() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A", mockPersonOne, mockPersonTwo);
    Group<Person> mockGroupTwo = mockGroup("B", mockPersonTwo);

    doCallRealMethod().when(mockGroupOne).difference(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));
    doCallRealMethod().when(mockGroupTwo).contains(any());
    doCallRealMethod().when(mockGroupTwo).findOne(any(Predicate.class));

    Set<Person> difference = mockGroupOne.difference(mockGroupTwo);

    assertThat(difference).isNotNull();
    assertThat(difference).containsExactly(mockPersonOne);

    verify(mockGroupOne, times(1)).difference(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).findBy(isNotNull(Predicate.class));
    verify(mockGroupOne, times(1)).iterator();
    verify(mockGroupOne, times(1)).spliterator();
    Arrays.asList(mockPersonOne, mockPersonTwo).forEach(mockPerson ->
      verify(mockGroupTwo, times(1)).contains(eq(mockPerson)));
    verify(mockGroupTwo, times(2)).findOne(isNotNull(Predicate.class));
    verify(mockGroupTwo, times(2)).iterator();
    verify(mockGroupTwo, times(2)).spliterator();
    verifyNoMoreInteractions(mockGroupOne, mockGroupTwo);
    verifyNoInteractions(mockPersonOne, mockPersonTwo);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void differenceOfEmptyGroup() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A");
    Group<Person> mockGroupTwo = mockGroup("B", mockPerson);

    doCallRealMethod().when(mockGroupOne).difference(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));

    Set<Person> difference = mockGroupOne.difference(mockGroupTwo);

    assertThat(difference).isNotNull();
    assertThat(difference).isEmpty();

    verify(mockGroupOne, times(1)).difference(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).findBy(isNotNull(Predicate.class));
    verify(mockGroupOne, times(1)).iterator();
    verify(mockGroupOne, times(1)).spliterator();
    verifyNoInteractions(mockGroupTwo, mockPerson);
    verifyNoMoreInteractions(mockGroupOne);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void differenceOfMatchingGroups() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A", mockPerson);
    Group<Person> mockGroupTwo = mockGroup("B", mockPerson);

    doCallRealMethod().when(mockGroupOne).difference(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));
    doCallRealMethod().when(mockGroupTwo).contains(any());
    doCallRealMethod().when(mockGroupTwo).findOne(any(Predicate.class));

    Set<Person> difference = mockGroupOne.difference(mockGroupTwo);

    assertThat(difference).isNotNull();
    assertThat(difference).isEmpty();

    verify(mockGroupOne, times(1)).difference(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).findBy(isNotNull(Predicate.class));
    verify(mockGroupOne, times(1)).iterator();
    verify(mockGroupOne, times(1)).spliterator();
    verify(mockGroupTwo, times(1)).contains(eq(mockPerson));
    verify(mockGroupTwo, times(1)).findOne(isNotNull(Predicate.class));
    verify(mockGroupTwo, times(1)).iterator();
    verify(mockGroupTwo, times(1)).spliterator();
    verifyNoMoreInteractions(mockGroupOne, mockGroupTwo);
    verifyNoInteractions(mockPerson);
  }

  @Test
  public void differenceOfNullGroup() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).difference(any());

    assertThatIllegalArgumentException()
      .isThrownBy(() -> mockGroup.difference(null))
      .withMessage("Group used in set difference is required")
      .withNoCause();

    verify(mockGroup, times(1)).difference(isNull());
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsAllPeopleMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person jackHandy = Person.newPerson("Jack", "Handy");

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe, jackHandy);

    doCallRealMethod().when(mockGroup).findBy(any(Predicate.class));

    Set<Person> matches = mockGroup.findBy(person -> "Doe".equals(person.getLastName()));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(2);
    assertThat(matches).containsExactlyInAnyOrder(jonDoe, janeDoe);

    verify(mockGroup, times(1)).findBy(isA(Predicate.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsNoPeopleMatchingPredicate() {

    Predicate<Person> predicate = spy(new PersonGreaterThanAgePredicate(49));

    Person jonDoe = Person.newPerson("Jon", "Doe").age(21);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);
    Person jackHandy = Person.newPerson("Jack", "Handy").age(40);

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe, jackHandy);

    doCallRealMethod().when(mockGroup).findBy(any(Predicate.class));

    Set<Person> matches = mockGroup.findBy(predicate);

    assertThat(matches).isNotNull();
    assertThat(matches).isEmpty();

    verify(mockGroup, times(1)).findBy(eq(predicate));
    verify(predicate, times(1)).test(eq(jonDoe));
    verify(predicate, times(1)).test(eq(janeDoe));
    verify(predicate, times(1)).test(eq(jackHandy));
    verifyNoMoreInteractions(predicate);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsSinglePersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").asMale();
    Person janeDoe = Person.newPerson("Jane", "Doe").asFemale();
    Person jackHandy = Person.newPerson("Jack", "Handy").asMale();

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe, jackHandy);

    doCallRealMethod().when(mockGroup).findBy(any(Predicate.class));

    Set<Person> matches = mockGroup.findBy(person -> Gender.FEMALE.equals(person.getGender().orElse(null)));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(1);
    assertThat(matches).containsExactlyInAnyOrder(janeDoe);

    verify(mockGroup, times(1)).findBy(isA(Predicate.class));
  }

  @Test
  public void findByWithNullPredicate() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).findBy(any());

    assertThatIllegalArgumentException()
      .isThrownBy(() -> mockGroup.findBy(null))
      .withMessage("Predicate is required")
      .withNoCause();

    verify(mockGroup, times(1)).findBy(isNull());
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsFirstPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe);

    doCallRealMethod().when(mockGroup).findOne(any(Predicate.class));

    Optional<Person> optionalPerson = mockGroup.findOne(person -> "Doe".equals(person.getLastName()));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson).isPresent();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);

    verify(mockGroup, times(1)).findOne(isA(Predicate.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsNoPersonMatchingPredicate() {

    Predicate<Person> predicate = spy(new PersonGreaterThanAgePredicate(20));

    Person jonDoe = Person.newPerson("Jon", "Doe").age(20);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe);

    doCallRealMethod().when(mockGroup).findOne(any(Predicate.class));

    Optional<Person> optionalPerson = mockGroup.findOne(predicate);

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson).isNotPresent();

    verify(mockGroup, times(1)).findOne(eq(predicate));
    verify(predicate, times(1)).test(eq(jonDoe));
    verify(predicate, times(1)).test(eq(janeDoe));
    verifyNoMoreInteractions(predicate);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsOnlyPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").asMale();

    Group<Person> mockGroup = mockGroup(jonDoe);

    doCallRealMethod().when(mockGroup).findOne(any(Predicate.class));

    Optional<Person> optionalPerson = mockGroup.findOne(person ->
      Gender.MALE.equals(person.getGender().orElse(null)));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson).isPresent();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);

    verify(mockGroup, times(1)).findOne(isA(Predicate.class));
  }

  @Test
  public void findOneWithNullPredicate() {

    Group<Person> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).findOne(any());

    assertThatIllegalArgumentException()
      .isThrownBy(() -> mockGroup.findOne(null))
      .withMessage("Predicate is required")
      .withNoCause();

    verify(mockGroup, times(1)).findOne(isNull());
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  public void generatesUniqueIds() {

    assertThat(Stream.generate(Group::generateId)
      .limit(100)
      .collect(Collectors.toSet()))
      .hasSize(100);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void intersectionOfGroups() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A", mockPersonOne, mockPersonTwo);
    Group<Person> mockGroupTwo = mockGroup("B", mockPersonTwo);

    doCallRealMethod().when(mockGroupOne).intersection(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));
    doAnswer(invocation -> mockPersonTwo.equals(invocation.getArgument(0))).when(mockGroupTwo).contains(any());

    Set<Person> difference = mockGroupOne.intersection(mockGroupTwo);

    assertThat(difference).isNotNull();
    assertThat(difference).containsExactly(mockPersonTwo);

    verify(mockGroupOne, times(1)).intersection(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).findBy(isNotNull(Predicate.class));
    verify(mockGroupOne, times(1)).iterator();
    verify(mockGroupOne, times(1)).spliterator();
    verify(mockGroupTwo, times(2)).contains(isA(Person.class));
    verifyNoMoreInteractions(mockGroupOne, mockGroupTwo);
    verifyNoInteractions(mockPersonOne, mockPersonTwo);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void intersectionOfEmptyGroup() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A");
    Group<Person> mockGroupTwo = mockGroup("B", mockPerson);

    doCallRealMethod().when(mockGroupOne).intersection(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));
    doCallRealMethod().when(mockGroupTwo).intersection(any(Group.class));
    doCallRealMethod().when(mockGroupTwo).findBy(any(Predicate.class));

    Set<Person> intersection = mockGroupOne.intersection(mockGroupTwo);

    assertThat(intersection).isNotNull();
    assertThat(intersection).isEmpty();

    intersection = mockGroupTwo.intersection(mockGroupOne);

    assertThat(intersection).isNotNull();
    assertThat(intersection).isEmpty();

    verify(mockGroupOne, times(1)).contains(eq(mockPerson));
    verify(mockGroupOne, times(1)).intersection(eq(mockGroupTwo));
    verify(mockGroupTwo, times(1)).intersection(eq(mockGroupOne));

    Arrays.asList(mockGroupOne, mockGroupTwo).forEach(mockGroup -> {
      verify(mockGroup, times(1)).findBy(isNotNull(Predicate.class));
      verify(mockGroup, times(1)).iterator();
      verify(mockGroup, times(1)).spliterator();
    });

    verifyNoMoreInteractions(mockGroupOne, mockGroupTwo);
    verifyNoInteractions(mockPerson);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void intersectionOfMatchingGroups() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("A", mockPerson);
    Group<Person> mockGroupTwo = mockGroup("B", mockPerson);

    doCallRealMethod().when(mockGroupOne).intersection(any(Group.class));
    doCallRealMethod().when(mockGroupOne).findBy(any(Predicate.class));
    doAnswer(invocation -> mockPerson.equals(invocation.getArgument(0))).when(mockGroupTwo).contains(any());

    Set<Person> difference = mockGroupOne.intersection(mockGroupTwo);

    assertThat(difference).isNotNull();
    assertThat(difference).containsExactly(mockPerson);

    verify(mockGroupOne, times(1)).intersection(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).findBy(isNotNull(Predicate.class));
    verify(mockGroupOne, times(1)).iterator();
    verify(mockGroupOne, times(1)).spliterator();
    verify(mockGroupTwo, times(1)).contains(eq(mockPerson));
    verifyNoMoreInteractions(mockGroupOne, mockGroupTwo);
    verifyNoInteractions(mockPerson);
  }

  @Test
  public void intersectionWithNullGroup() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).intersection(any());

    assertThatIllegalArgumentException()
      .isThrownBy(() -> mockGroup.intersection(null))
      .withMessage("Group used in intersection is required")
      .withNoCause();
  }

  @Test
  public void isEmptyWhenSizeIsGreaterThanZeroReturnsFalse() {

    Group<?> mockGroup = mockGroup();

    doReturn(1).when(mockGroup).size();
    doCallRealMethod().when(mockGroup).isEmpty();

    assertThat(mockGroup.isEmpty()).isFalse();

    verify(mockGroup, times(1)).isEmpty();
    verify(mockGroup, times(1)).size();
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  public void isEmptyWhenSizeIsLessThanOneReturnsTrue() {

    Group<?> mockGroup = mockGroup();

    doReturn(0, -1, -2).when(mockGroup).size();
    doCallRealMethod().when(mockGroup).isEmpty();

    assertThat(mockGroup.isEmpty()).isTrue();
    assertThat(mockGroup.isEmpty()).isTrue();
    assertThat(mockGroup.isEmpty()).isTrue();

    verify(mockGroup, times(3)).isEmpty();
    verify(mockGroup, times(3)).size();
    verifyNoMoreInteractions(mockGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithExistingPersonReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Set<Person> people = CollectionUtils.asSet(jonDoe, janeDoe);

    Group<Person> mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.leave(janeDoe)).isTrue();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);

    verify(mockGroup, times(1)).leave(janeDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithNonExistingPersonReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Set<Person> people = CollectionUtils.asSet(jonDoe, janeDoe);

    Group<Person> mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.leave(Person.newPerson("Pie", "Doe"))).isFalse();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);

    verify(mockGroup, times(1)).leave(isA(Person.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithNullPersonIsNullSafeReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    Set<Person> people = Collections.singleton(jonDoe);

    Group<Person> mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);
    assertThat(mockGroup.leave((Person) null)).isFalse();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);

    verify(mockGroup, times(1)).leave(isNull(Person.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveEmptyGroupReturnsFalse() {

    Person jackHandy = Person.newPerson("Jack", "Handy");

    Group<Person> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).isEmpty();
    assertThat(mockGroup.leave(jackHandy)).isFalse();
    assertThat(mockGroup).isEmpty();

    verify(mockGroup, times(1)).leave(eq(jackHandy));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithPredicateRemovesEveryPersonFromGroupAndReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person pieDoe = Person.newPerson("Pie", "Doe");

    Set<Person> people = CollectionUtils.asSet(jonDoe, janeDoe, pieDoe);

    Group<Person> mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe, pieDoe);
    assertThat(mockGroup.leave(person -> true)).isTrue();
    assertThat(mockGroup).isEmpty();
    assertThat(people).isEmpty();

    verify(mockGroup, times(1)).leave(isA(Predicate.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithPredicateRemovesMultiplePeopleFromGroupReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person jackHandy = Person.newPerson("Jack", "Handy");

    Set<Person> people = CollectionUtils.asSet(jonDoe, janeDoe, jackHandy);

    Group<Person> mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe, jackHandy);
    assertThat(mockGroup.leave(personInGroup -> "Doe".equalsIgnoreCase(personInGroup.getLastName()))).isTrue();
    assertThat(mockGroup).containsExactlyInAnyOrder(jackHandy);
    assertThat(people).containsExactlyInAnyOrder(jackHandy);

    verify(mockGroup, times(1)).leave(isA(Predicate.class));
  }

  @Test
  public void sizeOfGroupWithTwoPeopleReturnsTwo() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Group<Person> mockGroup = mockGroup(jonDoe, janeDoe);

    doCallRealMethod().when(mockGroup).size();

    assertThat(mockGroup.size()).isEqualTo(2);

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void sizeOfGroupWithOnePersonReturnsOne() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    Group<Person> mockGroup = mockGroup(jonDoe);

    doCallRealMethod().when(mockGroup).size();

    assertThat(mockGroup.size()).isOne();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void sizeOfEmptyGroupReturnsZero() {

    Group<?> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).size();

    assertThat(mockGroup.size()).isZero();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void streamGroupOfPeople() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPersonOne, mockPersonTwo);

    doCallRealMethod().when(mockGroup).stream();

    assertThat(mockGroup).isNotNull();

    Stream<Person> stream = mockGroup.stream();

    assertThat(stream).isNotNull();
    assertThat(stream).containsExactly(mockPersonOne, mockPersonTwo);

    verifyNoInteractions(mockPersonOne, mockPersonTwo);
  }

  @Test
  public void streamGroupOfPerson() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPerson);

    doCallRealMethod().when(mockGroup).stream();

    assertThat(mockGroup).isNotNull();

    Stream<Person> stream = mockGroup.stream();

    assertThat(stream).isNotNull();
    assertThat(stream).containsExactly(mockPerson);

    verifyNoInteractions(mockPerson);
  }

  @Test
  public void streamEmptyGroup() {

    Group<Person> mockGroup = mockGroup();

    doCallRealMethod().when(mockGroup).stream();

    assertThat(mockGroup).isNotNull();

    Stream<Person> stream = mockGroup.stream();

    assertThat(stream).isNotNull();
    assertThat(stream).isEmpty();
  }

  @Test
  public void unionIsCorrect() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);
    Person mockPersonThree = mock(Person.class);

    Group<Person> mockGroupOne = mockGroup("GroupOne", mockPersonOne, mockPersonTwo);
    Group<Person> mockGroupTwo = mockGroup("GroupTwo", mockPersonTwo, null, mockPersonThree);

    doCallRealMethod().when(mockGroupOne).stream();
    doCallRealMethod().when(mockGroupTwo).stream();
    doCallRealMethod().when(mockGroupOne).union(any());

    Set<Person> people = mockGroupOne.union(mockGroupTwo);

    assertThat(people).isNotNull();
    assertThat(people).containsExactlyInAnyOrder(mockPersonOne, mockPersonTwo, mockPersonThree);

    verify(mockGroupOne, times(1)).union(eq(mockGroupTwo));
    verify(mockGroupOne, times(1)).stream();
    verify(mockGroupTwo, times(1)).stream();

    verifyNoInteractions(mockPersonOne, mockPersonTwo, mockPersonThree);
  }

  @Test
  public void unionIsNullSafe() {

    Person mockPerson = mock(Person.class);

    Group<Person> mockGroup = mockGroup(mockPerson);

    doCallRealMethod().when(mockGroup).stream();
    doCallRealMethod().when(mockGroup).union(any());

    Set<Person> people = mockGroup.union(null);

    assertThat(people).isNotNull();
    assertThat(people).containsExactly(mockPerson);

    verify(mockGroup, times(1)).union(isNull());
    verify(mockGroup, times(1)).stream();
    verify(mockGroup, times(1)).iterator();
    verify(mockGroup, times(1)).spliterator();
    verifyNoMoreInteractions(mockGroup);
    verifyNoInteractions(mockPerson);
  }

  private static class PersonGreaterThanAgePredicate implements Predicate<Person> {

    private final int age;

    public PersonGreaterThanAgePredicate(int age) {
      this.age = age;
    }

    @Override
    public boolean test(@NotNull Person person) {
      return person.getAge().orElse(0) > this.age;
    }
  }
}
