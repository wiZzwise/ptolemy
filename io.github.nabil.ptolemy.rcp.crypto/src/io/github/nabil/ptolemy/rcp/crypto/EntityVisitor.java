package io.github.nabil.ptolemy.rcp.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EntityVisitor {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract boolean visit(EncryptableEntity entity);

}
