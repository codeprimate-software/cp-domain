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
import static org.cp.elements.util.ArrayUtils.asIterator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.cp.elements.enums.Gender;
import org.cp.elements.lang.Visitor;
import org.junit.Test;

/**
 * Unit Tests for {@link Group}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.core.model.Group
 * @since 1.0.0
 */
public class GroupTests {

  @Test
  public void acceptsVisitor() {

    Visitor mockVisitor = mock(Visitor.class);

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group mockGroup = mock(Group.class);

    doCallRealMethod().when(mockGroup).accept(any(Visitor.class));
    doAnswer(invocation -> asIterator(mockPersonOne, mockPersonTwo)).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).spliterator();

    mockGroup.accept(mockVisitor);

    verify(mockPersonOne, times(1)).accept(eq(mockVisitor));
    verify(mockPersonTwo, times(1)).accept(eq(mockVisitor));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsAll() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group mockGroup = mock(Group.class);

    doCallRealMethod().when(mockGroup).count(any(Predicate.class));
    doAnswer(invocation -> asIterator(mockPersonOne, mockPersonTwo)).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).spliterator();

    assertThat(mockGroup.count(person -> true)).isEqualTo(2);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsNone() {

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);

    Group mockGroup = mock(Group.class);

    doCallRealMethod().when(mockGroup).count(any(Predicate.class));
    doAnswer(invocation -> asIterator(mockPersonOne, mockPersonTwo)).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).spliterator();

    assertThat(mockGroup.count(person -> false)).isEqualTo(0);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void countsOne() {

    Predicate<Person> mockPredicate = mock(Predicate.class);

    Person mockPersonOne = mock(Person.class);
    Person mockPersonTwo = mock(Person.class);
    Person mockPersonThree = mock(Person.class);

    Group mockGroup = mock(Group.class);

    doCallRealMethod().when(mockGroup).count(any(Predicate.class));
    doAnswer(invocation -> asIterator(mockPersonOne, mockPersonTwo, mockPersonThree)).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).spliterator();
    when(mockPredicate.test(any(Person.class))).thenReturn(true).thenReturn(false);

    assertThat(mockGroup.count(mockPredicate)).isEqualTo(1);

    verify(mockPredicate, times(1)).test(eq(mockPersonOne));
    verify(mockPredicate, times(1)).test(eq(mockPersonTwo));
    verify(mockPredicate, times(1)).test(eq(mockPersonThree));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsAllPeopleMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person jackHandy = Person.newPerson("Jack", "Handy");

    Group mockGroup = mock(Group.class);

    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(person -> "Doe".equals(person.getLastName()));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(2);
    assertThat(matches).containsExactlyInAnyOrder(jonDoe, janeDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsNoPeopleMatchingPredicate() {

    Predicate<Person> predicate = spy(new PersonGreaterThanAgePredicate(50));

    Person jonDoe = Person.newPerson("Jon", "Doe").age(21);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);
    Person jackHandy = Person.newPerson("Jack", "Handy").age(40);

    Group mockGroup = mock(Group.class);

    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(predicate);

    assertThat(matches).isNotNull();
    assertThat(matches).isEmpty();

    verify(predicate, times(1)).test(eq(jonDoe));
    verify(predicate, times(1)).test(eq(janeDoe));
    verify(predicate, times(1)).test(eq(jackHandy));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsSinglePersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").asMale();
    Person janeDoe = Person.newPerson("Jane", "Doe").asFemale();
    Person jackHandy = Person.newPerson("Jack", "Handy").asMale();

    Group mockGroup = mock(Group.class);

    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(person -> Gender.FEMALE.equals(person.getGender().orElse(null)));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(1);
    assertThat(matches).containsExactlyInAnyOrder(janeDoe);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findByWithNullPredicateThrowsIllegalArgumentException() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.findBy(any())).thenCallRealMethod();

    try {
      mockGroup.findBy(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Predicate is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {

      verify(mockGroup, times(1)).findBy(eq(null));
      verifyNoMoreInteractions(mockGroup);
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsFirstPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(person -> "Doe".equals(person.getLastName()));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isTrue();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsOnlyPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").asMale();

    Group mockGroup = mock(Group.class);

    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(person ->
      Gender.MALE.equals(person.getGender().orElse(null)));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isTrue();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsNoPersonMatchingPredicate() {

    Predicate<Person> predicate = spy(new PersonGreaterThanAgePredicate(20));

    Person jonDoe = Person.newPerson("Jon", "Doe").age(20);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);

    Group mockGroup = mock(Group.class);

    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();
    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(predicate);

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isFalse();

    verify(predicate, times(1)).test(eq(jonDoe));
    verify(predicate, times(1)).test(eq(janeDoe));
  }

  @Test(expected = IllegalArgumentException.class)
  public void findOneWithNullPredicateThrowsIllegalArgumentException() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.findOne(any())).thenCallRealMethod();

    try {
      mockGroup.findOne(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Predicate is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(mockGroup, times(1)).findOne(eq(null));
    }
  }

  @Test
  public void isEmptyWhenSizeIsGreaterThanZeroReturnsFalse() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.size()).thenReturn(1);
    when(mockGroup.isEmpty()).thenCallRealMethod();

    assertThat(mockGroup.isEmpty()).isFalse();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void isEmptyWhenSizeIsLessThanZeroReturnsTrue() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.size()).thenReturn(-1);
    when(mockGroup.isEmpty()).thenCallRealMethod();

    assertThat(mockGroup.isEmpty()).isTrue();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void isEmptyWhenSizeIsZeroReturnsTrue() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.size()).thenReturn(0);
    when(mockGroup.isEmpty()).thenCallRealMethod();

    assertThat(mockGroup.isEmpty()).isTrue();

    verify(mockGroup, times(1)).size();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithExistingPersonReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    List<Person> people = new ArrayList<>(Arrays.asList(jonDoe, janeDoe));

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.leave(janeDoe)).isTrue();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithNonExistingPersonReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    List<Person> people = new ArrayList<>(Arrays.asList(jonDoe, janeDoe));

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.leave(Person.newPerson("Jack", "Handy"))).isFalse();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithNullPersonReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    List<Person> people = Collections.singletonList(jonDoe);

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);
    assertThat(mockGroup.leave((Person) null)).isFalse();
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveEmptyGroupReturnsFalse() {

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> Collections.emptyIterator()).when(mockGroup).iterator();
    doCallRealMethod().when(mockGroup).leave(any(Person.class));
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).isEmpty();
    assertThat(mockGroup.leave(Person.newPerson("Jack", "Handy"))).isFalse();
    assertThat(mockGroup).isEmpty();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithPredicateRemovesEveryPersonFromGroupAndReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person pieDoe = Person.newPerson("Pie", "Doe");

    List<Person> people = new ArrayList<>(Arrays.asList(jonDoe, janeDoe, pieDoe));

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe, pieDoe);
    assertThat(mockGroup.leave(person -> true)).isTrue();
    assertThat(mockGroup).isEmpty();
    assertThat(people).isEmpty();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void leaveWithPredicateRemovesMultiplePeopleFromGroupReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person jackHandy = Person.newPerson("Jack", "Handy");

    List<Person> people = new ArrayList<>(Arrays.asList(jonDoe, janeDoe, jackHandy));

    Group mockGroup = mock(Group.class);

    doAnswer(invocation -> people.iterator()).when(mockGroup).iterator();
    doAnswer(invocation -> people.spliterator()).when(mockGroup).spliterator();
    doCallRealMethod().when(mockGroup).leave(any(Predicate.class));

    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe, jackHandy);
    assertThat(mockGroup.leave(personInGroup -> "Doe".equalsIgnoreCase(personInGroup.getLastName()))).isTrue();
    assertThat(mockGroup).containsExactlyInAnyOrder(jackHandy);
    assertThat(people).containsExactlyInAnyOrder(jackHandy);
  }

  @Test
  public void sizeOfGroupWithTwoPeopleReturnsTwo() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> asIterator(jonDoe, janeDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.size()).thenCallRealMethod();

    assertThat(mockGroup.size()).isEqualTo(2);
  }

  @Test
  public void sizeOfGroupWithOnePersonReturnsOne() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> asIterator(jonDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.size()).thenCallRealMethod();

    assertThat(mockGroup.size()).isEqualTo(1);
  }

  @Test
  public void sizeOfEmptyGroupReturnsZero() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> Collections.emptyIterator());
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.size()).thenCallRealMethod();

    assertThat(mockGroup.size()).isEqualTo(0);
  }

  private static class PersonGreaterThanAgePredicate implements Predicate<Person> {

    private final int age;

    public PersonGreaterThanAgePredicate(int age) {
      this.age = age;
    }

    @Override
    public boolean test(Person person) {
      return person.getAge().orElse(0) > this.age;
    }
  }
}
