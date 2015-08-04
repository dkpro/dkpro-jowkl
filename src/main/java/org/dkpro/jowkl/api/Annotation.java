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

import org.dkpro.jowkl.db.DatabaseStatements;


/**
 * Contains OmegaWiki Annotation
 * @author matuschek
 *
 */
/**
 * @author matuschek
 *
 */

public class Annotation {
	private int annotation_id;
	private int language_id;
	private AnnotatableUnits parent;
	private String name;
	private String value;
	private DatabaseStatements dbStatements;

	/**Annotation for DM or ST
	 *
	 * @param anId annotation Id
	 * @param language_id
	 * @param dm parent annotatable unit
	 * @param name
	 * @param value
	 */
	public Annotation(int anId,int language_id, AnnotatableUnits dm, String name, String value){
		this.annotation_id = anId;
		this.parent = dm;
		this.language_id=language_id;
		this.name = name;
		this.value=value;
		this.dbStatements = null;
	}

	/**Annotation for DM or ST
	 *
	 * @param anId annotation Id
	 * @param language_id
	 * @param dm parent annotatable unit
	 * @param name
	 * @param value
	 * @param dbStatements
	 */
	public Annotation(int anId,int language_id, AnnotatableUnits dm, String name, String value, DatabaseStatements dbStatements){
		this.annotation_id = anId;
		this.parent = dm;
		this.language_id=language_id;
		this.name = name;
		this.value=value;
		this.dbStatements = dbStatements;
	}

	/**
	 *
	 * @return the annotation id
	 */
	public int getAnnotationId() {
		return annotation_id;
	}

	/**
	 * @param aId the annotation Id to set
	 */
	public void setAnnotationId(int aId) {
		this.annotation_id = aId;
	}

	/**
	 * @return the parent annotatable unit
	 */
	public AnnotatableUnits getParent() {
		return parent;
	}

	/**
	 * @param dm the parent to set
	 */
	public void setParent(AnnotatableUnits dm) {
		this.parent = dm;
	}



	/**
	 * @return the dbStatements
	 */
	public DatabaseStatements getDbStatements() {
		return dbStatements;
	}

	/**
	 * @param dbStatements the dbStatements to set
	 */
	public void setDbStatements(DatabaseStatements dbStatements) {
		this.dbStatements = dbStatements;
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int languageId) {
		language_id = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Term toString
	 */
	@Override
	public String toString(){
		return "["+this.annotation_id+"]"+this.name+": "+this.value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + annotation_id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Annotation other = (Annotation) obj;
		if (annotation_id != other.annotation_id) {
			return false;
		}
		return true;
	}

}
