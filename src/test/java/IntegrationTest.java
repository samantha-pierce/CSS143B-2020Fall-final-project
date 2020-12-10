import edu.uwb.css143b2020fall.service.Indexer;
import edu.uwb.css143b2020fall.service.IndexerImpl;
import edu.uwb.css143b2020fall.service.Searcher;
import edu.uwb.css143b2020fall.service.SearcherImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private Indexer indexer;
    private Searcher searcher;

    @Before
    public void setUp() {
        indexer = new IndexerImpl();
        searcher = new SearcherImpl();
    }

    @Test
    public void testIntegration() {
        List<TestCase> cases = getTestCase();
        for (TestCase testCase : cases) {
            List<Integer> actual = searcher.search(
                    testCase.target,
                    indexer.index(testCase.documents)
            );
            assertEquals(testCase.expect, actual);
        }
    }

    private List<TestCase> getTestCase() {
        List<String> documents = Util.getDocumentsForIntTest();

        List<TestCase> testCases = new ArrayList<>(Arrays.asList(
                new TestCase(
                        documents,
                        "",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "hello world",
                        new ArrayList<>(Arrays.asList(0, 1, 6))
                ),
                new TestCase(
                        documents,
                        "llo wor",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "wor",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "hello",
                        new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6))
                ),
                new TestCase(
                        documents,
                        "just world",
                        new ArrayList<>(Arrays.asList(0))
                ),
                new TestCase(
                        documents,
                        "sunday",
                        new ArrayList<>(Arrays.asList(6))
                ),
                new TestCase(
                        documents,
                        "hello world fun",
                        new ArrayList<>(Arrays.asList(6))
                ),
                new TestCase(
                        documents,
                        "world world fun",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "office",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "ryan murphy",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "new macbook",
                        new ArrayList<>(Arrays.asList(7))
                ),
                new TestCase(
                        documents,
                        "is awesome",
                        new ArrayList<>(Arrays.asList(7))
                )
        ));

        return testCases;
    }

    private class TestCase {
        private List<String> documents;
        private String target;
        private List<Integer> expect;

        public TestCase(List<String> documents, String target, List<Integer> expect) {
            this.documents = documents;
            this.target = target;
            this.expect = expect;
        }
    }

    @Test
    public void integrationTest() {
        List<TestCase> searchCases = getSearchCases();
        for (TestCase testCase : searchCases) {
            List<Integer> actual = searcher.search(
                    testCase.target,
                    indexer.index(testCase.documents)
            );
            assertEquals(testCase.expect, actual);
        }
    }

    private List<String> getDocuments() {
        return new ArrayList<>(
                Arrays.asList(
                        "The Sun accounts for 99.86% of the mass in the solar system",
                        "Over one million Earth’s could fit inside the Sun",
                        "The energy created by the Sun’s core is nuclear fusion",
                        "The Sun is almost a perfect sphere",
                        "The Aurora Borealis and Aurora Australis are caused by the interaction of solar winds with Earth’s atmosphere"
                )
        );
    }

    private List<TestCase> getSearchCases() {
        List<String> docs = getDocuments();

        List<TestCase> searchCases = new ArrayList<>(Arrays.asList(
                new TestCase(
                        docs,
                        "the sun",
                        new ArrayList<>(Arrays.asList(0, 1, 3))
                ),
                new TestCase(
                        docs,
                        "solar",
                        new ArrayList<>(Arrays.asList(0, 4))
                ),
                new TestCase(
                        docs,
                        "solar system",
                        new ArrayList<>(Arrays.asList(0))
                ),
                new TestCase(
                        docs,
                        "interaction of solar winds",
                        new ArrayList<>(Arrays.asList(4))
                ),
                new TestCase(
                        docs,
                        "in the solar system",
                        new ArrayList<>(Arrays.asList(0))
                )
        ));

        return searchCases;
    }
}