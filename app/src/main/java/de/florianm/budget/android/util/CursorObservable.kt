package de.florianm.budget.android.util

import android.database.Cursor
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class CursorObservable private constructor(val cursor: Cursor) : ObservableOnSubscribe<Cursor> {
    override fun subscribe(e: ObservableEmitter<Cursor>) {
        try{
            if(cursor.moveToFirst()){
                do {
                    e.onNext(cursor)
                } while(cursor.moveToNext())
            }

            e.onComplete()
        } catch (error : Throwable){
            e.onError(error)
        }
    }

    companion object{
        fun create(cursor: Cursor): Observable<Cursor> {
            return Observable.create(CursorObservable(cursor))
        }
    }
}