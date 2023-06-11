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
 * Unit Tests for {@link ExchangeCode}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.ExchangeCode
 * @since 0.1.0
 */
public class ExchangeCodeUnitTests {

  private void assertExchangeCode(ExchangeCode exchangeCode, String number) {

    assertThat(exchangeCode).isNotNull();
    assertThat(exchangeCode.getNumber()).isEqualTo(number);
  }

  @Test
  public void fromExchangeCode() {

    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);

    doReturn("456").when(mockExchangeCode).getNumber();

    ExchangeCode copy = ExchangeCode.from(mockExchangeCode);

    assertExchangeCode(copy, "456");

    verify(mockExchangeCode, times(1)).getNumber();
    verifyNoMoreInteractions(mockExchangeCode);
  }

  @Test
  public void fromNullExchangeCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> ExchangeCode.from(null))
      .withMessage("ExchangeCode to copy is required")
      .withNoCause();
  }

  @Test
  public void ofIntegerNumber() {
    assertExchangeCode(ExchangeCode.of(555), "555");
    assertExchangeCode(ExchangeCode.of(-654), "654");
  }

  @Test
  public void ofStringNumber() {
    assertExchangeCode(ExchangeCode.of("555"), "555");
  }

  @Test
  public void parseTenDigitPhoneNumber() {
    assertExchangeCode(ExchangeCode.parse("5035551234"), "555");
  }

  @Test
  public void parseFormattedTenDigitPhoneNumber() {
    assertExchangeCode(ExchangeCode.parse("(503) 555-1234"), "555");
    assertExchangeCode(ExchangeCode.parse("971-555-1234"), "555");
  }

  @Test
  public void parseSevenDigitPhoneNumber() {
    assertExchangeCode(ExchangeCode.parse("5551234"), "555");
  }

  @Test
  public void parseFormattedSevenDigitPhoneNumber() {
    assertExchangeCode(ExchangeCode.parse("555-1234"), "555");
  }

  @Test
  public void parseInvalidPhoneNumber() {

    Arrays.asList("1234", "SOS-1234", "555-lOlO", "  ", "", null).forEach(phoneNumber ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> ExchangeCode.parse(phoneNumber))
        .withMessage("Phone Number [%s] must be 10-digits or 7-digits", phoneNumber)
        .withNoCause());
  }

  @Test
  public void constructExchangeCode() {
    assertExchangeCode(new ExchangeCode("456"), "456");
  }

  @Test
  public void constructExchangeCodeWithFormattedNumber() {
    assertExchangeCode(new ExchangeCode("[555]"), "555");
  }

  @Test
  public void constructExchangeCodeWithInvalidLength() {

    Arrays.asList("22", "4444").forEach(number ->
      assertThatIllegalArgumentException()
      .isThrownBy(() -> new ExchangeCode(number))
      .withMessage("ExchangeCode [%s] must be a %d-digit number", number,
          ExchangeCode.REQUIRED_EXCHANGE_CODE_LENGTH)
      .withNoCause());
  }

  @Test
  public void constructExchangeCodeWithInvalidNumber() {

    Arrays.asList("5O5", "l2E4", "SSS").forEach(number ->
      assertThatIllegalArgumentException()
      .isThrownBy(() -> new ExchangeCode(number))
      .withMessage("ExchangeCode [%s] must be a %d-digit number", number,
          ExchangeCode.REQUIRED_EXCHANGE_CODE_LENGTH)
      .withNoCause());
  }

  @Test
  public void constructExchangeCodeWithNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new ExchangeCode(null))
      .withMessage("ExchangeCode [null] must be a %d-digit number", ExchangeCode.REQUIRED_EXCHANGE_CODE_LENGTH)
      .withNoCause();
  }

  @Test
  public void cloneExchangeCode() throws CloneNotSupportedException {

    ExchangeCode exchangeCode = ExchangeCode.of("456");

    Object clone = exchangeCode.clone();

    assertThat(clone).isInstanceOf(ExchangeCode.class)
      .asInstanceOf(InstanceOfAssertFactories.type(ExchangeCode.class))
      .isNotSameAs(exchangeCode)
      .extracting(ExchangeCode::getNumber)
      .isEqualTo("456");
  }

  @Test
  public void compareToIsCorrect() {

    ExchangeCode exchangeCodeOne = ExchangeCode.of("456");
    ExchangeCode exchangeCodeTwo = ExchangeCode.of("555");

    assertThat(exchangeCodeOne).isEqualByComparingTo(exchangeCodeOne);
    assertThat(exchangeCodeTwo).isGreaterThan(exchangeCodeOne);
    assertThat(exchangeCodeOne).isLessThan(exchangeCodeTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsIsCorrect() {

    ExchangeCode exchangeCode = ExchangeCode.of("456");

    assertThat(exchangeCode).isEqualTo(exchangeCode);
    assertThat(exchangeCode).isNotEqualTo(ExchangeCode.of("555"));
    assertThat(exchangeCode).isNotEqualTo("456");
    assertThat(exchangeCode).isNotEqualTo(null);
  }

  @Test
  public void hashCodeIsCorrect() {

    ExchangeCode exchangeCode = ExchangeCode.of("456");

    assertThat(exchangeCode).hasSameHashCodeAs(exchangeCode);
    assertThat(exchangeCode).doesNotHaveSameHashCodeAs("456");
    assertThat(exchangeCode).doesNotHaveSameHashCodeAs(ExchangeCode.of("555"));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void rendersCorrectly() {

    Renderer<ExchangeCode> mockRenderer = mock(Renderer.class);

    doAnswer(invocation -> "Exchange Code [%s]".formatted(invocation.getArgument(0, ExchangeCode.class)))
      .when(mockRenderer).render(any());

    ExchangeCode exchangeCode = ExchangeCode.of("456");

    assertThat(exchangeCode.render(mockRenderer)).isEqualTo("Exchange Code [456]");

    verify(mockRenderer, times(1)).render(eq(exchangeCode));
    verifyNoMoreInteractions(mockRenderer);
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    ExchangeCode exchangeCode = ExchangeCode.of("456");

    assertExchangeCode(exchangeCode, "456");

    byte[] exchangeCodeBytes = IOUtils.serialize(exchangeCode);

    assertThat(exchangeCodeBytes).isNotNull();
    assertThat(exchangeCodeBytes).isNotEmpty();

    ExchangeCode deserializedExchangeCode = IOUtils.deserialize(exchangeCodeBytes);

    assertThat(deserializedExchangeCode).isNotNull();
    assertThat(deserializedExchangeCode).isNotSameAs(exchangeCode);
    assertThat(deserializedExchangeCode).isEqualTo(exchangeCode);
  }

  @Test
  public void toStringIsCorrect() {
    assertThat(ExchangeCode.of("456")).hasToString("456");
  }
}
