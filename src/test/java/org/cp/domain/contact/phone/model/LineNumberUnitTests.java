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
 * Unit Tests for {@link LineNumber}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.LineNumber
 * @since 0.1.0
 */
public class LineNumberUnitTests {

  private void assertLineNumber(LineNumber lineNumber, String number) {

    assertThat(lineNumber).isNotNull();
    assertThat(lineNumber.getNumber()).isEqualTo(number);
  }

  @Test
  public void fromLineNumber() {

    LineNumber mockLineNumber = mock(LineNumber.class);

    doReturn("1234").when(mockLineNumber).getNumber();

    LineNumber copy = LineNumber.from(mockLineNumber);

    assertLineNumber(copy, "1234");

    verify(mockLineNumber, times(1)).getNumber();
    verifyNoMoreInteractions(mockLineNumber);
  }

  @Test
  public void fromNullLineNumber() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> LineNumber.from(null))
      .withMessage("LineNumber to copy is required")
      .withNoCause();
  }

  @Test
  public void ofIntegerNumber() {
    assertLineNumber(LineNumber.of(9876), "9876");
    assertLineNumber(LineNumber.of(-9876), "9876");
  }

  @Test
  public void ofStringNumber() {
    assertLineNumber(LineNumber.of("1234"), "1234");
  }

  @Test
  public void parseTenDigitPhoneNumber() {
    assertLineNumber(LineNumber.parse("5035551234"), "1234");
  }

  @Test
  public void parseFormattedTenDigitPhoneNumber() {

    assertLineNumber(LineNumber.parse("(503) 555-1234"), "1234");
    assertLineNumber(LineNumber.parse("971-555-1234"), "1234");
  }

  @Test
  public void parseSevenDigitPhoneNumber() {
    assertLineNumber(LineNumber.parse("5551234"), "1234");
  }

  @Test
  public void parseFormattedSevenDigitPhoneNumber() {
    assertLineNumber(LineNumber.parse("555-1234"), "1234");
  }

  @Test
  public void parseFourDigitPhoneNumber() {

    assertLineNumber(LineNumber.parse("0012"), "0012");
    assertLineNumber(LineNumber.parse("1234"), "1234");
    assertLineNumber(LineNumber.parse("1248"), "1248");
  }

  @Test
  public void parseFormattedFourDigitPhoneNumber() {

    assertLineNumber(LineNumber.parse("[1234]"), "1234");
    assertLineNumber(LineNumber.parse("SOS-1248"), "1248");
  }

  @Test
  public void parseAnyPhoneNumber() {
    assertLineNumber(LineNumber.parse("31 6 85 31 67"), "3167");
  }

  @Test
  public void parseInvalidPhoneNumbers() {

    Arrays.asList("  ", "", null, "555", "555-lOlO").forEach(phoneNumber ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> LineNumber.parse(phoneNumber))
        .withMessage("Phone Number [%s] must be at least %d-digits",
          phoneNumber, LineNumber.REQUIRED_LINE_NUMBER_LENGTH)
        .withNoCause());
  }

  @Test
  public void constructLineNumber() {
    assertLineNumber(new LineNumber("1234"), "1234");
  }

  @Test
  public void constructLineNumberWithNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new LineNumber(null))
      .withMessage("LineNumber [null] must be a %d-digit number", LineNumber.REQUIRED_LINE_NUMBER_LENGTH)
      .withNoCause();
  }

  @Test
  public void constructLineNumberWithTooFewDigits() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new LineNumber("123"))
      .withMessage("LineNumber [123] must be a %d-digit number", LineNumber.REQUIRED_LINE_NUMBER_LENGTH)
      .withNoCause();
  }

  @Test
  public void constructLineNumberWithTooManyDigits() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new LineNumber("12345"))
      .withMessage("LineNumber [12345] must be a %d-digit number", LineNumber.REQUIRED_LINE_NUMBER_LENGTH)
      .withNoCause();
  }

  @Test
  public void cloneIsCorrect() throws CloneNotSupportedException {

    LineNumber lineNumber = LineNumber.of("1234");

    Object clone = lineNumber.clone();

    assertThat(clone).isInstanceOf(LineNumber.class)
      .asInstanceOf(InstanceOfAssertFactories.type(LineNumber.class))
      .isNotSameAs(lineNumber)
      .extracting(LineNumber::getNumber)
      .isEqualTo("1234");
  }

  @Test
  public void compareToIsCorrect() {

    LineNumber lineNumberOne = LineNumber.of("1234");
    LineNumber lineNumberTwo = LineNumber.of("1248");

    assertThat(lineNumberOne).isEqualByComparingTo(lineNumberOne);
    assertThat(lineNumberTwo).isGreaterThan(lineNumberOne);
    assertThat(lineNumberOne).isLessThan(lineNumberTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsIsCorrect() {

    LineNumber lineNumber = LineNumber.of("1234");

    assertThat(lineNumber).isEqualTo(lineNumber);
    assertThat(lineNumber).isEqualTo(LineNumber.of("1234"));
    assertThat(lineNumber).isNotEqualTo(LineNumber.of("4321"));
    assertThat(lineNumber).isNotEqualTo("1234");
    assertThat(lineNumber).isNotEqualTo(null);
  }

  @Test
  public void hashCodeIsCorrect() {

    LineNumber lineNumber = LineNumber.of("1234");

    assertThat(lineNumber).hasSameHashCodeAs(lineNumber);
    assertThat(lineNumber).hasSameHashCodeAs(LineNumber.of("1234"));
    assertThat(lineNumber).doesNotHaveSameHashCodeAs(LineNumber.of("4321"));
    assertThat(lineNumber).doesNotHaveSameHashCodeAs("1234");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void rendersCorrectly() {

    Renderer<LineNumber> mockRenderer = mock(Renderer.class);

    doAnswer(invocation -> "-%s-".formatted(invocation.getArgument(0, LineNumber.class).getNumber()))
      .when(mockRenderer).render(any());

    LineNumber lineNumber = LineNumber.of("1234");

    assertThat(lineNumber).isNotNull();
    assertThat(lineNumber.render(mockRenderer)).isEqualTo("-1234-");

    verify(mockRenderer, times(1)).render(eq(lineNumber));
    verifyNoMoreInteractions(mockRenderer);
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    LineNumber lineNumber = LineNumber.of("1234");

    assertLineNumber(lineNumber, "1234");

    byte[] lineNumberBytes = IOUtils.serialize(lineNumber);

    assertThat(lineNumberBytes).isNotNull();
    assertThat(lineNumberBytes).isNotEmpty();

    LineNumber deserializedLineNumber = IOUtils.deserialize(lineNumberBytes);

    assertThat(deserializedLineNumber).isNotNull();
    assertThat(deserializedLineNumber).isNotSameAs(lineNumber);
    assertThat(deserializedLineNumber).isEqualTo(lineNumber);
  }
  @Test
  public void toStringIsCorrect() {
    assertThat(LineNumber.of("1234")).hasToString("1234");
  }
}
