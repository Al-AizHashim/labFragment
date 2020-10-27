package alaiz.hashim.labfragment

import java.util.*

data class  Student (val stuid: UUID = UUID.randomUUID(),
                     var stuNumber:Int=0,
                     var stuName: String = "",
                     var stuPass: Boolean = false)