package io.github.nabil.ptolemy.rcp.crypto;

public class DecryptEntityVisitor extends EntityVisitor {
	private final String pass;
	private final CryptoContext cryptoContext;

	public DecryptEntityVisitor(String pass, CryptoContext cryptoContext) {
		this.pass = pass;
		this.cryptoContext = cryptoContext;
	}

	@Override
	public boolean visit(EncryptableEntity entity) {
		if (entity.isCrypted()) {
			try {
				String value = cryptoContext.decrypt(entity.getSecret(), pass);
				entity.setSecret(value);
				entity.setCrypted(false);
				return true;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return false;
	}
}
