package com.uts.inventory

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity() {
    lateinit var btnAddAdmin : Button
    lateinit var etNameAdmin : EditText
    lateinit var etEmail : EditText
    lateinit var etPassword : EditText
    lateinit var rvItemsListAdmin : RecyclerView
    lateinit var tvNoRecordsAvailableAdmin : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        btnAddAdmin = findViewById(R.id.btnAddAdmin)
        etNameAdmin = findViewById(R.id.etNameAdmin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        rvItemsListAdmin = findViewById(R.id.rvItemsListAdmin)
        tvNoRecordsAvailableAdmin = findViewById(R.id.tvNoRecordsAvailableAdmin)

        btnAddAdmin.setOnClickListener { view ->

            addRecord()
        }

        setupListofDataIntoRecyclerView()
    }

    //Method for saving the employee records in database
    private fun addRecord() {
        val name = etNameAdmin.text.toString()
        val description = etEmail.text.toString()
        val price = etPassword.text.toString()
        val databaseHandler: DataHelper = DataHelper(this)
        if (!name.isEmpty() && !description.isEmpty()) {
            val status =
                databaseHandler.addAdmin(AdminModelClass(0, name, description,price))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                etNameAdmin.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()

                setupListofDataIntoRecyclerView()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Name or Description or Price cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Function is used to get the Items List which is added in the database table.
     */
    private fun getItemsList(): ArrayList<AdminModelClass> {
        //creating the instance of DatabaseHandler class
        val databaseHandler: DataHelper = DataHelper(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val empList: ArrayList<AdminModelClass> = databaseHandler.viewAdmin()

        return empList
    }

    /**
     * Function is used to show the list on UI of inserted data.
     */
    private fun setupListofDataIntoRecyclerView() {

        if (getItemsList().size > 0) {

            rvItemsListAdmin.visibility = View.VISIBLE
            tvNoRecordsAvailableAdmin.visibility = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            rvItemsListAdmin.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = ItemAdapterAdmin(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
            rvItemsListAdmin.adapter = itemAdapter
        } else {

            rvItemsListAdmin.visibility = View.GONE
            tvNoRecordsAvailableAdmin.visibility = View.VISIBLE
        }
    }

    /**
     * Method is used to show the custom update dialog.
     */
    fun updateRecordDialog(empModelClass: AdminModelClass) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dialog_update)
        val etUpdateName = updateDialog.findViewById(R.id.etUpdateName) as EditText
        val etUpdateDescription = updateDialog.findViewById(R.id.etUpdateDescription) as EditText
        val etUpdatePrice = updateDialog.findViewById(R.id.etUpdatePrice) as EditText
        val tvUpdate = updateDialog.findViewById(R.id.tvUpdate) as TextView
        val tvCancel = updateDialog.findViewById(R.id.tvCancel) as TextView

        etUpdateName.setText(empModelClass.name)
        etUpdateDescription.setText(empModelClass.email)
        etUpdatePrice.setText(empModelClass.password)

        tvUpdate.setOnClickListener(View.OnClickListener {

            val name = etUpdateName.text.toString()
            val description = etUpdateDescription.text.toString()
            val price = etUpdatePrice.text.toString()

            val databaseHandler: DataHelper = DataHelper(this)

            if (!name.isEmpty() && !description.isEmpty()) {
                val status =
                    databaseHandler.updateAdmin(AdminModelClass(empModelClass.id, name, description, price))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()

                    setupListofDataIntoRecyclerView()

                    updateDialog.dismiss() // Dialog will be dismissed
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Email cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        tvCancel.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    /**
     * Method is used to show the delete alert dialog.
     */
    fun deleteRecordAlertDialog(empModelClass: AdminModelClass) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${empModelClass.name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            //creating the instance of DatabaseHandler class
            val databaseHandler: DataHelper = DataHelper(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteAdmin(AdminModelClass(empModelClass.id, "", "", ""))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListofDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}