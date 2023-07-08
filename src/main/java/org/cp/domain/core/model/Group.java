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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * Abstract Data Type (ADT) defining a {@link Iterable collection} of {@link Object entities} that form a relationship.
 *
 * @author John Blum
 * @param <T> {@link Class type} of the {@link Object members} contained in this {@link Group}.
 * @see java.lang.Iterable
 * @see java.util.UUID
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @since 0.1.0
 */
public interface Group<T> extends Identifiable<UUID>, Iterable<T>, Nameable<String>, Renderable, Visitable {

  /**
   * Accepts the given {@link Visitor} used to visit each member in this {@link Group}.
   *
   * @param visitor {@link Visitor} used to visit each member in this {@link Group};
   * must not be {@literal null}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(@NotNull Visitor visitor) {

    StreamUtils.stream(this)
      .filter(Visitable.class::isInstance)
      .map(Visitable.class::cast)
      .forEach(entity -> entity.accept(visitor));
  }

  /** Determines whether the given entity is a member of this {@link Group}.
   *
   * @param entity {@link Object entity} to evaluate for membership in this {@link Group}.
   * @return a boolean value indicating whether the given entity is a member of this {@link Group}.
   * @see #findOne(Predicate)
   */
  @NullSafe
  default boolean contains(@Nullable T entity) {
    return entity != null && findOne(entity::equals).isPresent();
  }

  /**
   * Counts the number of members in this {@link Group} matching the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} used to match members in this {@link Group} to count;
   * must not be {@literal null}.
   * @return a {@link Integer count} of the number of members in this {@link Group}
   * matching the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see java.util.function.Predicate
   * @see #isEmpty()
   */
  default int count(@NotNull Predicate<T> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    long count = StreamUtils.stream(this)
      .filter(predicate)
      .count();

    return Long.valueOf(count).intValue();
  }

  /**
   * Computes of {@literal difference} of this {@link Group} with the given, required {@link Group}.
   *
   * @param group {@link Group} to compare with this {@link Group} in the {@literal set difference operation};
   * must not be {@literal null}.
   * @return a {@link Set} of members in this {@link Group} but not in the given {@link Group}.
   * @throws IllegalArgumentException if the given {@link Group} is {@literal null}.
   * @see #intersection(Group)
   * @see #findBy(Predicate)
   */
  default Set<T> difference(@NotNull Group<T> group) {

    Assert.notNull(group, "Group used in set difference is required");

    return findBy(entity -> !group.contains(entity));
  }

  /**
   * Finds all members in this {@link Group} matching the given, required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining query criteria used to find and match members in this {@link Group};
   * must not be {@literal null}.
   * @return all members in this {@link Group} matching the query criteria defined by the given {@link Predicate}.
   * Returns an {@link Set#isEmpty() empty Set} if no members in this {@link Group} were found matching the query
   * criteria defined by the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see java.util.function.Predicate
   * @see #findOne(Predicate)
   * @see java.util.Set
   */
  default Set<T> findBy(@NotNull Predicate<T> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return StreamUtils.stream(this)
      .filter(predicate)
      .collect(Collectors.toSet());
  }

  /**
   * Finds the first member in this {@link Group} matching the query criteria defined by the given,
   * required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining query criteria used to fina and match a single member
   * in this {@link Group}; must not be {@literal null}.
   * @return the first member in this {@link Group} matching the query criteria defined by the given {@link Predicate}.
   * @throws IllegalArgumentException if the {@link Predicate} is {@literal null}.
   * @see java.util.function.Predicate
   * @see java.util.Optional
   * @see #findBy(Predicate)
   */
  default Optional<T> findOne(@NotNull Predicate<T> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    return StreamUtils.stream(this)
      .filter(predicate)
      .findFirst();
  }

  /**
   * Computes the {@literal intersection} of this {@link Group} with the given, required {@link Group}.
   *
   * @param group {@link Group} to compare with this {@link Group} in the {@literal intersection operation};
   * must not be {@literal null}.
   * @return a {@link Set} of members in this {@link Group} and the given {@link Group}.
   * @throws IllegalArgumentException if the given {@link Group} is {@literal null}.
   * @see #difference(Group)
   * @see #findBy(Predicate)
   */
  default Set<T> intersection(@NotNull Group<T> group) {

    Assert.notNull(group, "Group used in intersection is required");

    return findBy(group::contains);
  }

  /**
   * Determines whether this {@link Group} contains any members.
   *
   * @return a boolean value indicating whether this {@link Group} contains any members.
   * @see #size()
   */
  default boolean isEmpty() {
    return size() < 1;
  }

  /**
   * Adds the given entity to this {@link Group} only if the given entity is not {@literal null}
   * and is not already a member of this {@link Group}.
   *
   * @param entity {@link Object entity} to add; must not be {@literal null}.
   * @return a boolean value indicating whether the given entity was successfully added to this {@link Group}.
   * @see #leave(T)
   */
  boolean join(T entity);

  /**
   * Removes the given entity from this {@link Group}.
   *
   * @param entity {@link Object entity} to remove.
   * @return a boolean value indicating whether the given entity was a member of this {@link Group}
   * and was successfully removed.
   * @see #leave(Predicate)
   * @see #join(T)
   */
  @NullSafe
  default boolean leave(@Nullable T entity) {
    return leave(entityInGroup -> entityInGroup.equals(entity));
  }

  /**
   * Removes all entities belonging to this {@link Group} matching the query criteria defined by the given,
   * required {@link Predicate}.
   *
   * @param predicate {@link Predicate} defining the query criteria used to find and match entities belonging to
   * this {@link Group} to remove; must not be {@literal null}.
   * @return a boolean value indicating whether this {@link Group} was modified as a result of invoking this operation.
   * @throws IllegalArgumentException if the given {@link Predicate} is {@literal null}.
   * @throws ConcurrentModificationException if this {@link Group} is concurrently modified by another {@link Thread}
   * while this method is executing.
   * @see java.util.function.Predicate
   * @see #leave(T)
   */
  default boolean leave(@NotNull Predicate<T> predicate) {

    Assert.notNull(predicate, "Predicate is required");

    boolean result = false;

    for (Iterator<T> entity = iterator(); entity.hasNext(); ) {
      if (predicate.test(entity.next())) {
        entity.remove();
        result = true;
      }
    }

    return result;
  }

  /**
   * Returns the {@link Integer number} of members in this {@link Group}.
   *
   * @return the {@link Integer number} of members in this {@link Group}.
   * @see #count(Predicate)
   * @see #isEmpty()
   */
  default int size() {
    long count = StreamUtils.stream(this).count();
    return Long.valueOf(count).intValue();
  }

  /**
   * Returns a {@link Stream} of entities belonging to this {@link Group}.

   * @return a {@link Stream} of entities belonging to this {@link Group}.
   * @see java.util.stream.Stream
   */
  default Stream<T> stream() {
    return StreamUtils.stream(this);
  }

  /**
   * Computes the {@literal union} of this {@link Group} with the given {@link Group}.
   *
   * @param group {@link Group} to combine in union with this {@link Group}.
   * @return the {@link Set} {@literal union} of this {@link Group} with the given {@link Group}.
   * @see java.util.Set
   */
  default @NotNull Set<T> union(@Nullable Group<T> group) {

    Set<T> union = stream()
      .filter(Objects::nonNull)
      .collect(Collectors.toSet());

    if (group != null) {
      group.stream()
        .filter(Objects::nonNull)
        .forEach(union::add);
    }

    return union;
  }
}
