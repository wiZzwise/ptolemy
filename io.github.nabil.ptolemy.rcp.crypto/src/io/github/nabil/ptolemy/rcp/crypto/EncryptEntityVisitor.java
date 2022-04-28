package io.github.nabil.ptolemy.rcp.crypto;

public class EncryptEntityVisitor extends EntityVisitor {

	private final CryptoContext cryptoContext;

	public EncryptEntityVisitor(CryptoContext cryptoContext) {
		this.cryptoContext = cryptoContext;
	}

	@Override
	public boolean visit(EncryptableEntity entity) {
		if (!entity.isCrypted()) {
			try {
				entity.setSecret(this.cryptoContext.encrypt(entity.getSecret()));
				entity.setCrypted(true);
				return true;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return false;
	}
}
