package com.kashmiera.questionmarking.Negation;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Constituent;
import edu.stanford.nlp.trees.LabeledScoredConstituentFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import com.kashmiera.questionmarking.Negation.models.VerbRelation;
import com.kashmiera.questionmarking.utils.NLPUtils;

import java.util.*;

public class ConstituentParser {

	ArrayList<List<Tree>> wordArrays = new ArrayList<>();

	ArrayList<String> indexPoints = new ArrayList<>();

	ArrayList<SemanticGraph> graphs = new ArrayList<>();

	ArrayList<String> innerSentences = new ArrayList<>();

	public ArrayList<String> runConstituentParser(Annotation annotation, NLPUtils nlpUtils) {
		Tree tree = nlpUtils.constituentParse(annotation);
		Set<Constituent> treeConstituents = tree.constituents(new LabeledScoredConstituentFactory());

		for (Constituent constituent : treeConstituents) {
			if (constituent.label() != null &&
					(constituent.label().toString().equals("S"))) {
				//System.err.println("found constituent: " + constituent.toString());
				wordArrays.add(tree.getLeaves().subList(constituent.start(), constituent.end() + 1));
			}
		}
		createSentences();
		//System.out.println(tree.toString());

		return innerSentences;
	}

	private void createSentences() {
		for (List<Tree> wordArray : wordArrays) {
			//            System.out.println(wordArray);
			String sentence = "";
			for (Tree word : wordArray) {
				if (".".equals(word.toString()) || ",".equals(word.toString()) || "'s".equals(word.toString())) {
					sentence = sentence + word.toString();
				} else {
					sentence = sentence + " " + word.toString();
				}
			}
			//            System.out.println(sentence);
			innerSentences.add(sentence);
		}
	}

	public ArrayList<VerbRelation> getVerbRelationships(Annotation annotation, NLPUtils nlpUtils) {

		ArrayList<VerbRelation> verbArrayList = new ArrayList<>();
		graphs = nlpUtils.getSemanticDependencyGraph(annotation);
		Integer count=0;
		for (SemanticGraph graph : graphs) {
			Collection<TypedDependency> typedDependencies = graph.typedDependencies();
			//System.out.println(graph.toList());
			for (TypedDependency dependency : typedDependencies) {

				String depTag = dependency.dep().tag();
				String govTag = dependency.gov().tag();

				if ("VB".equals(depTag) || "VBD".equals(depTag) || "VBG".equals(depTag) ||
						"VBN".equals(depTag) || "VBP".equals(depTag) || "VBZ".equals(depTag)) {

					if (!dependency.dep().lemma().equals("be")) {
						VerbRelation verb = new VerbRelation();
						verb.setDepLemma(dependency.dep().lemma());
						verb.setDepWord(dependency.dep().word());
						verb.setDepTag(dependency.dep().tag());
						verb.setGovLemma(dependency.gov().lemma());
						verb.setGovWord(dependency.gov().word());
						verb.setGovTag(dependency.gov().tag());
						verb.setRelation(dependency.reln().toString());
						verb.setVerbIsDep(true);
						count++;
						verb.setId(count);
						verbArrayList.add(verb);
					}

				} else if ("VB".equals(govTag) || "VBD".equals(govTag) || "VBG".equals(govTag) ||
						"VBN".equals(govTag) || "VBP".equals(govTag) || "VBZ".equals(govTag)) {
					if (!dependency.gov().lemma().equals("be")) {
						VerbRelation verb = new VerbRelation();
						verb.setDepLemma(dependency.dep().lemma());
						verb.setDepWord(dependency.dep().word());
						verb.setDepTag(dependency.dep().tag());
						verb.setGovLemma(dependency.gov().lemma());
						verb.setGovWord(dependency.gov().word());
						verb.setGovTag(dependency.gov().tag());
						verb.setRelation(dependency.reln().toString());
						verb.setVerbIsGov(true);
						count++;
						verb.setId(count);
						verbArrayList.add(verb);
					}
				}

			}
		}

		return verbArrayList;

	}
}
