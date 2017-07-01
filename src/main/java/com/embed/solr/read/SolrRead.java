package com.embed.solr.read;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;

/**
 * Created by sozhang on 6/30/17.
 */
public class SolrRead {

    // depending on your SolrServer implementation
    private SolrServer solrServer;

    public SolrRead(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * Sample Solr read
     *
     * @param params
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public SolrDocumentList read(SolrParams params) throws IOException, SolrServerException {
        QueryResponse queryResponse = solrServer.query(params);

        return queryResponse.getResults();
    }

}
