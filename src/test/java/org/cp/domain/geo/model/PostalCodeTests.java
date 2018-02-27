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

package org.cp.domain.geo.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link PostalCode}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.PostalCode
 * @since 1.0.0
 */
public class PostalCodeTests {

  @Test
  public void constructPostalCodeWithNumber() {

    PostalCode postalCode = new PostalCode("12345");

    assertThat(postalCode).isNotNull();
    assertThat(postalCode.getNumber()).isEqualTo("12345");
    assertThat(postalCode.getCountry().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPostalCodeWithBlankNumberThrowsIllegalArgumentException() {
    testConstructPostalCodeWithInvalidNumberThrowsIllegalArgumentException("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPostalCodeWithEmptyNumberThrowsIllegalArgumentException() {
    testConstructPostalCodeWithInvalidNumberThrowsIllegalArgumentException("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructPostalCodeWithNullNumberThrowsIllegalArgumentException() {
    testConstructPostalCodeWithInvalidNumberThrowsIllegalArgumentException(null);
  }

  private void testConstructPostalCodeWithInvalidNumberThrowsIllegalArgumentException(String number) {

    try {
      new PostalCode(number);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Postal Code number [%s] is required", number);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void ofNumber() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode).isNotNull();
    assertThat(postalCode.getNumber()).isEqualTo("12345");
    assertThat(postalCode.getCountry().isPresent()).isFalse();
  }

  @Test
  public void fromCopiesPostalCode() {

    PostalCode postalCode = PostalCode.of("12345");
    PostalCode postalCodeCopy = PostalCode.from(postalCode);

    assertThat(postalCodeCopy).isNotNull();
    assertThat(postalCodeCopy).isNotSameAs(postalCode);
    assertThat(postalCodeCopy).isEqualTo(postalCode);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullPostalCodeThrowsIllegalArgumentException() {

    try {
      PostalCode.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Postal Code is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    PostalCode postalCode = PostalCode.of("12345");
    PostalCode postalCodeClone = (PostalCode) postalCode.clone();

    assertThat(postalCodeClone).isNotNull();
    assertThat(postalCodeClone).isNotSameAs(postalCode);
    assertThat(postalCodeClone).isEqualTo(postalCode);
  }

  @Test
  public void comparedToEqualPostalCodeReturnsZero() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("12345");

    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isEqualTo(postalCodeTwo);
    assertThat(postalCodeOne.compareTo(postalCodeTwo)).isZero();
  }

  @Test
  public void comparedToGreaterPostalCodeReturnsLessThanZero() {

    PostalCode postalCodeOne = PostalCode.of("52003");
    PostalCode postalCodeTwo = PostalCode.of("97213");

    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isNotEqualTo(postalCodeTwo);
    assertThat(postalCodeOne.compareTo(postalCodeTwo)).isLessThan(0);
  }

  @Test
  public void comparedToLessPostalCodeReturnsGreaterThanZero() {

    PostalCode postalCodeOne = PostalCode.of("97213");
    PostalCode postalCodeTwo = PostalCode.of("52003");

    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isNotEqualTo(postalCodeTwo);
    assertThat(postalCodeOne.compareTo(postalCodeTwo)).isGreaterThan(0);
  }

  @Test
  public void equalsNullReturnsFalse() {
    assertThat(PostalCode.of("12345")).isNotEqualTo(null);
  }

  @Test
  public void equalPostalCodesAreEqual() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("12345");

    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isEqualTo(postalCodeTwo);
  }
  @Test
  public void identicalPostalCodesAreEqual() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode).isEqualTo(postalCode);
  }

  @Test
  public void unequalPostalCodesAreNotEqual() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("54321");

    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isNotEqualTo(postalCodeTwo);
  }

  @Test
  public void hashCodeOfPostalCode() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode.hashCode()).isNotZero();
    assertThat(postalCode.hashCode()).isEqualTo(postalCode.hashCode());
    assertThat(postalCode.hashCode()).isNotEqualTo(PostalCode.of("54321"));
  }

  @Test
  public void toStringIsSameAsPostalCodeNumber() {
    assertThat(PostalCode.of("12345").toString()).isEqualTo("12345");
  }
}
