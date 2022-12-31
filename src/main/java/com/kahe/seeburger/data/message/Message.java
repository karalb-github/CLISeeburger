package com.kahe.seeburger.data.message;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

import lombok.Data;

@Data
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
	@XmlAttribute
	String version;
	String name;
	Integer kind;
	@XmlElement(name = "record")
	List<Record> records;

}
