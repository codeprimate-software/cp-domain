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
 * Unit Tests for {@link Extension}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.Extension
 * @since 0.1.0
 */
public class ExtensionUnitTests {

  @Test
  public void fromIsCorrect() {

    Extension mockExtension = mock(Extension.class);

    doReturn("1234").when(mockExtension).getNumber();

    Extension copy = Extension.from(mockExtension);

    assertThat(copy).isNotNull();
    assertThat(copy.getNumber()).isEqualTo("1234");

    verify(mockExtension, times(1)).getNumber();
    verifyNoMoreInteractions(mockExtension);
  }

  @Test
  public void fromNullThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Extension.from(null))
      .withMessage("Extension to copy is required")
      .withNoCause();
  }

  @Test
  public void ofIntegerNumber() {

    Extension extension = Extension.of(1234);

    assertThat(extension).isNotNull();
    assertThat(extension.getNumber()).isEqualTo("1234");
  }

  @Test
  public void ofNegativeNumber() {

    Extension extension = Extension.of(-4321);

    assertThat(extension).isNotNull();
    assertThat(extension.getNumber()).isEqualTo("4321");
  }

  @Test
  public void fromStringNumber() {

    Extension extension = Extension.of("0248");

    assertThat(extension).isNotNull();
    assertThat(extension.getNumber()).isEqualTo("0248");
  }

  @Test
  public void constructNewExtension() {

    Extension extension = new Extension("1234567890");

    assertThat(extension).isNotNull();
    assertThat(extension.getNumber()).isEqualTo("1234567890");
  }

  @Test
  public void constructNewExtensionWithInvalidNumbers() {

    Arrays.asList("  ", "", null).forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Extension(number))
        .withMessage("Extension [%s] must contain digits only", number)
        .withNoCause());
  }

  @Test
  public void constructNewExtensionWithNonDigitNumbers() {

    Arrays.asList("x1234", "1O1", "#456").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Extension(number))
        .withMessage("Extension [%s] must contain digits only", number)
        .withNoCause());
  }

  @Test
  public void cloneIsCorrect() throws CloneNotSupportedException {

    Extension extension = Extension.of(123);

    Object clone = extension.clone();

    assertThat(clone).isInstanceOf(Extension.class)
      .asInstanceOf(InstanceOfAssertFactories.type(Extension.class))
      .isNotSameAs(extension)
      .extracting(Extension::getNumber)
      .isEqualTo("123");
  }

  @Test
  public void compareToIsCorrect() {

    Extension one = Extension.of(123);
    Extension two = Extension.of(456);
    Extension three = Extension.of(789);

    assertThat(one).isLessThan(two);
    assertThat(two).isGreaterThan(one);
    assertThat(three).isGreaterThan(two);
    assertThat(three).isGreaterThan(one);
    assertThat(two).isEqualByComparingTo(two);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsIsCorrect() {

    Extension extension = Extension.of(123);

    assertThat(extension).isEqualTo(extension);
    assertThat(extension).isEqualTo(Extension.of(123));
    assertThat(extension).isNotEqualTo(Extension.of(1234));
    assertThat(extension).isNotEqualTo("123");
    assertThat(extension).isNotEqualTo("extension");
    assertThat(extension).isNotEqualTo(null);
  }

  @Test
  public void hashCodeIsCorrect() {

    Extension extension = Extension.of(123);

    assertThat(extension).hasSameHashCodeAs(extension);
    assertThat(extension).hasSameHashCodeAs(Extension.of(123));
    assertThat(extension).doesNotHaveSameHashCodeAs(Extension.of(321));
    assertThat(extension).doesNotHaveSameHashCodeAs("123");
    assertThat(extension).doesNotHaveSameHashCodeAs(new Object());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void rendersCorrectly() {

    Renderer<Extension> mockRenderer = mock(Renderer.class);

    doAnswer(invocation -> "Ext. ".concat(invocation.getArgument(0, Extension.class).getNumber()))
      .when(mockRenderer).render(any());

    Extension extension = Extension.of(456);

    assertThat(extension.render(mockRenderer)).isEqualTo("Ext. 456");

    verify(mockRenderer, times(1)).render(eq(extension));
    verifyNoMoreInteractions(mockRenderer);
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    Extension extension = new Extension("1234");

    byte[] extensionBytes = IOUtils.serialize(extension);

    assertThat(extensionBytes).isNotNull();
    assertThat(extensionBytes).isNotEmpty();

    Extension deserializedExtension = IOUtils.deserialize(extensionBytes);

    assertThat(deserializedExtension).isNotNull();
    assertThat(deserializedExtension).isEqualTo(extension);
    assertThat(deserializedExtension).isNotSameAs(extension);
  }

  @Test
  public void toStringIsCorrect() {
    assertThat(Extension.of(123)).hasToString("123");
  }
}
