package alaiz.hashim.labfragment

import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    val students = mutableListOf<Student>()

    init {
        for (i in 0 until 10) {
            val student = Student()
            student.stuName = "StudentName #$i"
            student.stuPass = i % 2 == 0
            students += student
        }
    }
}