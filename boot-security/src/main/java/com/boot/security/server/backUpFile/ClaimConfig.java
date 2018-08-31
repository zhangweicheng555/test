package com.boot.security.server.backUpFile;

/**
 *配置文件映射实体类
 */
//@Component
//@ConfigurationProperties(prefix = "app.task.claim")
public class ClaimConfig {

	private int auditSize = 10;
	private int auditTotal = 30;

	private int editSize = 1;
	private int editTotal = 3;

	private int checkSize = 10;
	private int checkTotal = 30;

	private int verifySize = 10;
	private int verifyTotal = 30;

	public int getAuditSize() {
		return auditSize;
	}

	public void setAuditSize(int auditSize) {
		this.auditSize = auditSize;
	}

	public int getAuditTotal() {
		return auditTotal;
	}

	public void setAuditTotal(int auditTotal) {
		this.auditTotal = auditTotal;
	}

	public int getEditSize() {
		return editSize;
	}

	public void setEditSize(int editSize) {
		this.editSize = editSize;
	}

	public int getEditTotal() {
		return editTotal;
	}

	public void setEditTotal(int editTotal) {
		this.editTotal = editTotal;
	}

	public int getCheckSize() {
		return checkSize;
	}

	public void setCheckSize(int checkSize) {
		this.checkSize = checkSize;
	}

	public int getCheckTotal() {
		return checkTotal;
	}

	public void setCheckTotal(int checkTotal) {
		this.checkTotal = checkTotal;
	}

	public int getVerifySize() {
		return verifySize;
	}

	public void setVerifySize(int verifySize) {
		this.verifySize = verifySize;
	}

	public int getVerifyTotal() {
		return verifyTotal;
	}

	public void setVerifyTotal(int verifyTotal) {
		this.verifyTotal = verifyTotal;
	}

	public int size(String role) {
		if ("audit".equalsIgnoreCase(role)) {
			return this.auditSize;
		} else if ("work".equalsIgnoreCase(role)) {
			return this.editSize;
		} else if ("check".equalsIgnoreCase(role)) {
			return this.checkSize;
		} else if ("verify".equalsIgnoreCase(role)) {
			return this.verifySize;
		}
		return 1;
	}

	public int total(String role) {
		if ("audit".equalsIgnoreCase(role)) {
			return this.auditTotal;
		} else if ("work".equalsIgnoreCase(role)) {
			return this.editTotal;
		} else if ("check".equalsIgnoreCase(role)) {
			return this.checkTotal;
		} else if ("verify".equalsIgnoreCase(role)) {
			return this.verifyTotal;
		}
		return 1;
	}

}
