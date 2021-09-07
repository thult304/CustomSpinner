package vn.propzy.customspinner

data class TypeItem(
    var id: Int? = null,
    var name: String? = null,
)

data class TypeGroup(
    var id: Int? = null,
    var name: String? = null,
    var children: List<TypeItem>? = null,
    var isExpended: Boolean = false
) {
}

object CreateFakeData {
    fun dataSpinner(): MutableList<TypeGroup> {
        val result = mutableListOf<TypeGroup>()
        for (i in 0..10) {

            val group = TypeGroup(id = i, name = "Group $i")

            val children = mutableListOf<TypeItem>()
            group.children = children

            for (j in 0..5){
                val item = TypeItem(id = j, name = "Item $j")
                children.add(item)
            }


            result.add(group)
        }
        return result
    }
}
