import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {
    static final String solrBaseUrl = "http://47.106.129.221:9090/solr/collection1";
    @Test
    public void testSolr() throws IOException, SolrServerException {
        SolrServer server = new HttpSolrServer(solrBaseUrl);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","testDoc");
        doc.addField("item_title","XXX");
        doc.addField("price",1000L);
        server.add(doc);
        server.commit();
    }

}
