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
package org.dkpro.jowkl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Holds Database Prepared Statements for OmegaWiki as called by the API methods.
 *  See these methods for explanations
 * @author matuschek
 *
 */
public class DatabaseStatements {
	HashMap<String, PreparedStatement> prepStatement;
	Connection dbConnection;
	DatabaseConfiguration dbConfig;

	/**
	 * Constructor
	 * @param dbConfig DatabaseConfiguration
	 * @throws SQLException
	 */
	public DatabaseStatements(DatabaseConfiguration dbConfig) throws SQLException{
		prepStatement = new HashMap<String, PreparedStatement>();
		this.dbConfig = dbConfig;
		dbConnection = getConnection(dbConfig);
		initStatements();

	}

	/**
	 * Initialize Prepared Statements
	 * @throws SQLException
	 */
	public void initStatements() throws SQLException{

		initCaseSensitiveStatements();

		PreparedStatement pstmt = null;

		String sql = "SELECT defined_meaning_id as id FROM uw_defined_meaning" +
				" JOIN uw_expression" +
				" ON  uw_defined_meaning.expression_id=uw_expression.expression_id" +
				" WHERE defined_meaning_id=?  OR binary spelling =? ";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectDefinedMeaning", pstmt);

		sql = "SELECT uw_expression.expression_id,defined_meaning_id, language_id, spelling FROM uw_expression " +
				"JOIN uw_syntrans " +
				"ON uw_syntrans.expression_id=uw_expression.expression_id " +
				"WHERE uw_expression.expression_id=?  OR binary spelling =?";
				pstmt = dbConnection.prepareStatement(sql);
				prepStatement.put("SelectExpression", pstmt);

		sql = "SELECT uw_expression.expression_id as id,defined_meaning_id, language_id, spelling,syntrans_sid  as sid FROM uw_expression " +
		"JOIN uw_syntrans " +
		"ON uw_syntrans.expression_id=uw_expression.expression_id " +
		"WHERE uw_syntrans.syntrans_sid=? AND language_id =?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectSynTrans", pstmt);

		sql = "SELECT uw_expression.expression_id as id,defined_meaning_id, language_id, spelling FROM uw_expression " +
		"JOIN uw_syntrans " +
		"ON uw_syntrans.expression_id=uw_expression.expression_id " +
		"WHERE language_id =? AND (uw_expression.expression_id=?  OR binary spelling =?)";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectXpressionLanguage", pstmt);

		sql = "SELECT DISTINCT meaning2_mid,relationtype_mid FROM uw_meaning_relations " +
		"JOIN uw_defined_meaning AS dm1 " +
		"ON dm1.defined_meaning_id  = uw_meaning_relations.meaning1_mid " +
		"WHERE dm1.defined_meaning_id=? AND relationtype_mid=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningLinks", pstmt);

		sql = "SELECT DISTINCT meaning2_mid,relationtype_mid FROM uw_meaning_relations " +
				"JOIN uw_defined_meaning AS dm1 " +
				"ON dm1.defined_meaning_id  = uw_meaning_relations.meaning1_mid " +
				"WHERE dm1.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningLinksAll", pstmt);

		sql = "SELECT DISTINCT meaning2_mid,relationtype_mid FROM uw_meaning_relations" +
				"		JOIN uw_defined_meaning AS dm1" +
				"		ON dm1.defined_meaning_id  = uw_meaning_relations.meaning1_mid" +
				"		JOIN" +
				"		uw_syntrans" +
				"		ON meaning2_mid = uw_syntrans.defined_meaning_id" +
				"		JOIN" +
				"		uw_expression" +
				"		ON uw_syntrans.expression_id=uw_expression.expression_id" +
				"		WHERE dm1.defined_meaning_id=? AND language_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningLinksAllLanguage", pstmt);


		sql = "SELECT DISTINCT meaning1_mid,relationtype_mid FROM uw_meaning_relations " +
		"JOIN uw_defined_meaning AS dm1 " +
		"ON dm1.defined_meaning_id  = uw_meaning_relations.meaning2_mid " +
		"WHERE dm1.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningLinksBackwardsAll", pstmt);


sql = "SELECT DISTINCT meaning1_mid,relationtype_mid FROM uw_meaning_relations" +
"		JOIN uw_defined_meaning AS dm1" +
"		ON dm1.defined_meaning_id  = uw_meaning_relations.meaning2_mid" +
"		JOIN" +
"		uw_syntrans" +
"		ON meaning1_mid = uw_syntrans.defined_meaning_id" +
"		JOIN" +
"		uw_expression" +
"		ON uw_syntrans.expression_id=uw_expression.expression_id" +
"		WHERE dm1.defined_meaning_id=? AND language_id=?";
pstmt = dbConnection.prepareStatement(sql);
prepStatement.put("DefinedMeaningLinksBackwardsAllLanguage", pstmt);

		sql = "SELECT DISTINCT meaning1_mid,relationtype_mid FROM uw_meaning_relations " +
		"JOIN uw_defined_meaning AS dm1 " +
		"ON dm1.defined_meaning_id  = uw_meaning_relations.meaning2_mid " +
		"WHERE dm1.defined_meaning_id=? AND relationtype_mid=?";
pstmt = dbConnection.prepareStatement(sql);
prepStatement.put("DefinedMeaningLinksBackwards", pstmt);

sql = "SELECT DISTINCT defined_meaning_id FROM uw_syntrans " +
		"JOIN uw_expression ON uw_expression.expression_id=uw_syntrans.expression_id " +
		"WHERE language_id=? AND defined_meaning_id IN " +
		"(SELECT DISTINCT defined_meaning_id FROM uw_syntrans " +
		"JOIN uw_expression" +
		" ON uw_expression.expression_id=uw_syntrans.expression_id" +
		" WHERE language_id=?" +
		" )";
pstmt = dbConnection.prepareStatement(sql);
prepStatement.put("GetInterlanguageDMLinks", pstmt);

sql = "SELECT * FROM " +
		"(SELECT DISTINCT syntrans_sid AS sid_en,defined_meaning_id as id,spelling AS spell_en FROM uw_syntrans JOIN uw_expression ON uw_expression.expression_id=uw_syntrans.expression_id " +
		" WHERE language_id=?  AND  defined_meaning_id IN " +
				"		(SELECT DISTINCT defined_meaning_id FROM uw_syntrans " +
				"		JOIN uw_expression" +
				"	 ON uw_expression.expression_id=uw_syntrans.expression_id " +
				"		 WHERE language_id=?) 	)AS Y " +
				"JOIN(SELECT DISTINCT syntrans_sid AS sid_de, defined_meaning_id,spelling AS spell_de FROM uw_syntrans " +
				" JOIN uw_expression ON uw_expression.expression_id=uw_syntrans.expression_id " +
				"WHERE language_id=?) AS X " +
				"ON Y.id = X.defined_meaning_id";
pstmt = dbConnection.prepareStatement(sql);
prepStatement.put("GetInterlanguageSTLinks", pstmt);

sql = "SELECT DISTINCT syntrans_sid,defined_meaning_id,url FROM uw_url_attribute_values " +
		"JOIN uw_syntrans " +
		"ON uw_syntrans.syntrans_sid=object_id " +
		"JOIN uw_expression " +
		"ON uw_expression.expression_id=uw_syntrans.expression_id " +
		"WHERE attribute_mid=740663 AND language_id = ?";
pstmt = dbConnection.prepareStatement(sql);
prepStatement.put("GetWPLinks", pstmt);


		sql = "SELECT defined_meaning_id as id FROM  uw_defined_meaning";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("AllDefinedMeanings", pstmt);


		sql = "SELECT uw_expression.expression_id as id ,defined_meaning_id, language_id, spelling FROM uw_expression " +
		"JOIN uw_syntrans " +
		"ON uw_syntrans.expression_id=uw_expression.expression_id ";


		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("AllExpressions", pstmt);

		sql = "SELECT uw_expression.expression_id as id,defined_meaning_id, language_id, spelling FROM uw_expression " +
		"JOIN uw_syntrans " +
		"ON uw_syntrans.expression_id=uw_expression.expression_id " +
		"WHERE language_id = ?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("AllExpressionsLang", pstmt);

		sql = "SELECT DISTINCT uw_defined_meaning.defined_meaning_id as id " +
				"FROM uw_expression " +
				"JOIN " +
				"uw_syntrans " +
				"JOIN uw_defined_meaning " +
				"WHERE language_id = ? AND uw_syntrans.expression_id = uw_expression.expression_id AND uw_defined_meaning.defined_meaning_id = uw_syntrans.defined_meaning_id";

		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("AllDefinedMeaningsLang", pstmt);


		sql = "select count(defined_meaning_id) as num from uw_defined_meaning";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("CountDefinedMeanings", pstmt);
	}

	/**
	 * Initialize Prepared Statements,
	 * @throws SQLException
	 */
	private void initCaseSensitiveStatements() throws SQLException{


		PreparedStatement pstmt = null;


		String sql1=
			"SELECT translated_content_id,spelling,uw_translated_content.language_id, text_text " +
			"FROM uw_defined_meaning AS dm1 " +
			"JOIN uw_expression " +
			"ON dm1.expression_id=uw_expression.expression_id " +
			"JOIN uw_translated_content " +
			"ON dm1.meaning_text_tcid=uw_translated_content.translated_content_id " +
			"JOIN uw_text " +
			"ON uw_translated_content.text_id=uw_text.text_id " +
			"WHERE dm1.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql1);
		prepStatement.put("SelectGlosses", pstmt);

		String sql =
			"SELECT uw_expression.expression_id, spelling, language_id FROM uw_defined_meaning "+
			"JOIN uw_expression "+
			"ON uw_defined_meaning.expression_id=uw_expression.expression_id "+
			"WHERE defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectSpelling", pstmt);

		sql =
			"SELECT dm2.defined_meaning_id FROM uw_defined_meaning dm1 " +
			"JOIN uw_collection_contents " +
			"ON member_mid = dm1.defined_meaning_id " +
			"JOIN uw_collection " +
			"ON uw_collection.collection_id=uw_collection_contents.collection_id " +
			"JOIN uw_defined_meaning AS dm2 " +
			"ON dm2.defined_meaning_id=collection_mid " +
			"WHERE dm1.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectCollections", pstmt);

		sql =
			"SELECT  dm2.defined_meaning_id FROM uw_defined_meaning dm1 " +
			"JOIN uw_class_membership " +
			"ON class_member_mid = dm1.defined_meaning_id " +
			"JOIN uw_defined_meaning AS dm2 " +
			"ON dm2.defined_meaning_id=class_mid " +
			"WHERE dm1.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectClasses", pstmt);

		sql =
			"SELECT syntrans_sid, uw_syntrans.expression_id, identical_meaning,spelling,language_id FROM uw_syntrans "+
			"JOIN uw_expression "+
			"ON uw_syntrans.expression_id=uw_expression.expression_id "+
			"WHERE defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SelectSyntranses", pstmt);


		sql =
			"SELECT language_id,type_spelling, value_spelling, uw_option_attribute_values.value_id,id1,id2 FROM" +
			" uw_defined_meaning AS dm" +
			" JOIN" +
			" uw_option_attribute_values" +
			" ON dm.defined_meaning_id = uw_option_attribute_values.object_id" +
			" JOIN" +
			" (SELECT uw_option_attribute_options.option_id,expression1.spelling AS type_spelling,expression2.spelling AS value_spelling, uw_option_attribute_options.language_id, dm1.defined_meaning_id as id1,dm2.defined_meaning_id as id2" +
			" FROM  uw_option_attribute_options" +
			" JOIN uw_class_attributes" +
			" ON attribute_id=object_id" +
			" JOIN uw_defined_meaning AS dm1" +
			" ON attribute_mid=dm1.defined_meaning_id" +
			" JOIN uw_expression AS expression1" +
			" ON dm1.expression_id=expression1.expression_id" +
			" JOIN uw_defined_meaning AS dm2" +
			" ON option_mid=dm2.defined_meaning_id" +
			" JOIN uw_expression AS expression2" +
			" ON expression2.expression_id=dm2.expression_id" +
			" ) AS pos" +
			" ON pos.option_id=uw_option_attribute_values.option_id" +
			" WHERE dm.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningAnnos", pstmt);

		sql =
			"SELECT spelling, url, uw_url_attribute_values.value_id,dm1.defined_meaning_id as id FROM " +
			"uw_defined_meaning as dm " +
			"JOIN " +
			"uw_url_attribute_values " +
			"ON dm.defined_meaning_id = uw_url_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1 " +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id " +
			"WHERE dm.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningUrlAnnos", pstmt);

		sql =
			"SELECT spelling, text, uw_text_attribute_values.value_id,dm1.defined_meaning_id as id FROM " +
			"uw_defined_meaning as dm " +
			"JOIN " +
			"uw_text_attribute_values " +
			"ON dm.defined_meaning_id = uw_text_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1 " +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id " +
			"WHERE dm.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningTextAnnos", pstmt);

		sql =
			"SELECT spelling, text_text, uw_translated_content_attribute_values.value_id, uw_translated_content.language_id,dm1.defined_meaning_id as id FROM "+
			"uw_defined_meaning as dm " +
			"JOIN " +
			"uw_translated_content_attribute_values " +
			"ON dm.defined_meaning_id = uw_translated_content_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1	" +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id	" +
			"JOIN uw_translated_content " +
			"ON value_tcid=uw_translated_content.translated_content_id " +
			"JOIN uw_text " +
			"ON uw_translated_content.text_id=uw_text.text_id " +
			"WHERE dm.defined_meaning_id=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("DefinedMeaningTranslatedAnnos", pstmt);


		sql =
			"SELECT language_id,type_spelling, value_spelling, uw_option_attribute_values.value_id FROM" +
			" uw_syntrans" +
			" JOIN" +
			" uw_option_attribute_values" +
			" ON syntrans_sid = uw_option_attribute_values.object_id" +
			" JOIN" +
			" (SELECT uw_option_attribute_options.option_id,expression1.spelling AS type_spelling,expression2.spelling AS value_spelling, uw_option_attribute_options.language_id" +
			" FROM  uw_option_attribute_options" +
			" JOIN uw_class_attributes" +
			" ON attribute_id=object_id" +
			" JOIN uw_defined_meaning AS dm1" +
			" ON attribute_mid=dm1.defined_meaning_id" +
			" JOIN uw_expression AS expression1" +
			" ON dm1.expression_id=expression1.expression_id" +
			" JOIN uw_defined_meaning AS dm2" +
			" ON option_mid=dm2.defined_meaning_id" +
			" JOIN uw_expression AS expression2" +
			" ON expression2.expression_id=dm2.expression_id" +
			" ) AS pos" +
			" ON pos.option_id=uw_option_attribute_values.option_id" +
			" WHERE syntrans_sid=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SyntransAnnos", pstmt);

		sql =
			"SELECT spelling, url, uw_url_attribute_values.value_id FROM " +
			"uw_syntrans " +
			"JOIN " +
			"uw_url_attribute_values " +
			"ON syntrans_sid = uw_url_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1 " +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id " +
			"WHERE syntrans_sid=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SyntransUrlAnnos", pstmt);

		sql =
			"SELECT spelling, text, uw_text_attribute_values.value_id FROM " +
			"uw_syntrans " +
			"JOIN " +
			"uw_text_attribute_values " +
			"ON syntrans_sid = uw_text_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1 " +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id " +
			"WHERE syntrans_sid=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SyntransTextAnnos", pstmt);

		sql =
			"SELECT spelling, text_text, uw_translated_content_attribute_values.value_id, uw_translated_content.language_id FROM "+
			"uw_syntrans " +
			"JOIN " +
			"uw_translated_content_attribute_values " +
			"ON syntrans_sid = uw_translated_content_attribute_values.object_id " +
			"JOIN uw_defined_meaning AS dm1	" +
			"ON attribute_mid=dm1.defined_meaning_id " +
			"JOIN uw_expression AS expression1 " +
			"ON dm1.expression_id=expression1.expression_id	" +
			"JOIN uw_translated_content " +
			"ON value_tcid=uw_translated_content.translated_content_id " +
			"JOIN uw_text " +
			"ON uw_translated_content.text_id=uw_text.text_id " +
			"WHERE syntrans_sid=?";
		pstmt = dbConnection.prepareStatement(sql);
		prepStatement.put("SyntransTranslatedAnnos", pstmt);
	}

	/**
	 * Creates connection to the SQL Database
	 * @param dbConfig DatabaseConfiguration
	 * @throws SQLException
	 * @return
	 */
	public Connection getConnection(DatabaseConfiguration dbConfig) throws SQLException
	{

		try {
			Class.forName(dbConfig.getDb_driver());
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		dbConnection = DriverManager.getConnection(
				"jdbc:"+dbConfig.getDb_vendor()+"://"+dbConfig.getHost()+"/"+dbConfig.getDatabase()+
				"?user="+dbConfig.getUser()+"&password=" + dbConfig.getPassword()+"&amp;?autoReconnect=true");

		return dbConnection;

	}

	/**
	 * Returns PreparedStatement according to its name
	 * @param name Name of Prepared Statement
	 * @return PreparedStatement
	 */
	public PreparedStatement getPreparedStatement(String name){
		return prepStatement.get(name);
	}



	/**
	 * Sets DatabaseConfiguration
	 * @param dbConfig New DatabaseConfiguration
	 * @throws SQLException
	 */
	public void setDatabaseConfiguration(DatabaseConfiguration dbConfig)
			throws SQLException{

		this.dbConfig = dbConfig;
		dbConnection = getConnection(dbConfig);
		initStatements();
		this.dbConfig = dbConfig;
	}

	/**
	 * @return Current DatabaseConfiguration
	 */
	public DatabaseConfiguration getDatabaseConfiguration(){
		return dbConfig;
	}
	@Override
	public void finalize()
	{
		try
		{
		Iterator<PreparedStatement> it = prepStatement.values().iterator();
		while (it.hasNext())
		{
			PreparedStatement ps  = it.next();
			ps.close();
		}
		dbConnection.close();
		}
		catch(SQLException ex)
		{
		ex.printStackTrace();
		}
	}
}
