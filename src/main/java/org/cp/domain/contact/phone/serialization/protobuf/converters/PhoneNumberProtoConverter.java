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
package org.cp.domain.contact.phone.serialization.protobuf.converters;

import com.google.protobuf.Message;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.Extension;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.contact.phone.model.proto.PhoneNumberProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;

/**
 * {@link Converter} used to Protobuf {@link Message} into a {@link PhoneNumber}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.domain.contact.phone.model.proto.PhoneNumberProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class PhoneNumberProtoConverter extends AbstractConverter<PhoneNumberProto.PhoneNumber, PhoneNumber> {

  @Override
  public PhoneNumber convert(PhoneNumberProto.PhoneNumber phoneNumber) {

    PhoneNumber convertedPhoneNumber = PhoneNumber.of(
        toAreaCode(phoneNumber.getAreaCode()),
        toExchangeCode(phoneNumber.getExchangeCode()),
        toLineNumber(phoneNumber.getLineNumber()));

    if (phoneNumber.hasExtension()) {
      convertedPhoneNumber.setExtension(toExtension(phoneNumber));
    }

    return convertedPhoneNumber;
  }

  private AreaCode toAreaCode(PhoneNumberProto.AreaCode areaCode) {
    return AreaCode.of(areaCode.getCode());
  }

  private ExchangeCode toExchangeCode(PhoneNumberProto.ExchangeCode exchangeCode) {
    return ExchangeCode.of(exchangeCode.getCode());
  }

  private LineNumber toLineNumber(PhoneNumberProto.LineNumber lineNumber) {
    return LineNumber.of(lineNumber.getNumber());
  }

  private Extension toExtension(PhoneNumberProto.PhoneNumber phoneNumber) {
    return Extension.of(phoneNumber.getExtension().getExtension());
  }
}
