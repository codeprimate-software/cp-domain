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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link PostalCode}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.PostalCode
 * @since 0.1.0
 */
public class PostalCodeUnitTests {

  private void assertPostalCode(PostalCode actual, PostalCode expected) {
    assertPostalCode(actual, expected.getNumber(), expected.getCountry().orElse(null));
  }

  private void assertPostalCode(PostalCode postalCode, String number) {

    assertPostalCode(postalCode, number, null);
    assertThat(postalCode.getCountry()).isNotPresent();
  }

  private void assertPostalCode(PostalCode postalCode, String number, Country country) {

    assertThat(postalCode).isNotNull();
    assertThat(postalCode.getNumber()).isEqualTo(number);
    assertThat(postalCode.getCountry().orElse(null)).isEqualTo(country);
  }

  @Test
  public void constructNewPostalCodeWithNumber() {

    PostalCode postalCode = new PostalCode("12345");

    assertPostalCode(postalCode, "12345");
  }

  @Test
  public void constructNewPostalCodeWithInvalidNumber() {

    Arrays.asList(null, "", "  ").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new PostalCode(number))
        .withMessage("Number [%s] is required", number)
        .withNoCause());
  }

  @Test
  public void fromPostalCode() {

    PostalCode postalCode = PostalCode.of("12345");
    PostalCode postalCodeCopy = PostalCode.from(postalCode);

    assertPostalCode(postalCode, postalCodeCopy);
  }

  @Test
  public void fromNullPostalCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PostalCode.from(null))
      .withMessage("PostalCode to copy is required")
      .withNoCause();
  }

  @Test
  public void ofNumber() {

    PostalCode postalCode = PostalCode.of("12345");

    assertPostalCode(postalCode, "12345");
  }

  @Test
  public void postalCodeCountryIsEmptyByDefault() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode).isNotNull();
    assertThat(postalCode.getNumber()).isEqualTo("12345");
    assertThat(postalCode.getCountry()).isNotPresent();
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    PostalCode postalCode = PostalCode.of("12345");

    Object postalCodeClone = postalCode.clone();

    assertThat(postalCodeClone).isInstanceOf(PostalCode.class);
    assertThat(postalCodeClone).isNotSameAs(postalCode);
    assertThat(postalCodeClone).isEqualTo(postalCode);
  }

  @Test
  public void comparedToEqualPostalCodeReturnsZero() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("12345");

    assertThat(postalCodeOne).isNotNull();
    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isEqualTo(postalCodeTwo);
    assertThat(postalCodeOne.compareTo(postalCodeTwo)).isZero();
    assertThat(postalCodeTwo.compareTo(postalCodeOne)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  public void compareToIdenticalPostalCodeReturnsZero() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode).isNotNull();
    assertThat(postalCode.compareTo(postalCode)).isZero();
  }

  @Test
  public void compareToUnequalPostalCodeReturnsNonZero() {

    PostalCode postalCodeOne = PostalCode.of("52003");
    PostalCode postalCodeTwo = PostalCode.of("97213");

    assertThat(postalCodeOne).isNotNull();
    assertThat(postalCodeTwo).isNotNull();
    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isNotEqualTo(postalCodeTwo);
    assertThat(postalCodeOne.compareTo(postalCodeTwo)).isLessThan(0);
    assertThat(postalCodeTwo.compareTo(postalCodeOne)).isGreaterThan(0);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(PostalCode.of("12345").equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(PostalCode.of("12345").equals("12345")).isFalse();
  }

  @Test
  public void equalPostalCodesAreEqual() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("12345");

    assertThat(postalCodeOne).isNotNull();
    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isEqualTo(postalCodeTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void identicalPostalCodesAreEqual() {

    PostalCode postalCode = PostalCode.of("12345");

    assertThat(postalCode).isEqualTo(postalCode);
    assertThat(postalCode.equals(postalCode)).isTrue();
  }

  @Test
  public void unequalPostalCodesAreNotEqual() {

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("54321");

    assertThat(postalCodeOne).isNotNull();
    assertThat(postalCodeTwo).isNotNull();
    assertThat(postalCodeOne).isNotSameAs(postalCodeTwo);
    assertThat(postalCodeOne).isNotEqualTo(postalCodeTwo);
    assertThat(postalCodeTwo).isNotEqualTo(postalCodeOne);
  }

  @Test
  public void hashCodeOfPostalCode() {

    PostalCode postalCode = PostalCode.of("12345");

    int hashCode = postalCode.hashCode();

    assertThat(hashCode).isNotZero();
    assertThat(postalCode.hashCode()).isEqualTo(hashCode);
    assertThat(postalCode.hashCode()).isNotEqualTo(PostalCode.of("54321"));
  }

  @Test
  public void toStringIsSameAsPostalCodeNumber() {
    assertThat(PostalCode.of("12345").toString()).isEqualTo("12345");
  }

  @Test
  public void postalCodeSerializationIsCorrect() throws IOException, ClassNotFoundException {

    PostalCode postalCode = PostalCode.of("12345");

    assertPostalCode(postalCode, "12345");

    byte[] postalCodeBytes = IOUtils.serialize(postalCode);

    assertThat(postalCodeBytes).isNotNull();
    assertThat(postalCodeBytes).hasSizeGreaterThan(0);

    PostalCode deserializedPostalCode = IOUtils.deserialize(postalCodeBytes);

    assertPostalCode(deserializedPostalCode, postalCode);
  }
}
