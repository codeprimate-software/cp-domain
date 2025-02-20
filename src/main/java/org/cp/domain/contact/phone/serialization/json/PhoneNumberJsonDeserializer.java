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
package org.cp.domain.contact.phone.serialization.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.Extension;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;

/**
 * Jackson {@link JsonDeserializer} used to deserialize JSON as a {@link PhoneNumber}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @since 0.3.0
 */
@SuppressWarnings("unused")
public class PhoneNumberJsonDeserializer extends JsonDeserializer<PhoneNumber> {

  @Override
  @SuppressWarnings("all")
  public PhoneNumber deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    JsonNode phoneNumberNode = parsePhoneNumberNode(jsonParser);

    AreaCode areaCode = parseAreaCode(jsonParser, phoneNumberNode);
    ExchangeCode exchangeCode = parseExchangeCode(jsonParser, phoneNumberNode);
    LineNumber lineNumber = parseLineNumber(jsonParser, phoneNumberNode);
    Extension extension = parseExtension(jsonParser, phoneNumberNode);

    PhoneNumber.Builder phoneNumberBuilder = PhoneNumber.builder()
      .inAreaCode(areaCode)
      .usingExchange(exchangeCode)
      .withLineNumber(lineNumber)
      .withExtension(extension);

    if (isTextEnabled(phoneNumberNode)) {
      phoneNumberBuilder.withTextEnabled();
    }

    if (phoneNumberNode.hasNonNull("country")) {
      Country country = Country.valueOf(phoneNumberNode.get("country").asText());
      phoneNumberBuilder.inCountry(country);
    }

    Long identifier = parseId(jsonParser, phoneNumberNode);
    PhoneNumber.Type phoneNumberType = parsePhoneNumberType(jsonParser, phoneNumberNode);

    PhoneNumber phoneNumber = phoneNumberBuilder.build().identifiedBy(identifier);

    phoneNumber.setType(phoneNumberType);

    return phoneNumber;
  }

  private static JsonNode parsePhoneNumberNode(JsonParser jsonParser) throws IOException {
    return jsonParser.getCodec().readTree(jsonParser);
  }

  private AreaCode parseAreaCode(JsonParser jsonParser, JsonNode phoneNumberNode) throws IOException {
    Assert.state(phoneNumberNode.has("areaCode"), "Expected an AreaCode in JSON [%s]", jsonParser.getText());
    JsonNode areaCodeNode = phoneNumberNode.get("areaCode");
    String number = areaCodeNode.get("number").asText();
    return AreaCode.of(number);
  }

  private ExchangeCode parseExchangeCode(JsonParser jsonParser, JsonNode phoneNumberNode) throws IOException {
    Assert.state(phoneNumberNode.has("exchangeCode"), "Expected an ExchangeCode in JSON [%s]", jsonParser.getText());
    JsonNode exchangeCodeNode = phoneNumberNode.get("exchangeCode");
    String number = exchangeCodeNode.get("number").asText();
    return ExchangeCode.of(number);
  }

  private LineNumber parseLineNumber(JsonParser jsonParser, JsonNode phoneNumberNode) throws IOException {
    Assert.state(phoneNumberNode.has("lineNumber"), "Expected an LineNumber in JSON [%s]", jsonParser.getText());
    JsonNode lineNumberNode = phoneNumberNode.get("lineNumber");
    String number = lineNumberNode.get("number").asText();
    return LineNumber.of(number);
  }

  private Extension parseExtension(JsonParser jsonParser, JsonNode phoneNumberNode) throws IOException {

    return phoneNumberNode.hasNonNull("extension")
      ? Extension.of(phoneNumberNode.get("extension").asText())
      : null;
  }

  private PhoneNumber.Type parsePhoneNumberType(JsonParser jsonParser, JsonNode phoneNumberNode) {

    return phoneNumberNode.hasNonNull("type")
      ? PhoneNumber.Type.from(phoneNumberNode.get("type").asText())
      : PhoneNumber.Type.UNKNOWN;
  }

  private Long parseId(JsonParser jsonParser, JsonNode node) {
    return node.has("id") ? node.get("id").asLong() : null;
  }

  private boolean isTextEnabled(JsonNode phoneNumberNode) {
    return phoneNumberNode.get("textEnabled").asBoolean(false);
  }
}
