package com.kashmiera.questionmarking.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.trees.*;
//import Negation.ReferenceModel;

public class NLPUtils {

    private AnnotationPipeline pipeline;

    public NLPUtils() {

    }

    public NLPUtils(String annotatorList) {
        Properties props = new Properties();
        props.setProperty("annotators", annotatorList);
        this.pipeline = new StanfordCoreNLP(props);
    }

    public NLPUtils(Properties properties) {
        this.pipeline = new StanfordCoreNLP(properties);
    }

    public NLPUtils(Properties properties, String host, int port, int threads) {
        this.pipeline = new StanfordCoreNLPClient(properties, host, port, threads);
    }

    public Annotation annotate(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        return annotation;
    }

    public ArrayList<String> getNouns(Annotation annotation) {
        ArrayList<String> nouns = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                // proper nouns and pronouns are considered
                if ("NN".equals(pos) || "NNS".equals(pos) || "NNP".equals(pos) ||
                        "NNPS".equals(pos) || "PRP".equals(pos) || "PRP$".equals(pos)) {
                    nouns.add(word.toLowerCase());
                }
            }
        }

        return nouns;
    }

    public ArrayList<String> getVerbs(Annotation annotation) {
        ArrayList<String> verbs = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                // "be" verbs are considered
                if ("VB".equals(pos) || "VBD".equals(pos) || "VBG".equals(pos) ||
                        "VBN".equals(pos) || "VBP".equals(pos) || "VBZ".equals(pos)) {
                    verbs.add(word.toLowerCase());
                }
            }
        }

        return verbs;
    }

    public ArrayList<String> getVerbsWithOutBe(Annotation annotation) {
        ArrayList<String> verbsWithOutBe = new ArrayList<>();

        ArrayList<String> allVerbs = getVerbs(annotation);

        // TODO: 4/5/18 change variable location
        ArrayList<String> present = new ArrayList<>(Arrays.asList(
                "be", "is", "are", "am", "being", "has", "have", "do", "does"));
        ArrayList<String> past = new ArrayList<>(Arrays.asList(
                "was", "were", "been", "would", "should", "did"));
        ArrayList<String> future = new ArrayList<>(Arrays.asList("will", "shall"));

        for (String verb : allVerbs) {
            String verbLowerCase = verb.toLowerCase();
            if (present.contains(verbLowerCase) || past.contains(verbLowerCase) || future.contains(verbLowerCase)) {
                continue;
            } else {
                verbsWithOutBe.add(verbLowerCase);
            }
        }

        return verbsWithOutBe;
    }


    public ArrayList<String> getAdjectives(Annotation annotation) {
        ArrayList<String> adjectives = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if ("JJ".equals(pos) || "JJR".equals(pos) || "JJS".equals(pos)) {
                    adjectives.add(word.toLowerCase());
                }
            }
        }

        return adjectives;
    }

    public ArrayList<String> getSubjects(Annotation annotation) {
        ArrayList<String> subjects = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class);
//			IndexedWord root = dependencies.getFirstRoot();
//			System.out.printf("root(ROOT-0, %s-%d)%n", root.word(), root.index());
            for (SemanticGraphEdge edge : dependencies.edgeIterable()) {
//				System.out.printf ("%s(%s-%d, %s-%d)%n", edge.getRelation().toString(), edge.getGovernor().word(), edge.getGovernor().index(), edge.getDependent().word(), edge.getDependent().index());
                String relation = edge.getRelation().toString();
                // nominal subject, passive nominal subject, clausal subject, clausal passive subject
                // controlling subjects all considered
                if ("nsubj".equals(relation) || "nsubjpass".equals(relation) ||
                        "nsubj:xsubj".equals(relation) || "nsubjpass:xsubj".equals(relation) ||
                        "csubj".equals(relation) || "csubjpass".equals(relation) ||
                        "csubj:xsubj".equals(relation) || "csubjpass:xsubj".equals(relation)) {
                    subjects.add(edge.getDependent().word().toLowerCase());
                }
            }
        }

        return subjects;
    }

    public ArrayList<String> getObjects(Annotation annotation) {
        ArrayList<String> objects = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class);
            for (SemanticGraphEdge edge : dependencies.edgeIterable()) {
                String relation = edge.getRelation().toString();
                // direct object, indirect object considered
                // prepositional object not considered
                if ("dobj".equals(relation) || "iobj".equals(relation)) {
                    objects.add(edge.getDependent().word().toLowerCase());
                }
            }
        }

        return objects;
    }

    public ArrayList<String> getEntities(Annotation annotation) {
        ArrayList<String> entities = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the NER tag of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                if ("PERSON".equals(ne) || "ORGANIZATION".equals(ne) || "LOCATION".equals(ne) ||
                        "MONEY".equals(ne) || "PERCENT".equals(ne) || "DATE".equals(ne) ||
                        "TIME".equals(ne)) {
                    entities.add(word.toLowerCase());
                }
            }
        }

        return entities;
    }


    /*public ArrayList<String> replaceCoreferences(Annotation annotation) {
        ArrayList<ArrayList<String>> sentenceWords = new ArrayList<>();
        ArrayList<ArrayList<String>> replaceSentenceWords = new ArrayList<>();
        ArrayList<ArrayList<Integer>> unReplacableIndices = new ArrayList<>();
        ArrayList<ReferenceModel> referencesSentence1 = new ArrayList<>();
        ArrayList<ReferenceModel> referencesSentence2 = new ArrayList<>();
        ArrayList<ArrayList<ReferenceModel>> referencedSentences = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            ArrayList<String> tokenizedWords = new ArrayList<>();
            ArrayList<Integer> unreplacableWordIndices = new ArrayList<>();
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                tokenizedWords.add(word);
                // proper nouns and pronouns are considered
                if ("PRP$".equals(pos)) {
                    unreplacableWordIndices.add(token.index());
                }
            }
            sentenceWords.add(tokenizedWords);
            unReplacableIndices.add(unreplacableWordIndices);
        }
        for (CorefChain chain : annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
            String represent = chain.getRepresentativeMention().mentionSpan;
            represent = represent.replaceAll("\\$", "&");
            System.out.println("represent: " + represent);
            for (CorefChain.CorefMention mention : chain.getMentionsInTextualOrder()) {

                int sentNo = mention.sentNum;
                int startIndex = mention.startIndex;
                int endIndex = mention.endIndex;
                String word = mention.mentionSpan;

                //adding details to reference model
                ReferenceModel referenceModel = new ReferenceModel();
                referenceModel.setRepresent(represent);
                referenceModel.setReplacableWord(word);
                referenceModel.setStartIndex(startIndex);
                referenceModel.setEndIndex(endIndex);

                if (sentNo == 1) {
                    referencesSentence1.add(referenceModel);
                } else if (sentNo == 2) {
                    referencesSentence2.add(referenceModel);
                }
            }
        }

        referencedSentences.add(referencesSentence1);
        referencedSentences.add(referencesSentence2);


        for (int j = 0; j < sentenceWords.size(); j++) {
            ArrayList<String> currentSentence = sentenceWords.get(j);
            ArrayList<String> replaceSingleSentence = new ArrayList<>();

            for (int i = 0; i < currentSentence.size(); i++) {
                boolean wordAdded = false;
                int m = i + 1;

                for (ReferenceModel referenceModel : referencedSentences.get(j)) {

                    if (referenceModel.getStartIndex() == m) {
                        if (!unReplacableIndices.get(j).contains(referenceModel.getStartIndex())) {
                            int additionalLenght = referenceModel.getEndIndex() - referenceModel.getStartIndex();
                            i += additionalLenght - 1;
                            replaceSingleSentence.add(referenceModel.getRepresent());
                            wordAdded = true;
                            break;
                        }
                    }
                }
                if (!wordAdded) {
                    replaceSingleSentence.add(currentSentence.get(i));
                }
            }
            replaceSentenceWords.add(replaceSingleSentence);

        }

        String targetSentence = this.referenceReplacedSentence(replaceSentenceWords.get(0));
        String sourceSentence = this.referenceReplacedSentence(replaceSentenceWords.get(1));


        return new ArrayList<>(Arrays.asList(sourceSentence, targetSentence));
    }*/

    public AnnotationPipeline getPipeline() {
        return pipeline;
    }

	/* coreference access
	for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
		System.out.println("---");
		System.out.println("mentions");
		for (Mention m : sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)) {
			System.out.println("\t" + m);
		}
	}
	Map<Integer, CorefChain> graph = annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class);


	for(Map.Entry<Integer, CorefChain> entry : graph) {
		CorefChain c =   entry.getValue();
		println "ClusterId: " + entry.getKey();
		CorefMention cm = c.getRepresentativeMention();
		println "Representative Mention: " + aText.subSequence(cm.startIndex, cm.endIndex);

		List<CorefMention> cms = c.getCorefMentions();
		println  "Mentions:  ";
		cms.each { it ->
				print aText.subSequence(it.startIndex, it.endIndex) + "|";
		}
	}
	*/

    private String referenceReplacedSentence(ArrayList<String> tokenList) {
        Pattern p = Pattern.compile("(\\w+)");
        String referencedSentence = "";

        for (int i = 0; i < tokenList.size(); i++) {
            String token = tokenList.get(i);
            if (i == 0) {
                referencedSentence = referencedSentence + token;
            } else {
                String[] words = token.split(" ");
                Matcher m1 = p.matcher(words[0]);
                if (m1.matches()) {
                    referencedSentence = referencedSentence + " " + token;
                } else {
                    referencedSentence = referencedSentence + token;
                }
            }
        }

        return referencedSentence;
    }

    public Tree constituentParse(Annotation annotation) {

        // get tree
        Tree tree =
                annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
        return tree;
       /* System.out.println(tree);
        Set<Constituent> treeConstituents = tree.constituents(new LabeledScoredConstituentFactory());
        for (Constituent constituent : treeConstituents) {
            if (constituent.label() != null &&
                    (constituent.label().toString().equals("S") )) {
                System.err.println("found constituent: " + constituent.toString());
                System.err.println(tree.getLeaves().subList(constituent.start(), constituent.end() + 1));
            }
        }*/
    }

    public ArrayList<SemanticGraph> getSemanticDependencyGraph(Annotation annotation){
        ArrayList<SemanticGraph> graphs=new ArrayList<>();
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class);
            graphs.add(dependencies);
        }
        return graphs;
    }


}
