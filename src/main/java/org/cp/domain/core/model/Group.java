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

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Filter;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;

/**
 * {@link Group} is an Abstract Data Type (ADT) defining a collection of {@link Person people}
 * that form a relationship.
 *
 * @author John Blum
 * @see java.lang.Iterable
 * @see java.util.Iterator
 * @see org.cp.domain.core.model.Person
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Visitable
 * @since 1.0.0
 */
public interface Group extends Identifiable<String>, Iterable<Person>, Nameable<String>, Visitable {

  /**
   * Adds the given {@link Person} to this {@link Group} iff the given {@link Person} is not {@literal null}
   * and the {@link Person} is not already part of this {@link Group}.
   *
   * @param person {@link Person} to add; must not be {@literal null}.
   * @return a boolean value indicating whether the given {@link Person} was successfully added
   * to this {@link Group}.
   * @see #remove(Person)
   * @see org.cp.domain.core.model.Person
   */
  boolean add(Person person);

  /**
   * Finds all {@link Person people} in this {@link Group} that match the criteria of the given {@link Filter}.
   *
   * @param predicate {@link Predicate} defining the query filter criteria used to find/match {@link Person people}
   * of interests in this {@link Group}; must not be {@literal null}.
   * @return all {@link Person people} in this {@link Group} matching the query filter criteria
   * defined by the given {@link Predicate}.  Returns an empty {@link Set} if no {@link Person people}
   * in this {@link Group} were found matching the {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see org.cp.domain.core.model.Person
   * @see java.util.function.Predicate
   * @see #findOne(Predicate)
   */
  default Set<Person> findBy(Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return stream(this.spliterator(), false)
      .filter(predicate)
      .collect(Collectors.toSet());
  }

  /**
   * Finds the first {@link Person} in this {@link Group} matching the query filter criteria
   * defined by the {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining the query filter criteria required to match
   * a {@link Person} in this {@link Group}.
   * @return the first {@link Person} in this {@link Group} matching the query filter criteria
   * defined by the {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see org.cp.domain.core.model.Person
   * @see java.util.function.Predicate
   * @see #findBy(Predicate)
   */
  default Optional<Person> findOne(Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return stream(this.spliterator(), false)
      .filter(predicate)
      .findFirst();
  }

  /**
   * Determines whether this {@link Group} contains any {@link Person people}.
   *
   * @return a boolean value indicating whether this {@link Group} contains any {@link Person people}.
   * @see #size()
   */
  default boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Removes the given {@link Person} from this {@link Group}.
   *
   * @param person {@link Person} to remove.
   * @return a boolean value based on whether the {@link Person} was a member of this {@link Group}
   * and was removed successfully.
   * @see org.cp.domain.core.model.Person
   * @see #add(Person)
   */
  default boolean remove(Person person) {

    boolean result = false;

    for (Iterator<Person> people = iterator(); people.hasNext(); ) {
      if (people.next().equals(person)) {
        people.remove();
        result = true;
      }
    }

    return result;
  }

  /**
   * Returns a {@link Integer#TYPE int value} with the number of {@link Person people} in this {@link Group}.
   *
   * @return a {@link Integer#TYPE int value} with the number of {@link Person people} in this {@link Group}.
   * @see #isEmpty()
   */
  int size();

  /**
   * Accepts the given {@link Visitor} to visit each {@link Person} in this {@link Group}.
   *
   * @param visitor {@link Visitor} used to visit each {@link Person} in this {@link Group}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(Visitor visitor) {
    StreamSupport.stream(spliterator(), false).forEach(person -> person.accept(visitor));
  }
}
