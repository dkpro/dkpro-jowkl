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
 * Contains OW Translated content
 * @author matuschek
 *
 */
public class TranslatedContent {


	private int translated_content_id;
	private int language_id;
	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int languageId) {
		language_id = languageId;
	}

	private DefinedMeaning defined_meaning;
	private String gloss;
	private DatabaseStatements dbStatements;




	/**Constructor
	 *
	 * @param tcidtranslated_content_id
	 * @param lid
	 * @param dm
	 * @param gloss
	 */
	public TranslatedContent(int translated_content_id, int language_id, DefinedMeaning defined_meaning, String gloss){
		this.translated_content_id = translated_content_id;
		this.language_id=language_id;
		this.defined_meaning = defined_meaning;
		this.gloss = gloss;
		this.dbStatements = null;
	}

	/**Constructor
	 *
	 * @param tcid
	 * @param lid
	 * @param dm
	 * @param gloss
	 * @param dbStatements
	 */
	public TranslatedContent(int translated_content_id, int language_id, DefinedMeaning defined_meaning, String gloss, DatabaseStatements dbStatements){
		this.translated_content_id = translated_content_id;
		this.language_id=language_id;
		this.defined_meaning = defined_meaning;
		this.gloss = gloss;
		this.dbStatements = dbStatements;
	}


	/**
	 * @return the tcid
	 */
	public int getTranslatedContentid() {
		return translated_content_id;
	}

	/**
	 * @param tcid the tcid to set
	 */
	public void setTranslatedContentid(int tcid) {
		this.translated_content_id = tcid;
	}

	/**
	 * @return the DefinedMeaning
	 */
	public DefinedMeaning getDefinedMeaning() {
		return defined_meaning;
	}

	/**
	 * @param dm the DefinedMeaning to set
	 */
	public void setDefinedMeaning(DefinedMeaning dm) {
		this.defined_meaning = dm;
	}

	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return gloss;
	}

	/**
	 * @param gloss the gloss to set
	 */
	public void setGloss(String gloss) {
		this.gloss = gloss;
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

	/**
	 * Term toString
	 */
	@Override
	public String toString(){
		return this.gloss+"#"+this.translated_content_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + translated_content_id;
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
		TranslatedContent other = (TranslatedContent) obj;
		if (translated_content_id != other.translated_content_id && language_id != other.language_id) {
			return false;
		}
		if (translated_content_id == other.translated_content_id && language_id != other.language_id) {
			return false;
		}
		return true;
	}

}
