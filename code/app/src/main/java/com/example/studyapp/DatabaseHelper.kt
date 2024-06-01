// DatabaseHelper.kt

package com.example.studyapp

import android.util.Log
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// SQLite 데이터베이스를 관리하기 위해 SQLiteOpenHelper를 확장한 DatabaseHelper 클래스
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DiaryApp.db"
        private const val TABLE_NAME = "diary_entries"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    // 테이블을 생성하는 SQL 명령문
    private val SQL_CREATE_ENTRIES = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TITLE TEXT,
            $COLUMN_CONTENT TEXT
        )
    """.trimIndent()

    // 테이블을 삭제하는 SQL 명령문
    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    // 데이터베이스가 생성될 때 호출
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    // 데이터베이스 버전이 업그레이드될 때 호출
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 기존 테이블을 삭제하고 다시 생성
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    // 특정 ID의 다이어리 항목을 가져옴
    fun getDiaryEntry(entryId: Long): DiaryEntry? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT),
            "$COLUMN_ID=?", arrayOf(entryId.toString()), null, null, null, null
        )

        return if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val titleIndex = cursor.getColumnIndex(COLUMN_TITLE)
            val contentIndex = cursor.getColumnIndex(COLUMN_CONTENT)

            if (idIndex >= 0 && titleIndex >= 0 && contentIndex >= 0) {
                // 커서에서 값 추출하고 DiaryEntry 객체 생성
                val id = cursor.getLong(idIndex)
                val title = cursor.getString(titleIndex)
                val content = cursor.getString(contentIndex)
                DiaryEntry(id, title, content)
            } else {
                // 열 인덱스가 유효하지 않은 경우 처리
                Log.e("DatabaseHelper", "하나 이상의 열 인덱스가 유효하지 않습니다.")
                null
            }
        } else {
            // 커서가 비어있는 경우 처리
            Log.e("DatabaseHelper", "커서가 비어 있습니다.")
            null
        }
    }

    // 새로운 다이어리 항목을 데이터베이스에 삽입
    fun insertDiaryEntry(title: String, text: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_CONTENT, text)
        }
        // 값 삽입 후 새로운 행의 ID 반환
        val newRowId = db.insert(TABLE_NAME, null, values)
        return newRowId
    }

    // 데이터베이스에서 모든 다이어리 항목을 가져옴
    fun getAllDiaryEntries(): List<DiaryEntry> {
        val entries = mutableListOf<DiaryEntry>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val titleIndex = cursor.getColumnIndex(COLUMN_TITLE)
            val textIndex = cursor.getColumnIndex(COLUMN_CONTENT)

            // 커서에서 값 추출하고 DiaryEntry 객체 생성
            val id = if (idIndex != -1) cursor.getLong(idIndex) else -1
            val title = if (titleIndex != -1) cursor.getString(titleIndex) else ""
            val text = if (textIndex != -1) cursor.getString(textIndex) else ""

            entries.add(DiaryEntry(id, title, text))
        }

        // 커서와 데이터베이스 닫기
        cursor.close()
        db.close()
        return entries
    }

    // 특정 ID의 다이어리 항목을 삭제
    fun deleteDiaryEntry(entryId: Long): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(entryId.toString())
        // 데이터베이스에서 항목 삭제하고 영향 받은 행 수 반환
        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
        return deletedRows
    }
}
