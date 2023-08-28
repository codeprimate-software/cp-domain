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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress.Domain;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;

/**
 * Unit Tests for {@link EmailAddress.Domain}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.email.model.EmailAddress.Domain
 * @since 0.1.0
 */
public class EmailAddressDomainUnitTests {

  private void assertDomain(Domain domain, String domainName, String domainExtensionName) {

    Domain.Extension domainExtension = ObjectUtils.doOperationSafely(args ->
      Domain.Extension.valueOf(StringUtils.toUpperCase(domainExtensionName)), Domain.Extension.XYZ);

    assertThat(domain).isNotNull();
    assertThat(domain.getName()).isEqualTo(domainName);
    assertThat(domain.getExtensionName()).isEqualTo(domainExtensionName);
    assertThat(domain.getExtension().orElse(Domain.Extension.XYZ)).isEqualTo(domainExtension);
  }

  @Test
  void constructDomain() {

    Domain domain = new Domain("home", Domain.Extension.NET);

    assertDomain(domain, "home", "net");
  }

  @Test
  void constructDomainWithExtensionName() {

    Domain domain = new Domain("nonprofit", "org");

    assertDomain(domain, "nonprofit", "org");
  }

  @Test
  void constructDomainWithNoName() {

    Arrays.asList("  ", "", null).forEach(illegalDomainName ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Domain(illegalDomainName, Domain.Extension.IO))
        .withMessage("Name [%s] is required", illegalDomainName)
        .withNoCause());
  }

  @Test
  void constructDomainWithNoExtension() {

    Arrays.asList("  ", "", null).forEach(illegalExtensionName ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Domain("example", illegalExtensionName))
        .withMessage("Extension [%s] is required", illegalExtensionName)
        .withNoCause());
  }

  @Test
  void constructDomainWithNullExtension() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Domain("example", (Domain.Extension) null))
      .withMessage("Domain.Extension is required")
      .withNoCause();
  }

  @Test
  void constructDomainWithUnknownExtension() {

    Domain domain = new Domain("test", "vip");

    assertDomain(domain, "test", "vip");
    assertThat(domain.getExtension()).isNotPresent();
  }

  @Test
  void fromDomain() {

    Domain mockDomain = mock(Domain.class);

    doReturn("example").when(mockDomain).getName();
    doReturn("com").when(mockDomain).getExtensionName();

    Domain copy = Domain.from(mockDomain);

    assertDomain(copy, "example", "com");

    verify(mockDomain, times(1)).getName();
    verify(mockDomain, times(1)).getExtensionName();
    verifyNoMoreInteractions(mockDomain);
  }

  @Test
  void fromNullDomain() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Domain.from(null))
      .withMessage("Domain to copy is required")
      .withNoCause();
  }

  @Test
  void ofNameAndExtension() {

    Domain domain = Domain.of("example", Domain.Extension.NET);

    assertDomain(domain, "example", "net");
  }

  @Test
  void ofNameAndExtensionName() {

    Domain domain = Domain.of("example", "ORg");

    assertDomain(domain, "example", "org");
  }

  @Test
  void parseDomainName() {

    Domain domain = Domain.parse("vmware.com");

    assertDomain(domain, "vmware", "com");
  }

  @Test
  void parseIllegalDomainName() {

    Arrays.asList("  ", "", null).forEach(illegalDomainName ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> Domain.parse(illegalDomainName))
        .withMessage("Domain Name [%s] to parse is required", illegalDomainName)
        .withNoCause());
  }

  @Test
  void parseInvalidDomainName() {

    Arrays.asList("exampleNet", "example", "net").forEach(invalidDomainName ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> Domain.parse(invalidDomainName))
        .withMessage("Domain Name [%s] format is not valid", invalidDomainName)
        .withNoCause());
  }

  @Test
  void cloneIsCorrect() throws CloneNotSupportedException {

    Domain domain = Domain.of("example", Domain.Extension.NET);

    Object clone = domain.clone();

    assertThat(clone).isInstanceOf(Domain.class);
    assertDomain((Domain) clone, "example", "net");
  }

  @Test
  void compareToIsCorrect() {

    Domain exampleDotIo = Domain.of("example", "io");
    Domain homeDotNet = Domain.of("home", Domain.Extension.NET);
    Domain microsoftDotCom = Domain.of("microsoft", Domain.Extension.COM);
    Domain nonprofitDotOrg = Domain.of("nonprofit", "orG");
    Domain schoolDotEdu = Domain.of("school", "edu");
    Domain vmwareDotCom = Domain.of("vmware", "com");

    List<Domain> domainList = Stream.of(schoolDotEdu, exampleDotIo, nonprofitDotOrg, microsoftDotCom, homeDotNet,
        vmwareDotCom).sorted().toList();

    assertThat(domainList)
      .containsExactly(microsoftDotCom, vmwareDotCom, schoolDotEdu, exampleDotIo, homeDotNet, nonprofitDotOrg);
  }

  @Test
  void equalDomainsAreEqual() {

    Domain one = Domain.of("example", Domain.Extension.COM);
    Domain two = Domain.of("example", "com");

    assertThat(one).isEqualTo(two);
    assertThat(one).isNotSameAs(two);
  }

  @Test
  @SuppressWarnings("all")
  void identicalDomainsAreEqual() {

    Domain domain = Domain.of("example", Domain.Extension.NET);

    assertThat(domain.equals(domain)).isTrue();
  }

  @Test
  void unequalDomainsAreNotEqual() {

    Domain one = Domain.of("example", Domain.Extension.COM);
    Domain two = Domain.of("example", Domain.Extension.NET);

    assertThat(one).isNotEqualTo(two);
  }

  @Test
  @SuppressWarnings("all")
  void domainIsNotEqualToNullIsNullSafe() {
    assertThat(Domain.of("example", "io").equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  void domainIsNotEqualToObject() {
    assertThat(Domain.of("example", "io")).isNotEqualTo("example.io");
  }

  @Test
  void hashCodeIsCorrect() {

    Domain domain = Domain.of("example", Domain.Extension.EDU);

    int domainHashCode = domain.hashCode();

    assertThat(domainHashCode).isNotZero();
    assertThat(domainHashCode).isEqualTo(domain.hashCode());
    assertThat(domain).hasSameHashCodeAs(Domain.of("example", "edu"));
    assertThat(domain).doesNotHaveSameHashCodeAs(Domain.of("example", Domain.Extension.IO));
    assertThat(domain).doesNotHaveSameHashCodeAs("example.edu");
  }

  @Test
  void toStringIsCorrect() {

    Domain domain = Domain.of("example", Domain.Extension.COM);

    assertThat(domain).hasToString("example.com");
  }
}
