package de.fu.xml.xread.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.any23.Any23;
import org.apache.any23.extractor.ExtractionException;
import org.apache.any23.filter.IgnoreAccidentalRDFa;
import org.apache.any23.http.HTTPClient;
import org.apache.any23.source.DocumentSource;
import org.apache.any23.source.HTTPDocumentSource;
import org.apache.any23.writer.ReportingTripleHandler;
import org.apache.any23.writer.RepositoryWriter;
import org.apache.any23.writer.TripleHandler;
import org.apache.any23.writer.TripleHandlerException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.rdfxml.util.RDFXMLPrettyWriter;
import org.openrdf.sail.memory.MemoryStore;

import de.fu.xml.xread.main.model.RDFTupel;

/**
 * Encapsulates the various translators and the RDF repository. Translates a
 * given resource. Saves the RDF statements in the repository and returns the
 * result in RDFXML.
 * 
 * @author NemoNessuno
 */
public class RDFTranslator {

	private static RDFTranslator _instance;
	private Repository _repository;
	private Any23 _runner;

	private RDFTranslator(String repositoryFile) throws RepositoryException {
		File dataDir = new File(repositoryFile);

		_repository = new SailRepository(new MemoryStore(dataDir));
		_repository.initialize();
		_runner = new Any23();
	}

	/**
	 * Creates a Repository working with the mainRepository file
	 * 
	 * @return a RDFTranslator instance using the mainRepository file
	 * @throws RepositoryException
	 */
	public static RDFTranslator getInstance() throws RepositoryException {
		if (_instance == null) {
			_instance = new RDFTranslator("mainRepository");
		}

		return _instance;
	}

	/**
	 * Creates a Repository working with the testRepository file
	 * 
	 * @return a RDFTranslator instance using the testRepository file
	 * @throws RepositoryException
	 */
	public static RDFTranslator getTestInstance() throws RepositoryException {
		if (_instance == null) {
			_instance = new RDFTranslator("testRepository");
		}

		return _instance;
	}

	// TODO: Error handling; especially 404
	public String translateResource(String uri) {
		String result = "";
		URI context = _repository.getValueFactory().createURI(uri);
		
		try {
			extractResourceToRepository(uri);
			result = translateStatementsToRDFXML(getStatementsForContext(context));
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExtractionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TripleHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String translateStatementsToRDFXML(
			RepositoryResult<Statement> statements) throws RepositoryException, RDFHandlerException, IOException {
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		RDFXMLPrettyWriter writer = new RDFXMLPrettyWriter(stream);				

		writer.startRDF();

		while (statements.hasNext()) {
			Statement statement = statements.next();
			writer.handleStatement(statement);
		}

		writer.endRDF();
		writer.flush();
		writer.close();
		
		return stream.toString("UTF-8");
		
	}

	private RepositoryResult<Statement> getStatementsForContext(URI context) throws RepositoryException {
		RepositoryResult<Statement> result;
		
		RepositoryConnection conn = _repository.getConnection();
		
		 result = conn.getStatements(null, null, null, true, context);
		 
		 conn.close();
		 return result;
	}

	private void extractResourceToRepository(String uri) throws IOException,
			RepositoryException, URISyntaxException, URIException,
			ExtractionException, TripleHandlerException {
		HTTPClient client = _runner.getHTTPClient();
		RepositoryConnection connection = _repository.getConnection();

		DocumentSource source = new HTTPDocumentSource(client,
				URIUtil.encodeQuery(uri));

		TripleHandler connectionHandler = new RepositoryWriter(connection);
		TripleHandler handler = new ReportingTripleHandler(
				new IgnoreAccidentalRDFa(connectionHandler, true));

		_runner.extract(source, handler);

		handler.close();
		connection.close();
	}

	// TODO: dunno if this is still useful!
	public List<RDFTupel> retrieveAllConcerningSubject(URI subject)
			throws RepositoryException {

		List<RDFTupel> result = new ArrayList<RDFTupel>();

		RepositoryConnection connection = _repository.getConnection();
		RepositoryResult<Statement> statements = null;

		try {
			statements = connection.getStatements(subject, null, null, true);

			while (statements.hasNext()) {
				Statement statement = statements.next();
				result.add(new RDFTupel(statement.getSubject(), statement
						.getPredicate(), statement.getObject(), statement
						.getContext()));
			}

		} catch (RepositoryException e) {
			throw e;
		} finally {
			statements.close();
			connection.close();
		}

		return result;
	}
}
