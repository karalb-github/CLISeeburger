package com.kahe.seeburger.data.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class Record {

	String name;
	Description description;
	Integer min;
	Integer max;
	Integer option;
	Integer virtual;
	@XmlElement(name = "field")
	List<Field> fields;
	@XmlElement(name = "record")
	List<Record> records;
}
