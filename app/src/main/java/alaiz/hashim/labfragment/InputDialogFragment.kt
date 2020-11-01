package alaiz.hashim.labfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class InputDialogFragment: DialogFragment() {
    //private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onStudentAdded(student:Student)
       // fun onStudentSelected(studentId: UUID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //val v=activity?.layoutInflater?.inflate(R.layout.new_student_dialog,null)
        val v=activity?.layoutInflater?.inflate(R.layout.new_student_dialog,null)

        val studentNumberET:EditText=v?.findViewById(R.id.newStudentnumbertv)as EditText
        val  studentNameET:EditText=v?.findViewById(R.id.newStudentNametv)as EditText
        val  studentpassCheckBox:CheckBox=v?.findViewById(R.id.passCheckBox)as CheckBox
        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(v)
            .setPositiveButton("ADD"){dialog, _ ->
                val stu=Student(UUID.randomUUID(),
                    studentNumberET.text.toString().toInt(),
                    studentNameET.text.toString(),
                    studentpassCheckBox.isChecked)
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onStudentAdded(stu)
                }
            }
            .setNegativeButton("Cancel"){dialog, _ ->
                dialog.cancel()
            }.create()


    }
    companion object {
        fun newInstance(): InputDialogFragment {

            return InputDialogFragment()
        }
    }

}