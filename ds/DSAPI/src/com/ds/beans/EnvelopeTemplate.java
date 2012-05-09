package com.ds.beans;

public class EnvelopeTemplate {

	private String description;
	private String lastModified;
	private String name;
	private int pageCount;
	private String password;
	private boolean shared;
	private String templateId;
	private String uri;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "EnvelopeTemplate [\n "
				+ (description != null ? "description=" + description + ",\n "
						: "")
				+ (lastModified != null ? "lastModified=" + lastModified
						+ ",\n " : "")
				+ (name != null ? "name=" + name + ",\n " : "")
				+ "pageCount="
				+ pageCount
				+ ",\n "
				+ (password != null ? "password=" + password + ",\n " : "")
				+ "shared="
				+ shared
				+ ",\n "
				+ (templateId != null ? "templateId=" + templateId + ",\n "
						: "") + (uri != null ? "uri=" + uri : "") + "]\n";
	}

}
