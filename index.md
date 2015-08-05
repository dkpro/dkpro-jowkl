---
#
# Use the widgets beneath and the content will be
# inserted automagically in the webpage. To make
# this work, you have to use › layout: frontpage
#
layout: frontpage
title: "DKPro JOWKL"
#header:
#	title: DKPro Core
#   image_fullwidth: "header_unsplash_12.jpg"
header-1:
    title: DKPro JOWKL (Java OmegaWiki Library)
    text: A free, Java-based application programming interface that allows to access all information in the free,
multi-lingual online dictionary [OmegaWiki][1]. Core features: fast and efficient access to OmegaWiki, direct access to OmegaWiki database dumps, no preprocessing necessary, language independent.

---


Publications and Citation Information
-------------------------------------

DKPro JOWKL has been developed as part of the [UBY project][2]. You can read more 
about JOWKL and UBY in our scientific articles:

> Michael Matuschek, Christian M. Meyer, and Iryna Gurevych: Multilingual 
  Knowledge in Aligned Wiktionary and Omega­Wiki for Translation Applications, 
  Translation: Computation, Corpora, Cognition: Special Issue on “Language 
  Technology for a Multilingual Europe” (TC3), Vol. 3(1): 87–118, June 2013.
  [(download)][3]
> Iryna Gurevych, Judith Eckle-Kohler, Silvana Hartmann, Michael Matuschek, 
  Christian M. Meyer, and Christian Wirth: UBY – A Large-Scale Unified 
  Lexical-Semantic Resource Based on LMF, in: Proceedings of the 13th 
  Conference of the European Chapter of the Association for Computational 
  Linguistics (EACL), p. 580–590, April 2012. Avignon, France.
  [(download)][4]  


License and Availability
------------------------

The latest version of DKPro JOWKL is available via Maven Central. 
If you use Maven as your build tool, then you can add DKPro JOWKL 
as a dependency in your pom.xml file:

<dependency>
   <groupId>org.dkpro.jowkl</groupId>
   <artifactId>dkpro-jowkl</artifactId>
   <version>1.0.0</version>
</dependency>

DKPro JOWKL is available as open source software under the 
[Apache License 2.0 (ASL)][5]. The software thus comes "as is" without any 
warranty (see license text for more details).


About DKPro JOWKL
-----------------

Prior to being available as open source software, DKPro JOWKL has been 
a research project at the Ubiquitous Knowledge Processing (UKP) Lab of 
the Technische Universität Darmstadt, Germany. The following people have 
mainly contributed to this project (in alphabetical order):

* Richard Eckart de Castilho
* Iryna Gurevych
* Zijad Maksuti
* Michael Matuschek
* Christian M. Meyer


[1]: http://www.omegawiki.org/
[2]: http://www.ukp.informatik.tu-darmstadt.de/uby/
[3]: http://www.t-c3.org/index.php/t-c3/article/view/20
[4]: http://www.aclweb.org/anthology/E/E12/E12-1059.pdf
[5]: http://www.apache.org/licenses/LICENSE-2.0
