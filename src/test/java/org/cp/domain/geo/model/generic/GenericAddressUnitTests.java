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
package org.cp.domain.geo.model.generic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;

/**
 * Unit Tests for {@link GenericAddress}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.generic.GenericAddress
 * @since 0.1.0
 */
public class GenericAddressUnitTests {

  @Test
  public void newGenericAddressBuilder() {

    GenericAddress.Builder addressBuilder = GenericAddress.newGenericAddressBuilder();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
  }

  @Test
  public void newGenericAddressBuilderWithCountry() {

    GenericAddress.Builder addressBuilder = GenericAddress.newGenericAddressBuilder(Country.GERMANY);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.GERMANY);
  }

  @Test
  public void newGenericAddress() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    GenericAddress address = GenericAddress.newGenericAddressBuilder()
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(Country.localCountry());
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getCoordinates()).isNotPresent();

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void newGenericAddressWithCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    GenericAddress address = GenericAddress.newGenericAddressBuilder(Country.GERMANY)
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .at(mockCoordinates)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(Country.GERMANY);
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getCoordinates().orElse(null)).isEqualTo(mockCoordinates);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode, mockCoordinates);
  }
}
