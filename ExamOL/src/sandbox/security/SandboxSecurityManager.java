package sandbox.security;

public class SandboxSecurityManager extends SecurityManager {
	/**
	 * ��ֹ���˷Ƿ��˳������
	 */
	@Override
	public void checkExit(int status) {
		throw new RuntimeException("�Ƿ�����");
	}

}
