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
import org.cp.elements.lang.Assert;

/**
 * {@link Converter} used to convert a {@link PhoneNumber} into a Protobuf {@link Message}
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.domain.contact.phone.model.proto.PhoneNumberProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class PhoneNumberConverter extends AbstractConverter<PhoneNumber, PhoneNumberProto.PhoneNumber> {

  @Override
  public PhoneNumberProto.PhoneNumber convert(PhoneNumber phoneNumber) {

    Assert.notNull(phoneNumber, "PhoneNumber to convert is required");

    PhoneNumberProto.PhoneNumber.Builder phoneNumberBuilder = PhoneNumberProto.PhoneNumber.newBuilder()
      .setAreaCode(buildAreaCode(phoneNumber.getAreaCode()))
      .setExchangeCode(buildExchangeCode(phoneNumber.getExchangeCode()))
      .setLineNumber(buildLineNumber(phoneNumber.getLineNumber()));

    phoneNumber.getExtension().ifPresent(extension -> phoneNumberBuilder.setExtension(buildExtension(extension)));

    return phoneNumberBuilder.build();
  }

  private PhoneNumberProto.AreaCode buildAreaCode(AreaCode areaCode) {
    return PhoneNumberProto.AreaCode.newBuilder().setCode(areaCode.getNumber()).build();
  }

  private PhoneNumberProto.ExchangeCode buildExchangeCode(ExchangeCode exchangeCode) {
    return PhoneNumberProto.ExchangeCode.newBuilder().setCode(exchangeCode.getNumber()).build();
  }

  private PhoneNumberProto.LineNumber buildLineNumber(LineNumber lineNumber) {
    return PhoneNumberProto.LineNumber.newBuilder().setNumber(lineNumber.getNumber()).build();
  }

  private PhoneNumberProto.Extension buildExtension(Extension extension) {
    return PhoneNumberProto.Extension.newBuilder().setExtension(extension.getNumber()).build();
  }
}
