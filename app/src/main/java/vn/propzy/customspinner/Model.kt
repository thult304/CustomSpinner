package vn.propzy.customspinner

data class Level(
    var id: Int? = null,
    var name: String? = null,
    var listLevel2: List<Level>? = null,
)

data class LevelJoined(
    var level1: Level? = null,
    var level2: Level? = null,
) {
    fun isParent() = level2 == null
    override fun toString(): String {
        return (level1?.id ?: level2?.id).toString()
    }
}

object CreateFakeData {
    fun dataSpinner(): MutableList<LevelJoined> {
        val result = mutableListOf<LevelJoined>()
        for (i in 0..10) {

            val level2 = Level(name = "level2")
            val level1 = Level(id = i, name = "Level1", listLevel2 = mutableListOf(level2))

            val level = if (i % 2 != 0) {
                LevelJoined(level1, level2)
            } else {
                LevelJoined(level1)
            }
            result.add(level)
        }
        return result
    }
}
