package com.kahe.seeburger.command.prod;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.kahe.seeburger.data.message.Field;
import com.kahe.seeburger.data.message.Message;
import com.kahe.seeburger.data.message.Record;

public class OutputFactory {

	public final static String getSegments(String template, Map<String, Record> segments) throws Exception {
		StringBuffer sb = new StringBuffer();

		for (Record segment : segments.values()) {
			sb.append(replaceSegmentPlaceholders(template, segment));
			sb.append("\n");
		}
		return sb.toString();
	}

	public final static String getSegmentTree(String template, Map<String, Record> segments) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (Record segment : segments.values()) {
			Integer posCounter = 0;
			Integer posTenStep = 0;
			for (Field component : segment.getFields()) {
				posTenStep = posTenStep + 10;
				posCounter = posTenStep;
				if (component.getFields() != null) {
					for (Field element : component.getFields()) {
						sb.append(replaceSegmentTreePlaceholders(template, segment, element,
								composePath(component.getName(), element.getName(), element.getNumber()), posCounter));
						sb.append("\n");
						posCounter = posCounter + 1;
					}
				} else {
					sb.append(replaceSegmentTreePlaceholders(template, segment, component,
							composePath(component.getName(), null, component.getNumber()), posTenStep));
					sb.append("\n");
				}

			}
		}
		return sb.toString();
	}

	public final static String getTextMessageTree(Message message) throws Exception {

		Map<String, String[]> messageTree = composeMessageTree(null, null, message.getRecords());

		int maxLength = determinateMaxLenght(messageTree.entrySet());

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String[]> entry : messageTree.entrySet()) {
			sb.append(StringUtils.rightPad(entry.getKey(), maxLength) + " " + entry.getValue()[0]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public final static String getXMLSegmentTree(Message message, Record segment, String template) throws Exception {

		Map<String, String[]> messageTree = composeMessageTree(null, null, message.getRecords());

		StringBuffer sb = new StringBuffer();
		Integer posCounter = 0;

		for (Map.Entry<String, String[]> entry : messageTree.entrySet()) {
			if (entry.getValue()[0].startsWith(segment.getName())) {
				if (!entry.getValue()[0].equals(segment.getName())) {
					Field element = new Field();
					element.setName(StringUtils.replace(entry.getValue()[0], segment.getName() + ".", ""));
					element.setMaxLength(0);
					posCounter = posCounter + 10;
					sb.append(replaceSegmentTreePlaceholders(template, segment, element, element.getName(), posCounter));
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}

	public final static String getSqlMessageTree(String template, Message message) throws Exception {
		Map<String, String[]> messageTree = composeMessageTree(null, null, message.getRecords());

		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Map.Entry<String, String[]> entry : messageTree.entrySet()) {
			if (!Boolean.valueOf(entry.getValue()[1])) {
				i = i + 100;
				sb.append(StringUtils
						.replace(StringUtils.replace(StringUtils.replace(template, "${Path}", entry.getValue()[0]),
								"${Sort}", Integer.toString(i)), "${SegmentCode}", entry.getValue()[2]));
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private static Map<String, String[]> composeMessageTree(Map<String, String[]> messageTree, String treeIdx,
			List<Record> records) {
		if (records == null)
			return null;
		if (treeIdx == null)
			treeIdx = "";
		if (messageTree == null)
			messageTree = new TreeMap<String, String[]>();
		int i = 0;
		for (Record record : records) {
			StringBuffer path = new StringBuffer();
			StringBuffer idx = new StringBuffer();

			i++;
			idx.append(treeIdx);
			if (treeIdx != "")
				idx.append(".");
			idx.append(StringUtils.leftPad(Integer.toString(i), 3, "0"));
			if (messageTree.containsKey(treeIdx)) {
				path.append(messageTree.get(treeIdx)[0]);
				path.append(".");
			}
			path.append(record.getName());

			Boolean virtual = true;
			if (record.getVirtual() == null || record.getVirtual() == 0)
				virtual = false;

			messageTree.put(idx.toString(), new String[] { path.toString(), virtual.toString(), record.getName() });
			composeMessageTree(messageTree, idx.toString(), record.getRecords());

		}
		return messageTree;
	}

	private final static String replaceSegmentPlaceholders(String template, Record segment) {
		String stmt = StringUtils.replace(template, "${SegmentCode}", segment.getName());
		if (segment.getDescription() == null) {
			stmt = StringUtils.replace(stmt, "'${Title}'", "null");
			stmt = StringUtils.replace(stmt, "'${Description}'", "null");

		} else {
			stmt = StringUtils.replace(stmt, "${Title}", maskSingleQuote(segment.getDescription().getStandardTitle()));
			stmt = StringUtils.replace(stmt, "${Description}",
					maskSingleQuote(replaceLineFeed(segment.getDescription().getStandardText())));
		}
		return stmt;
	}

	private final static String replaceSegmentTreePlaceholders(String template, Record segment, Field element,
			String path, Integer position) throws Exception {
		String stmt = StringUtils.replace(template, "${SegmentCode}", segment.getName());
		stmt = StringUtils.replace(stmt, "${Path}", path);
		stmt = StringUtils.replace(stmt, "${Qualifier}", mapQualifier(element.getIsQualifier()));
		stmt = StringUtils.replace(stmt, "${Type}", mapType(element.getType(), element.getName()));
		stmt = StringUtils.replace(stmt, "${Position}", position.toString());
		stmt = StringUtils.replace(stmt, "${MaxLength}", element.getMaxLength().toString());
		stmt = StringUtils.replace(stmt, "'${ShapeCode}'", "null");
		if (element.getDescription() == null) {
			stmt = StringUtils.replace(stmt, "'${Title}'", "null");
			stmt = StringUtils.replace(stmt, "'${Description}'", "null");

		} else {
			stmt = StringUtils.replace(stmt, "${Title}", maskSingleQuote(element.getDescription().getStandardTitle()));
			stmt = StringUtils.replace(stmt, "${Description}",
					maskSingleQuote(replaceLineFeed(element.getDescription().getStandardText())));
		}
		return stmt;
	}

//	private final static String replaceMessageTreePlaceholders(String template, Record segment) {
//		String stmt = StringUtils.replace(template, "${SegmentCode}", segment.getName());
//		stmt = StringUtils.replace(stmt, "${Path}", path);
//		stmt = StringUtils.replace(stmt, "${Sort}", sort);
//		return stmt;
//	}
	private final static String mapType(Integer type, String elementCode) throws Exception {
		if (elementCode.equals("2380"))
			return "date";
		if (elementCode.equals("5004"))
			return "num";
		/*
		 * switch (type) { case 5: return "date"; case 6: return "date"; }
		 */
		return "char";
	}

	private final static String mapQualifier(Integer qualifier) throws Exception {
		if (qualifier == null)
			return "0";
		if (qualifier != 1)
			return "0";
		return qualifier.toString();
	}

	private final static String composePath(String parent, String child, Integer counter) {
		StringBuffer sb = new StringBuffer();
		if (child != null) {
			sb.append(parent);
			sb.append(".");
			sb.append(child);
		} else {
			sb.append(parent);
		}
		if (counter != null) {
			sb.append("#");
			sb.append(counter);
		}
		return sb.toString();
	}

	private final static String replaceLineFeed(String string) {
		return string.replaceAll("\\r|\\n", "' +  char(13) + char(10) + '");
	}

	private final static String maskSingleQuote(String string) {
		return string.replaceAll("'", "''");
	}

	private final static int determinateMaxLenght(Set<Map.Entry<String, String[]>> entries) {
		int maxLength = 0;
		for (Map.Entry<String, String[]> entry : entries) {
			if (entry.getKey().length() > maxLength)
				maxLength = entry.getKey().length();
		}
		return maxLength;

	}
}