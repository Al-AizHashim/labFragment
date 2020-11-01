package alaiz.hashim.labfragment

import androidx.lifecycle.ViewModel
import java.util.*

class StudentViewModel : ViewModel() {

    val students = mutableListOf<Student>()

    init {
        for (i in 1 until 4) {
            val student = Student()
            student.stuNumber=i
            student.stuName = "StudentName #$i"
            student.stuPass = true
            students += student
        }
    }
    fun addStudent(student: Student) {
       students.add(student)
    }
    fun deleteStudent(student: Student){
        students.remove(student)
    }
}