package com.kahe.seeburger.data.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {
	String name;
	Description description;
	Integer min;
	Integer max;
	String regularExpression;
	Integer type;
	Integer minLength;
	Integer maxLength;
	Integer option;
	Integer number;
	Integer isQualifier;
	@XmlElement(name = "field")
	List<Field> fields;
}
