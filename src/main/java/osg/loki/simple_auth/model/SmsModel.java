

package osg.loki.simple_auth.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"code",
"descr",
"smsid",
"user_smsid",
"colsmsOfSending",
"priceOfSending",
"colAbonentSend"
})
public class SmsModel {

@JsonProperty("code")
private Integer code;
@JsonProperty("descr")
private String descr;
@JsonProperty("smsid")
private String smsid;
@JsonProperty("user_smsid")
private String userSmsid;
@JsonProperty("colsmsOfSending")
private Integer colsmsOfSending;
@JsonProperty("priceOfSending")
private String priceOfSending;
@JsonProperty("colAbonentSend")
private Integer colAbonentSend;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("code")
public Integer getCode() {
return code;
}

@JsonProperty("code")
public void setCode(Integer code) {
this.code = code;
}

@JsonProperty("descr")
public String getDescr() {
return descr;
}

@JsonProperty("descr")
public void setDescr(String descr) {
this.descr = descr;
}

@JsonProperty("smsid")
public String getSmsid() {
return smsid;
}

@JsonProperty("smsid")
public void setSmsid(String smsid) {
this.smsid = smsid;
}

@JsonProperty("user_smsid")
public String getUserSmsid() {
return userSmsid;
}

@JsonProperty("user_smsid")
public void setUserSmsid(String userSmsid) {
this.userSmsid = userSmsid;
}

@JsonProperty("colsmsOfSending")
public Integer getColsmsOfSending() {
return colsmsOfSending;
}

@JsonProperty("colsmsOfSending")
public void setColsmsOfSending(Integer colsmsOfSending) {
this.colsmsOfSending = colsmsOfSending;
}

@JsonProperty("priceOfSending")
public String getPriceOfSending() {
return priceOfSending;
}

@JsonProperty("priceOfSending")
public void setPriceOfSending(String priceOfSending) {
this.priceOfSending = priceOfSending;
}

@JsonProperty("colAbonentSend")
public Integer getColAbonentSend() {
return colAbonentSend;
}

@JsonProperty("colAbonentSend")
public void setColAbonentSend(Integer colAbonentSend) {
this.colAbonentSend = colAbonentSend;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}