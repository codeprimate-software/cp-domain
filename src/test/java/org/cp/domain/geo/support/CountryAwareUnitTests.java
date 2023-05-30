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
package org.cp.domain.geo.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;

/**
 * Unit Tests for {@link CountryAware}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.support.CountryAware
 * @since 0.1.0
 */
public class CountryAwareUnitTests {

  @Test
  public void getCountryReturnsOptionalEmpty() {

    CountryAware mockCountryAware = mock(CountryAware.class);

    doCallRealMethod().when(mockCountryAware).getCountry();

    assertThat(mockCountryAware.getCountry()).isNotPresent();

    verify(mockCountryAware, times(1)).getCountry();
    verifyNoMoreInteractions(mockCountryAware);
  }

  @Test
  public void inCountryCallsSetCountry() {

    CountryAware mockCountryAware = mock(CountryAware.class);

    doCallRealMethod().when(mockCountryAware).in(any());

    assertThat(mockCountryAware.<CountryAware>in(Country.UNITED_STATES_OF_AMERICA)).isSameAs(mockCountryAware);

    verify(mockCountryAware, times(1)).in(eq(Country.UNITED_STATES_OF_AMERICA));
    verify(mockCountryAware, times(1)).setCountry(eq(Country.UNITED_STATES_OF_AMERICA));
    verifyNoMoreInteractions(mockCountryAware);
  }

  @Test
  public void inCountryWithNullCallsSetCountryIsNullSafe() {

    CountryAware mockCountryAware = mock(CountryAware.class);

    doCallRealMethod().when(mockCountryAware).in(any());

    assertThat(mockCountryAware.<CountryAware>in(null)).isSameAs(mockCountryAware);

    verify(mockCountryAware, times(1)).in(isNull());
    verify(mockCountryAware, times(1)).setCountry(isNull());
    verifyNoMoreInteractions(mockCountryAware);
  }
}
