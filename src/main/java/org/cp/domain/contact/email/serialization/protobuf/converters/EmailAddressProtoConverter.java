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
package org.cp.domain.contact.email.serialization.protobuf.converters;

import com.google.protobuf.Message;

import org.cp.domain.contact.email.model.EmailAddress;
import org.cp.domain.contact.email.model.proto.EmailAddressProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;

/**
 * {@link Converter} used to convert a Protobuf {@link Message} into an {@link EmailAddress}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.model.EmailAddress
 * @see org.cp.domain.contact.email.model.proto.EmailAddressProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class EmailAddressProtoConverter extends AbstractConverter<EmailAddressProto.EmailAddress, EmailAddress> {

  @Override
  public EmailAddress convert(EmailAddressProto.EmailAddress emailAddress) {
    return EmailAddress.parse(toString(emailAddress));
  }

  private String toString(EmailAddressProto.EmailAddress emailAddress) {
    return "%1$s@%2$s".formatted(emailAddress.getUsername(), emailAddress.getDomainName());
  }
}
