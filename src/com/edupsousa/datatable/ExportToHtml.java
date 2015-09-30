package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExportToHtml implements Print{

	@Override
	public String print(LinkedHashMap<String, Integer> columnsTypes,
			ArrayList<DataTableRow> rows) {
		
		String html = "<table>\n";

		if (!columnsTypes.isEmpty()) {

			html += "<tr>";

			for (String name : columnsTypes.keySet()) {
				html += "<td>" + name + "</td>";
			}

			html += "</tr>\n";

		}

		if (rows.size() > 0) {

			DataTableRow row;

			for (int i = 0; i < rows.size(); i++) {

				html += "<tr>";

				row = rows.get(i);

				for (String collumnName : columnsTypes.keySet()) {

					html += "<td>";
					String value = row.getValue(collumnName) + "";

					html += value + "</td>";

				}

				html += "</tr>\n";

			}

		}

		html += "</table>\n";

		return html;
		
	}

	

}
