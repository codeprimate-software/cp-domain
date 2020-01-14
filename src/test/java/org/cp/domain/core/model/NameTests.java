/*
 * Copyright 2016 Author or Authors.
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.cp.elements.io.IOUtils;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.Visitor;
import org.junit.Test;

/**
 * Unit Tests for {@link Name}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mock
 * @see org.mockito.Mockito
 * @see org.cp.domain.core.model.Name
 * @see org.cp.elements.lang.Nameable
 * @since 1.0.0
 */
public class NameTests {

  @Test
  public void ofName() {

    Name source = Name.of("Jon", "Jason", "Bloom");
    Name target = Name.of(source);

    assertThat(target).isNotNull();
    assertThat(target).isNotSameAs(source);
    assertThat(target).isEqualTo(source);
  }

  @Test
  public void ofNameWithNoMiddleName() {

    Name source = Name.of("Jon", "Bloom");
    Name target = Name.of(source);

    assertThat(target).isNotNull();
    assertThat(target).isNotSameAs(source);
    assertThat(target.getFirstName()).isEqualTo("Jon");
    assertThat(target.getMiddleName().isPresent()).isFalse();
    assertThat(target.getLastName()).isEqualTo("Bloom");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullNameThrowsException() {

    try {
      Name.of((Name) null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void ofNameableOfName() {

    Name name = Name.of("Jon", "Jason", "Bloom");

    Nameable<Name> mockNameable = mock(Nameable.class);

    when(mockNameable.getName()).thenReturn(name);

    Name copy = Name.of(mockNameable);

    assertThat(copy).isNotNull();
    assertThat(copy).isNotSameAs(name);
    assertThat(copy.getFirstName()).isEqualTo("Jon");
    assertThat(copy.getMiddleName().orElse(null)).isEqualTo("Jason");
    assertThat(copy.getLastName()).isEqualTo("Bloom");

    verify(mockNameable, times(1)).getName();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void ofNameableOfNameWithNoMiddleName() {

    Name name = Name.of("Jon", "Bloom");

    Nameable<Name> mockNameable = mock(Nameable.class);

    when(mockNameable.getName()).thenReturn(name);

    Name copy = Name.of(mockNameable);

    assertThat(copy).isNotNull();
    assertThat(copy).isNotSameAs(name);
    assertThat(copy.getFirstName()).isEqualTo("Jon");
    assertThat(copy.getMiddleName().isPresent()).isFalse();
    assertThat(copy.getLastName()).isEqualTo("Bloom");

    verify(mockNameable, times(1)).getName();
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullNameableThrowsException() {

    try {
      Name.of((Nameable<Name>) null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Nameable of Name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void ofStringContainingFirstNameAndLastName() {

    Name name = Name.of("Jon Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void ofStringContainingFirstNameMiddleInitialAndLastName() {

    Name name = Name.of("Jon J Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("J");
  }

  @Test
  public void ofStringContainingFirstNameMiddleNameAndLastName() {

    Name name = Name.of("Jon Jason Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("Jason");
  }

  @Test
  public void ofStringContainingFirstNameLastNameAndSuffix() {

    Name name = Name.of("Jon Bloom Sr");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void ofStringContainingFirstNameMiddleInitialLastNameAndSuffix() {

    Name name = Name.of("Jon J Bloom Jr.");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("J");
  }

  @Test
  public void ofStringContainingFirstNameMiddleNameLastNameAndUnknownSuffix() {

    Name name = Name.of("Charles Gordon Howell III");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Charles");
    assertThat(name.getLastName()).isEqualTo("Howell");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("Gordon");
  }

  @Test
  public void ofStringContainingMissTitleFirstNameAndLastName() {

    Name name = Name.of("Miss Ellie Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Ellie");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void ofStringContainingMissesTitleFirstNameMiddleInitialAndLastName() {

    Name name = Name.of("Mrs. Sarah E Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Sarah");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("E");
  }

  @Test
  public void ofStringContainingMisterTitleFirstNameMiddleNameAndLastName() {

    Name name = Name.of("Mr. Jon Jason Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("Jason");
  }

  @Test
  public void ofStringContainingMultipleTitlesWithNameAndMultipleSuffixes() {

    Name name = Name.of("Sir Dr. Senior Bloom Jr. 111");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Senior");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void ofStringContainingPaddedFirstNameMiddleNameAndLastName() {

    Name name = Name.of("  Jon   J Bloom    ");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("J");
  }

  @Test
  public void ofStringContainingTitleFirstNameLastNameAndSuffix() {

    Name name = Name.of("Dr. Evil Mister Sr.");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Evil");
    assertThat(name.getLastName()).isEqualTo("Mister");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  private void testOfStringContainingIllegalNameThrowsException(String name) {

    try {
      Name.of(name);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("First and last name are required; was [%s]", name);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofStringContainingOnlyFirstNameThrowsException() {
    testOfStringContainingIllegalNameThrowsException("Jon");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofBlankStringThrowsException() {
    testOfStringContainingIllegalNameThrowsException("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofEmptyStringThrowsException() {
    testOfStringContainingIllegalNameThrowsException("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullStringThrowsException() {
    testOfStringContainingIllegalNameThrowsException(null);
  }

  @Test
  public void ofFirstNameAndLastName() {

    Name name = Name.of("Jon", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void ofFirstNameMiddleNameAndLastName() {

    Name name = Name.of("Jon", "Jason", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("Jason");
  }

  @Test
  public void constructName() {

    Name name = new Name("Jon", "Jason", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().orElse(null)).isEqualTo("Jason");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNameWithNoFirstName() {

    try {
      new Name(" ", "J", "Bloom");
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("First name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructNameWithNoLastName() {

    try {
      Name.of("Jon", " ", "");
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Last name is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void acceptsVisitor() {

    Name name = Name.of("Jon", "Bloom");

    Visitor mockVisitor = mock(Visitor.class);

    assertThat(name).isNotNull();

    name.accept(mockVisitor);

    verify(mockVisitor, times(1)).visit(eq(name));
  }

  @Test
  public void changeLastName() {

    Name name = Name.of("Jon", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.getName()).isSameAs(name);
    assertThat(name.getFirstName()).isEqualTo("Jon");
    assertThat(name.getLastName()).isEqualTo("Bloom");
    assertThat(name.getMiddleName().isPresent()).isFalse();

    Name nameChange = name.change("Doe");

    assertThat(nameChange).isNotNull();
    assertThat(nameChange).isNotSameAs(name);
    assertThat(nameChange.getName()).isSameAs(nameChange);
    assertThat(nameChange.getFirstName()).isEqualTo("Jon");
    assertThat(nameChange.getLastName()).isEqualTo("Doe");
    assertThat(nameChange.getMiddleName().isPresent()).isFalse();
  }

  @Test
  public void likeByFirstNameReturnsTrue() {

    Name jonBloom = Name.of("Jon", "R", "Bloom");
    Name jonBlum = Name.of("Jon", "J", "Blum");

    assertThat(jonBloom.like(jonBlum)).isTrue();
    assertThat(jonBlum.like(jonBloom)).isTrue();
  }

  @Test
  public void likeByLastNameReturnsTrue() {

    Name jonBloom = Name.of("Jon", "R", "Bloom");
    Name johnBloom = Name.of("John", "J", "Bloom");

    assertThat(jonBloom.like(johnBloom)).isTrue();
    assertThat(johnBloom.like(jonBloom)).isTrue();
  }

  @Test
  public void likeByUnlikeNamesReturnsFalse() {

    Name johnBlum = Name.of("John", "J", "Blum");
    Name jonBloom = Name.of("Jon", "J", "Bloom");

    assertThat(johnBlum.like(jonBloom)).isFalse();
    assertThat(jonBloom.like(johnBlum)).isFalse();
  }

  @Test
  public void likeHandlesNullReturnsFalse() {
    assertThat(Name.of("Jon", "R", "Bloom").like(null)).isFalse();
  }

  @Test
  public void cloneIsSuccessful() {

    Name name = Name.of("Jon", "Bloom");
    Name clone = (Name) name.clone();

    assertThat(clone).isNotNull();
    assertThat(clone).isNotSameAs(name);
    assertThat(clone.getName()).isSameAs(clone);
    assertThat(clone.getFirstName()).isEqualTo("Jon");
    assertThat(clone.getLastName()).isEqualTo("Bloom");
    assertThat(clone.getMiddleName().isPresent()).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void compareToItselfIsIdentical() {

    Name name = Name.of("Jon", "J", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.compareTo(name)).isEqualTo(0);
  }

  @Test
  public void compareToWithEqualNamesIsEqual() {

    Name jonBloomOne = Name.of("Jon", "Bloom");
    Name jonBloomTwo = Name.of("Jon", "Bloom");

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.compareTo(jonBloomTwo)).isEqualTo(0);
  }

  @Test
  public void compareToIsGreaterThan() {

    Name jonBloom = Name.of("Jon", "Bloom");
    Name sarahBloom = Name.of("Sarah", "Bloom");

    assertThat(jonBloom).isNotNull();
    assertThat(sarahBloom).isNotNull();
    assertThat(sarahBloom).isNotSameAs(jonBloom);
    assertThat(sarahBloom.compareTo(jonBloom)).isGreaterThan(0);
  }

  @Test
  public void compareToIsLessThan() {

    Name ellieBloom = Name.of("Ellie", "Bloom");
    Name jonBloom = Name.of("Jon", "Bloom");

    assertThat(ellieBloom).isNotNull();
    assertThat(jonBloom).isNotNull();
    assertThat(ellieBloom).isNotSameAs(jonBloom);
    assertThat(ellieBloom.compareTo(jonBloom)).isLessThan(0);
  }

  @Test
  public void equalsWithEqualNamesIsTrue() {

    Name jonBloomOne = Name.of("Jon", "J", "Bloom");
    Name jonBloomTwo = Name.of("Jon", "J", "Bloom");

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsWithIdenticalNamesIsTrue() {

    Name name = Name.of("Jon", "J", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.equals(name)).isTrue();
  }

  @Test
  public void equalsWithNearlyEqualNamesIsFalse() {

    Name jonBloomOne = Name.of("Jon", "J", "Bloom");
    Name jonBloomTwo = Name.of("Jon", "Bloom");

    assertThat(jonBloomOne).isNotNull();
    assertThat(jonBloomTwo).isNotNull();
    assertThat(jonBloomOne).isNotSameAs(jonBloomTwo);
    assertThat(jonBloomOne.equals(jonBloomTwo)).isFalse();
  }

  @Test
  public void equalsWithSimilarNamesIsFalse() {

    Name johnBlum = Name.of("John", "J", "Blum");
    Name jonBloom = Name.of("Jon", "J", "Bloom");

    assertThat(johnBlum).isNotNull();
    assertThat(jonBloom).isNotNull();
    assertThat(johnBlum).isNotSameAs(jonBloom);
    assertThat(johnBlum.equals(jonBloom)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsFalse() {
    assertThat(Name.of("Jon", "J", "Bloom").equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsRandomObjectIsFalse() {
    assertThat(Name.of("Jon", "Bloom").equals("test")).isFalse();
  }

  @Test
  public void hashCodeForNameIsNotZero() {
    assertThat(Name.of("Jon", "J", "Bloom").hashCode()).isNotEqualTo(0);
  }

  @Test
  public void hashCodeForDifferentNamesAreNotEqual() {
    assertThat(Name.of("Jon", "J", "Bloom").hashCode())
      .isNotEqualTo(Name.of("Jon", "Bloom").hashCode());
  }

  @Test
  public void hashCodeForEqualNamesAreEqual() {
    assertThat(Name.of("Jon", "Bloom").hashCode()).isEqualTo(Name.of("Jon", "Bloom").hashCode());
  }

  @Test
  public void hashCodeForIdenticalNamesAreEqual() {

    Name name = Name.of("Jon", "J", "Bloom");

    assertThat(name).isNotNull();
    assertThat(name.hashCode()).isEqualTo(name.hashCode());
  }

  @Test
  public void toStringWithFirstNameAndLastNameIsCorrect() {
    assertThat(Name.of("Jon", "Bloom").toString()).isEqualTo("Jon Bloom");
  }

  @Test
  public void toStringWithFirstNameMiddleNameAndLastNameIsCorrect() {

    assertThat(Name.of("Jon", "J", "Bloom").toString()).isEqualTo("Jon J Bloom");
    assertThat(Name.of("Jon", "Jason", "Bloom").toString()).isEqualTo("Jon Jason Bloom");
  }

  @Test
  public void toStringWithTitleFirstNameMiddleNameLastNameAndSuffixIsStringWithFirstNameMiddleNameAndLastNameOnly() {
    assertThat(Name.of("Dr. Evil T Doer Sr.").toString()).isEqualTo("Evil T Doer");
  }

  @Test
  public void nameIsSerializable() throws IOException, ClassNotFoundException {

    Name name = Name.of("Jon", "J", "Bloom");

    assertThat(name).isNotNull();

    byte[] nameBytes = IOUtils.serialize(name);

    assertThat(nameBytes).isNotNull();
    assertThat(nameBytes).isNotEmpty();

    Name deserializedName = IOUtils.deserialize(nameBytes);

    assertThat(deserializedName).isNotNull();
    assertThat(deserializedName).isNotSameAs(name);
    assertThat(deserializedName.getFirstName()).isEqualTo("Jon");
    assertThat(deserializedName.getLastName()).isEqualTo("Bloom");
    assertThat(deserializedName.getMiddleName().orElse(null)).isEqualTo("J");
  }
}
