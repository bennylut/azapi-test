/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.pivot.model.impl;

import bgu.dcr.az.orm.api.FieldMetadata;
import bgu.dcr.az.orm.api.RecordAccessor;
import bgu.dcr.az.orm.impl.FieldMetadataImpl;
import bgu.dcr.az.orm.impl.ObjectArrayRecord;
import bgu.dcr.az.orm.impl.Record;
import bgu.dcr.az.orm.impl.SimpleData;
import bgu.dcr.az.pivot.model.AggregatedField;
import bgu.dcr.az.pivot.model.AggregationFunction;
import bgu.dcr.az.pivot.model.Field;
import bgu.dcr.az.pivot.model.Pivot;
import bgu.dcr.az.pivot.model.TableData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a pivot table according to provided pivot. First all filtered initial
 * rows deleted (according to selected FilterFields). Next columns and rows
 * calculated: -columns labels are all permutations of selected SeriesFields
 * values X selected ValuesFields -rows labels are all permutations of selected
 * AxisFields values Pay attention that the order of selected Series/Axis field
 * matter. Finally the inner data generated by extracting set of values
 * (ValuesFields) and applying to it selected aggregation function.
 *
 * @author Zovadi
 */
public class InMemoryPivotTable extends SimpleData<ObjectArrayRecord> implements TableData {

    private Headers columns;
    private Headers rows;
    private final HeaderExtractor columnsHeaderExtractor;
    private final HeaderExtractor rowsHeaderExtractor;

    /**
     * creates a pivot table (evaluated and stored in memory) according to
     * provided pivot
     *
     * @param pivot
     */
    public InMemoryPivotTable(Pivot pivot) {
        columnsHeaderExtractor = new SimpleHeaderExtractor();
        rowsHeaderExtractor = new SimpleHeaderExtractor();
        generatePivotTableData(pivot);
    }

    @Override
    public Headers getColumnHeaders() {
        return columns;
    }

    @Override
    public Headers getRowHeaders() {
        return rows;
    }

    /**
     * actually performs creation of a pivot table an filling it with values
     * according to provided pivot {
     *
     * @see InMemoryPivotTable}
     * @param pivot
     */
    private void generatePivotTableData(Pivot pivot) {
        // the container for set of values for given fields
        Map<Field, Set<Object>> sets = new HashMap<>();

        // collecting set of all values for a given fields in order 
        // to generate permutations (rows/columns headers)
        for (RecordAccessor r : pivot.getData()) {
            if (!isRestrictedRecord(pivot, r)) {
                extractFieldsValues(pivot.getSelectedSeriesFields(), r, sets);
                extractFieldsValues(pivot.getSelectedAxisFields(), r, sets);
            }
        }

        // generating columns labels
        columns = permutateFieldsValues(pivot.getSelectedSeriesFields(), sets, pivot.getSelectedValuesFields());
        // generating rows labels
        rows = permutateFieldsValues(pivot.getSelectedAxisFields(), sets, FXCollections.emptyObservableList());

        // generates association of each of columns to an integer
        Map<ObjectArray, Integer> lookupColumns = generateLookup(columns);
        
        if (columns.numberOfHeaders() == 1 && columns.getHeader(0).length == 0) {
            return;
        }
        
        // generates association of each of rows to an integer
        Map<ObjectArray, Integer> lookupRows = generateLookup(rows);

        // generates the set of values (for every column X row) in order to aggregate it
        Map<Integer, Collection<Object>> values = extractValues(pivot, lookupColumns, lookupRows);

        fillValues(pivot, values, lookupColumns, lookupRows);

        fillFieldMetadata();
    }

    /**
     * extracts the values (one for each given field) from a single data record
     * and stores it in the set corresponding to the given field
     *
     * @param fields collection of field which values we want to extract
     * @param record data record that contains values for a given fields
     * @param sets map contains the set of values for a given fields. This map
     * will be updated with new values after execution.
     */
    private void extractFieldsValues(Collection<? extends Field> fields, RecordAccessor record, Map<Field, Set<Object>> sets) {
        for (Field f : fields) {
            if (!sets.containsKey(f)) {
                sets.put(f, new HashSet<>());
            }
            sets.get(f).add(f.getValue(record));
        }
    }

    /**
     * Generate all possible permutations of values for a given set of fields.
     * If the addition is not an empty collection its values will be included
     * into the permutations.
     *
     * @param fields a set of fields for which values we want to create
     * permutations
     * @param sets the sets of values (one set for every field). If it does not
     * contains one of fields then the set of values for this field will be
     * empty
     * @param addition additional set of values inserted to permutations if
     * needed
     * @return the array that contains resulted permutations (each record in
     * this array contains a permutation of values). The amount of records in
     * resulted array is MULT_{forall field in fields} (|values(field)|) *
     * |addition|
     */
    private Headers permutateFieldsValues(ObservableList<Field> fields, Map<Field, Set<Object>> sets, ObservableList addition) {
        Map<Field, Object[]> oAxis = new HashMap<>();
        Object[] oAdd = addition.toArray();

        for (Field f : fields) {
            if (!sets.containsKey(f)) {
                sets.put(f, new HashSet<>());
            }
            oAxis.put(f, sets.get(f).toArray());
        }

        int amountOfValues = 1, t = 0;
        int[] factors = new int[fields.size() + (oAdd.length == 0 ? 0 : 1)];

        for (Field f : fields) {
            factors[t++] = amountOfValues;
            amountOfValues *= oAxis.get(f).length;
        }

        if (oAdd.length != 0) {
            factors[t] = amountOfValues;
            amountOfValues *= oAdd.length;
        }

        Object[][] result = new Object[amountOfValues][];

        for (int i = 0; i < result.length; i++) {
            result[i] = new Object[factors.length];
            int j = 0;
            for (Field f : fields) {
                result[i][j] = oAxis.get(f)[(i / factors[j]) % oAxis.get(f).length];
                j++;
            }

            if (oAdd.length != 0) {
                result[i][j] = oAdd[(i / factors[j]) % oAdd.length];
            }
        }

        return new SimpleHeaders(result);
    }

    /**
     * generates association of every record in records with an integer for
     * future fast search
     *
     * @param headers a set of records for which we want to create association
     * @return association between records and integers
     */
    private Map<ObjectArray, Integer> generateLookup(Headers headers) {
        Map<ObjectArray, Integer> res = new HashMap<>();

        for (int i = 0; i < headers.numberOfHeaders(); i++) {
            res.put(new ObjectArray(headers.getHeader(i)), i);
        }

        return res;
    }

    /**
     * extracts set of values for each cell of pivot table. We assume that at
     * least one value field is selected
     *
     * @param pivot contains value fields
     * @param lookupColumns lookup for columns (data to index)
     * @param lookupRows lookup for rows (data to index)
     * @return the set of extracted values for each cell in pivot table
     */
    private Map<Integer, Collection<Object>> extractValues(Pivot pivot, Map<ObjectArray, Integer> lookupColumns, Map<ObjectArray, Integer> lookupRows) {
        Map<Integer, Collection<Object>> res = new HashMap<>();

        for (RecordAccessor r : pivot.getData()) {
            if (isRestrictedRecord(pivot, r)) {
                continue;
            }

            for (AggregatedField v : pivot.getSelectedValuesFields()) {
                int column = getColumnIndex(r, pivot.getSelectedSeriesFields(), v, lookupColumns);
                int row = getRowIndex(r, pivot.getSelectedAxisFields(), lookupRows);
                int index = column * rows.numberOfHeaders() + row;

                if (!res.containsKey(index)) {
                    res.put(index, new LinkedList<>());
                }

                res.get(index).add(((Number) v.getValue(r)).doubleValue());
            }
        }

        return res;
    }

    private int getColumnIndex(RecordAccessor r, Collection<Field> selectedFields, AggregatedField v, Map<ObjectArray, Integer> lookup) {
        Object[] res = new Object[selectedFields.size() + 1];
        int i = 0;

        for (Field f : selectedFields) {
            res[i++] = f.getValue(r);
        }

        res[i] = v;

        return lookup.get(new ObjectArray(res));
    }

    private int getRowIndex(RecordAccessor r, Collection<Field> selectedFields, Map<ObjectArray, Integer> lookup) {
        Object[] res = new Object[selectedFields.size()];
        int i = 0;

        for (Field f : selectedFields) {
            res[i++] = f.getValue(r);
        }

        return lookup.get(new ObjectArray(res));
    }

    /**
     * fills up the pivot table with aggregated values. We assume that at least
     * one value field is selected and it is a last element at each column
     *
     * @param pivot
     * @param values
     * @param lookupColumns
     * @param lookupRows
     */
    private void fillValues(Pivot pivot, Map<Integer, Collection<Object>> values, Map<ObjectArray, Integer> lookupColumns, Map<ObjectArray, Integer> lookupRows) {
        boolean emptyRows = rows.numberOfHeaders() == 1 && rows.getHeader(0).length == 0;
        for (int j = 0; j < rows.numberOfHeaders() && columns.numberOfHeaders() != 0; j++) {
            Object[] data = new Object[columns.numberOfHeaders() + 1];
            data[0] = emptyRows ? "Total" : rowsHeaderExtractor.extract(rows.getHeader(j));
            for (int i = 0; i < columns.numberOfHeaders(); i++) {

                int column = lookupColumns.get(new ObjectArray(columns.getHeader(i)));
                int row = lookupRows.get(new ObjectArray(rows.getHeader(j)));
                int index = column * rows.numberOfHeaders() + row;
                final Collection<Object> value = values.get(index);

                if (value == null) {
                    data[i + 1] = 0;
                } else {
                    final Object[] columnHeader = columns.getHeader(i);
                    final AggregatedField af = (AggregatedField) columnHeader[columnHeader.length - 1];
                    data[i + 1] = ((AggregationFunction) af.aggregationFunctionProperty().get()).aggregate(value);
                }
            }
            getInnerData().add(new ObjectArrayRecord(data));
        }

        if (emptyRows) {
            rows = new SimpleHeaders(new Object[][]{{"Total"}});
        }
    }

    @Override
    public String toString() {
        return "Columns: \n" + joinI(columns) + "\n" + "Rows: \n" + joinI(rows) + "Table: \n" + joinI(getInnerData()) + "\n";
    }

    public String joinI(ArrayList<? extends Record> c) {
        LinkedList<String> subRes = new LinkedList<>();
        c.stream().forEach(i -> subRes.addLast(join(i) + ",\n"));

        return join(subRes);
    }

    public String joinI(Headers c) {
        LinkedList<String> subRes = new LinkedList<>();

        for (Object[] i : c) {
            subRes.addLast(join(i) + ",\n");
        }

        return join(subRes);
    }

    public String join(Object[] c) {
        StringBuilder sb = new StringBuilder("{");
        for (Object s : c) {
            sb.append(s).append(", ");
        }
        if (sb.length() != 1) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.append("}").toString();
    }

    public String join(Iterable c) {
        StringBuilder sb = new StringBuilder("{");
        for (Object s : c) {
            sb.append("" + s).append(", ");
        }
        if (sb.length() != 1) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.append("}").toString();
    }

    private boolean isRestrictedRecord(Pivot pivot, RecordAccessor r) {
        return pivot.getSelectedFilterFields().stream().anyMatch(ff -> ff.getRestrictedValues().contains(ff.getValue(r)));
    }

    private void fillFieldMetadata() {
        FieldMetadata[] meta = new FieldMetadata[columns.numberOfHeaders() + 1];

        meta[0] = new FieldMetadataImpl("Row headers", String.class);

        for (int i = 0; i < columns.numberOfHeaders(); i++) {
            meta[i + 1] = new FieldMetadataImpl(columnsHeaderExtractor.extract(columns.getHeader(i)), Double.class);
        }

        setFields(meta);
    }

    private class ObjectArray {

        Object[] array;

        public ObjectArray(Object[] array) {
            this.array = array;
        }

        public Object[] getArray() {
            return array;
        }

        public void setArray(Object[] array) {
            this.array = array;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Arrays.deepHashCode(this.array);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ObjectArray other = (ObjectArray) obj;
            return Arrays.deepEquals(this.array, other.array);
        }
    }
}
