package com.rmgs.dto;

import java.util.HashMap;
import java.util.Map;

public class Profile {
	private String key;
	private String name;
	private String language;
	private String languageName;
	private boolean isInherited;
	private boolean isDefault;
	private long activeRuleCount;
	private long activeDeprecatedRuleCount;
	private String rulesUpdatedAt;
	private String organization;
	private boolean isBuiltIn;

	
	private Map<String,String> activeMap=  new HashMap<>();
	private Map<String,String> inactiveMap=  new HashMap<>();
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	public boolean getIsInherited() {
		return isInherited;
	}
	public void setInherited(boolean isInherited) {
		this.isInherited = isInherited;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public long getActiveRuleCount() {
		return activeRuleCount;
	}
	public void setActiveRuleCount(long activeRuleCount) {
		this.activeRuleCount = activeRuleCount;
	}
	public long getActiveDeprecatedRuleCount() {
		return activeDeprecatedRuleCount;
	}
	public void setActiveDeprecatedRuleCount(long activeDeprecatedRuleCount) {
		this.activeDeprecatedRuleCount = activeDeprecatedRuleCount;
	}
	public String getRulesUpdatedAt() {
		return rulesUpdatedAt;
	}
	public void setRulesUpdatedAt(String rulesUpdatedAt) {
		this.rulesUpdatedAt = rulesUpdatedAt;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public boolean getIsBuiltIn() {
		return isBuiltIn;
	}
	public void setBuiltIn(boolean isBuiltIn) {
		this.isBuiltIn = isBuiltIn;
	}
	public Map<String, String> getActiveMap() {
		return activeMap;
	}
	public void setActiveMap(Map<String, String> activeMap) {
		this.activeMap = activeMap;
	}
	public Map<String, String> getInactiveMap() {
		return inactiveMap;
	}
	public void setInactiveMap(Map<String, String> inactiveMap) {
		this.inactiveMap = inactiveMap;
	}
	
}
