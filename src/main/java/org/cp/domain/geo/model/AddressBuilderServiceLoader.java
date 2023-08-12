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

import java.util.Locale;
import java.util.function.Predicate;

import org.cp.domain.geo.annotation.CountryQualifier;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.utils.GeoUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.service.ServiceUnavailableException;
import org.cp.elements.service.loader.ServiceLoaderSupport;

/**
 * Elements {@link ServiceLoaderSupport} implementation used to locate and load {@link Address.Builder} objects
 * to construct and build instance of {@link Address} based in the current {@link Locale}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address.Builder
 * @see org.cp.elements.service.loader.ServiceLoaderSupport
 * @since 0.1.0
 */
@SuppressWarnings("rawtypes")
abstract class AddressBuilderServiceLoader implements ServiceLoaderSupport<Address.Builder> {

  static final AddressBuilderServiceLoader INSTANCE = new AddressBuilderServiceLoader() { };

  static @NotNull Predicate<Address.Builder> addressBuilderPredicate(@Nullable Country country) {

    return addressBuilder -> {

      Class<?> addressBuilderType = addressBuilder.getClass();

      return addressBuilderType.isAnnotationPresent(CountryQualifier.class)
        && addressBuilderType.getAnnotation(CountryQualifier.class).value().equals(GeoUtils.resolveCountry(country));
    };
  }

  @NotNull Address.Builder getServiceInstance(@Nullable Country country) {

    try {
      return getServiceInstance(addressBuilderPredicate(country));
    }
    catch (ServiceUnavailableException ignore) {
      return AddressFactory.newGenericAddressBuilder(country);
    }
  }

  @Override
  public Class<Address.Builder> getType() {
    return Address.Builder.class;
  }
}
