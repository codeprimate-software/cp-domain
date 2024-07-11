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
import org.cp.domain.geo.model.proto.GeoProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;

/**
 * {@link Converter} used to convert a Protobuf {@link Message} into an {@link Address}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.proto.GeoProto.Address
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class AddressProtoConverter extends AbstractConverter<GeoProto.Address, Address> {

  @Override
  public Address convert(GeoProto.Address address) {

    Street street = toStreet(address.getStreet());

    Country country = resolveCountry(address);

    Address.Builder<Address> addressBuilder = Address.builder((country))
      .on(street)
      .in(toCity(address.getCity()))
      .in(toPostalCode(address.getPostalCode()));

    if (address.hasUnit()) {
      addressBuilder.in(toUnit(address.getUnit()));
    }

    return addressBuilder.build();
  }

  private Street toStreet(GeoProto.Street streetProto) {

    Street street = Street.of(streetProto.getNumber(), streetProto.getName())
      .as(Street.Type.fromName(streetProto.getType().name()));

    if (streetProto.hasDirection()) {
      street.withDirection(Direction.valueOf(streetProto.getDirection().name()));
    }

    return street;
  }

  private Unit toUnit(GeoProto.Unit unitProto) {
    return Unit.of(unitProto.getNumber()).as(Unit.Type.fromDescription(unitProto.getType().name()));
  }

  private City toCity(GeoProto.City city) {
    return City.of(city.getName());
  }

  private PostalCode toPostalCode(GeoProto.PostalCode postalCode) {
    return PostalCode.of(postalCode.getNumber());
  }

  private Country resolveCountry(GeoProto.Address address) {

    return address.hasCountry()
      ? Country.valueOf(address.getCountry().getName())
      : Country.localCountry();
  }
}
