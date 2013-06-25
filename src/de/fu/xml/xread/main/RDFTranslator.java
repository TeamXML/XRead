package de.fu.xml.xread.main;

import java.io.ByteArrayOutputStream;
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
import org.apache.any23.writer.RDFXMLWriter;
import org.apache.any23.writer.ReportingTripleHandler;
import org.apache.any23.writer.RepositoryWriter;
import org.apache.any23.writer.TripleHandler;
import org.apache.any23.writer.TripleHandlerException;
import org.apache.commons.httpclient.URIException;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.rdfxml.util.RDFXMLPrettyWriter;

import android.content.Context;
import android.util.Log;
import de.fu.xml.xread.main.any23extension.AndroidMemoryStore;
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
	private static final String TAG = "RDFTranslator";

	private RDFTranslator(Context context, String repositoryFile)
			throws RepositoryException {

		_repository = new SailRepository(new AndroidMemoryStore(context,
				repositoryFile));
		_repository.initialize();
		_runner = new Any23();
		_runner.setHTTPUserAgent("XRead");
	}

	/**
	 * Creates a Repository working with the mainRepository file
	 * 
	 * @return a RDFTranslator instance using the mainRepository file
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public static RDFTranslator getInstance(Context context)
			throws RepositoryException, IOException {
		if (_instance == null) {
			_instance = new RDFTranslator(context, "main_repository");
		}

		return _instance;
	}

	/**
	 * Creates a Repository working with the testRepository file
	 * 
	 * @return a RDFTranslator instance using the testRepository file
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public static RDFTranslator getTestInstance(Context context)
			throws RepositoryException, IOException {
		if (_instance == null) {
			_instance = new RDFTranslator(context, "test_repository");
		}

		return _instance;
	}

	// TODO: Error handling; especially 404
	public String translateResource(String uri) {
		String result = null;
		URI context = _repository.getValueFactory().createURI(uri);

		RepositoryConnection connection = null;

		try {
			extractResourceToRepository(uri);
			connection = _repository.getConnection();
			RepositoryResult<Statement> statements = connection.getStatements(
					null, null, null, true, context);
			result = translateStatementsToRDFXML(statements);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} catch (RDFHandlerException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} catch (ExtractionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} catch (TripleHandlerException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "An exception occured! ", e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "An exception occured! ", e);
			}
		}

		return result;
	}

	private String translateStatementsToRDFXML(
			RepositoryResult<Statement> statements) throws RepositoryException,
			RDFHandlerException, IOException {

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

	private void extractResourceToRepository(String uri) throws IOException,
			RepositoryException, URISyntaxException, URIException,
			ExtractionException, TripleHandlerException {

		HTTPClient client = _runner.getHTTPClient();

		DocumentSource source = new HTTPDocumentSource(client, uri);
		
		RepositoryConnection connection = _repository.getConnection();

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
