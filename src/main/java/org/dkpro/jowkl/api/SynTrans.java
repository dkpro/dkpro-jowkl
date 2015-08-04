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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.dkpro.jowkl.db.DatabaseStatements;
import org.dkpro.jowkl.exception.OmegaWikiException;

/**
 * Contains OmegaWiki Syntrans
 * @author matuschek
 *
 */
public class SynTrans implements AnnotatableUnits {
	private int syntransid;
	private Expression syntrans;
	private DefinedMeaning dm;
	private DatabaseStatements dbStatements;
	private boolean identicalMeaning;
	private Set<Annotation> annotations;
	public int getSyntransid() {
		return syntransid;
	}

	public void setSyntransid(int syntransid) {
		this.syntransid = syntransid;
	}

	public Expression getSyntrans() {
		return syntrans;
	}

	public void setSyntrans(Expression syntrans) {
		this.syntrans = syntrans;
	}

	public DefinedMeaning getDefinedMeaning() {
		return dm;
	}

	public void setDefinedMeaning(DefinedMeaning dm) {
		this.dm = dm;
	}

	public boolean isIdenticalMeaning() {
		return identicalMeaning;
	}

	public void setIdenticalMeaning(boolean identicalMeaning) {
		this.identicalMeaning = identicalMeaning;
	}


	/**
	 * @param stId syntransId
	 * @param syntrans Expression item to be modeled
	 * @param dm parent DefinedMeaning
	 * @param im identical meaning flag
	 */
	public SynTrans(int stId, Expression syntrans,DefinedMeaning dm, boolean im){
		this.syntransid = stId;
		this.syntrans= syntrans;
		this.dm=dm;
		this.dbStatements = null;
		this.identicalMeaning=im;
	}

	/**
	 * @param stId syntransId
	 * @param syntrans Expression item to be modeled
	 * @param dm parent DefinedMeaning
	 * @param im identical meaning flag
	 * @param dbStatements DatabaseStatement Object with Prepared Statements
	 */
	public SynTrans(int stId, Expression syntrans, DefinedMeaning dm,boolean im, DatabaseStatements dbStatements){
		this.syntransid = stId;
		this.dbStatements = dbStatements;
		this.dm=dm;
		this.syntrans = syntrans;
		this.identicalMeaning=im;
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
	 * SynTrans To String
	 */
	@Override
	public String toString(){

		String result = String.valueOf(this.syntransid)+": "+ this.getSyntrans()+ ", id_meaning: "+this.identicalMeaning;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + syntransid;
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
		SynTrans other = (SynTrans) obj;
		if (syntransid != other.syntransid) {
			return false;
		}
		return true;
	}


	/**
	 *  Returns the annotations for this SynTrans
	 * @param language_id language of the annotations
	 * @return the set of annotations
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
	 *  Returns the POS for this SynTrans
	 * @return the POS
	 */
	public Annotation getPOS() throws UnsupportedEncodingException, OmegaWikiException
	{

		Set<Annotation> annos = this.getAnnotations();
		for (Annotation anno :annos) {

			if (anno.getName().equals("part of speech")) {
				return anno;
			}
		}
		return null;
	}

	/**
	 *  Returns the POS for this SynTrans, depending on language_id
	 *  @param language_id
	 * @return the POS
	 */
	public Annotation getPOS(int language_id) throws UnsupportedEncodingException, OmegaWikiException
	{

		Set<Annotation> annos = this.getAnnotations(language_id);
		for (Annotation anno :annos) {
			if (anno.getName().equals("part of speech")) {
				return anno;
			}
		}
		return null;
	}
	/**
	 *  Returns the annotations for this SynTrans
	 * @return the set of annotations
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
			ResultSet rs = null;
			try {

				PreparedStatement pstmt = dbStatements.getPreparedStatement("SyntransAnnos");
				pstmt.setInt(1, this.syntransid);
				rs = pstmt.executeQuery();
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
				pstmt = dbStatements.getPreparedStatement("SyntransUrlAnnos");
				pstmt.setInt(1, this.syntransid);
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
				pstmt = dbStatements.getPreparedStatement("SyntransTextAnnos");
				pstmt.setInt(1, this.syntransid);
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

				pstmt = dbStatements.getPreparedStatement("SyntransTranslatedAnnos");
				pstmt.setInt(1, this.syntransid);
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

			} catch(SQLException ex) {

				throw new OmegaWikiException("OmegaWiki SQLException: " + ex.getMessage());
			}finally
			{
				try {
					rs.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			return annotations;

	}

	@Override
	public void setAnnotations(Set<Annotation> annos) {

		this.annotations=annos;

	}



}
