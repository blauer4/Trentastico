package com.geridea.trentastico.model

class PartitioningCase internal constructor(val case: String, var isVisible: Boolean) : Comparable<PartitioningCase> {

    override fun hashCode(): Int {
        return case.hashCode()
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is PartitioningCase) {
            val aCase = obj as PartitioningCase?
            return aCase!!.case == case
        }

        return false
    }

    override fun compareTo(aCase: PartitioningCase): Int {
        return this.case.compareTo(aCase.case)
    }

    fun copy(): PartitioningCase {
        return PartitioningCase(case, isVisible)
    }
}/*
 * Created with ♥ by Slava on 25/03/2017.
 */
