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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.dkpro.jowkl.db.DatabaseConfiguration;
import org.dkpro.jowkl.db.DatabaseStatements;
import org.dkpro.jowkl.exception.OmegaWikiException;

/**
 * Operates with OmegaWiki, and smoothly so :)
 * @author matuschek
 *
 */
public class OmegaWiki {
	private DatabaseConfiguration dbConfig;
	private DatabaseStatements dbStatements;

	/**
	 * Constructor. Needs DatabaseConfiguration
	 * @param dbConfig DatabaseConfiguration
	 * @throws OmegaWikiException
	 */
	public OmegaWiki(DatabaseConfiguration dbConfig)
					throws OmegaWikiException{
		this.dbConfig = dbConfig;
		 try {
			dbStatements = new DatabaseStatements(dbConfig);
		} catch (SQLException e) {

			throw new OmegaWikiException("OmegaWiki. Error connecting to the DataBase.", e);

		}
	}


	/**
	 * Returns Expressions according to Id
	 * @param exId
	 * @return Set of Expressions, null if not found
	 * @throws OmegaWikiException
	 */
	public Set<Expression> getExpressionById(int exId) throws OmegaWikiException{
		Expression result = null;
		Set<Expression> returnSet= new HashSet<Expression>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectExpression");
			pstmt.setInt(1, exId);
			pstmt.setString(2, "");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
					result = new Expression(rs.getInt("id"), rs.getInt("language_id"), new DefinedMeaning(rs.getInt("defined_meaning_id"), dbStatements),
							rs.getString("spelling"),dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}
		return returnSet;

	}
	/**
	 * Returns SynTrans according to Id
	 * @param stID
	 * @return Set of SynTranses, null if not found
	 * @throws OmegaWikiException
	 */
	public SynTrans getSynTransnById(int stID, int language) throws OmegaWikiException{
		SynTrans result = null;

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectSynTrans");
			pstmt.setInt(1, stID);
			pstmt.setInt(2, language);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
					DefinedMeaning dm = new DefinedMeaning(rs.getInt("defined_meaning_id"));


					Expression e = new Expression(rs.getInt("id"), rs.getInt("language_id"), dm,  rs.getString("spelling"),dbStatements);
					result =new SynTrans(rs.getInt("sid"), e, dm, true, dbStatements);

				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return result;

	}

	/**
	 * Return Expressions according to spelling and language
	 * @param spelling
	 * @param language_id
	 * @return the set of expressions
	 * @throws OmegaWikiException
	 */
	public Set<Expression> getExpressionByWord(String spelling, int language_id) throws OmegaWikiException{
		Expression result = null;
		Set<Expression> returnSet= new HashSet<Expression>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectXpressionLanguage");

		try {
			pstmt.setInt(1, language_id);
			pstmt.setInt(2, 0);
			pstmt.setString(3, spelling);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {


					result = new Expression(rs.getInt("id"), rs.getInt("language_id"), new DefinedMeaning(rs.getInt("defined_meaning_id"), dbStatements),
							rs.getString("spelling"),dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return returnSet;
	}
	/**
	 * Returns Expressions according to spelling
	 * @param spelling
	 * @return Set of Expressions, null if not found
	 * @throws OmegaWikiException
	 */
	public Set<Expression> getExpressionByWord(String spelling) throws OmegaWikiException{
		Expression result = null;
		Set<Expression> returnSet= new HashSet<Expression>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectExpression");
		try {

			pstmt.setInt(1, 0);
			pstmt.setString(2, spelling);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {


					result = new Expression(rs.getInt("expression_id"), rs.getInt("language_id"), new DefinedMeaning(rs.getInt("defined_meaning_id"), dbStatements),
							rs.getString("spelling"),dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return returnSet;
	}





	/**
	 * Returns DM according to its id
	 * @param dmID
	 * @return DefinedMeaning, null if not found
	 * @throws OmegaWikiException
	 */
	public DefinedMeaning getDefinedMeaningById(int dmId) throws OmegaWikiException {
		DefinedMeaning result = null;
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectDefinedMeaning");
			pstmt.setInt(1, dmId);
			pstmt.setString(2, "");
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result = new DefinedMeaning(rs.getInt("id"), dbStatements);
				}
				pstmt.clearParameters();

			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return result;
	}
	/**
	 * Returns DM according to its spelling
	 * @param spelling
	 * @return DefinedMeaning, null if not found
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getDefinedMeaningByWord(String spelling) throws OmegaWikiException {
		DefinedMeaning result = null;
		Set<DefinedMeaning> returnSet= new HashSet<DefinedMeaning>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectExpression");
		try {


			pstmt.setInt(1, 0);
			pstmt.setString(2, spelling);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {


					result = new DefinedMeaning(rs.getInt("defined_meaning_id"), dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return returnSet;
	}

	/**
	 * Returns DM according to its spelling and language
	 * @param spelling
	 * @param language_id
	 * @return DefinedMeaning, null if not found
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getDefinedMeaningByWord(String spelling, int language_id) throws OmegaWikiException{

		DefinedMeaning result = null;
		Set<DefinedMeaning> returnSet= new HashSet<DefinedMeaning>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectXpressionLanguage");
		try {

			pstmt.setInt(1, language_id);
			pstmt.setInt(2, 0);
			pstmt.setString(3, spelling);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {


					result = new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return returnSet;

	}
	/**
	 * Returns DM that have SynTranses in both specified languages
	 * @param language1_id
	 * @param language2_id
	 * @return Set of  DefinedMeanings, null if not found
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getInterlanguageDMLinks(int language1_id, int language2_id) throws OmegaWikiException{

		DefinedMeaning result = null;
		Set<DefinedMeaning> returnSet= new HashSet<DefinedMeaning>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("GetInterlanguageDMLinks");
		try {

			pstmt.setInt(1, language1_id);
			pstmt.setInt(2, language2_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

					result = new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements);
					returnSet.add(result);
				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return returnSet;

	}

	/**
	 * Returns ST and their translations between both specified languages
	 * @param language1_id
	 * @param language2_id
	 * @return Map of SynTranses and their translations, null if not found
	 * @throws OmegaWikiException
	 */
	public Map <SynTrans,Set<SynTrans>> getInterlanguageSTLinks(int language1_id, int language2_id) throws OmegaWikiException{

		SynTrans first = null;
		SynTrans second = null;
		Map <SynTrans,Set<SynTrans>> stm = new HashMap<SynTrans,Set<SynTrans>>();
		PreparedStatement pstmt = dbStatements.getPreparedStatement("GetInterlanguageSTLinks");
		try {

			pstmt.setInt(1, language1_id);
			pstmt.setInt(2, language2_id);
			pstmt.setInt(3, language2_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DefinedMeaning dm = new DefinedMeaning(rs.getInt("id"),dbStatements);
					first = new SynTrans(rs.getInt("sid_en"),null,dm,true,dbStatements);
					second = new SynTrans(rs.getInt("sid_de"),null,dm,true,dbStatements);

					if (stm.containsKey(first))
					{
						stm.get(first).add(second);
					}
					else
					{
						Set<SynTrans> sts = new HashSet<SynTrans>();
						sts.add(second);
						stm.put(first, sts);
					}

				}
				pstmt.clearParameters();

				rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return stm;

	}

	/**
	 * Returns Set of all Expressions, that are contained in OW
	 * @return Set of Expressions
	 * @throws OmegaWikiException
	 */
	public Set<Expression> getAllExpressions() throws OmegaWikiException{
		Set<Expression> result = new HashSet<Expression>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("AllExpressions");
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result.add(new Expression(rs.getInt("id"),rs.getInt("language_id"),new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements),rs.getString("spelling"), dbStatements));
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException", ex);
		}

		return result;
	}

	/**
	 * Returns Set of all Expressions that are contained in OW in a certain language
	 * @return Set of Expressions
	 * @throws OmegaWikiException
	 */
	public Set<Expression> getAllExpressions(int language) throws OmegaWikiException{
		Set<Expression> result = new HashSet<Expression>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("AllExpressionsLang");
			pstmt.setInt(1, language);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result.add(new Expression(rs.getInt("id"),rs.getInt("language_id"),new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements),rs.getString("spelling"), dbStatements));
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException", ex);
		}

		return result;
	}


	/**
	 * Returns the links to Wikipedia for SynTranses in a specified language
	 * @param language
	 * @return Map of ST and links to WP
	 * @throws OmegaWikiException
	 */
	public Map <SynTrans,String> getWPLinks(int language) throws OmegaWikiException{
		Map <SynTrans,String>  result = new HashMap<SynTrans,String>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("GetWPLinks");
			pstmt.setInt(1, language);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					DefinedMeaning dm = new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements);
					SynTrans st = new SynTrans(rs.getInt("syntrans_sid"),null,dm,true,dbStatements);
					result.put(st,rs.getString("url"));
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException", ex);
		}

		return result;
	}

	/**
	 * Returns Set of all DMs, that are contained in OW
	 * @return Set of DMs
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getAllDefinedMeanings() throws OmegaWikiException{
		Set<DefinedMeaning> result = new HashSet<DefinedMeaning>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("AllDefinedMeanings");
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result.add(new DefinedMeaning(rs.getInt("id"), dbStatements));
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException", ex);
		}

		return result;
	}

	/**
	 * Returns Set of all DMs, that are contained in OW and that have SynTranses in the specified language
	 * @param language
	 * @return Set of DMs
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getAllDefinedMeanings(int language) throws OmegaWikiException{
		Set<DefinedMeaning> result = new HashSet<DefinedMeaning>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("AllDefinedMeaningsLang");
			pstmt.setInt(1, language);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result.add(new DefinedMeaning(rs.getInt("id"), dbStatements));
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return result;
	}

	/**
	 * Returns Number of all DefinedMeanings, that are contained in OW
	 * @return Number of all DMs
	 * @throws OmegaWikiException
	 */
	public int getNumberOfDefinedMeanings() throws OmegaWikiException{
		int result = 0;
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("CountDefinedMeanings");
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					result = rs.getInt("num");
				}
			} finally {

				rs.close();
			}
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException", ex);
		}

		return result;
	}



	/**
	 * Sets DatabaseConfiguration
	 * @param dbConfig New DatabaseConfiguration
	 * @throws OmegaWikiException
	 */
	public void setDatabaseConfiguration(DatabaseConfiguration dbConfig)
				throws OmegaWikiException {
		try{
			dbStatements.setDatabaseConfiguration(dbConfig);
		}catch (SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException", ex);
		}
		this.dbConfig = dbConfig;
	}

	/**
	 * @return Current DatabaseConfiguration
	 */
	public DatabaseConfiguration getDatabaseConfiguration(){
		return dbConfig;
	}

}
