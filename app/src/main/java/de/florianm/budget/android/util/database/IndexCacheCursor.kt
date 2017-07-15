package de.florianm.budget.android.util.database

import android.database.Cursor
import android.database.CursorWrapper
import android.util.ArrayMap

class IndexCacheCursor(cursor: Cursor): CursorWrapper(cursor){
    val indexCache = ArrayMap<String, Int>()

    override fun getColumnIndex(columnName: String): Int {
        if(!indexCache.contains(columnName)){
            indexCache.put(columnName, super.getColumnIndex(columnName))
        }

        return super.getColumnIndex(columnName)
    }
}
