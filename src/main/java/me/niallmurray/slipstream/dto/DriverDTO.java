
package me.niallmurray.slipstream.dto;

import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class DriverDTO {

    @JsonProperty("driverId")
    public String name;
    @JsonProperty("permanentNumber")
    public String carNumber;
    @JsonProperty("code")
    public String shortName;
    @JsonProperty("url")
    public String wikiPage;
    @JsonProperty("givenName")
    public String firstName;
    @JsonProperty("familyName")
    public String surname;
    @JsonProperty("dateOfBirth")
    public String dateOfBirth;
    @JsonProperty("nationality")
    public String nationality;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + name + '\'' +
                ", permanentNumber='" + carNumber + '\'' +
                ", code='" + shortName + '\'' +
                ", url='" + wikiPage + '\'' +
                ", givenName='" + firstName + '\'' +
                ", familyName='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
