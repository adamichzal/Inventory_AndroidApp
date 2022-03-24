package com.uts.inventory

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

class DataHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_INVENTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_PRICE + " REAL" + ")")
        val CREATE_ADMIN_TABLE = ("CREATE TABLE " + TABLE_ADMIN + "("
                + KEY_ID_ADMIN + " INTEGER PRIMARY KEY," + KEY_NAME_ADMIN + " TEXT,"
                + KEY_EMAIL_ADMIN + " TEXT," + KEY_PASSWORD + " TEXT" + ")")
        db.execSQL(CREATE_CONTACTS_TABLE)
        db.execSQL(CREATE_ADMIN_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
        db.execSQL("DROP TABLE IF EXISTS InventoryTable");
        db.execSQL("DROP TABLE IF EXISTS AdminTable");
        onCreate(db);
    }

    companion object {
        private const val DATABASE_NAME = "inventoryAdam"
        private const val DATABASE_VERSION = 2

        private val TABLE_INVENTORY = "InventoryTable"
        private val KEY_ID = "_id"
        private val KEY_NAME = "name"
        private val KEY_DESCRIPTION = "description"
        private val KEY_PRICE = "price"

        private val TABLE_ADMIN = "AdminTable"
        private val KEY_ID_ADMIN = "_id"
        private val KEY_NAME_ADMIN = "name"
        private val KEY_EMAIL_ADMIN = "email"
        private val KEY_PASSWORD = "password"
    }

    /**
     * Function to insert data
     */
    fun addEmployee(inv: InvModelClass): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, inv.name) // InvModelClass Name
        contentValues.put(KEY_DESCRIPTION, inv.description) // InvModelClass Description
        contentValues.put(KEY_PRICE, inv.price) // InvModelClass Price

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_INVENTORY, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    //Method to read the records from database in form of ArrayList
    fun viewEmployee(): ArrayList<InvModelClass> {

        val empList: ArrayList<InvModelClass> = ArrayList<InvModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_INVENTORY"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var description: String
        var price: Double

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                price = cursor.getDouble(cursor.getColumnIndex(KEY_PRICE))

                val emp = InvModelClass(id = id, name = name, description = description, price = price)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    /**
     * Function to update record
     */
    fun updateEmployee(emp: InvModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.name) // EmpModelClass Name
        contentValues.put(KEY_DESCRIPTION, emp.description) // EmpModelClass Description
        contentValues.put(KEY_PRICE, emp.price) // EmpModelClass Price

        // Updating Row
        val success = db.update(TABLE_INVENTORY, contentValues, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete record
     */
    fun deleteEmployee(emp: InvModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_INVENTORY, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to insert data admin
     */
    fun addAdmin(admin: AdminModelClass): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME_ADMIN, admin.name) // InvModelClass Name
        contentValues.put(KEY_EMAIL_ADMIN, admin.email) // InvModelClass Description
        contentValues.put(KEY_PASSWORD, admin.password) // InvModelClass Price

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_ADMIN, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    //Method to read the records from database in form of ArrayList
    fun viewAdmin(): ArrayList<AdminModelClass> {

        val empList: ArrayList<AdminModelClass> = ArrayList<AdminModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_ADMIN"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String
        var password: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID_ADMIN))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME_ADMIN))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADMIN))
                password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))

                val emp = AdminModelClass(id = id, name = name, email = email, password = password)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    /**
     * Function to update record
     */
    fun updateAdmin(emp: AdminModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME_ADMIN, emp.name) // EmpModelClass Name
        contentValues.put(KEY_EMAIL_ADMIN, emp.email) // EmpModelClass Description
        contentValues.put(KEY_PASSWORD, emp.password) // EmpModelClass Price

        // Updating Row
        val success = db.update(TABLE_ADMIN, contentValues, KEY_ID_ADMIN + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete record
     */
    fun deleteAdmin(emp: AdminModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID_ADMIN, emp.id) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_ADMIN, KEY_ID_ADMIN + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
}