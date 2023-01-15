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
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;

/**
 * Unit Tests for {@link Addressable}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Addressable
 * @since 0.1.0
 */
public class AddressableUnitTests {

  @Test
  public void isAddressPresentWhenAddressIsSetReturnsTrue() {

    Address mockAddress = mock(Address.class);

    Addressable addressable = mock(Addressable.class);

    doReturn(mockAddress).when(addressable).getAddress();
    doCallRealMethod().when(addressable).isAddressPresent();

    assertThat(addressable.isAddressPresent()).isTrue();

    verify(addressable, times(1)).isAddressPresent();
    verify(addressable, times(1)).getAddress();
    verifyNoInteractions(mockAddress);
  }

  @Test
  public void isAddressPresentWhenAddressIsNotSetReturnsFalse() {

    Addressable addressable = mock(Addressable.class);

    doReturn(null).when(addressable).getAddress();
    doCallRealMethod().when(addressable).isAddressPresent();

    assertThat(addressable.isAddressPresent()).isFalse();

    verify(addressable, times(1)).isAddressPresent();
    verify(addressable, times(1)).getAddress();
  }
}
