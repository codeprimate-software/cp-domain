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
import static org.cp.elements.util.ArrayUtils.asIterable;
import static org.cp.elements.util.ArrayUtils.asIterator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.cp.elements.enums.Gender;
import org.cp.elements.lang.Visitor;
import org.cp.elements.util.CollectionUtils;
import org.junit.Test;

/**
 * Unit tests for {@link Group}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.core.model.Group
 * @since 1.0.0
 */
public class GroupTests {

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsAllPeopleMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");
    Person jackHandy = Person.newPerson("Jack", "Handy");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(person -> "Doe".equals(person.getLastName()));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(2);
    assertThat(matches).containsExactlyInAnyOrder(jonDoe, janeDoe);

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsSinglePersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").as(Gender.MALE);
    Person janeDoe = Person.newPerson("Jane", "Doe").as(Gender.FEMALE);
    Person jackHandy = Person.newPerson("Jack", "Handy").as(Gender.MALE);

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(person -> Gender.FEMALE.equals(person.getGender().orElse(null)));

    assertThat(matches).isNotNull();
    assertThat(matches).hasSize(1);
    assertThat(matches).containsExactlyInAnyOrder(janeDoe);

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByFindsNoPeopleMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").age(21);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);
    Person jackHandy = Person.newPerson("Jack", "Handy").age(40);

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe, jackHandy));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findBy(any(Predicate.class))).thenCallRealMethod();

    Set<Person> matches = mockGroup.findBy(person -> person.getAge().orElse(0) > 50);

    assertThat(matches).isNotNull();
    assertThat(matches).isEmpty();

    verify(mockGroup, times(1)).iterator();
  }

  @Test(expected = IllegalArgumentException.class)
  @SuppressWarnings("unchecked")
  public void findByWithNullPredicateThrowsIllegalArgumentException() {

    try {
      Group mockGroup = mock(Group.class);

      when(mockGroup.findBy(any())).thenCallRealMethod();

      mockGroup.findBy(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Predicate is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsFirstPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(person -> "Doe".equals(person.getLastName()));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isTrue();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsOnlyPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").as(Gender.MALE);

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(person ->
      Gender.MALE.equals(person.getGender().orElse(null)));

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isTrue();
    assertThat(optionalPerson.orElse(null)).isEqualTo(jonDoe);

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findOneFindsNoPersonMatchingPredicate() {

    Person jonDoe = Person.newPerson("Jon", "Doe").age(20);
    Person janeDoe = Person.newPerson("Jane", "Doe").age(16);

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenReturn(asIterator(jonDoe, janeDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.findOne(any(Predicate.class))).thenCallRealMethod();

    Optional<Person> optionalPerson = mockGroup.findOne(person -> person.getAge().orElse(0) >= 21);

    assertThat(optionalPerson).isNotNull();
    assertThat(optionalPerson.isPresent()).isFalse();

    verify(mockGroup, times(1)).iterator();
  }

  @Test(expected = IllegalArgumentException.class)
  public void findOneWithNullPredicateThrowsIllegalArgumentException() {

    try {
      Group mockGroup = mock(Group.class);

      when(mockGroup.findOne(any())).thenCallRealMethod();

      mockGroup.findOne(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Predicate is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void isEmptyWhenSizeIsGreaterThanZeroReturnsFalse() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.size()).thenReturn(1);
    doCallRealMethod().when(mockGroup).isEmpty();

    assertThat(mockGroup.isEmpty()).isFalse();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void isEmptyWhenSizeIsZeroReturnsTrue() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.size()).thenReturn(0);
    doCallRealMethod().when(mockGroup).isEmpty();

    assertThat(mockGroup.isEmpty()).isTrue();

    verify(mockGroup, times(1)).size();
  }

  @Test
  public void removePersonReturnsTrue() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    List<Person> people = CollectionUtils.addAll(new ArrayList<>(), asIterable(jonDoe, janeDoe));

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> people.iterator());
    when(mockGroup.remove(any(Person.class))).thenCallRealMethod();

    assertThat(mockGroup).hasSize(2);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.remove(janeDoe)).isTrue();
    assertThat(mockGroup).hasSize(1);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);

    verify(mockGroup, atLeastOnce()).iterator();
  }

  @Test
  public void removeNonExistingPersonReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");
    Person janeDoe = Person.newPerson("Jane", "Doe");

    List<Person> people = CollectionUtils.addAll(new ArrayList<>(), asIterable(jonDoe, janeDoe));

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> people.iterator());
    when(mockGroup.remove(any(Person.class))).thenCallRealMethod();

    assertThat(mockGroup).hasSize(2);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);
    assertThat(mockGroup.remove(Person.newPerson("Jack", "Handy"))).isFalse();
    assertThat(mockGroup).hasSize(2);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe, janeDoe);

    verify(mockGroup, atLeastOnce()).iterator();
  }

  @Test
  public void removeAnyPersonFromEmptyGroupReturnsFalse() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> Collections.emptyIterator());
    when(mockGroup.remove(any(Person.class))).thenCallRealMethod();

    assertThat(mockGroup).isEmpty();
    assertThat(mockGroup.remove(Person.newPerson("Jack", "Handy"))).isFalse();
    assertThat(mockGroup).isEmpty();

    verify(mockGroup, atLeastOnce()).iterator();
  }

  @Test
  public void removeNullFromGroupReturnsFalse() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> asIterator(jonDoe));
    when(mockGroup.remove(any(Person.class))).thenCallRealMethod();

    assertThat(mockGroup).hasSize(1);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);
    assertThat(mockGroup.remove(Person.newPerson("Jack", "Handy"))).isFalse();
    assertThat(mockGroup).hasSize(1);
    assertThat(mockGroup).containsExactlyInAnyOrder(jonDoe);

    verify(mockGroup, atLeastOnce()).iterator();
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

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  public void sizeOfGroupWithOnePersonReturnsOne() {

    Person jonDoe = Person.newPerson("Jon", "Doe");

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> asIterator(jonDoe));
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.size()).thenCallRealMethod();

    assertThat(mockGroup.size()).isEqualTo(1);

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  public void sizeOfEmptyGroupReturnsZero() {

    Group mockGroup = mock(Group.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> Collections.emptyIterator());
    when(mockGroup.spliterator()).thenCallRealMethod();
    when(mockGroup.size()).thenCallRealMethod();

    assertThat(mockGroup.size()).isEqualTo(0);

    verify(mockGroup, times(1)).iterator();
  }

  @Test
  public void acceptsVisitor() {

    Person personOne = mock(Person.class);
    Person personTwo = mock(Person.class);

    Group mockGroup = mock(Group.class);

    Visitor mockVisitor = mock(Visitor.class);

    when(mockGroup.iterator()).thenAnswer(invocation -> asIterator(personOne, personTwo));
    when(mockGroup.spliterator()).thenCallRealMethod();
    doCallRealMethod().when(mockGroup).accept(any(Visitor.class));

    mockGroup.accept(mockVisitor);

    verify(personOne, times(1)).accept(eq(mockVisitor));
    verify(personTwo, times(1)).accept(eq(mockVisitor));
  }
}
