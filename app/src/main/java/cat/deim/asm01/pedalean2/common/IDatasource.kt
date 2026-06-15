package cat.deim.asm01.pedalean2.common

interface IDatasource<DataModel> {
    fun getAll(): List<DataModel>
    fun getById(uuid: String): DataModel?
    fun insert(dataModel: DataModel): Boolean
    fun update(dataModel: DataModel): Boolean
    fun delete(uuid: String): Boolean
}