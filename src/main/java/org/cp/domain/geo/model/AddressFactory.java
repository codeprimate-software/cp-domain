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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.cp.domain.geo.annotation.CountryQualifier;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.util.GeoUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.service.ServiceUnavailableException;
import org.cp.elements.service.loader.ServiceLoaderSupport;

/**
 * Factory for {@link Address Addresses}.
 *
 * @author John Blum
 * @param <T> {@link Class Type} of {@link Address} created by this factory.
 * @see org.cp.domain.geo.annotation.CountryQualifier
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Address.Builder
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public abstract class AddressFactory<T extends Address> {

  private static final AddressFactory.Loader ADDRESS_FACTORY_LOADER = new AddressFactory.Loader() { };

  @SuppressWarnings("rawtypes")
  private static final Map<Country, AddressFactory> cache = new ConcurrentHashMap<>();

  /**
   * Factory method used to request an {@link AddressFactory} for creating {@link Address Addresses}
   * located in the {@link Country#localCountry() local Country} based on {@link Locale}.
   *
   * @param <T> {@link Class Type} of {@link Address} created by the factory.
   * @return a new {@link AddressFactory} used to create {@link Address Addresses}
   * located in the {@link Country#localCountry() local Country} based on {@link Locale}.
   * @see org.cp.domain.geo.model.Address
   * @see #getInstance(Country)
   */
  public static @NotNull <T extends Address> AddressFactory<T> getInstance() {
    return getInstance(Country.localCountry());
  }

  /**
   * Factory method used to request an {@link AddressFactory} for creating {@link Address Addresses}
   * located in the given {@link Country}.
   *
   * @param <T> {@link Class Type} of {@link Address} created by the factory.
   * @param country {@link Country} in which {@link Address Addresses} created by the factory will be located;
   * defaults to {@link Country#localCountry()}.
   * @return a new {@link AddressFactory} used to created {@link Address Addresses}
   * located in the given {@link Country}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Address
   */
  @SuppressWarnings("unchecked")
  public static @NotNull <T extends Address> AddressFactory<T> getInstance(@NotNull Country country) {

    try {
      return cache.computeIfAbsent(GeoUtils.resolveToUnknownCountry(country), requestedCountry ->
        ADDRESS_FACTORY_LOADER.getServiceInstance(addressFactoryPredicate(requestedCountry)));
    }
    catch (ServiceUnavailableException ignore) {
      return cache.computeIfAbsent(Country.UNKNOWN, key -> new DefaultAddressFactory());
    }
  }

  @SuppressWarnings("rawtypes")
  private static @NotNull Predicate<AddressFactory> addressFactoryPredicate(@Nullable Country country) {

    return addressFactory -> {

      Class<?> addressFactoryType = addressFactory.getClass();

      return addressFactoryType.isAnnotationPresent(CountryQualifier.class)
        && addressFactoryType.getAnnotation(CountryQualifier.class).value().equals(GeoUtils.resolveCountry(country));
    };
  }

  /**
   * Constructs a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country} based on {@link Locale}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} on the given {@link Street}, in the given {@link City} and {@link PostalCode},
   * located in the {@link Country local country}.
   * @throws IllegalArgumentException if the given {@link Street}, {@link City} or {@link PostalCode}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   */
  @SuppressWarnings("unchecked")
  public @NotNull T newAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    return (T) new FactoryAddress(street, city, postalCode);
  }

  /**
   * Constructs a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country}.
   * @throws IllegalArgumentException if the given {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   */
  @SuppressWarnings("unchecked")
  public @NotNull T newAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @NotNull Country country) {

    return (T) new FactoryAddress(street, city, postalCode, country);
  }

  /**
   * Constructs a new {@link Address.Builder} used to build an {@link Address}
   * located in the {@link Country#localCountry() local Country} determined by {@link Locale}.
   *
   * @param <BUILDER> {@link Class Type} of {@link Address.Builder} used to build an {@link Address} of type {@link T}.
   * @return a new {@link Address.Builder}.
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.domain.geo.model.Address
   */
  @SuppressWarnings("unchecked")
  public @NotNull <BUILDER extends Address.Builder<T>> BUILDER newAddressBuilder() {
    return (BUILDER) new FactoryAddressBuilder().inLocalCountry();
  }

  /**
   * Constructs a new {@link Address.Builder} used to build an {@link Address} located in the given {@link Country}.
   *
   * @param <BUILDER> {@link Class Type} of {@link Address.Builder} used to build an {@link Address} of type {@link T}.
   * @param country {@link Country} in which the new {@link Address} is located.
   * @return a new {@link Address.Builder}.
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.enums.Country
   */
  @SuppressWarnings("unchecked")
  public @NotNull <BUILDER extends Address.Builder<T>> BUILDER newAddressBuilder(@NotNull Country country) {
    return (BUILDER) new FactoryAddressBuilder().in(country);
  }

  private static class DefaultAddressFactory extends AddressFactory<FactoryAddress> { }

  /**
   * {@link AbstractAddress} implementation created by this factory.
   *
   * @see org.cp.domain.geo.model.AbstractAddress
   */
  static class FactoryAddress extends AbstractAddress {

    FactoryAddress(Street street, City city, PostalCode postalCode) {
      super(street, city, postalCode);
    }

    FactoryAddress(Street street, City city, PostalCode postalCode, Country country) {
      super(street, city, postalCode, country);
    }
  }

  /**
   * {@link Address.Builder} implementation used to create {@link FactoryAddress}.
   *
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.domain.geo.model.AddressFactory.FactoryAddress
   */
  static class FactoryAddressBuilder extends Address.Builder<FactoryAddress> {

    @Override
    protected FactoryAddress doBuild() {
      return new FactoryAddress(getStreet(), getCity(), getPostalCode(), getCountry());
    }
  }

  /**
   * Elements {@link ServiceLoaderSupport} used to load {@link Locale} (context) specific
   * {@link AddressFactory} instances.
   *
   * @see org.cp.elements.service.loader.ServiceLoaderSupport
   */
  @SuppressWarnings("rawtypes")
  interface Loader extends ServiceLoaderSupport<AddressFactory> {

    @Override
    default Class<AddressFactory> getType() {
      return AddressFactory.class;
    }
  }
}
