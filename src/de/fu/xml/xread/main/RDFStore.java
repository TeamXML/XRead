package de.fu.xml.xread.main;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import de.fu.xml.xread.main.model.RDFTupel;

/**
 * 
 * @author NemoNessuno Needs some useful description
 */
public class RDFStore {

	private static final String DEFAULT_CONTEXT = "http://fu.de/xml/xread/default";
	
	private static RDFStore _instance;
	private Repository _repository;
	private URI _defaultContext;
	

	// private static final String INDEXES = "spoc,posc,cosp";

	private RDFStore() throws RepositoryException {
		// File dataDir = new File(".");

		// _repository = new SailRepository(new NativeStore(dataDir, INDEXES));
		_repository = new SailRepository(new MemoryStore());
		_repository.initialize();
		_defaultContext = _repository.getValueFactory().createURI(DEFAULT_CONTEXT);
	}

	public static RDFStore getInstance() throws RepositoryException {
		if (_instance == null) {
			_instance = new RDFStore();
		}

		return _instance;
	}

	/**
	 * Most basic function to add something to the repository
	 * 
	 * @param subject
	 *            - URI of the subject
	 * @param predicate
	 *            - URI of the predicate
	 * @param object
	 *            - Literal of the object
	 * @param context
	 *            - context for the tuple
	 * @throws RepositoryException
	 */
	public void add(URI subject, URI predicate, String object, URI context)
			throws RepositoryException {

		RepositoryConnection connection = _repository.getConnection();

		try {			
			connection.add(subject, predicate, _repository.getValueFactory().createLiteral(object), context);
		} catch (RepositoryException e) {
			throw e;
		} finally {
			connection.close();
		}
	}
	
	/**
	 * Most basic function to add something to the repository, to 
	 * the DEFAULT_CONTEXT
	 *
	 * @param subject
	 *            - URI of the subject
	 * @param predicate
	 *            - URI of the predicate
	 * @param object
	 *            - Literal of the object	 * 
	 * @throws RepositoryException
	 */
	public void add(URI subject, URI predicate, String object)
			throws RepositoryException {
		add(subject, predicate, object, _defaultContext);
	}

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
