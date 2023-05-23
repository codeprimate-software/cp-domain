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
package org.cp.domain.geo.model.usa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.PostalCode;
import org.mockito.quality.Strictness;

/**
 * Unit Tests for {@link ZIP}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.usa.ZIP
 * @since 0.1.0
 */
public class ZipUnitTests {

  @Test
  public void constructNewZipWithFiveDigitPostalCode() {

    ZIP zip = new ZIP("12345");

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension()).isNotPresent();
    assertThat(zip.getNumber()).isEqualTo("12345");
  }

  @Test
  public void constructNewZipWithNineDigitPostalCode() {

    ZIP zip = new ZIP("12345-6789");

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension().orElse(null)).isEqualTo("6789");
    assertThat(zip.getNumber()).isEqualTo("123456789");
  }

  @Test
  public void constructNewZipWithIllegalPostalCode() {

    Arrays.asList("  ", "", null).forEach(postalCode ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new ZIP(postalCode))
        .withMessage("Number [%s] is required", postalCode)
        .withNoCause());
  }

  @Test
  public void constructPostalCodeWithInvalidPostalCode() {

    Arrays.asList("1234", "123456", "1234567", "1234567890").forEach(postalCode ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new ZIP(postalCode))
        .withMessage("5 or 9 digit postal code is required; but was [%s]", postalCode)
        .withNoCause());
  }

  @Test
  public void fromZip() {

    ZIP zip = new ZIP("12345");
    ZIP copy = ZIP.from(zip);

    assertThat(copy).isSameAs(zip);
  }

  @Test
  public void fromPostalCode() {

    PostalCode mockPostalCode = mock(PostalCode.class, withSettings().strictness(Strictness.LENIENT));

    doReturn("12345").when(mockPostalCode).getNumber();
    doReturn(Optional.of(Country.GERMANY)).when(mockPostalCode).getCountry();

    ZIP zip = ZIP.from(mockPostalCode);

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension()).isNotPresent();
    assertThat(zip.getNumber()).isEqualTo("12345");
    assertThat(zip.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);

    verify(mockPostalCode, times(1)).getNumber();
    verifyNoMoreInteractions(mockPostalCode);
  }

  @Test
  public void fromNonCompliantPostalCode() {

    PostalCode mockPostalCode = mock(PostalCode.class);

    doReturn("123").when(mockPostalCode).getNumber();

    assertThatIllegalArgumentException()
      .isThrownBy(() -> ZIP.from(mockPostalCode))
      .withMessage("5 or 9 digit postal code is required; but was [123]")
      .withNoCause();

    verify(mockPostalCode, times(1)).getNumber();
    verifyNoMoreInteractions(mockPostalCode);
  }

  @Test
  public void fromNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> ZIP.from(null))
      .withMessage("PostalCode to copy is required")
      .withNoCause();
  }

  @Test
  public void ofCodeHavingFiveDigitPostalCode() {

    ZIP zip = ZIP.of("12345");

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension()).isNotPresent();
    assertThat(zip.getNumber()).isEqualTo("12345");
  }

  @Test
  public void ofCodeHavingNineDigitPostalCode() {

    ZIP zip = ZIP.of("12345-6789");

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension().orElse(null)).isEqualTo("6789");
    assertThat(zip.getNumber()).isEqualTo("123456789");
  }

  @Test
  public void getCountry() {
    assertThat(ZIP.of("12345").getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void getStateIsValid() {
    assertThat(ZIP.of("97205").getState()).isEqualTo(State.OREGON);
  }

  @Test
  public void getStateIsInvalid() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> ZIP.of("00001").getState())
      .withMessage("State for ZIP code [00001] not found")
      .withNoCause();
  }

  @Test
  public void plusFourIsCorrect() {

    ZIP zip = ZIP.of("12345");

    assertThat(zip).isNotNull();
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension()).isNotPresent();
    assertThat(zip.getNumber()).isEqualTo("12345");
    assertThat(zip.plusFour("6789")).isSameAs(zip);
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension().orElse(null)).isEqualTo("6789");
    assertThat(zip.getNumber()).isEqualTo("123456789");
    assertThat(zip.plusFour(null)).isSameAs(zip);
    assertThat(zip.getCode()).isEqualTo("12345");
    assertThat(zip.getFourDigitExtension()).isNotPresent();
    assertThat(zip.getNumber()).isEqualTo("12345");
  }

  @Test
  public void toStringWithFiveDigitPostalCodeIsCorrect() {
    assertThat(ZIP.of("12345").toString()).isEqualTo("12345");
  }

  @Test
  public void toStringWithNineDigitPostalCodeIsCorrect() {
    assertThat(ZIP.of("12345").plusFour("6789").toString()).isEqualTo("12345-6789");
  }
}
