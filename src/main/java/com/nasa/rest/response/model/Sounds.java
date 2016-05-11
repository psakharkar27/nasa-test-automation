package com.nasa.rest.response.model;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Created by prsakharkar on 5/6/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({ "count", "results"})
public class Sounds {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("results")
    private List<Results> results = new ArrayList<Results>();

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("results")
    public List<Results> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Results> results) {
        this.results = results;
    }

}
