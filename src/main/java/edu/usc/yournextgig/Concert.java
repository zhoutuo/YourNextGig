
package edu.usc.yournextgig;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * A concert
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "venue",
    "name",
    "info",
    "id",
    "dtstart",
    "dtend",
    "performers"
})
public class Concert {

    /**
     * A venue
     * 
     */
    @JsonProperty("venue")
    private Venue venue;
    @JsonProperty("name")
    private String name;
    @JsonProperty("info")
    private String info;
    @JsonProperty("id")
    private String id;
    @JsonProperty("dtstart")
    private Date dtstart;
    @JsonProperty("dtend")
    private Date dtend;
    @JsonProperty("performers")
    private List<Performer> performers = new ArrayList<Performer>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A venue
     * 
     */
    @JsonProperty("venue")
    public Venue getVenue() {
        return venue;
    }

    /**
     * A venue
     * 
     */
    @JsonProperty("venue")
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("info")
    public String getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(String info) {
        this.info = info;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("dtstart")
    public Date getDtstart() {
        return dtstart;
    }

    @JsonProperty("dtstart")
    public void setDtstart(Date dtstart) {
        this.dtstart = dtstart;
    }

    @JsonProperty("dtend")
    public Date getDtend() {
        return dtend;
    }

    @JsonProperty("dtend")
    public void setDtend(Date dtend) {
        this.dtend = dtend;
    }

    @JsonProperty("performers")
    public List<Performer> getPerformers() {
        return performers;
    }

    @JsonProperty("performers")
    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
