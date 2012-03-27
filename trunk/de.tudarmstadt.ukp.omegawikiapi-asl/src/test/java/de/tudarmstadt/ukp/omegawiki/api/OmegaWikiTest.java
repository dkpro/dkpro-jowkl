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
package de.tudarmstadt.ukp.omegawiki.api;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import de.tudarmstadt.ukp.omegawiki.db.DatabaseConfiguration;
import de.tudarmstadt.ukp.omegawiki.exception.OmegaWikiException;


public class OmegaWikiTest extends TestCase {

	private OmegaWiki ow;

	@Override
	protected void setUp() throws Exception {
		DatabaseConfiguration dc = new DatabaseConfiguration("bender.ukp.informatik.tu-darmstadt.de",
				"OmegaWiki","com.mysql.jdbc.Driver","mysql", "student", "student", OWLanguage.English);
		ow = new OmegaWiki(dc);
	};

	public void testGetAllExpressions() throws OmegaWikiException {
		System.out.println(ow.getAllExpressions().size());
		assertTrue(ow.getNumberOfDefinedMeanings() > 0);


	}

	public void testGetNumberOfDefinedMeanings() throws OmegaWikiException {
		System.out.println(ow.getNumberOfDefinedMeanings());
		assertTrue(ow.getNumberOfDefinedMeanings() > 0);

		Set<DefinedMeaning> dm = ow.getAllDefinedMeanings();
		assertEquals(ow.getNumberOfDefinedMeanings(), dm.size());

	}
	public void testDictionaryExtraction() throws OmegaWikiException, UnsupportedEncodingException {
		Set<Expression> ex = ow.getExpressionByWord("baby",OWLanguage.English);
		//ow.getAllExpressions(OWLanguage.English);
		for (Expression e : ex)
		{
			{
				DefinedMeaning dm = e.getDefinedMeaning();
				Set<SynTrans> sts = dm.getSynTranses(OWLanguage.German);
				for (SynTrans st : sts)
				{
					System.out.println(e.getSpelling());
					System.out.println("|- "+ st.getSyntrans().getSpelling() );
				}

			}
		}
	}

	public void testGetExpression() throws OmegaWikiException, UnsupportedEncodingException {
		Set<Expression> exp = ow.getExpressionByWord("car",OWLanguage.French);

		Iterator<Expression> eit =exp.iterator();
		while (eit.hasNext())
		{
			Expression e = eit.next();
			System.out.println(e.getExpressionId());
		}
	}

	public void testGetDefinedMeaningByName() throws OmegaWikiException, UnsupportedEncodingException {
		Set<DefinedMeaning> dms= ow.getDefinedMeaningByWord("car",OWLanguage.French);

		Iterator<DefinedMeaning> eit =dms.iterator();
		while (eit.hasNext())
		{
			DefinedMeaning dm = eit.next();
			System.out.println(dm.getDefinedMeaningId());
		}
	}

	public void testGetDefinedMeaning() throws OmegaWikiException, UnsupportedEncodingException {
		DefinedMeaning dm = ow.getDefinedMeaningById(482);
		Iterator<TranslatedContent> glosses = dm.getGlosses(OWLanguage.English).iterator();
		//System.out.println(dm.getSpelling().getSpelling());
	/*System.out.println("---Annotations---");
		Iterator<Annotation> annoIt=dm.getAnnotations().iterator();
		while(annoIt.hasNext())
		{
			Annotation anno = annoIt.next();
			System.out.println(anno);
		}
		System.out.println("---End: Annotations---");
		while (glosses.hasNext())
		{
			TranslatedContent tc = glosses.next();
			System.out.println(tc.getGloss());
		}*/
		Iterator<SynTrans> st = dm.getSynTranses(OWLanguage.English).iterator();
		while (st.hasNext())
		{
			SynTrans syntrans = st.next();
			System.out.println(syntrans);
			System.out.println("---Annotations---");
			Iterator<Annotation> annoIt2=syntrans.getAnnotations().iterator();
			while(annoIt2.hasNext())
			{
				Annotation anno = annoIt2.next();
				System.out.println(anno);
			}
			System.out.println("---End: Annotations---");
		}
/*		Iterator<Entry<Integer, DefinedMeaning>> dmia = dm.getDefinedMeaningLinksAll().entrySet().iterator();
		while (dmia.hasNext())
		{
			Entry<Integer, DefinedMeaning> dm1 = dmia.next();

			System.out.println((ow.getDefinedMeaningById(dm1.getKey()).getSpelling())+": "+(dm1.getValue()).getSpelling());

		}

		dmia = dm.getDefinedMeaningLinksBackwardsAll().entrySet().iterator();
		while (dmia.hasNext())
		{
			Entry<Integer, DefinedMeaning> dm1 = dmia.next();

			System.out.println((ow.getDefinedMeaningById(dm1.getKey()).getSpelling())+": "+(dm1.getValue()).getSpelling());

		}*/

		Iterator<DefinedMeaning> dmi = dm.getCollections().iterator();
		while (dmi.hasNext())
		{
			DefinedMeaning dm1 = dmi.next();
			System.out.println(dm1.getSpelling());

		}

		dmi = dm.getClasses().iterator();
		while (dmi.hasNext())
		{
			DefinedMeaning dm2 = dmi.next();
			System.out.println(dm2.getSpelling());

		}
		dmi = dm.getDefinedMeaningLinks(4).iterator();
		while (dmi.hasNext())
		{
			DefinedMeaning dm3 = dmi.next();
			System.out.println(dm3.getSpelling());

		}
		dmi = dm.getDefinedMeaningLinksBackwards(4).iterator();
		while (dmi.hasNext())
		{
			DefinedMeaning dm4 = dmi.next();
			System.out.println(dm4.getSpelling());

		}

	}
}
