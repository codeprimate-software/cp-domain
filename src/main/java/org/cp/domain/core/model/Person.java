/*
 * Copyright 2017 Author or Authors.
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

package org.cp.domain.core.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

import org.cp.elements.enums.Gender;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Id;

/**
 * Application domain object type defining and modeling a person.
 *
 * @author John Blum
 * @see java.time.LocalDateTime
 * @see org.cp.elements.enums.Gender
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Nameable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Person implements Identifiable<Long>, Nameable<String> {

  private Gender gender;

  private LocalDateTime birthDate;

  @Id
  private Long id;

  private String firstName;
  private String middleName;
  private String lastName;

  public Person(String firstName, String lastName) {
    this(firstName, lastName, null);
  }

  public Person(String firstName, String lastName, LocalDateTime birthDate) {
    Assert.hasText(firstName, "First name is required");
    Assert.hasText(lastName, "Last name is required");

    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }

  public Optional<Integer> getAge() {
    return Optional.ofNullable(getBirthDate())
      .map(birthDate -> Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears());
  }

  public LocalDateTime getBirthDate() {
    return this.birthDate;
  }

  public Gender getGender() {
    return this.gender;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    Assert.state(StringUtils.hasText(this.firstName), "First name was not set");
    return this.firstName;
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public String getLastName() {
    Assert.state(StringUtils.hasText(this.lastName), "Last name was not set"));
    return this.lastName;
  }

  @Override
  public String getName() {
    StringBuilder name = new StringBuilder(getFirstName());

    Optional.ofNullable(getMiddleName()).filter(StringUtils::hasText)
      .ifPresent(middleName -> name.append(" ").append(middleName));

    name.append(getLastName());

    return name.toString();
  }
}
