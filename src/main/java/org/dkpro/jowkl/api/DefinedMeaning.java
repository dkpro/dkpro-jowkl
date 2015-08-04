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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dkpro.jowkl.db.DatabaseStatements;
import org.dkpro.jowkl.exception.OmegaWikiException;

/**
 * Contains OmegaWiki defined meaning
 * @author matuschek
 *
 */
public class DefinedMeaning implements AnnotatableUnits{
	private int defined_meaning_id;
	private Expression spelling;
	private Set<TranslatedContent> glosses;
	private Set<SynTrans> syntranses;
	private Set<DefinedMeaning> collections;
	private Set<DefinedMeaning> classes;
	private Set<Annotation> annotations;
	private DatabaseStatements dbStatements;

	/**
	 * @param defined_meaning_id
	 */
	public DefinedMeaning(int defined_meaning_id){
		this.defined_meaning_id = defined_meaning_id;
		this.glosses = null;
		this.spelling=null;
		this.syntranses=null;
		this.dbStatements = null;
		this.collections=null;
		this.classes=null;
	}

	/**
	 * @param defined_meaning_id
	 * @param dbStatements DatabaseStatement Object with Prepared Statements
	 */
	public DefinedMeaning(int defined_meaning_id, DatabaseStatements dbStatements){
		this.defined_meaning_id = defined_meaning_id;
		this.dbStatements = dbStatements;
		this.glosses = null;
		this.syntranses=null;
		this.spelling=null;
		this.collections=null;
		this.classes=null;
	}


	/**
	 * Returns Spelling for this DefinedMeaning
	 * @return Expression
	 * @throws OmegaWikiException
	 */
	public Expression getSpelling() throws OmegaWikiException, UnsupportedEncodingException{
		if(this.spelling != null) {
			return spelling;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		this.spelling=null;
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectSpelling");
			pstmt.setInt(1, this.defined_meaning_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String spell="";
				spell= new String(rs.getBytes("spelling"),"UTF-8");
				spelling=(new Expression(rs.getInt("expression_id"),rs.getInt("language_id"), this,spell,dbStatements));
			}
			pstmt.clearParameters();
			rs.close();
		} catch(SQLException ex) {

			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}finally
		{

		}

		return spelling;
	}


	/**
	 * Returns SynTranses for this DefinedMeaning in the specified language
	 * @param language_id
	 * @return Set of SynTranses
	 * @throws OmegaWikiException
	 */
	public Set<SynTrans> getSynTranses(int language_id) throws OmegaWikiException,UnsupportedEncodingException{

		Set <SynTrans> sts = new HashSet<SynTrans>();
		Iterator<SynTrans> it = this.getSynTranses().iterator();
		while (it.hasNext())
		{
			SynTrans st = it.next();
			if (st.getSyntrans().getLanguageId() == language_id) {
				sts.add(st);
			}
		}
		return sts;
	}
	/**
	 * Returns SynTranses for this DefinedMeaning
	 * @return Set of SynTranses
	 * @throws OmegaWikiException
	 */

	public Set<SynTrans> getSynTranses() throws OmegaWikiException,UnsupportedEncodingException{
		if(this.syntranses != null) {
			return syntranses;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		syntranses = new HashSet<SynTrans>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectSyntranses");
			pstmt.setInt(1, this.defined_meaning_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Expression syntrans=null;
				String syntransText = new String(rs.getBytes("spelling"),"UTF-8");
				syntrans=(new Expression(rs.getInt("expression_id"),rs.getInt("language_id"),this, syntransText,dbStatements));

				 syntranses.add(new SynTrans(rs.getInt("syntrans_sid"),syntrans, this,
			    					rs.getBoolean("identical_meaning"),dbStatements));
			}
			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}

		return syntranses;
	}


	/**
	 * Returns the collection (as DM) in which this DefinedMeaning is included
	 * @return Set of DefinedMeanings
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getCollections() throws OmegaWikiException,UnsupportedEncodingException{
		if(this.collections != null) {
			return collections;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		collections = new HashSet<DefinedMeaning>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectCollections");
			pstmt.setInt(1, this.defined_meaning_id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				 collections.add(new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements));
			}
			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException: " + ex.getMessage());
		}

		return collections;
	}

	/**
	 * Returns the classes (as DM) in which this DefinedMeaning is included
	 * @return Set of DefinedMeanings
	 * @throws OmegaWikiException
	 */
	public Set<DefinedMeaning> getClasses() throws OmegaWikiException,UnsupportedEncodingException{
		if(this.classes != null) {
			return classes;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		classes = new HashSet<DefinedMeaning>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectClasses");
			pstmt.setInt(1, this.defined_meaning_id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				 classes.add(new DefinedMeaning(rs.getInt("defined_meaning_id"),dbStatements));
			}
			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException: " + ex.getMessage());
		}

		return classes;
	}



	/**
	 * Returns Glosses for this DefinedMeaning in one language
	 * @param language_id
	 * @return Set of Glosses
	 * @throws OmegaWikiException
	 */
	public Set<TranslatedContent> getGlosses(int language_id) throws OmegaWikiException,UnsupportedEncodingException{
		Set <TranslatedContent> tcs = new HashSet<TranslatedContent>();
		Iterator<TranslatedContent> it = this.getGlosses().iterator();
		while (it.hasNext())
		{
			TranslatedContent tc = it.next();
			if (tc.getLanguage_id() == language_id) {
				tcs.add(tc);
			}
		}
		return tcs;
	}
	/**
	 * Returns Glosses for this DefinedMeaning in several languages
	 * @return Set of Glosses
	 * @throws OmegaWikiException
	 */
	public Set<TranslatedContent> getGlosses() throws OmegaWikiException,UnsupportedEncodingException{
		if(this.glosses != null) {
			return glosses;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		glosses = new HashSet<TranslatedContent>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("SelectGlosses");
			pstmt.setInt(1, this.defined_meaning_id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String glossText="";
				glossText = new String(rs.getBytes("text_text"),"UTF-8");
				 glosses.add(new TranslatedContent(rs.getInt("translated_content_id"),rs.getInt("language_id"), this,
			    					glossText,dbStatements));
			}
			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OpenThesaurus SQLException: " + ex.getMessage());
		}

		return glosses;
	}

	/**
	 * Returns all DMs that are linked to this DM incl relation type id
	 * @return Map of id and DM
	 */

	public Map<DefinedMeaning, Integer> getDefinedMeaningLinksAll() throws OmegaWikiException{

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Map<DefinedMeaning,Integer> result = new HashMap<DefinedMeaning,Integer>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinksAll");
			pstmt.setInt(1, this.defined_meaning_id);


			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.put(new DefinedMeaning(rs.getInt("meaning2_mid"), this.dbStatements),new Integer(rs.getInt("relationtype_mid")));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}
		return result;

	}


	/**
	 * Returns all DMs that are linked to this DM incl relation type id in a certain language
	 * @param language_id
	 * @return Map of id and DM
	 */
	public Map<DefinedMeaning, Integer> getDefinedMeaningLinksAllLang(int language_id) throws OmegaWikiException{

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Map<DefinedMeaning,Integer> result = new HashMap<DefinedMeaning,Integer>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinksAllLanguage");
			pstmt.setInt(1, this.defined_meaning_id);
			pstmt.setInt(2, language_id);


			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.put(new DefinedMeaning(rs.getInt("meaning2_mid"), this.dbStatements),new Integer(rs.getInt("relationtype_mid")));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}
		return result;

	}

	/**
	 * Returns all DMs that are linked to this DM backwards incl relation type id
	 * @return Set of id and DM
	 */
	public Map<DefinedMeaning,Integer> getDefinedMeaningLinksBackwardsAll() throws OmegaWikiException{

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Map<DefinedMeaning,Integer> result = new HashMap<DefinedMeaning,Integer>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinksBackwardsAll");
			pstmt.setInt(1, this.defined_meaning_id);


			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.put(new DefinedMeaning(rs.getInt("meaning1_mid"), this.dbStatements),new Integer(rs.getInt("relationtype_mid")));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}
		return result;

	}
	/**
	 * Returns all DMs that are linked to this DM backwards incl relation type id in a certain language
	 * @param language_id
	 * @return Map of id and DM
	 */
	public Map<DefinedMeaning,Integer> getDefinedMeaningLinksBackwardsAllLanguage(int language_id) throws OmegaWikiException{

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Map<DefinedMeaning,Integer> result = new HashMap<DefinedMeaning,Integer>();
		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinksBackwardsAllLanguage");
			pstmt.setInt(1, this.defined_meaning_id);
			pstmt.setInt(2, language_id);


			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.put(new DefinedMeaning(rs.getInt("meaning1_mid"), this.dbStatements),new Integer(rs.getInt("relationtype_mid")));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}
		return result;

	}
	/**
	 * Returns all DMs that are linked to this DM via this relation type id
	 * @return Set of id and DM
	 * @parm dmLinkType
	 */
	public Set<DefinedMeaning> getDefinedMeaningLinks(int dmLinkType) throws OmegaWikiException{
		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Set<DefinedMeaning> result = new HashSet<DefinedMeaning>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinks");
			pstmt.setInt(1, this.defined_meaning_id);
			pstmt.setInt(2, dmLinkType);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.add(new DefinedMeaning(rs.getInt("meaning2_mid"), this.dbStatements));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}

		return result;
	}

	/**
	 * Returns all DMs that are linked backwards to this DM via this relation type id
	 * @return Set of id and DM
	 * @parm dmLinkType
	 */
	public Set<DefinedMeaning> getDefinedMeaningLinksBackwards(int dmLinkType) throws OmegaWikiException{
		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		Set<DefinedMeaning> result = new HashSet<DefinedMeaning>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningLinksBackwards");
			pstmt.setInt(1, this.defined_meaning_id);
			pstmt.setInt(2, dmLinkType);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 result.add(new DefinedMeaning(rs.getInt("meaning1_mid"), this.dbStatements));
			}

			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}

		return result;
	}

	/**
	 * @return the dmId
	 */
	public int getDefinedMeaningId() {
		return defined_meaning_id;
	}

	/**
	 * @param dmId the dmtId to set
	 */
	public void setDefinedMeaningId(int dmId) {
		this.defined_meaning_id = dmId;
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
	 * DM To String
	 */
	@Override
	public String toString(){

		String result = String.valueOf(this.defined_meaning_id)+": ";

		try{

			for(TranslatedContent t : this.getGlosses()){
				result += t+"|";
			}
		}
		catch(Exception ex){
			result += ex.getMessage();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + defined_meaning_id;
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
		DefinedMeaning other = (DefinedMeaning) obj;
		if (defined_meaning_id != other.defined_meaning_id) {
			return false;
		}
		return true;
	}


	/**
	 * Returns the annotations for this DM in a certain language
	 * @param language_id
	 * @return Set of annotations
	 */
	@Override
	public Set<Annotation> getAnnotations(int language_id)  throws OmegaWikiException, UnsupportedEncodingException{

		Set <Annotation> sts = new HashSet<Annotation>();
		Iterator<Annotation> it = this.getAnnotations().iterator();
		while (it.hasNext())
		{
			Annotation st = it.next();
			if (st.getLanguage_id() == language_id) {
				sts.add(st);
			}
		}
		return sts;
	}

	/**
	 * Returns the subject (as DM) for this DM
	 * @return Subject of this DM
	 */
	public DefinedMeaning getSubject()  throws OmegaWikiException, UnsupportedEncodingException{

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}
		DefinedMeaning dummy=null;

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningAnnos");
			pstmt.setInt(1, this.defined_meaning_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{

				String name="";
				name= new String(rs.getBytes("type_spelling"),"UTF-8");
				new String(rs.getBytes("value_spelling"),"UTF-8");
				if(name.equals("subject")) {
					dummy=new DefinedMeaning(rs.getInt("id2"),this.dbStatements);
				}

			}


			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}

		return dummy;

}

	/**
	 * Returns the annotations for this DM
	 * @return Set of annotations
	 */
	@Override
	public Set<Annotation> getAnnotations()  throws OmegaWikiException, UnsupportedEncodingException{
		if(this.annotations != null) {
			return annotations;
		}

		if(dbStatements==null) {
			throw new OmegaWikiException("Please initialize DBStatements");
		}

		this.annotations=new HashSet<Annotation>();

		try {

			PreparedStatement pstmt = dbStatements.getPreparedStatement("DefinedMeaningAnnos");
			pstmt.setInt(1, this.defined_meaning_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{

				String name="";
				String value="";
				name= new String(rs.getBytes("type_spelling"),"UTF-8");
				value= new String(rs.getBytes("value_spelling"),"UTF-8");
				Annotation anno=(new Annotation(rs.getInt("value_id"),rs.getInt("language_id"), this,name,value, dbStatements));
				this.annotations.add(anno);
			}
			pstmt.clearParameters();
			pstmt = dbStatements.getPreparedStatement("DefinedMeaningUrlAnnos");
			pstmt.setInt(1, this.defined_meaning_id);
			rs = pstmt.executeQuery();
			while (rs.next())
			{

				String name="";
				String value="";
				name= new String(rs.getBytes("spelling"),"UTF-8");
				value= new String(rs.getBytes("url"),"UTF-8");
				Annotation anno=(new Annotation(rs.getInt("value_id"),-1, this,name,value, dbStatements));
				this.annotations.add(anno);
			}
			pstmt.clearParameters();
			pstmt = dbStatements.getPreparedStatement("DefinedMeaningTextAnnos");
			pstmt.setInt(1, this.defined_meaning_id);
			rs = pstmt.executeQuery();
			while (rs.next())
			{

				String name="";
				String value="";
				name= new String(rs.getBytes("spelling"),"UTF-8");
				value= new String(rs.getBytes("text"),"UTF-8");
				Annotation anno=(new Annotation(rs.getInt("value_id"),-1, this,name,value, dbStatements));
				this.annotations.add(anno);
			}
			pstmt.clearParameters();

			pstmt = dbStatements.getPreparedStatement("DefinedMeaningTranslatedAnnos");
			pstmt.setInt(1, this.defined_meaning_id);
			rs = pstmt.executeQuery();
			while (rs.next())
			{

				String name="";
				String value="";
				name= new String(rs.getBytes("spelling"),"UTF-8");
				value= new String(rs.getBytes("text_text"),"UTF-8");
				Annotation anno=(new Annotation(rs.getInt("value_id"),rs.getInt("language_id"), this,name,value, dbStatements));
				this.annotations.add(anno);
			}
			pstmt.clearParameters();

			rs.close();
		} catch(SQLException ex) {
			throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
		}

		return annotations;

}

	@Override
	public void setAnnotations(Set<Annotation> arg1) {
		this.annotations=arg1;
	}


}
