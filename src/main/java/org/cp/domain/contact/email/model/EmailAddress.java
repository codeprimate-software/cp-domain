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
package org.cp.domain.contact.email.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.concurrent.ThreadSafe;
import org.cp.elements.security.model.User;
import org.cp.elements.text.FormatUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling an {@literal email address}.
 *
 * @author John Blum
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.concurrent.ThreadSafe
 * @see org.cp.elements.security.model.User
 * @since 0.1.0
 */
@Immutable
@ThreadSafe
@SuppressWarnings("unused")
public class EmailAddress implements Cloneable, Comparable<EmailAddress>, Serializable, Visitable {

  private static final String EMAIL_ADDRESS_AT_SYMBOL = "@";
  private static final String EMAIL_ADDRESS_TO_STRING = "%1$s".concat(EMAIL_ADDRESS_AT_SYMBOL).concat("%2$s");

  /**
   * Factory method used to construct a new {@link EmailAddress} copied from the given, required {@link EmailAddress}.
   *
   * @param emailAddress {@link EmailAddress} to copy; must not be {@literal null}.
   * @return a new {@link EmailAddress} copied from the existing, required {@link EmailAddress}.
   * @throws IllegalArgumentException if the given {@link EmailAddress} to copy is {@literal null}.
   */
  public static @NotNull EmailAddress from(@NotNull EmailAddress emailAddress) {

    Assert.notNull(emailAddress, "Email Address to copy is required");

    return new EmailAddress(emailAddress.getUser(), emailAddress.getDomain());
  }

  /**
   * Factory method used to construct a new {@link EmailAddress} initialized with the given, required {@link User}
   * and {@link Domain}.
   *
   * @param user {@link User} containing the {@link String username} used as the {@literal handle}
   * or {@literal local part} of the new {@link EmailAddress}; must not be {@literal null}.
   * @param domain {@link Domain} of the new {@link EmailAddress}; must not be {@literal null}.
   * @return a new {@link EmailAddress} initialized with the given, required {@link User} and {@link Domain}.
   * @throws IllegalArgumentException if the given {@link User} or {@link Domain} are {@literal null}.
   * @see org.cp.domain.contact.email.model.EmailAddress.Domain
   * @see org.cp.elements.security.model.User
   */
  public static @NotNull EmailAddress of(@NotNull User<String> user, @NotNull Domain domain) {
    return new EmailAddress(user, domain);
  }

  /**
   * Factory method used to construct a new {@link EmailAddress} parsed from the given, required {@link String}
   * representing an {@literal email address}.
   * <p>
   * {@link String Email Addresses} are expected to be in the format {@link String jonDoe@example.com}.
   *
   * @param emailAddress {@link String} containing the {@literal email address} to parse;
   * must not be {@literal null} or {@literal empty}; must be a valid {@literal email address}.
   * @return a new {@link EmailAddress} parsed from the given, required {@link String}
   * representing an {@link EmailAddress}.
   * @throws IllegalArgumentException if the given {@link String email address} to parse is {@literal null}
   * or {@literal empty}, or if the given {@link String email address} format is not valid.
   */
  public static @NotNull EmailAddress parse(@NotNull String emailAddress) {

    int indexOfAtSymbol = assertEmailAddress(emailAddress);

    String username = emailAddress.substring(0, indexOfAtSymbol);
    String domainName = emailAddress.substring(indexOfAtSymbol + 1);

    return new EmailAddress(asUser(username), Domain.parse(domainName));
  }

  private static int assertEmailAddress(String emailAddress) {

    Assert.hasText(emailAddress, "Email Address [%s] to parse is required", emailAddress);

    int indexOfAtSymbol = emailAddress.indexOf(EMAIL_ADDRESS_AT_SYMBOL);

    Assert.isTrue(indexOfAtSymbol > 0, "Email Address [%s] format is not valid", emailAddress);

    return indexOfAtSymbol;
  }

  protected static @NotNull User<String> asUser(@NotNull String name) {

    Assert.hasText(name, "User name [%s] is required", name);

    return new User<>() {

      @Override
      public String getId() {
        return null;
      }

      @Override
      public String getName() {
        return name;
      }
    };
  }

  private final Domain domain;

  private final User<String> user;

  /**
   * Constructs a new {@link EmailAddress} initialized with the given, required {@link User} and {@link Domain}.
   *
   * @param user {@link User} containing the {@link String username} used as the {@literal handle}
   * or {@literal local part} for this {@link EmailAddress}; must not be {@literal null}.
   * @param domain {@link Domain} containing the {@link String domain name} of this {@link EmailAddress};
   * must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link User} or {@link Domain} are {@literal null}.
   * @see org.cp.domain.contact.email.model.EmailAddress.Domain
   * @see org.cp.elements.security.model.User
   */
  public EmailAddress(@NotNull User<String> user, @NotNull Domain domain) {

    this.user = ObjectUtils.requireObject(user, "User is required");
    this.domain = ObjectUtils.requireObject(domain, "Domain is required");
  }

  /**
   * Gets the {@link Domain} of this {@link EmailAddress}.
   *
   * @return the {@link Domain} of this {@link EmailAddress}.
   * @see Domain
   */
  public @NotNull Domain getDomain() {
    return this.domain;
  }

  /**
   * Gets the {@link String domain name} of this {@link EmailAddress}.
   *
   * @return the {@link String domain name} of this {@link EmailAddress}.
   * @see Domain#toString()
   * @see #getDomain()
   */
  public @NotNull String getDomainName() {
    return getDomain().toString();
  }

  /**
   * Gets the {@link User} of this {@link EmailAddress}.
   *
   * @return the {@link User} of this {@link EmailAddress}.
   * @see org.cp.elements.security.model.User
   */
  public @NotNull User<String> getUser() {
    return this.user;
  }

  /**
   * Gets the {@link String username}, {@link String handle} or {@link String local part} of this {@link EmailAddress}.
   *
   * @return the {@link String username}, {@link String handle} or {@link String local part}
   * of this {@link EmailAddress}.
   * @see org.cp.elements.security.model.User#getName()
   * @see #getUser()
   */
  public @NotNull String getUsername() {
    return getUser().getName();
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull EmailAddress that) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getDomain(), that.getDomain())
      .doCompare(this.getUsername(), that.getUsername())
      .build();
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof EmailAddress that)) {
      return false;
    }

    return ObjectUtils.equals(this.getUsername(), that.getUsername())
      && ObjectUtils.equals(this.getDomain(), that.getDomain());
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getUsername(), getDomain());
  }

  @Override
  public @NotNull String toString() {
    return FormatUtils.format(EMAIL_ADDRESS_TO_STRING, getUsername(), getDomainName());
  }

  /**
   * Abstract Data Type (ADT) modeling the {@literal domain} of an {@link EmailAddress}.
   *
   * @author John Blum
   * @see java.lang.Cloneable
   * @see java.lang.Comparable
   * @see java.io.Serializable
   * @see org.cp.elements.lang.Nameable
   * @see org.cp.elements.lang.annotation.Immutable
   * @see org.cp.elements.lang.concurrent.ThreadSafe
   * @since 0.1.0
   */
  @Immutable
  @ThreadSafe
  @SuppressWarnings("unused")
  public static class Domain implements Cloneable, Comparable<Domain>, Nameable<String>, Serializable {

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
     * and {@link Extension}.
     *
     * @param name {@link String name} of this {@link Domain}; must not be {@literal null} or {@literal empty}.
     * @param extension {@link Extension} of this {@link Domain}.
     * @return a new {@link Domain} initialized with the given, required {@link String name} and {@link Extension}.
     * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty},
     * or {@link Extension} is {@literal null}.
     * @see org.cp.domain.contact.email.model.EmailAddress.Domain.Extension
     */
    public static @NotNull Domain of(@NotNull String name, @NotNull Extension extension) {
      return new Domain(name, extension);
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

      int indexOfDot = assertDomainName(domainName);

      String name = domainName.substring(0, indexOfDot);
      String extensionName = domainName.substring(indexOfDot + 1);

      return new Domain(name, extensionName);
    }

    private static int assertDomainName(String domainName) {

      Assert.hasText(domainName, "Domain Name [%s] to parse is required", domainName);

      int indexOfDot = domainName.lastIndexOf(DOMAIN_DOT_SEPARATOR);

      Assert.isTrue(indexOfDot > 0, "Domain Name [%s] format is not valid", domainName);

      return indexOfDot;
    }

    private final String name;
    private final String extensionName;

    /**
     * Constructs a new {@link Domain} initialized with the given, required {@link String name}
     * and {@link Extension}.
     *
     * @param name {@link String name} of this {@link Domain}; must not be {@literal null} or {@literal empty}.
     * @param extension {@link Extension} of this {@link Domain}.
     * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty},
     * or {@link Extension} is {@literal null}.
     * @see org.cp.domain.contact.email.model.EmailAddress.Domain.Extension
     * @see Domain(String, String)
     */
    public Domain(@NotNull String name, @NotNull Extension extension) {
      this(name, ObjectUtils.requireObject(extension, "Domain.Extension is required").name());
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
      this.extensionName = StringUtils.toLowerCase(StringUtils.requireText(extensionName,
        "Extension [%s] is required"));
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
     * Returns an {@link Optional} {@link Extension} modeling this {@link Domain Domain's}
     * {@link #getExtensionName() extension}.
     *
     * @return an {@link Optional} {@link Extension} modeling this {@link Domain Domain's}
     * {@link #getExtensionName() extension}.
     * @see Extension
     * @see #getExtensionName()
     * @see Optional
     */
    public Optional<Extension> getExtension() {
      return Extension.from(getExtensionName());
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
     * {@link Enum Enumeration} of common {@link Domain} {@link String Extensions}.
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

      public static Optional<Extension> from(@Nullable String domainName) {

        return Optional.ofNullable(domainName)
          .map(Extension::trimmedLowerCase)
          .filter(StringUtils::hasText)
          .map(Extension::stripName)
          .flatMap(it -> Arrays.stream(Extension.values())
            .filter(ext -> it.endsWith(ext.name().toLowerCase()))
            .findFirst());
      }

      private static String stripName(String domainName) {
        return domainName.substring(domainName.lastIndexOf(StringUtils.DOT_SEPARATOR) + 1);
      }

      private static String trimmedLowerCase(String value) {
        return StringUtils.trim(StringUtils.toLowerCase(value));
      }
    }
  }
}
