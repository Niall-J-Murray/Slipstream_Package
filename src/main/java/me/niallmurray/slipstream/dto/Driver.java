
package me.niallmurray.slipstream.dto;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "driverId",
    "permanentNumber",
    "code",
    "url",
    "givenName",
    "familyName",
    "dateOfBirth",
    "nationality"
})
@Generated("jsonschema2pojo")
public class Driver {

    @JsonProperty("driverId")
    public String driverId;
    @JsonProperty("permanentNumber")
    public String permanentNumber;
    @JsonProperty("code")
    public String code;
    @JsonProperty("url")
    public String url;
    @JsonProperty("givenName")
    public String givenName;
    @JsonProperty("familyName")
    public String familyName;
    @JsonProperty("dateOfBirth")
    public String dateOfBirth;
    @JsonProperty("nationality")
    public String nationality;

}
