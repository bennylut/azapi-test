/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.orm.impl;

import bgu.dcr.az.orm.api.Data;
import bgu.dcr.az.orm.api.FieldMetadata;
import bgu.dcr.az.orm.api.RecordAccessor;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author User
 */
public class SimpleData implements Data {

    private final ArrayList<Object[]> data;
    private final FieldMetadata[] fields;
    private SimpleRecordAccessor recordAccessorPrototype = null;

    public SimpleData(ArrayList<Object[]> data, FieldMetadata[] fields) {
        this.data = data;
        this.fields = fields;
    }

    @Override
    public int numRecords() {
        return data.size();
    }

    @Override
    public FieldMetadata[] columns() {
        return fields;
    }

    @Override
    public RecordAccessor getRecord(int i) {
        if (recordAccessorPrototype == null) {
            recordAccessorPrototype = new SimpleRecordAccessor(fields, data.get(i));
        } else {
            recordAccessorPrototype = recordAccessorPrototype.newWithSamePrototype(data.get(i));
        }

        return recordAccessorPrototype;
    }

    @Override
    public Iterator<RecordAccessor> iterator() {
        return new Iterator<RecordAccessor>() {

            int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < data.size();
            }

            @Override
            public RecordAccessor next() {
                return getRecord(pos++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        };
    }

}
