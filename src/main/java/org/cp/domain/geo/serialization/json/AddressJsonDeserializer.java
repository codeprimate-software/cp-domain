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
package org.cp.domain.geo.serialization.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.Direction;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.Elevation;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Unit;
import org.cp.elements.enums.LengthUnit;
import org.cp.elements.lang.Assert;

/**
 * {@link JsonDeserializer} for {@link Address}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @since 0.3.0
 */
@SuppressWarnings("unused")
public class AddressJsonDeserializer extends JsonDeserializer<Address> {

  @Override
  @SuppressWarnings("all")
  public Address deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    JsonNode addressNode = parseAddressNode(jsonParser);

    Address.Type addressType = parseAddressType(jsonParser, addressNode);
    Street street = parseStreet(jsonParser, addressNode);
    Unit unit = parseUnit(jsonParser, addressNode);
    City city = parseCity(jsonParser, addressNode);
    Coordinates coordinates = parseCoordinates(jsonParser, addressNode);
    Country country = parseCountry(jsonParser, addressNode);
    PostalCode postalCode = parsePostalCode(jsonParser, addressNode);

    Address address = Address.builder(country)
      .on(street)
      .in(unit)
      .in(city)
      .in(postalCode)
      .at(coordinates)
      .build()
      .as(addressType);

    return address;
  }

  private JsonNode parseAddressNode(JsonParser jsonParser) throws IOException {
    return jsonParser.getCodec().readTree(jsonParser);
  }

  private Address.Type parseAddressType(JsonParser jsonParser, JsonNode addressNode) {

    return addressNode.has("type")
      ? Address.Type.valueOf(addressNode.get("type").asText().toUpperCase())
      : null;
  }

  private Street parseStreet(JsonParser jsonParser, JsonNode addressNode) {

    Assert.isTrue(addressNode.has("street"), "Expected JSON [%s] to contain a Street", addressNode.asText());

    JsonNode streetNode = addressNode.get("street");

    Integer number = streetNode.get("number").asInt();
    String name = streetNode.get("name").asText();

    Street.Type streetType = streetNode.has("type")
      ? Street.Type.fromName(streetNode.get("type").asText().toUpperCase())
      : Street.Type.UNKNOWN;

    Direction direction = streetNode.hasNonNull("direction")
      ? Direction.fromName(streetNode.get("direction").asText().toUpperCase())
      : null;

    return Street.of(number, name).as(streetType).withDirection(direction);
  }

  private Unit parseUnit(JsonParser jsonParser, JsonNode addressNode) {

    if (addressNode.has("unit")) {

      JsonNode unitNode = addressNode.get("unit");

      Unit unit = Unit.of(unitNode.get("number").asText());

      Unit.Type unitType = unitNode.hasNonNull("type")
        ? Unit.Type.fromDescription(unitNode.get("type").asText().toUpperCase())
        : Unit.Type.UNKNOWN;

      unit.as(unitType);

      return unit;
    }

    return null;
  }

  private City parseCity(JsonParser jsonParser, JsonNode addressNode) {
    Assert.notNull(addressNode.has("city"), "Expected JSON [%s] to contain a City", addressNode.asText());
    JsonNode cityNode = addressNode.get("city");
    return City.of(cityNode.get("name").asText());
  }

  private Coordinates parseCoordinates(JsonParser jsonParser, JsonNode addressNode) {

    if (addressNode.hasNonNull("coordinates")) {

      JsonNode coordinatesNode = addressNode.get("coordinates");

      double latitude = coordinatesNode.get("latitude").asDouble();
      double longitude = coordinatesNode.get("longitude").asDouble();

      Coordinates coordinates = Coordinates.at(latitude, longitude);

      if (coordinatesNode.hasNonNull("elevation")) {
        JsonNode elevationNode = coordinatesNode.get("elevation");
        double altitude = elevationNode.get("altitude").asDouble();
        LengthUnit lengthUnit = LengthUnit.valueOfName(elevationNode.get("lengthUnit").asText());
        Elevation elevation = Elevation.at(altitude).in(lengthUnit);
        coordinates.at(elevation);
      }

      return coordinates;
    }

    return null;
  }

  private Country parseCountry(JsonParser jsonParser, JsonNode addressNode) {
    Assert.notNull(addressNode.has("country"), "Expected JSON [%s] to contain a Country", addressNode.asText());
    JsonNode countryNode = addressNode.get("country");
    return Country.valueOf(countryNode.asText().toUpperCase());
  }

  private PostalCode parsePostalCode(JsonParser jsonParser, JsonNode addressNode) {
    Assert.state(addressNode.has("postalCode"), "Expected JSON [%s] to contain a PostalCode", addressNode.asText());
    JsonNode postalCodeNode = addressNode.get("postalCode");
    return PostalCode.of(postalCodeNode.get("number").asText());
  }
}
