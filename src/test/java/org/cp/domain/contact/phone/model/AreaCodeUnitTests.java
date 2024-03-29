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
package org.cp.domain.contact.phone.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.cp.elements.io.IOUtils;
import org.cp.elements.lang.Renderer;

/**
 * Unit Tests for {@link AreaCode}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.AreaCode
 * @since 0.1.0
 */
public class AreaCodeUnitTests {

  private void assertAreaCode(AreaCode areaCode, String number) {

    assertThat(areaCode).isNotNull();
    assertThat(areaCode.getNumber()).isEqualTo(number);
  }

  private String reverse(String value) {

    char[] characters = value.toCharArray();
    StringBuilder reversed = new StringBuilder();

    for (int index = characters.length - 1; index > -1; index--) {
      reversed.append(characters[index]);
    }

    return reversed.toString();
  }

  @Test
  public void fromAreaCode() {

    AreaCode mockAreaCode = mock(AreaCode.class);

    doReturn("123").when(mockAreaCode).getNumber();

    AreaCode copy = AreaCode.from(mockAreaCode);

    assertAreaCode(copy, "123");

    verify(mockAreaCode, times(1)).getNumber();
    verifyNoMoreInteractions(mockAreaCode);
  }

  @Test
  public void fromNullAreaCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> AreaCode.from(null))
      .withMessage("AreaCode to copy is required")
      .withNoCause();
  }

  @Test
  public void ofIntegerNumber() {
    assertAreaCode(AreaCode.of(999), "999");
    assertAreaCode(AreaCode.of(-999), "999");
  }

  @Test
  public void ofStringNumber() {
    assertAreaCode(AreaCode.of("987"), "987");
  }

  @Test
  public void parseTenDigitPhoneNumber() {
    assertAreaCode(AreaCode.parse("5035551234"), "503");
  }

  @Test
  public void parseFormattedTenDigitPhoneNumber() {
    assertAreaCode(AreaCode.parse("503-555-1234"), "503");
    assertAreaCode(AreaCode.parse("(971) 555-2480"), "971");
  }

  @Test
  public void parsePhoneNumberWithNoAreaCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> AreaCode.parse("555-1234"))
      .withMessage("Phone Number [555-1234] must be 10-digits")
      .withNoCause();
  }

  @Test
  public void parseInvalidPhoneNumbers() {

    Arrays.asList("5O3-555-12E4", "P7l-SSS-lOlO", "  ", "", null).forEach(phoneNumber ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> AreaCode.parse(phoneNumber))
        .withMessage("Phone Number [%s] must be 10-digits", phoneNumber)
        .withNoCause());
  }

  @Test
  public void constructAreaCode() {
    assertAreaCode(new AreaCode("123"), "123");
  }

  @Test
  public void constructAreaCodeWithFormattedNumber() {
    assertAreaCode(new AreaCode("(503)"), "503");
  }

  @Test
  public void constructAreaCodeWithInvalidLength() {

    Arrays.asList("12", "1234").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new AreaCode(number))
        .withMessage("AreaCode [%s] must be a %d-digit number", number, AreaCode.REQUIRED_AREA_CODE_LENGTH)
        .withNoCause());
  }

  @Test
  public void constructAreaCodeWithInvalidNumber() {

    Arrays.asList("S0S", "5O5", "#10").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new AreaCode(number))
        .withMessage("AreaCode [%s] must be a %d-digit number", number, AreaCode.REQUIRED_AREA_CODE_LENGTH)
        .withNoCause());
  }

  @Test
  public void constructAreaCodeWithNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new AreaCode(null))
      .withMessage("AreaCode [null] must be a %s-digit number", AreaCode.REQUIRED_AREA_CODE_LENGTH)
      .withNoCause();
  }

  @Test
  public void cloneIsCorrect() throws CloneNotSupportedException {

    AreaCode areaCode = AreaCode.of("123");

    Object clone = areaCode.clone();

    assertThat(clone).isInstanceOf(AreaCode.class)
      .asInstanceOf(InstanceOfAssertFactories.type(AreaCode.class))
      .isNotSameAs(areaCode)
      .extracting(AreaCode::getNumber)
      .isEqualTo("123");
  }

  @Test
  @SuppressWarnings("all")
  public void compareToIsCorrect() {

    AreaCode areaCodeOne = AreaCode.of("123");
    AreaCode areaCodeTwo = AreaCode.of("248");

    assertThat(areaCodeOne).isEqualByComparingTo(areaCodeOne);
    assertThat(areaCodeTwo).isGreaterThan(areaCodeOne);
    assertThat(areaCodeOne).isLessThan(areaCodeTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsIsCorrect() {

    AreaCode areaCodeOne = AreaCode.of("123");
    AreaCode areaCodeTwo = AreaCode.of("248");

    assertThat(areaCodeOne).isEqualTo(areaCodeOne);
    assertThat(areaCodeOne).isEqualTo(AreaCode.of("123"));
    assertThat(areaCodeOne).isNotEqualTo(areaCodeTwo);
    assertThat(areaCodeOne).isNotEqualTo("123");
    assertThat(areaCodeOne).isNotEqualTo("test");
    assertThat(areaCodeOne).isNotEqualTo(null);
  }

  @Test
  public void hashCodeIsCorrect() {

    AreaCode areaCodeOne = AreaCode.of("123");
    AreaCode areaCodeTwo = AreaCode.of("248");

    assertThat(areaCodeOne).hasSameHashCodeAs(areaCodeOne);
    assertThat(areaCodeOne).hasSameHashCodeAs(AreaCode.of("123"));
    assertThat(areaCodeOne).doesNotHaveSameHashCodeAs(areaCodeTwo);
    assertThat(areaCodeOne).doesNotHaveSameHashCodeAs("123");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void rendersCorrectly() {

    Renderer<AreaCode> mockRenderer = mock(Renderer.class);

    doAnswer(invocation -> reverse(invocation.getArgument(0, AreaCode.class).getNumber()))
      .when(mockRenderer).render(any());

    AreaCode areaCode = AreaCode.of("123");

    assertThat(areaCode).isNotNull();
    assertThat(areaCode.render(mockRenderer)).isEqualTo("321");

    verify(mockRenderer, times(1)).render(eq(areaCode));
    verifyNoMoreInteractions(mockRenderer);
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    AreaCode areaCode = AreaCode.of("123");

    assertAreaCode(areaCode, "123");

    byte[] areaCodeBytes = IOUtils.serialize(areaCode);

    assertThat(areaCodeBytes).isNotNull();
    assertThat(areaCodeBytes).isNotEmpty();

    AreaCode deserializedAreaCode = IOUtils.deserialize(areaCodeBytes);

    assertThat(deserializedAreaCode).isNotNull();
    assertThat(deserializedAreaCode).isNotSameAs(areaCode);
    assertThat(deserializedAreaCode).isEqualTo(areaCode);
  }

  @Test
  public void toStringIsCorrect() {
    assertThat(AreaCode.of("123")).hasToString("123");
  }
}
