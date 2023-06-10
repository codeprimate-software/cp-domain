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

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.Renderable;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.stream.StreamUtils;

/**
 * Abstract Data Type (ADT) defining a {@link Iterable collection} of {@link Person people} that form a relationship.
 *
 * @author John Blum
 * @see java.lang.Iterable
 * @see java.util.UUID
 * @see org.cp.domain.core.model.Person
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @since 1.0.0
 */
public interface Group extends Identifiable<UUID>, Iterable<Person>, Nameable<String>, Renderable, Visitable {

  /**
   * Accepts the given {@link Visitor} used to visit each {@link Person} in this {@link Group}.
   *
   * @param visitor {@link Visitor} used to visit each {@link Person} in this {@link Group};
   * must not be {@literal null}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(@NotNull Visitor visitor) {
    StreamUtils.stream(this).forEach(person -> person.accept(visitor));
  }

  /**
   * Counts the number of {@link Person people} in this {@link Group} matching the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} used to match {@link Person people} in this {@link Group} to count;
   * must not be {@literal null}.
   * @return a {@link Integer count} of the number of {@link Person people} in this {@link Group}
   * matching the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see java.util.function.Predicate
   * @see #isEmpty()
   */
  default int count(@NotNull Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    long count = StreamUtils.stream(this)
      .filter(predicate)
      .count();

    return Long.valueOf(count).intValue();
  }

  /**
   * Computes the {@literal set difference} between this {@link Group} and the given, required {@link Group}
   * of {@link Person people}.
   *
   * @param group {@link Group} to compare with this {@link Group} in the {@literal set difference} operation;
   * must not be {@literal null}.
   * @return a {@link Set} of {@link Person people} in this {@link Group} but not in the given {@link Group}.
   * @throws IllegalArgumentException if the given {@link Group} is {@literal null}.
   * @see #findBy(Predicate)
   */
  default Set<Person> difference(@NotNull Group group) {

    Assert.notNull(group, "Group used in set difference is required");

    return findBy(person -> group.findOne(person::equals).isEmpty());
  }

  /**
   * Finds all {@link Person people} in this {@link Group} matching the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining query criteria used to find and match {@link Person people}
   * in this {@link Group}; must not be {@literal null}.
   * @return all {@link Person people} in this {@link Group} matching the query criteria defined by
   * the given {@link Predicate}. Returns an {@link Set#isEmpty() empty Set} if no {@link Person people}
   * in this {@link Group} were found matching the query criteria defined by the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see org.cp.domain.core.model.Person
   * @see java.util.function.Predicate
   * @see #findOne(Predicate)
   * @see java.util.Set
   */
  default Set<Person> findBy(@NotNull Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return StreamUtils.stream(this)
      .filter(predicate)
      .collect(Collectors.toSet());
  }

  /**
   * Finds the first {@link Person} in this {@link Group} matching the query criteria defined by
   * the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining query criteria used to fina and match a single {@link Person}
   * in this {@link Group}; must not be {@literal null}.
   * @return the first {@link Person} in this {@link Group} matching the query criteria defined by
   * the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see org.cp.domain.core.model.Person
   * @see java.util.function.Predicate
   * @see java.util.Optional
   * @see #findBy(Predicate)
   */
  default Optional<Person> findOne(@NotNull Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return StreamUtils.stream(this)
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
    return size() < 1;
  }

  /**
   * Adds the given {@link Person} to this {@link Group} only if the given {@link Person} is not {@literal null}
   * and is not already a member of this {@link Group}.
   *
   * @param person {@link Person} to add; must not be {@literal null}.
   * @return a boolean value indicating whether the given {@link Person} was successfully added to this {@link Group}.
   * @see org.cp.domain.core.model.Person
   * @see #leave(Person)
   */
  boolean join(Person person);

  /**
   * Removes the given {@link Person} from this {@link Group}.
   *
   * @param person {@link Person} to remove.
   * @return a boolean value indicating whether the given {@link Person} was a member of this {@link Group}
   * and was removed successfully.
   * @see org.cp.domain.core.model.Person
   * @see #leave(Predicate)
   * @see #join(Person)
   */
  @NullSafe
  default boolean leave(@Nullable Person person) {
    return leave(personInGroup -> personInGroup.equals(person));
  }

  /**
   * Removes all {@link Person people} in this {@link Group} matching the query criteria defined by
   * the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining the query criteria used to find and match {@link Person people}
   * in this {@link Group} to remove; must not be {@literal null}.
   * @return a boolean value indicating whether this {@link Group} was modified as a result of invoking this operation.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @throws ConcurrentModificationException if this {@link Group} is concurrently modified by another {@link Thread}
   * while this method is executing.
   * @see org.cp.domain.core.model.Person
   * @see java.util.function.Predicate
   * @see #leave(Person)
   */
  default boolean leave(@NotNull Predicate<Person> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    boolean result = false;

    for (Iterator<Person> people = iterator(); people.hasNext(); ) {
      if (predicate.test(people.next())) {
        people.remove();
        result = true;
      }
    }

    return result;
  }

  /**
   * Returns the {@link Integer number} of {@link Person people} in this {@link Group}.
   *
   * @return the {@link Integer number} of {@link Person people} in this {@link Group}.
   * @see #count(Predicate)
   * @see #isEmpty()
   */
  default int size() {
    long count = StreamUtils.stream(this).count();
    return Long.valueOf(count).intValue();
  }
}
