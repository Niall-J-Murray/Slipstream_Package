
package me.niallmurray.slipstream.dto;

import java.util.List;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "position",
    "positionText",
    "points",
    "wins",
    "Driver",
    "Constructors"
})
@Generated("jsonschema2pojo")
public class DriverStanding {

    @JsonProperty("position")
    public String position;
    @JsonProperty("positionText")
    public String positionText;
    @JsonProperty("points")
    public String points;
    @JsonProperty("wins")
    public String wins;
    @JsonProperty("Driver")
    @Valid
    public Driver driver;
    @JsonProperty("Constructors")
    @Valid
    public List<Constructor> constructors;

}
