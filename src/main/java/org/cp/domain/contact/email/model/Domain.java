/*
 * Copyright 2011-Present Author or Authors.
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
package org.cp.domain.contact.email.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.concurrent.ThreadSafe;
import org.cp.elements.text.FormatUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling the {@literal domain} of an {@link EmailAddress}.
 *
 * @author John Blum
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.concurrent.ThreadSafe
 * @since 0.1.0
 */
@Immutable
@ThreadSafe
@SuppressWarnings("unused")
public class Domain implements Cloneable, Comparable<Domain>, Serializable {

  private static final String DOMAIN_DOT_SEPARATOR = StringUtils.DOT_SEPARATOR;
  private static final String DOMAIN_TO_STRING = "%1$s".concat(DOMAIN_DOT_SEPARATOR).concat("%2$s");

  /**
   * Factory method used to construct a new {@link Domain} copied from the given, required {@link Domain}.
   *
   * @param domain {@link Domain} to copy; must not be {@literal null}.
   * @return a new {@link Domain} copied from the existing, required {@link Domain}.
   * @throws IllegalArgumentException if the given {@link Domain} to copy is {@literal null}.
   */
  public static @NotNull Domain from(@NotNull Domain domain) {

    Assert.notNull(domain, "Domain to copy is required");

    return new Domain(domain.getName(), domain.getExtensionName());
  }

  /**
   * Factory method used to construct a new {@link Domain} initialized with the given, required {@link String name}
   * and {@link Domain.Extension}.
   *
   * @param name {@link String name} of this {@link Domain}; must not be {@literal null} or {@literal empty}.
   * @param domainExtension {@link Domain.Extension} of this {@link Domain}.
   * @return a new {@link Domain} initialized with the given, required {@link String name} and {@link Domain.Extension}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty},
   * or {@link Domain.Extension} is {@literal null}.
   * @see org.cp.domain.contact.email.model.Domain.Extension
   */
  public static @NotNull Domain of(@NotNull String name, @NotNull Domain.Extension domainExtension) {
    return new Domain(name, domainExtension);
  }

  /**
   * Factory method used to construct a new {@link Domain} initialized with the given, required {@link String name}
   * and {@link String domain extension}.
   *
   * @param name {@link String name} of the new {@link Domain}; must not be {@literal null} or {@literal empty}.
   * @param extensionName {@link String extension name} of the new {@link Domain};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Domain} initialized with the given required {@link String name}
   * and {@link String domain extension}.
   * @throws IllegalArgumentException if the given {@link String name} or {@link String extension name}
   * is {@literal null} or {@literal empty}.
   */
  public static @NotNull Domain of(@NotNull String name, @NotNull String extensionName) {
    return new Domain(name, extensionName);
  }

  /**
   * Factory method used to construct a new {@link Domain} initialized by parsing a given, required {@link String}
   * representing a {@literal domain name}.
   *
   * @param domainName {@link String} containing the {@literal domain name} to parse;
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Domain} parsed from the given, required {@link String}
   * representing a {@link String domain name}.
   * @throws IllegalArgumentException if the given {@link String domain name} is {@literal null} or {@literal empty},
   * or the {@link String domain name} format is not valid.
   */
  public static @NotNull Domain parse(@NotNull String domainName) {

    Assert.hasText(domainName, "Domain Name [%s] to parse is required");

    int indexOfDot = domainName.lastIndexOf(DOMAIN_DOT_SEPARATOR);

    Assert.isTrue(indexOfDot > 0, "Domain Name [%s] format is not valid", domainName);

    String name = domainName.substring(indexOfDot);
    String extensionName = domainName.substring(indexOfDot + 1);

    return new Domain(name, extensionName);
  }

  private final String name;
  private final String extensionName;

  /**
   * Constructs a new {@link Domain} initialized with the given, required {@link String name}
   * and {@link Domain.Extension}.
   *
   * @param name {@link String name} of this {@link Domain}; must not be {@literal null} or {@literal empty}.
   * @param domainExtension {@link Domain.Extension} of this {@link Domain}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty},
   * or {@link Domain.Extension} is {@literal null}.
   * @see org.cp.domain.contact.email.model.Domain.Extension
   * @see Domain(String, String)
   */
  public Domain(@NotNull String name, @NotNull Domain.Extension domainExtension) {

    this(name, ObjectUtils.requireObject(domainExtension, "Domain.Extension is required")
      .name().toLowerCase());
  }

  /**
   * Constructs a new {@link Domain} initialized with the given, required {@link String name}
   * and {@link String domain extension}.
   *
   * @param name {@link String name} of this {@link Domain}; must not be {@literal null} or {@literal empty}.
   * @param extensionName {@link String extension name} of this {@link Domain};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the given {@link String name} or {@link String extension name}
   * is {@literal null} or {@literal empty}.
   */
  public Domain(@NotNull String name, @NotNull String extensionName) {

    this.name = StringUtils.requireText(name, "Name [%s] is required");
    this.extensionName = StringUtils.requireText(extensionName, "Extension [%s] is required");
  }

  /**
   * Gets the {@link String name} of this {@link Domain}.
   *
   * @return the {@link String name} of this {@link Domain}.
   */
  public @NotNull String getName() {
    return this.name;
  }

  /**
   * Returns an {@link Optional} {@link Domain.Extension} modeling this {@link Domain Domain's}
   * {@link #getExtensionName() extension}.
   *
   * @return an {@link Optional} {@link Domain.Extension} modeling this {@link Domain Domain's}
   * {@link #getExtensionName() extension}.
   * @see org.cp.domain.contact.email.model.Domain.Extension
   * @see #getExtensionName()
   * @see java.util.Optional
   */
  public Optional<Extension> getExtension() {
    return Domain.Extension.from(getExtensionName());
  }

  /**
   * Gets the {@link String extension name} of this {@link Domain}.
   *
   * @return the {@link String extension name} of this {@link Domain}.
   */
  public @NotNull String getExtensionName() {
    return this.extensionName;
  }

  @Override
  @SuppressWarnings("all")
  protected @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull Domain that) {

    return ComparatorResultBuilder.<String>create()
      .doCompare(this.getExtensionName(), that.getExtensionName())
      .doCompare(this.getName(), that.getName())
      .build();
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Domain that)) {
      return false;
    }

    return ObjectUtils.equals(this.getName(), that.getName())
      && ObjectUtils.equals(this.getExtensionName(), that.getExtensionName());
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getName(), getExtensionName());
  }

  @Override
  public @NotNull String toString() {
    return FormatUtils.format(DOMAIN_TO_STRING, getName(), getExtensionName());
  }

  /**
   * {@link Enum Enumeration} of {@link Domain} {@link String Extensions}.
   *
   * @see java.lang.Enum
   */
  enum Extension {

    BIZ,
    CO,
    COM,
    DE,
    EDU,
    GOV,
    INFO,
    IO,
    ME,
    NET,
    ORG,
    SITE,
    UK,
    US,
    XYZ;

    public static Optional<Domain.Extension> from(@Nullable String domainName) {

      return Optional.ofNullable(domainName)
        .map(StringUtils::trim)
        .map(StringUtils::toLowerCase)
        .filter(StringUtils::hasText)
        .flatMap(it -> Arrays.stream(values())
          .filter(ext -> it.endsWith(ext.name().toLowerCase()))
          .findFirst());
    }
  }
}
