package com.embed.solr.read;

import com.embed.solr.provider.SolrProvider;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sozhang on 6/30/17.
 */
public class SolrReadTest {

    private SolrRead solrRead;

    @Before
    public void setUp() throws Exception {
        solrRead = new SolrRead(SolrProvider.getDefaultSolrServer());
    }

    @Test
    public void read() throws Exception {
        SolrParams solrParams = new ModifiableSolrParams()
                .set("q", "*:*");

        SolrDocumentList solrDocumentList = solrRead.read(solrParams);

        assertEquals(10, solrDocumentList.getNumFound());
        List<String> symbols = solrDocumentList.stream().map(d -> d.getFieldValue("Symbol").toString())
                .collect(Collectors.toList());

        assertTrue(symbols.contains("MSFT"));
        assertTrue(symbols.contains("AAPL"));
    }

    @Test
    public void readFilter() throws Exception {
        SolrParams solrParams = new ModifiableSolrParams()
                .set("q", "*:*")
                .set("fq", "Symbol:FB");

        SolrDocumentList solrDocumentList = solrRead.read(solrParams);

        assertEquals(1, solrDocumentList.getNumFound());
        assertEquals("FB", solrDocumentList.stream().findFirst().get().getFieldValue("Symbol").toString());
    }

}
