package com.rmgs.dto;

public class RuleUpdateModel {
	private String key;
	private String tags;
	private String markdown_note;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getMarkdown_note() {
		return markdown_note;
	}
	public void setMarkdown_note(String markdown_note) {
		this.markdown_note = markdown_note;
	}

}
