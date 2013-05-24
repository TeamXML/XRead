package de.fu.xml.xread.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryException;

import de.fu.xml.xread.main.RDFStore;
import de.fu.xml.xread.main.model.RDFTupel;

public class RDFStoreTest {

	private RDFStore _store;	

	@Before
	public void setUpBeforeEach() throws Exception {
		_store = RDFStore.getInstance();
	}

	@Test
	public void testAddToEmpty() throws RepositoryException {
		
		//The literal used in this test
		String object = "object"; 
		
		//When I have an empty RDFStore
		List<RDFTupel> result = _store
				.retrieveAllConcerningSubject(null);
		assertTrue(result.isEmpty());		
		
		//And add a tuple
		_store.add(RDF.SUBJECT, RDF.PREDICATE, object);		
		
		//Then I will be able to retrieve that tuple
		List<RDFTupel> check = _store
				.retrieveAllConcerningSubject(RDF.SUBJECT);
		assertEquals(1, result.size());
		assertEquals(object, check.get(0).getObject());
		assertEquals(RDF.SUBJECT, check.get(0).getSubject());
		assertEquals(RDF.PREDICATE, check.get(0).getPredicate());
	
	}
	
}
