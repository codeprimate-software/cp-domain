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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress.Domain;
import org.cp.elements.io.IOUtils;
import org.cp.elements.security.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Unit Tests for {@link EmailAddress}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.model.EmailAddress
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @since 0.1.0
 */
public class EmailAddressUnitTests {

  private void assertEmailAddress(EmailAddress emailAddress, User<?> user, Domain domain) {

    assertThat(emailAddress).isNotNull();
    assertThat(emailAddress.getUser()).isEqualTo(user);
    assertThat(emailAddress.getDomain()).isEqualTo(domain);
  }

  private void assertEmailAddress(EmailAddress emailAddress, String username, String domainName) {

    assertThat(emailAddress).isNotNull();
    assertThat(emailAddress.getUsername()).isEqualTo(username);
    assertThat(emailAddress.getDomainName()).isEqualTo(domainName);
  }

  private void assertUser(User<?> user, String username) {

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo(username);
  }

  private void assertDomain(Domain domain, String domainName, String domainExtensionName) {

    assertThat(domain).isNotNull();
    assertThat(domain.getName()).isEqualTo(domainName);
    assertThat(domain.getExtensionName()).isEqualTo(domainExtensionName);
    assertThat(domain.getExtension()).isEqualTo(Domain.Extensions.valueOf(domainExtensionName.toUpperCase()));
  }

  @Test
  @SuppressWarnings("unchecked")
  void constructEmailAddress() {

    User<String> mockUser = mock(User.class);
    Domain mockDomain = mock(EmailAddress.Domain.class);
    EmailAddress emailAddress = new EmailAddress(mockUser, mockDomain);

    assertEmailAddress(emailAddress, mockUser, mockDomain);

    verifyNoInteractions(mockUser, mockDomain);
  }

  @Test
  void constructEmailAddressWithNullUser() {

    Domain mockDomain = mock(EmailAddress.Domain.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new EmailAddress(null, mockDomain))
      .withMessage("User is required")
      .withNoCause();

    verifyNoInteractions(mockDomain);
  }

  @Test
  @SuppressWarnings("unchecked")
  void constructEmailAddressWithNullDomain() {

    User<String> mockUser = mock(User.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new EmailAddress(mockUser, null))
      .withMessage("Domain is required")
      .withNoCause();

    verifyNoInteractions(mockUser);
  }

  @Test
  void fromEmailAddress() {

    User<?> mockUser = mock(User.class);
    Domain mockDomain = mock(EmailAddress.Domain.class);
    EmailAddress mockEmailAddress = mock(EmailAddress.class);

    doReturn(mockUser).when(mockEmailAddress).getUser();
    doReturn(mockDomain).when(mockEmailAddress).getDomain();

    EmailAddress copy = EmailAddress.from(mockEmailAddress);

    assertEmailAddress(copy, mockUser, mockDomain);
    assertThat(copy).isNotSameAs(mockEmailAddress);

    verify(mockEmailAddress, times(1)).getUser();
    verify(mockEmailAddress, times(1)).getDomain();
    verifyNoInteractions(mockUser, mockDomain);
  }

  @Test
  void fromNullEmailAddress() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> EmailAddress.from(null))
      .withMessage("Email Address to copy is required")
      .withNoCause();
  }

  @Test
  @SuppressWarnings("unchecked")
  void ofUserAndDomain() {

    User<String> mockUser = mock(User.class);
    Domain mockDomain = mock(EmailAddress.Domain.class);
    EmailAddress emailAddress = EmailAddress.of(mockUser, mockDomain);

    assertEmailAddress(emailAddress, mockUser, mockDomain);

    verifyNoInteractions(mockUser, mockDomain);
  }

  @Test
  void parseEmailAddress() {

    EmailAddress emailAddress = EmailAddress.parse("jonDoe@gmail.com");

    assertEmailAddress(emailAddress, "jonDoe", "gmail.com");

    User<String> user = emailAddress.getUser();

    assertUser(user, "jonDoe");

    Domain domain = emailAddress.getDomain();

    assertDomain(domain, "gmail", "com");
  }

  @Test
  void parseIllegalEmailAddress() {

    Arrays.asList("  ", "", null).forEach(illegalEmailAddress ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> EmailAddress.parse(illegalEmailAddress))
        .withMessage("Email Address [%s] to parse is required", illegalEmailAddress)
        .withNoCause());
  }

  @Test
  void parseInvalidEmailAddress() {

    Arrays.asList("jonDoegmail.com", "jonDoe", "gmail.com").forEach(invalidEmailAddress ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> EmailAddress.parse(invalidEmailAddress))
        .withMessage("Email Address [%s] format is not valid", invalidEmailAddress)
        .withNoCause());
  }

  @Test
  void getDomainNameCallsGetDomain() {

    Domain mockDomain = mock(Domain.class);
    EmailAddress mockEmailAddress = mock(EmailAddress.class);

    doReturn(mockDomain).when(mockEmailAddress).getDomain();
    doCallRealMethod().when(mockEmailAddress).getDomainName();

    assertThat(mockEmailAddress.getDomainName()).isEqualTo(mockDomain.toString());

    verify(mockEmailAddress, times(1)).getDomainName();
    verify(mockEmailAddress, times(1)).getDomain();
    verifyNoMoreInteractions(mockEmailAddress);
    verifyNoInteractions(mockDomain);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getUsernameCallsGetUser() {

    User<String> mockUser = mock(User.class);
    EmailAddress mockEmailAddress = mock(EmailAddress.class);

    doReturn("MockUser").when(mockUser).getName();
    doReturn(mockUser).when(mockEmailAddress).getUser();
    doCallRealMethod().when(mockEmailAddress).getUsername();

    assertThat(mockEmailAddress.getUsername()).isEqualTo("MockUser");

    verify(mockEmailAddress, times(1)).getUsername();
    verify(mockEmailAddress, times(1)).getUser();
    verify(mockUser, times(1)).getName();
    verifyNoMoreInteractions(mockEmailAddress, mockUser);
  }

  @Test
  void cloneIsCorrect() throws CloneNotSupportedException {

    User<String> user = TestUser.as("TestUser");
    Domain domain = Domain.of("gmail", Domain.Extensions.COM);
    EmailAddress emailAddress = new EmailAddress(user, domain);

    Object clone = emailAddress.clone();

    assertThat(clone).isInstanceOf(EmailAddress.class);
    assertThat(clone).isNotSameAs(emailAddress);
    assertThat(clone).isEqualTo(emailAddress);
  }

  @Test
  void compareToIsCorrect() {

    EmailAddress zero = EmailAddress.parse("jackHandy@gmail.com");
    EmailAddress one = EmailAddress.parse("jonDoe@gmail.com");
    EmailAddress two = EmailAddress.parse("janeDoe@gmail.com");
    EmailAddress three = EmailAddress.parse("test@home.com");
    EmailAddress four = EmailAddress.parse("test@home.net");

    List<EmailAddress> emailAddressList = Stream.of(four, zero, three, one, two).sorted().toList();

    assertThat(emailAddressList).containsExactly(zero, two, one, three, four);
  }

  @Test
  void equalEmailAddressesAreEqual() {

    EmailAddress one = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.COM));
    EmailAddress two = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", "com"));

    assertThat(one).isEqualTo(two);
    assertThat(one).isNotSameAs(two);
  }

  @Test
  @SuppressWarnings("all")
  void identicalEmailAddressesAreEqual() {

    EmailAddress emailAddress = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.COM));

    assertThat(emailAddress.equals(emailAddress)).isTrue();
  }

  @Test
  void unequalEmailAddressesAreNotEqual() {

    EmailAddress one = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.COM));
    EmailAddress two = EmailAddress.of(TestUser.as("janeDoe"), Domain.of("example", Domain.Extensions.NET));

    assertThat(one).isNotEqualTo(two);
  }

  @Test
  @SuppressWarnings("all")
  void emailAddressIsNotEqualToNullIsNullSafe() {
    assertThat(EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.IO))
      .equals(null)).isFalse();
  }

  @Test
  void emailAddressIsNotEqualToObject() {
    assertThat(EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.IO)))
      .isNotEqualTo("jonDoe@example.io");
  }

  @Test
  void hashCodeIsCorrect() {

    EmailAddress emailAddress = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.IO));

    int emailAddressHashCode = emailAddress.hashCode();

    assertThat(emailAddressHashCode).isNotZero();
    assertThat(emailAddressHashCode).isEqualTo(emailAddress.hashCode());
    assertThat(emailAddress).hasSameHashCodeAs(EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", "io")));
    assertThat(emailAddress).doesNotHaveSameHashCodeAs(EmailAddress.of(TestUser.as("jonBloom"),
      Domain.of("example", Domain.Extensions.COM)));
    assertThat(emailAddress).doesNotHaveSameHashCodeAs("jonDoe@example.io");
  }

  @Test
  void toStringIsCorrect() {

    User<String> user = TestUser.as("TestUser");
    Domain domain = Domain.of("gmail", Domain.Extensions.COM);
    EmailAddress emailAddress = new EmailAddress(user, domain);

    assertThat(emailAddress).hasToString("TestUser@gmail.com");
  }

  @Test
  void serializationIsCorrect() throws IOException, ClassNotFoundException {

    EmailAddress emailAddress = EmailAddress.of(TestUser.as("jonDoe"), Domain.of("example", Domain.Extensions.COM));

    byte[] emailAddressBytes = IOUtils.serialize(emailAddress);

    assertThat(emailAddressBytes).isNotNull();
    assertThat(emailAddressBytes).isNotEmpty();

    EmailAddress deserializedEmailAddress = IOUtils.deserialize(emailAddressBytes);

    assertThat(deserializedEmailAddress).isNotNull();
    assertThat(deserializedEmailAddress).isNotSameAs(emailAddress);
    assertThat(deserializedEmailAddress).isEqualTo(emailAddress);
  }

  @Getter
  @RequiredArgsConstructor(staticName = "as")
  static class TestUser implements User<String>, Serializable {
    private final String id = UUID.randomUUID().toString();
    private final String name;
  }
}
