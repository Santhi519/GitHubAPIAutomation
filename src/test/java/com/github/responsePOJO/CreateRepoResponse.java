package com.github.responsePOJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRepoResponse {
	 @JsonProperty(value = "name")
	    public String name;
	    @JsonProperty(value = "login")
	    public String login;
	    @JsonProperty(value = "type")
	    public String type;
	    @JsonProperty(value = "status")
	    public String status;
	    @JsonProperty(value = "message")
	    public String message;
	    
}
