package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface Print {

	public String print(LinkedHashMap<String, Integer> columnsTypes, ArrayList<DataTableRow> rows);
	
	
}
