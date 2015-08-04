/*******************************************************************************
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.dkpro.jowkl.api;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.dkpro.jowkl.exception.OmegaWikiException;

/**
 * Interface for DMs and SynTranses which can both have annotations
 * @author matuschek
 *
 */
public interface AnnotatableUnits  {

	/**
	 * @return The Annotations for this object
	 * @throws OmegaWikiException
	 * @throws UnsupportedEncodingException
	 */

	public Set<Annotation> getAnnotations() throws OmegaWikiException, UnsupportedEncodingException;
	public Set<Annotation> getAnnotations(int language_id) throws OmegaWikiException, UnsupportedEncodingException;
	public void setAnnotations(Set<Annotation> arg1);


}
