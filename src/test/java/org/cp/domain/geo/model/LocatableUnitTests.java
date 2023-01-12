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
package org.cp.domain.geo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Optional;

import org.junit.Test;

import org.cp.elements.lang.ThrowableOperation;

/**
 * Unit Tests for {@link Locatable}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Locatable
 * @since 0.1.0
 */
public class LocatableUnitTests {

  @Test
  public void getCoordinatesIsEmpty() {

    Locatable mockLocatable = mock(Locatable.class);

    doCallRealMethod().when(mockLocatable).getCoordinates();

    Optional<Coordinates> coordinates = mockLocatable.getCoordinates();

    assertThat(coordinates).isNotNull();
    assertThat(coordinates).isNotPresent();

    verify(mockLocatable, times(1)).getCoordinates();
    verifyNoMoreInteractions(mockLocatable);
  }

  @Test
  public void setCoordinatesIsUnsupported() {

    Coordinates mockCoordinates = mock(Coordinates.class);

    Locatable mockLocatable = mock(Locatable.class);

    doCallRealMethod().when(mockLocatable).setCoordinates(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockLocatable.setCoordinates(mockCoordinates)))
      .havingMessage("Setting Coordinates on a Locatable object of type [%s] is not supported",
        mockLocatable.getClass().getName())
      .withNoCause();

    verify(mockLocatable, times(1)).setCoordinates(eq(mockCoordinates));
    verifyNoMoreInteractions(mockLocatable);
    verifyNoInteractions(mockCoordinates);
  }
}
