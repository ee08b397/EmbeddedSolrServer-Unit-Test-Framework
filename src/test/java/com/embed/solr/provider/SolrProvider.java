package com.embed.solr.provider;

import com.embed.solr.TestUtils;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * {@link SolrProvider} A thread-safe singleton Solr core provider for Solr testing.
 * <p>
 * Created by sozhang on 6/28/17.
 */
public class SolrProvider {

    private static SolrProvider solrProvider;
    private static EmbeddedSolrServer embeddedSolrServer;

    private SolrProvider() {
        String file = TestUtils.fromCp("index");
        CoreContainer container = new CoreContainer(file);
        container.load();

        embeddedSolrServer = new EmbeddedSolrServer(container, "sampleIndex");
    }

    public static synchronized EmbeddedSolrServer getDefaultSolrServer() {
        List<String> keys = asList("Symbol", "StockExchange", "Created");
        instantiateSolrProvider();
        try {
            cleanASLIndex(embeddedSolrServer);
            loadSampleData(embeddedSolrServer, keys, TestUtils.fromCp("index/sampleIndex/sample/default.csv"));
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }

        return embeddedSolrServer;
    }

    /**
     * Instantiate singleton Solr provider if it's not
     */
    private static void instantiateSolrProvider() {
        if (solrProvider == null)
            solrProvider = new SolrProvider();
    }

    /**
     * Load sample data into Solr core
     *
     * @param embeddedSolrServer
     * @param keys
     * @param filePath
     * @throws IOException
     * @throws SolrServerException
     */
    private static void loadSampleData(EmbeddedSolrServer embeddedSolrServer, List<String> keys, String filePath)
            throws IOException, SolrServerException {

        List<Map<String, Object>> maps = TestUtils.csvToListOfMap(new File(filePath));
        List<SolrInputDocument> docs = maps.stream().map(map -> {
            SolrInputDocument doc = new SolrInputDocument();

            String key = keys.stream().map(map::get).map(Object::toString).collect(joining());
            String id = Hashing.sha256().hashString(key, Charsets.UTF_8).toString();
            doc.addField("id", id);

            map.entrySet().stream().filter(e -> !e.getValue().toString().equals(""))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .forEach(doc::addField);

            return doc;
        }).collect(toList());

        embeddedSolrServer.add(docs);
        embeddedSolrServer.commit();
    }

    /**
     * Clean solr core for the next test
     *
     * @param embeddedSolrServer
     * @throws IOException
     * @throws SolrServerException
     */
    private static void cleanASLIndex(EmbeddedSolrServer embeddedSolrServer) throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.commit();
    }

}
