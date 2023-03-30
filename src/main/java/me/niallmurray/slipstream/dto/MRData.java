
package me.niallmurray.slipstream.dto;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "xmlns",
    "series",
    "url",
    "limit",
    "offset",
    "total",
    "StandingsTable"
})
@Generated("jsonschema2pojo")
public class MRData {

    @JsonProperty("xmlns")
    public String xmlns;
    @JsonProperty("series")
    public String series;
    @JsonProperty("url")
    public String url;
    @JsonProperty("limit")
    public String limit;
    @JsonProperty("offset")
    public String offset;
    @JsonProperty("total")
    public String total;
    @JsonProperty("StandingsTable")
    @Valid
    public StandingsTable standingsTable;

}
