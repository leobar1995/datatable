package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataTable {

	public static final int TYPE_INT = 0;
	public static final int TYPE_STRING = 1;

	public static final int FORMAT_CSV = 0;
	public static final int FORMAT_HTML = 1;

	private LinkedHashMap<String, Integer> columnsTypes = new LinkedHashMap<String, Integer>();
	private ArrayList<DataTableRow> rows = new ArrayList<DataTableRow>();

	public int columnsCount() {
		return columnsTypes.size();
	}

	public int rowsCount() {
		return rows.size();
	}

	public void addCollumn(String name, int type) {
		columnsTypes.put(name, type);
	}

	public boolean hasCollumn(String name) {
		return columnsTypes.containsKey(name);
	}

	public DataTableRow createRow() {
		return new DataTableRow(this);
	}

	public void insertRow(DataTableRow row) {
		checkRowCompatibilityAndThrows(row);
		rows.add(row);
	}

	public DataTableRow lastRow() {
		return rows.get(rows.size() - 1);
	}

	public int getCollumnType(String collumn) {
		return columnsTypes.get(collumn);
	}

	private void checkRowCompatibilityAndThrows(DataTableRow row) {
		for (String collumnName : columnsTypes.keySet()) {
			if (row.hasValueFor(collumnName)
					&& !(isValueCompatible(columnsTypes.get(collumnName),
							row.getValue(collumnName)))) {
				throw new ClassCastException("Wrong type for collumn "
						+ collumnName + ".");
			}
		}
	}

	private boolean isValueCompatible(int type, Object value) {
		if (type == this.TYPE_INT && !(value instanceof Integer)) {
			return false;
		} else if (type == this.TYPE_STRING && !(value instanceof String)) {
			return false;
		}
		return true;
	}

	public DataTableRow getRow(int i) {
		return rows.get(i);
	}

	public String export(int format) {
		if (format == DataTable.FORMAT_CSV) {
			Print csv = new ExportToCsv();
			return csv.print(columnsTypes, rows);
		} else if (format == DataTable.FORMAT_HTML) {
			Print html = new ExportToHtml();
			return html.print(columnsTypes, rows);
		}
		return "";
	}

	public void insertRowAt(DataTableRow row, int index) {
		rows.add(index, row);
	}

	public DataTable filterEqual(String collumn, Object value) {

		DataTable dt = new DataTable();
		dt.columnsTypes.putAll(columnsTypes);

		if (rowsCount() > 0) {

			for (int i = 0; i < rowsCount(); i++) {

				DataTableRow row = getRow(i);

				if (!row.getValue(collumn).toString().equals(value.toString())) {
					continue;
				}

				dt.insertRow(row);

			}

		}

		return dt;
	}

	public DataTable sort(int type, String collumn) {

		if (columnsTypes.get(collumn) == TYPE_STRING) {
			throw new ClassCastException("Only Integer columns can be sorted.");
		}

		DataTable dt = new DataTable();
		dt.columnsTypes.putAll(columnsTypes);
		dt.rows.addAll(rows);

		for (int i = dt.rowsCount() - 1; i >= 1; i--) {

			for (int j = 0; j < i; j++) {

				DataTableRow row = dt.getRow(j);
				DataTableRow rowProximo = dt.getRow(j + 1);

				int r = Integer.parseInt(row.getValue(collumn).toString());
				int rp = Integer.parseInt(rowProximo.getValue(collumn)
						.toString());

				if (type == 1) {

					if (r > rp) {
						dt.rows.set(j, rowProximo);
						dt.rows.set(j + 1, row);
					}

				} else {

					if (r < rp) {
						dt.rows.set(j, rowProximo);
						dt.rows.set(j + 1, row);
					}

				}

			}

		}

		return dt;

	}

	public DataTable sortAscending(String collumn) {
		return sort(1, collumn);
	}

	public DataTable sortDescending(String collumn) {
		return sort(2, collumn);
	}

}
