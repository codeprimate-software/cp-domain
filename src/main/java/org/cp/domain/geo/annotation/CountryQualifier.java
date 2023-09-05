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
package org.cp.domain.geo.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cp.domain.geo.enums.Country;

/**
 * {@link Annotation Qualifier} by {@link Country}.
 *
 * @author John Blum
 * @see java.lang.annotation.Annotation
 * @since 0.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface CountryQualifier {

  /**
   * Configures the {@link Country} in which this annotated {@link Object} has been qualified and resides.
   *
   * @return the {@link Country} in which this annotated {@link Object} has been qualified and resides.
   * @see org.cp.domain.geo.enums.Country
   */
  Country value() default Country.UNKNOWN;

}
