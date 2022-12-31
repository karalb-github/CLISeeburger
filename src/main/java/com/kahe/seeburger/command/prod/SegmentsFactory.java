package com.kahe.seeburger.command.prod;

import java.util.Map;
import java.util.TreeMap;

import com.kahe.seeburger.data.message.Message;
import com.kahe.seeburger.data.message.Record;

public class SegmentsFactory {

	public final static Map<String, Record> getSegments(Message message) {

		Map<String, Record> segments = new TreeMap<String, Record>();

		if (message.getRecords() == null)
			return null;

		for (Record record : message.getRecords()) {
			composeSegments(segments, record);
		}
		return segments;

	}

	private static void composeSegments(Map<String, Record> segments, Record record) {
		if (record == null)
			return;
		if (record.getVirtual() == null || record.getVirtual() == 0) {
			if (!segments.containsKey(record.getName())) {
				segments.put(record.getName(), record);
			}
		}
		if (record.getRecords() != null) {
			for (Record child : record.getRecords()) {
				composeSegments(segments, child);
			}
		}

	}
}
