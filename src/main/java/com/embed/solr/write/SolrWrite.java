package com.embed.solr.write;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * Created by sozhang on 6/30/17.
 */
public class SolrWrite {

    // depending on your SolrServer implementation
    private SolrServer solrServer;

    public SolrWrite(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * Sample Solr read
     *
     * @param doc
     * @throws IOException
     * @throws SolrServerException
     */
    public void write(SolrInputDocument doc) throws IOException, SolrServerException {
        solrServer.add(doc);
        solrServer.commit();
    }

}
