
package me.niallmurray.slipstream.dto;

import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "constructorId",
    "url",
    "name",
    "nationality"
})
@Generated("jsonschema2pojo")
public class Constructor {

    @JsonProperty("constructorId")
    public String constructorId;
    @JsonProperty("url")
    public String url;
    @JsonProperty("name")
    public String name;
    @JsonProperty("nationality")
    public String nationality;

}
