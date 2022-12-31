package com.kahe.seeburger;

import javax.xml.transform.Source;
import javax.xml.transform.Result;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class XMLMarshaller {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<?> clazz, final Source source) {

		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(clazz);
		return (T) marshaller.unmarshal(source);

	}


	@SuppressWarnings("null")
	public static String marshall(final Object object) {

		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		Result result = null;
		marshaller.marshal(object, result);
		return result.toString();

	}
}