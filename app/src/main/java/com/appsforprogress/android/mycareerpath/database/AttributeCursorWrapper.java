package com.appsforprogress.android.mycareerpath.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.appsforprogress.android.mycareerpath.Attribute;
import com.appsforprogress.android.mycareerpath.database.AttributeDBSchema.AttributeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 2/20/2016.
 */

// Wrap custom methods around a Cursor to alter Database query records
// to cast the data palled out of the Database into Java compatible data types
public class AttributeCursorWrapper extends CursorWrapper
{

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public AttributeCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    // READ values from columns in our DB:
    public Attribute getAttributeRecord()
    {
        // Retrieve Database record and cast to Java data types
        String uuidString = getString(getColumnIndex(AttributeTable.Cols.UUID));
        String oNetCode = getString(getColumnIndex(AttributeTable.Cols.ONET_CODE));
        String elementId = getString(getColumnIndex(AttributeTable.Cols.ELEMENT_ID));
        String elementName = getString(getColumnIndex(AttributeTable.Cols.ELEMENT_NAME));
        String scaleId = getString(getColumnIndex(AttributeTable.Cols.SCALE_ID));
        String dataValue = getString(getColumnIndex(AttributeTable.Cols.DATA_VALUE));
        String dateAdded = getString(getColumnIndex(AttributeTable.Cols.DATE_ADDED));

        // Create a new Attribute of attrType and assign values retrieved from the Database for display in AttrTypeFragment
        Attribute attribute = new Attribute(UUID.fromString(uuidString));
        attribute.setONetCode(oNetCode);
        attribute.setElementId(elementId);
        attribute.setElementName(elementName);
        attribute.setScaleId(scaleId);
        attribute.setDataValue(dataValue);
        attribute.setDateAdded(dateAdded);

        return attribute;
    }
}
