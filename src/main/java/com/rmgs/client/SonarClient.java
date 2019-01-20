package com.rmgs.client;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.rmgs.dto.CodingRules;
import com.rmgs.dto.DashbaordDto;
import com.rmgs.dto.Details;
import com.rmgs.dto.IssuesDto;
import com.rmgs.dto.Measures;
import com.rmgs.dto.Profile;
import com.rmgs.dto.ProfileAction;
import com.rmgs.dto.ProfileCopy;
import com.rmgs.dto.QualityProfiles;
import com.rmgs.dto.Rule;
import com.rmgs.dto.RuleDetails;
import com.rmgs.dto.RuleUpdateModel;
import com.rmgs.exceptions.DuplicatedRecordException;
import com.rmgs.model.Project;

@RestController
public class SonarClient {

	@Value("${api.http.host.sonar}")
	private String sonarUrl;

	public ResponseEntity<DashbaordDto> getProjects(long favourite) {
		ResponseEntity<DashbaordDto> responseEntity = null;
		String queryParam = "api/components/search_projects?ps:50&facets=reliability_rating,security_rating,sqale_rating,coverage,duplicated_lines_density,ncloc,alert_status,languages,tags&f=analysisDate,leakPeriodDate";
		if (favourite == 1) {
			queryParam += "&filter=isFavorite";
		}
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, DashbaordDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	public ResponseEntity<Measures> getMeasures(String projectKeys) {
		ResponseEntity<Measures> responseEntity = null;
		String queryParam = "api/measures/search?metricKeys=alert_status,bugs,reliability_rating,vulnerabilities,security_rating,code_smells,sqale_rating,duplicated_lines_density,coverage,ncloc,ncloc_language_distribution&projectKeys=";
		if (projectKeys != null) {
			queryParam += projectKeys;
		}
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, Measures.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	public CodingRules getRules(int page) {
		ResponseEntity<CodingRules> responseEntity = null;
		String queryParam = "api/rules/search?ps=500&facets=types,languages,tags,repositories,severities,statuses&f=name,lang,langName,sysTags,tags,status,severity,isTemplate,templateKey&s=name&asc=true&p="
				+ page;

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, CodingRules.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity.getBody();

	}

	public CodingRules getProfileRules(int page, String active, String profile) {
		ResponseEntity<CodingRules> responseEntity = null;
		String queryParam = "api/rules/search?ps=500&s=name&asc=true&p=" + page + "&qprofile=" + profile
				+ "&activation=" + active;

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, CodingRules.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity.getBody();

	}

	public Rule updateRule(Details rule) {
		ResponseEntity<Rule> responseEntity = null;
		String queryParam = "api/rules/update?key=" + rule.getKey() + "&markdown_note=" + rule.getMdNote() + "";
		if (rule.getSysTags() != null) {
			queryParam += "&tags=" + StringUtils.join(rule.getTags(), ',') + "";
		}

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");

			HttpEntity<RuleUpdateModel> entity = new HttpEntity<RuleUpdateModel>(null, headers);
			responseEntity = restTemplate.postForEntity(url, entity, Rule.class);
			return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResponseEntity<Details> getRuleDetails(String ruleKey) {
		ResponseEntity<RuleDetails> responseEntity = null;
		String queryParam = "api/rules/show?actives=true&key=" + ruleKey;

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, RuleDetails.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Details>(responseEntity.getBody().getRule(), HttpStatus.OK);

	}

	public ResponseEntity<IssuesDto> getIssues() {
		ResponseEntity<IssuesDto> responseEntity = null;
		String queryParam = "api/issues/search?s=FILE_LINE&resolved=false&ps=500&facets=severities,types,statuses,rules,tags,projectUuids,resolutions,assignees,authors,languages&additionalFields=_all";

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, IssuesDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	//////
	public QualityProfiles getQualityProfiles() {
		ResponseEntity<QualityProfiles> responseEntity = null;
		String queryParam = "api/qualityprofiles/search";

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			responseEntity = restTemplate.getForEntity(url, QualityProfiles.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity.getBody();

	}

	public void deleteProfile(String key) {
		String queryParam = "api/qualityprofiles/delete?profileKey=" + key;

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			HttpEntity<ProfileAction> entity = new HttpEntity<ProfileAction>(null, headers);
			restTemplate.postForEntity(url, entity, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Profile copyProfile(ProfileCopy profileCopy) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Profile> responseEntity = null;
		try {
			String url = sonarUrl + "api/qualityprofiles/copy";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("fromKey", profileCopy.getFromKey());
			map.add("toName", profileCopy.getToName());

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);

			responseEntity = restTemplate.postForEntity(url, request, Profile.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}

	public Profile updateProfileName(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Profile> responseEntity = null;
		try {
			String url = sonarUrl + "api/qualityprofiles/rename";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("key", profile.getKey());
			map.add("name", profile.getName());

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);

			responseEntity = restTemplate.postForEntity(url, request, Profile.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw new DuplicatedRecordException();
		}
		return responseEntity.getBody();

	}

	public void activateProfileRules(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + "api/qualityprofiles/activate_rule";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			for (Map.Entry<String, String> entry : profile.getActiveMap().entrySet()) {
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("severity", entry.getValue());
				map.add("profile_key", profile.getKey());
				map.add("rule_key", entry.getKey());
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
						headers);

				restTemplate.postForEntity(url, request, Profile.class);
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void deactivateProfileRules(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + "api/qualityprofiles/deactivate_rule";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			for (Map.Entry<String, String> entry : profile.getActiveMap().entrySet()) {
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("profile_key", profile.getKey());
				map.add("rule_key", entry.getKey());
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
						headers);

				restTemplate.postForEntity(url, request, Profile.class);
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public Profile createProfile(Profile profile) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Profile> responseEntity = null;
		try {
			String url = sonarUrl + "api/qualityprofiles/create";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("key", profile.getKey());
			map.add("name", profile.getName());
			map.add("language", profile.getLanguage());

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);

			responseEntity = restTemplate.postForEntity(url, request, Profile.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			throw e;
		}
		return responseEntity.getBody();

	}
	
	
	/**
	 * Projects
	 */
	
	public Project createProject(Project project) {
		ResponseEntity<Project> responseEntity = null;
		String queryParam = "api/projects/create?organization=default-organization&visibility=public&name=" + project.getName() + "&project=" + project.getProjKey() ;
		

		RestTemplate restTemplate = new RestTemplate();
		try {
			String url = sonarUrl + queryParam;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");

			HttpEntity<RuleUpdateModel> entity = new HttpEntity<RuleUpdateModel>(null, headers);
			responseEntity = restTemplate.postForEntity(url, entity, Project.class);
			return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return project;

	}
}
