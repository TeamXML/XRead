package de.fu.xml.xread.main.model;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * Basic Model class to represent a RDF Tupel
 * @author NemoNessuno
 *
 */
public class RDFTupel {
	
	private Resource _subject;
	private URI _predicate;
	private Value _object;
	private Resource _context;	
	private URI _sameas;
	
	public RDFTupel(Resource subject, URI predicate, Value object) { 
		_subject = subject;
		_predicate = predicate;
		_object = object;
	}
	
	public RDFTupel(Resource subject, URI predicate, Value object, Resource context) { 
		this(subject, predicate, object);
		_context = context;
	}

	public Resource getSubject() {
		return _subject;
	}



	public void setSubject(Resource subject) {
		_subject = subject;
	}



	public URI getPredicate() {
		return _predicate;
	}



	public void setPredicate(URI predicate) {
		_predicate = predicate;
	}



	public Value getObject() {
		return _object;
	}



	public void setObject(Value object) {
		_object = object;
	}



	public Resource getContext() {
		return _context;
	}



	public void setContext(Resource context) {
		_context = context;
	}
	
}
