## How to run KINGROUP ##

  1. [First install Java on your computer](http://www.java.com/en/download/index.jsp).
  1. NOTE Feb-2014: Java 7 does not start KINGROUP. For solution see [https://www.java.com/en/download/help/java\_blocked.xml](https://www.java.com/en/download/help/java_blocked.xml), where you need to add "http://kingroup.googlecode.com/" as trusted exception site.
  1. **[Click here to download, install and launch KINGROUP](http://kingroup.googlecode.com/files/kingroup_v2_090501.jnlp)**. **[If the previous link does not launch, try this version (compiled with java 1.5)](http://kingroup.googlecode.com/files/kingroup_v2_090501_1p5.jnlp)**. The program will be downloaded (if a newer version is available) and run on your computer via the [JavaWebStart](http://java.sun.com/javase/technologies/desktop/javawebstart/) technology. You should also be able to run KINGROUP off-line via a shortcut icon on your computer.


---

## Feedback & Requests ##
  * Send your questions to **dmitry.konovalov@jcu.edu.au**
## Help & FAQs ##
  * Dec2010 **Enable [1000 loci](http://kingroup.googlecode.com/files/kingroup_v2_101202.jnlp) Note: CERVUS input has not been updated to accept 1000 loci, convert to the KINSHIP format first.**
  * For Mac users: If you unable to view the screencasts below, try the **Firefox** browser.
  * Mar2010 Download [source code](http://kingroup.googlecode.com/files/kingroup_src_090202_100315.zip)
  * Nov2009 Q: Allele frequencies are imported as 0. A: Check **decimal separator** (point or comma) of your computer/country, e.g. if it is "comma" make sure your frequencies look like 0,123 (not 0.123)
  * May2009 [easy hypothesis testing](http://kingroup.googlecode.com/files/help_090501_HypothesisTesting.swf)
  * Apr2009 [download examples](http://kingroup.googlecode.com/files/help_kingroup_examples.zip)
  * Feb2009 [how to estimate HW allele frequencies from a sample](http://kingroup.googlecode.com/files/help_090218_AlleleFreqsFromSample.swf),  [how to reduce bias due to sample allele frequencies](http://kingroup.googlecode.com/files/help_090220_BiasDueToSampleFreqs.swf)
  * Feb2009 [how to save pairwise r-values for further processing](http://kingroup.googlecode.com/files/help_090212_UniquePairs.swf)
  * Feb2009 [How to assign kinship relationships from likelihoods](http://kingroup.googlecode.com/files/help_090205_KinshipWithLikelihoods.swf) using p-values. [How to check if a sample has enough loci for kinship analysis with likelihoods](http://kingroup.googlecode.com/files/help_090205_TestingSample.swf).
  * Feb2009 [How to make kinship pairs](http://kingroup.googlecode.com/files/help_SimulatedKinshipPairs.swf): also covers relatedness calculation and saving results to a file.
  * Full-sib reconstruction screencasts using Likelihoods and DR: with [Unrelated/ParentOffspring](http://kingroup.googlecode.com/files/help_FSR_example_2008.swf), [Halfsibs](http://kingroup.googlecode.com/files/help_FSR_example_with_HalfSibs.swf).
  * Manual [ch2](http://kingroup.googlecode.com/files/help_KINGROUP_v2_Manual_ch2_080520.pdf) for KINGROUP v2, Manual [ch1](http://kingroup.googlecode.com/files/help_KINGROUP_v2_Manual_ch1_080212.pdf) for KINGROUP v1.
  * [pdf](http://kingroup.googlecode.com/files/help_KinshipManual.pdf) The original manual for the [KINSHIP](http://www.gsoftnet.us/GSoft.html) program (for users without a Mac) from [Goodnight & Queller (1999) Molecular Ecology, 8, 1231–1234](http://kingroup.googlecode.com/files/pdf_KINSHIP.pdf).


---


## Publications ##

KINGROUP Version 2 completes the upgrade/rewrite of KINSHIP plus supports:

  * [pdf](http://kingroup.googlecode.com/files/pdf_KonovalovHeg2008_apbc013a.pdf) Konovalov & Heg (2008). Estimation of Population Allele Frequencies From Small Samples Containing Multiple Generations, Proceedings of 6th Asia-Pacific Bioinformatics Conference. 14-17 January 2008, Kyoto, Japan. Imperial College Press, London, pp321-331.
> > (2013-Feb) Since some papers have started using the KH2008-r-estimator, here is more theory for it [pdf](http://kingroup.googlecode.com/files/rare_061214_noEndNote_for_www.pdf).

  * [pdf](http://kingroup.googlecode.com/files/pdf_KonovalovHeg_2008_MolEcolRes.pdf) Konovalov & Heg (2008). A maximum-likelihood relatedness estimator allowing for negative relatedness values Molecular Ecology Resources , 8, pp256-263.

  * [pdf](http://kingroup.googlecode.com/files/pdf_Konovalov_2006_APBC2006.pdf) Konovalov (2006). Accuracy of Four Heuristics for the Full Sibship Reconstruction Problem in the Presence of Genotype Errors, Tao Jiang, Ueng-Cheng Yang, Phoebe Chen, and Limsoon Wong, editors. Proceedings of 4th Asia-Pacific Bioinformatics Conference, 13-16 February 2006, Taipei, Taiwan. Imperial College Press, London, pp7-16.

  * [pdf](http://kingroup.googlecode.com/files/pdf_Konovalov_2005_Bioinf_MS.pdf) Dmitry A. Konovalov, Nigel Bajema and Bruce Litow (2005) Modified SIMPSON O(n^3) algorithm for the full sibship reconstruction problem. BIOINFORMATICS, 21, 3912-3917.

  * [pdf](http://kingroup.googlecode.com/files/pdf_Konovalov_2005_Bioinf.pdf) [appendix](http://kingroup.googlecode.com/files/pdf_Konovalov_2005_Bioinf_Appendix.pdf) Dmitry A. Konovalov, Bruce Litow and Nigel Bajema (2005) Partition-distance via the assignment problem. BIOINFORMATICS, 21, pp2463-2468.

Version 1 supports: [pdf](http://kingroup.googlecode.com/files/pdf_Konovalov_2004_MolEcologyNotes.pdf) Dmitry A. Konovalov, Clint Manning, and Michael T. Henshaw (2004) KINGROUP: a program for pedigree relationship reconstruction and kin group assignments using genetic markers. Molecular Ecology Notes, 4, 779-782


---

## Dev Tools ##
  * [test dev version](http://kingroup.googlecode.com/files/kingroup_v2_test_dev.jnlp)
  * [test v3](http://code.google.com/p/kingroup3/)
  * [running from it.jcu.edu.au](http://www.it.jcu.edu.au/kingroup/kingroup_v2_090302/kingroup_v2_090302.jnlp)
  * www.kingroup.org forwarding [www.mydomain.com](http://www.mydomain.com/)
  * [NetBeans](http://www.netbeans.org/) [profiling](http://profiler.netbeans.org/l) [subversion](http://www.netbeans.org/kb/60/ide/subversion.html)
  * [subversion](http://subversion.tigris.org/) [TortoiseSVN](http://tortoisesvn.tigris.org/) [RapidSVN](http://rapidsvn.tigris.org/)
  * [visit stats](https://www.google.com/analytics/settings/?et=reset&hl=en)