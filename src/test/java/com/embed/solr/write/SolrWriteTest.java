package com.embed.solr.write;

import com.embed.solr.provider.SolrProvider;
import com.embed.solr.read.SolrRead;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sozhang on 6/30/17.
 */
public class SolrWriteTest {

    private SolrWrite solrWrite;
    private SolrRead solrRead;

    @Before
    public void setUp() throws Exception {
        EmbeddedSolrServer defaultSolrServer = SolrProvider.getDefaultSolrServer();
        solrWrite = new SolrWrite(defaultSolrServer);
        solrRead = new SolrRead(defaultSolrServer);
    }

    @Test
    public void write() throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("Symbol", "IPOCompany");
        doc.addField("StockExchange", "somename");
        doc.addField("Created", "someday");
        doc.addField("id", "IPOCompany-id");

        solrWrite.write(doc);

        SolrParams solrParams = new ModifiableSolrParams()
                .set("q", "*:*");

        SolrDocumentList solrDocumentList = solrRead.read(solrParams);

        assertEquals(11, solrDocumentList.getNumFound());
    }

}