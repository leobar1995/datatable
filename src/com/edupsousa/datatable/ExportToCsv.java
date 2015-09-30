package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExportToCsv implements Print{

	@Override
	public String print(LinkedHashMap<String, Integer> columnsTypes,
			ArrayList<DataTableRow> rows) {
				
		DataTableRow row;
		String output = "";

		for (String collumnName : columnsTypes.keySet()) {
			output += collumnName + ";";
		}
		
		output += "\n";
		
		for (int i = 0; i < rows.size(); i++) {
			
			row = rows.get(i);
			
			for (String collumnName : columnsTypes.keySet()) {
				
				if (columnsTypes.get(collumnName) == DataTable.TYPE_STRING) {
					output += "\"" + row.getValue(collumnName) + "\";";
				} else {
					output += row.getValue(collumnName) + ";";
				}
				
			}
			
			output += "\n";
		}

		return output;
		
	}

	

}