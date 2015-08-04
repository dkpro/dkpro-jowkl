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

import java.util.HashMap;

/**
 * Holds DefinedMeaning relation constants, that are used in OmegaWiki DataBase
 * @author matuschek
 *
 */
public class DefinedMeaningLinkType {

	static HashMap<Integer, String> linkNames = new HashMap<Integer, String>();
	public static final int broader_terms=	4;
	public static final int related_terms=	6;
	public static final int is_part_of_theme=	3;
	public static final int narrower_terms	=5;
	public static final int parent=	545136;
	public static final int child	=545139;
	public static final int is_spoken_in=	725316;
	public static final int capital=	6873;
	public static final int borders_on	=726586;
	public static final int flows_through=	726650;
	public static final int flows_into	=726670;
	public static final int ligt_aan_de	=726890;
	public static final int is_written_in=	728048;
	public static final int is_expressed_by=	728659;
	public static final int currency	=347472;
	public static final int official_language=	476516;
	public static final int demonym=	733003;
	public static final int edible=	7784;
	public static final int edible_part	=734712;
	public static final int antonym=	7574;
	public static final int operates_on=	741504;
	public static final int was_formerly_known_as =	744353;
	public static final int industry=	1922;
	public static final int active_in=	744605;
	public static final int is_administered_by=	750683;
	public static final int ingredient=	479058;
	public static final int works_in_a	=754150;
	public static final int located_in	=755407;
	public static final int is_member_of=	760699;
	public static final int wordt_gevolgd_door=	764419;
	public static final int is_played_by=	764518;
	public static final int partners_with=	764944;
	public static final int jouer=	485132;
	public static final int unit_of=	767120;
	public static final int família	=8152;
	public static final int is_practiced_by_a=	789884;
	public static final int sex	=346514;
	public static final int female=	6392;
	public static final int male	=6395;
	public static final int juvenile	=506365;
	public static final int is_a_breed_of=	805610;
	public static final int fedele	=699835;
	public static final int is_an_allotope_of	=826342;
	public static final int antideeltje	=367908;
	public static final int phylum=	799635;
	public static final int order=	797936;
	public static final int Class=	797986;
	public static final int is_made_of=	862691;
	public static final int domain_biology =	885000;
	public static final int kingdom=	801379;
	public static final int family =	347856;
	public static final int etnia=	636110;
	public static final int Singular=	424630;
	public static final int is_located_in_the =	974957;
	public static final int plant_biology	=2836;
	public static final int moon=	5729;
	public static final int is_an_ingredient_of =	1043295;
	public static final int genus=	715773;
	public static final int former_name =	1049622;
	public static final int orbits_around=	1071204;
	public static final int nfinito=	512433;
	public static final int holonym=	375078;
	public static final int meronym=	375074;
	public static final int constellation=	688481;
	public static final int country	=5671;
	public static final int county	=782;
	public static final int idiom	=506438;
	public static final int species	=3580;
	public static String getName(int i)
	{
		linkNames.put(4, "broader_terms");
		linkNames.put(6, "related_terms");
		linkNames.put(3, "is_part_of_theme");
		linkNames.put(5, "narrower_terms");
		linkNames.put(545136, "parent");
		linkNames.put(545139, "child");
		linkNames.put(725316, "is_spoken_in");
		linkNames.put(6873, "capital");
		linkNames.put(726586, "borders_on");
		linkNames.put(726650, "flows_through");
		linkNames.put(726670, "flows_into");
		linkNames.put(726890, "ligt_aan_de");
		linkNames.put(728048, "is_written_in");
		linkNames.put(728659, "is_expressed_by");
		linkNames.put(347472, "currency");
		linkNames.put(476516, "official_language");
		linkNames.put(733003, "demonym");
		linkNames.put(7784, "edible");
		linkNames.put(734712, "edible_part");
		linkNames.put(7574, "antonym");
		linkNames.put(741504, "operates_on");
		linkNames.put(744353, "was_formerly_known_as");
		linkNames.put(1922, "industry");
		linkNames.put(744605, "active_in");
		linkNames.put(750683, "is_administered_by");
		linkNames.put(479058, "ingredient");
		linkNames.put(754150, "works_in_a");
		linkNames.put(755407, "located_in");
		linkNames.put(760699, "is_member_of");
		linkNames.put(764419, "wordt_gevolgd_door");
		linkNames.put(764518, "is_played_by");
		linkNames.put(764944, "partners_with");
		linkNames.put(485132, "jouer");
		linkNames.put(767120, "unit_of");
		linkNames.put(8152, "família");
		linkNames.put(789884, "is_practiced_by_a");
		linkNames.put(346514, "sex");
		linkNames.put(6392, "female");
		linkNames.put(6395, "male");
		linkNames.put(506365, "juvenile");
		linkNames.put(805610, "is_a_breed_of");
		linkNames.put(699835, "fedele");
		linkNames.put(826342, "is_an_allotope_of");
		linkNames.put(367908, "antideeltje");
		linkNames.put(799635, "phylum");
		linkNames.put(797936, "order");
		linkNames.put(797986, "Class");
		linkNames.put(862691, "is_made_of");
		linkNames.put(885000, "domain_biology");
		linkNames.put(801379, "kingdom");
		linkNames.put(347856, "family");
		linkNames.put(636110, "etnia");
		linkNames.put(424630, "Singular");
		linkNames.put(974957, "is_located_in_the");
		linkNames.put(2836, "plant_biology");
		linkNames.put(5729, "moon");
		linkNames.put(1043295, "is_an_ingredient_of");
		linkNames.put(715773, "genus");
		linkNames.put(1049622, "former_name");
		linkNames.put(1071204, "orbits_around");
		linkNames.put(512433, "nfinito");
		linkNames.put(375078, "holonym");
		linkNames.put(375074, "meronym");
		linkNames.put(688481, "constellation");
		linkNames.put(5671, "country");
		linkNames.put(782, "county");
		linkNames.put(506438, "idiom");
		linkNames.put(3580, "species");
		return linkNames.get(i);
	}
}
