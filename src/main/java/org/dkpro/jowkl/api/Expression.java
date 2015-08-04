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
 * Contains OmegaWiki Expression
 * @author matuschek
 *
 */
public class Expression {
	private int expression_id;
	private int language_id;
	private DefinedMeaning dm;
	private String spelling;
	private DatabaseStatements dbStatements;


	/**Constructor
	 * @param exId expression_id
	 * @param language_id
	 * @param spelling
	 * @param dbStatements DatabaseStatement Object with Prepared Statements
	 * @param dm parent DM
	 *
	 */
	public Expression(int exId,int language_id, String spelling, DatabaseStatements dbStatements){
		this.expression_id = exId;
		this.dm = null;
		this.setLanguageId(language_id);
		this.spelling = spelling;
		this.dbStatements = dbStatements;
	}

	/**Constructor
	 * @param exId expression_id
	 * @param language_id
	 * @param dm parent DM
	 * @param spelling
	 */
	public Expression(int exId,int language_id, DefinedMeaning dm, String spelling){
		this.expression_id = exId;
		this.dm = dm;
		this.setLanguageId(language_id);
		this.spelling = spelling;
		this.dbStatements = null;
	}



	/**Constructor
	 * @param exId expression_id
	 * @param dm parent DM
	 * @param spelling
	 * @param language_id
	 * @param dbStatements DatabaseStatement Object with Prepared Statements
	 */
	public Expression(int exId,int language_id, DefinedMeaning dm, String spelling, DatabaseStatements dbStatements){
		this.expression_id = exId;
		this.dm = dm;
		this.spelling = spelling;
		this.setLanguageId(language_id);
		this.dbStatements = dbStatements;
	}


	/**
	 * @return the Id
	 */
	public int getExpressionId() {
		return expression_id;
	}

	/**
	 * @param exId the Id to set
	 */
	public void setExpressionId(int exId) {
		this.expression_id = exId;
	}

	/**
	 * @return the DM
	 */
	public DefinedMeaning getDefinedMeaning() {
		return dm;
	}

	/**
	 * @param dm the DM to set
	 */
	public void setDefinedMeaning(DefinedMeaning dm) {
		this.dm = dm;
	}

	/**
	 * @return the spelling
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * @param spelling the spelling to set
	 */
	public void setSpelling(String spelling) {
		this.spelling = spelling;
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
	 * Expression toString
	 */
	@Override
	public String toString(){
		return this.spelling+"#"+this.expression_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression_id;
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
		Expression other = (Expression) obj;
		if ((expression_id != other.expression_id )&&(dm.equals(other.dm))) {
			return false;
		}
		if ((expression_id == other.expression_id )&&!(dm.equals(other.dm))) {
			return false;
		}
		return true;
	}

	public void setLanguageId(int language_id) {
		this.language_id = language_id;
	}

	public int getLanguageId() {
		return language_id;
	}

}
