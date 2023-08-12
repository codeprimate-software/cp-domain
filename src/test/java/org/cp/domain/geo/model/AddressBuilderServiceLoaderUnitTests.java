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

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.annotation.CountryQualifier;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.generic.GenericAddress;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;

/**
 * Unit Tests for {@link AddressBuilderServiceLoader}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.AddressBuilderServiceLoader
 * @since 0.1.0
 */
public class AddressBuilderServiceLoaderUnitTests {

  @Test
  @SuppressWarnings("rawtypes")
  void addressBuilderPredicateMatches() {

    Predicate<Address.Builder> predicate = AddressBuilderServiceLoader.addressBuilderPredicate(Country.GERMANY);

    assertThat(predicate).isNotNull();
    assertThat(predicate.test(GermanyAddressBuilder.INSTANCE)).isTrue();
  }

  @Test
  @SuppressWarnings("rawtypes")
  void addressBuilderPredicateDoesNotMatch() {

    Predicate<Address.Builder> predicate =
      AddressBuilderServiceLoader.addressBuilderPredicate(Country.UNITED_STATES_OF_AMERICA);

    assertThat(predicate).isNotNull();
    assertThat(predicate.test(CanadaAddressBuilder.INSTANCE)).isFalse();
    assertThat(predicate.test(GermanyAddressBuilder.INSTANCE)).isFalse();
    assertThat(predicate.test(UnqualifiedAddressBuilder.INSTANCE)).isFalse();
  }

  @Test
  void getServiceInstanceReturnsGenericAddressBuilder() {

    Address.Builder<?> addressBuilder = AddressBuilderServiceLoader.INSTANCE
      .getServiceInstance(Country.UNITED_KINGDOM);

    assertThat(addressBuilder).isInstanceOf(GenericAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_KINGDOM);
  }

  @Test
  void getServiceInstanceReturnsUnitedStatesAddressBuilder() {

    Address.Builder<?> addressBuilder = AddressBuilderServiceLoader.INSTANCE
      .getServiceInstance(Country.UNITED_STATES_OF_AMERICA);

    assertThat(addressBuilder).isInstanceOf(UnitedStatesAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  void getTypeReturnsAddressBuilderClass() {
    assertThat(AddressBuilderServiceLoader.INSTANCE.getType()).isEqualTo(Address.Builder.class);
  }

  @CountryQualifier(Country.CANADA)
  static class CanadaAddressBuilder extends Address.Builder<Address> {
    static final CanadaAddressBuilder INSTANCE = new CanadaAddressBuilder();
  }

  @CountryQualifier(Country.GERMANY)
  static class GermanyAddressBuilder extends Address.Builder<Address> {
    static final GermanyAddressBuilder INSTANCE = new GermanyAddressBuilder();
  }

  static class UnqualifiedAddressBuilder extends Address.Builder<Address> {
    static final UnqualifiedAddressBuilder INSTANCE = new UnqualifiedAddressBuilder();
  }
}
