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
package org.cp.domain.geo.serialization.protobuf.converters;

import com.google.protobuf.Message;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.Direction;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Unit;
import org.cp.domain.geo.model.Unit.Type;
import org.cp.domain.geo.model.proto.GeoProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;
import org.cp.elements.lang.Assert;

/**
 * {@link Converter} used to convert an {@link Address} into a Protobuf {@link Message}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.proto.GeoProto.Address
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class AddressConverter extends AbstractConverter<Address, GeoProto.Address> {

  @Override
  public GeoProto.Address convert(Address address) {

    Assert.notNull(address, "Address to convert into a Protobuf message is required");

    GeoProto.Address.Builder addressBuilder = GeoProto.Address.newBuilder()
      .setStreet(toStreet(address.getStreet()))
      .setCity(toCity(address.getCity()))
      .setPostalCode(toPostalCode(address.getPostalCode()))
      .setCountry(toCountry(address.getCountry()));

    address.getUnit().map(this::toUnit).ifPresent(addressBuilder::setUnit);

    return addressBuilder.build();
  }

  private GeoProto.Street toStreet(Street street) {

    GeoProto.Street.Builder streetBuilder = GeoProto.Street.newBuilder()
      .setNumber(street.getNumber())
      .setName(street.getName())
      .setType(toStreetType(street.getType().orElse(Street.Type.UNKNOWN)));

    street.getDirection().map(this::toDirection).ifPresent(streetBuilder::setDirection);

    return streetBuilder.build();
  }

  private GeoProto.Direction toDirection(Direction direction) {
    return GeoProto.Direction.valueOf(direction.name());
  }

  private GeoProto.Street.Type toStreetType(Street.Type streetType) {
    return GeoProto.Street.Type.valueOf(streetType.name());
  }

  private GeoProto.Unit toUnit(Unit unit) {

    return GeoProto.Unit.newBuilder()
      .setNumber(unit.getNumber())
      .setType(toUnitType(unit.getType().orElse(Type.UNKNOWN)))
      .build();
  }

  private GeoProto.Unit.Type toUnitType(Unit.Type unitType) {
    return GeoProto.Unit.Type.valueOf(unitType.name());
  }

  private GeoProto.City toCity(City city) {
    return GeoProto.City.newBuilder().setName(city.getName()).build();
  }

  private GeoProto.PostalCode toPostalCode(PostalCode postalCode) {
    return GeoProto.PostalCode.newBuilder().setNumber(postalCode.getNumber()).build();
  }

  private GeoProto.Country toCountry(Country country) {

    return GeoProto.Country.newBuilder()
      .setName(country.name())
      .setIsoTwo(country.getIsoTwo())
      .setIsoThree(country.getIsoThree())
      .setIsoThreeDigitNumericCountryCode(country.getIsoThreeDigitNumericCountryCode())
      .build();
  }
}
