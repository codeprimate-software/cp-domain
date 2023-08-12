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
package org.cp.domain.geo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import org.cp.elements.lang.Constants;
import org.cp.elements.lang.ThrowableOperation;

/**
 * Unit Tests for {@link Addressable}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Addressable
 * @since 0.1.0
 */
public class AddressableUnitTests {

  @Test
  public void isAddressPresentWhenAddressIsSetReturnsTrue() {

    Address mockAddress = mock(Address.class);
    Addressable mockAddressable = mock(Addressable.class);

    doReturn(mockAddress).when(mockAddressable).getAddress();
    doCallRealMethod().when(mockAddressable).isAddressPresent();

    assertThat(mockAddressable.isAddressPresent()).isTrue();

    verify(mockAddressable, times(1)).isAddressPresent();
    verify(mockAddressable, times(1)).getAddress();
    verifyNoMoreInteractions(mockAddressable);
    verifyNoInteractions(mockAddress);
  }

  @Test
  public void isAddressPresentWhenAddressIsNotSetReturnsFalse() {

    Addressable mockAddressable = mock(Addressable.class);

    doReturn(null).when(mockAddressable).getAddress();
    doCallRealMethod().when(mockAddressable).isAddressPresent();

    assertThat(mockAddressable.isAddressPresent()).isFalse();

    verify(mockAddressable, times(1)).isAddressPresent();
    verify(mockAddressable, times(1)).getAddress();
    verifyNoMoreInteractions(mockAddressable);
  }

  @Test
  public void setAddressThrowsUnsupportedOperationException() {

    Address mockAddress = mock(Address.class);
    Addressable mockAddressable = mock(Addressable.class);

    doCallRealMethod().when(mockAddressable).setAddress(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockAddressable.setAddress(mockAddress)))
      .havingMessage(Constants.NOT_IMPLEMENTED)
      .withNoCause();

    verify(mockAddressable, times(1)).setAddress(eq(mockAddress));
    verifyNoMoreInteractions(mockAddressable);
    verifyNoInteractions(mockAddress);
  }
}
