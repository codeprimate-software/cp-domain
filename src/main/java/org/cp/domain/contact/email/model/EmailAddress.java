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

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
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
 * @see java.io.Serializable;
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.concurrent.ThreadSafe
 * @see org.cp.elements.security.model.User
 * @since 0.1.0
 */
@Immutable
@ThreadSafe
@SuppressWarnings("unused")
public class EmailAddress implements Cloneable, Comparable<EmailAddress>, Serializable {

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
   * @see org.cp.domain.contact.email.model.Domain
   * @see org.cp.elements.security.model.User
   */
  public static @NotNull EmailAddress of(@NotNull User<String> user, @NotNull Domain domain) {
    return new EmailAddress(user, domain);
  }

  /**
   * Factory method used to construct a new {@link EmailAddress} initialized from parsing the given,
   * required {@link String} representing an {@literal email address}.
   *
   * {@link String Email Addresses} are expected to be in the format {@link String jonDoe@gmail.com}.
   *
   * @param emailAddress {@link String} containing the {@literal email address} to parse;
   * must not be {@literal null} or {@literal empty}; must be a valid {@literal email address}.
   * @return a new {@link EmailAddress} parsed from the given, required {@link String}
   * representing an {@link EmailAddress}.
   * @throws IllegalArgumentException if the given {@link String email address} to parse is {@literal null}
   * or {@literal empty}, or if the given {@link String email address} format is not valid.
   */
  public static @NotNull EmailAddress parse(@NotNull String emailAddress) {

    Assert.hasText(emailAddress, "Email Address [%s] to parse is required", emailAddress);

    int indexOfAtSymbol = emailAddress.indexOf(EMAIL_ADDRESS_AT_SYMBOL);

    Assert.isTrue(indexOfAtSymbol > 0,
      "Email Address [%s] format is not valid", emailAddress);

    String username = emailAddress.substring(0, indexOfAtSymbol);
    String domainName = emailAddress.substring(indexOfAtSymbol + 1);

    return new EmailAddress(asUser(username), Domain.parse(domainName));
  }

  protected static @NotNull User<String> asUser(@NotNull String name) {

    Assert.hasText(name, "User name [%s] is required", name);

    return new User<String>() {

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
   * @see org.cp.domain.contact.email.model.Domain
   * @see org.cp.elements.security.model.User
   */
  public EmailAddress(@NotNull User<String> user, @NotNull Domain domain) {

    this.user = ObjectUtils.requireObject(user, "User [%s] is required");
    this.domain = ObjectUtils.requireObject(domain, "Domain [%s] is required");
  }

  /**
   * Gets the {@link Domain} of this {@link EmailAddress}.
   *
   * @return the {@link Domain} of this {@link EmailAddress}.
   * @see org.cp.domain.contact.email.model.Domain
   */
  public @NotNull Domain getDomain() {
    return this.domain;
  }

  /**
   * Gets the {@link String domain name} of this {@link EmailAddress}.
   *
   * @return the {@link String domain name} of this {@link EmailAddress}.
   * @see org.cp.domain.contact.email.model.Domain#toString()
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
}
