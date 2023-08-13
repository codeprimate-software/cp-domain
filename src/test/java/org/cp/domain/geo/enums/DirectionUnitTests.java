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
package org.cp.domain.geo.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

/**
 * Unit Tests for {@link Direction}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.enums.Direction
 * @since 0.1.0
 */
public class DirectionUnitTests {

  @Test
  void fromAbbreviationReturnsDirection() {

    Arrays.stream(Direction.values()).forEach(direction ->
      assertThat(Direction.fromAbbreviation(direction.getAbbreviation())).isEqualTo(direction));
  }

  @Test
  public void fromIllegalAbbreviationThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Direction.fromAbbreviation("NS"))
      .withMessage("Direction abbreviation [NS] is not valid")
      .withNoCause();
  }

  @Test
  public void abbreviationIsCorrect() {

    assertThat(Direction.EAST.getAbbreviation()).isEqualTo("E");
    assertThat(Direction.NORTH.getAbbreviation()).isEqualTo("N");
    assertThat(Direction.NORTHEAST.getAbbreviation()).isEqualTo("NE");
    assertThat(Direction.NORTHWEST.getAbbreviation()).isEqualTo("NW");
    assertThat(Direction.SOUTH.getAbbreviation()).isEqualTo("S");
    assertThat(Direction.SOUTHEAST.getAbbreviation()).isEqualTo("SE");
    assertThat(Direction.SOUTHWEST.getAbbreviation()).isEqualTo("SW");
    assertThat(Direction.WEST.getAbbreviation()).isEqualTo("W");
  }

  @Test
  public void nameIsCorrect() {

    assertThat(Direction.EAST.getName()).isEqualTo("East");
    assertThat(Direction.NORTH.getName()).isEqualTo("North");
    assertThat(Direction.NORTHEAST.getName()).isEqualTo("Northeast");
    assertThat(Direction.NORTHWEST.getName()).isEqualTo("Northwest");
    assertThat(Direction.SOUTH.getName()).isEqualTo("South");
    assertThat(Direction.SOUTHEAST.getName()).isEqualTo("Southeast");
    assertThat(Direction.SOUTHWEST.getName()).isEqualTo("Southwest");
    assertThat(Direction.WEST.getName()).isEqualTo("West");
  }

  private void testIsDirection(Function<Direction, Boolean> directionFunction, Predicate<Direction> directionPredicate) {

    Arrays.stream(Direction.values()).forEach(direction ->
      assertThat(directionFunction.apply(direction)).isEqualTo(directionPredicate.test(direction)));
  }

  @Test
  public void isNorthIsCorrect() {
    testIsDirection(Direction::isNorth, Direction.NORTH::equals);
  }

  @Test
  public void isNorthboundIsCorrect() {
    testIsDirection(Direction::isNorthbound, direction -> direction.name().contains("NORTH"));
  }

  @Test
  public void isNortheastIsCorrect() {
    testIsDirection(Direction::isNortheast, Direction.NORTHEAST::equals);
  }

  @Test
  public void isNorthwestIsCorrect() {
    testIsDirection(Direction::isNorthwest, Direction.NORTHWEST::equals);
  }

  @Test
  public void isSouthIsCorrect() {
    testIsDirection(Direction::isSouth, Direction.SOUTH::equals);
  }

  @Test
  public void isSouthboundIsCorrect() {
    testIsDirection(Direction::isSouthbound, direction -> direction.name().contains("SOUTH"));
  }

  @Test
  public void isSoutheastIsCorrect() {
    testIsDirection(Direction::isSoutheast, Direction.SOUTHEAST::equals);
  }

  @Test
  public void isSouthwestIsCorrect() {
    testIsDirection(Direction::isSouthwest, Direction.SOUTHWEST::equals);
  }

  @Test
  public void isEastIsCorrect() {
    testIsDirection(Direction::isEast, Direction.EAST::equals);
  }

  @Test
  public void isEastboundIsCorrect() {
    testIsDirection(Direction::isEastbound, direction -> direction.name().contains("EAST"));
  }

  @Test
  public void isWestIsCorrect() {
    testIsDirection(Direction::isWest, Direction.WEST::equals);
  }

  @Test
  public void isWestboundIsCorrect() {
    testIsDirection(Direction::isWestbound, direction -> direction.name().contains("WEST"));
  }
}
